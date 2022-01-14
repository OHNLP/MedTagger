package org.ohnlp.medtagger.backbone;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.beam.sdk.schemas.Schema;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.values.PCollection;
import org.apache.beam.sdk.values.Row;
import org.apache.uima.UIMAFramework;
import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.CAS;
import org.apache.uima.cas.CASException;
import org.apache.uima.fit.factory.AggregateBuilder;
import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.fit.internal.ResourceManagerFactory;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.resource.ResourceManager;
import org.apache.uima.util.CasCreationUtils;
import org.apache.uima.util.InvalidXMLException;
import org.ohnlp.backbone.api.Transform;
import org.ohnlp.backbone.api.exceptions.ComponentInitializationException;
import org.ohnlp.medtagger.context.RuleContextAnnotator;
import org.ohnlp.medtagger.ie.ae.MedTaggerIEAnnotator;
import org.ohnlp.medtagger.type.ConceptMention;
import org.ohnlp.typesystem.type.textspan.Segment;
import org.ohnlp.typesystem.type.textspan.Sentence;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystemAlreadyExistsException;
import java.nio.file.FileSystems;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngineDescription;

/**
 * An implementation of a MedTagger pipeline as an OHNLP Backbone Transform component
 */
public class MedTaggerBackboneTransform extends Transform {

    private String inputField;
    private String resources;
    private RunMode mode;

    @Override
    public void initFromConfig(JsonNode config) throws ComponentInitializationException {
        try {
            this.inputField = config.get("input").asText();
            this.mode = config.has("mode") ? RunMode.valueOf(config.get("mode").textValue().toUpperCase(Locale.ROOT)) : RunMode.STANDALONE;
            this.resources = config.get("ruleset").asText();
        } catch (Throwable t) {
            throw new ComponentInitializationException(t);
        }
    }

    @Override
    public PCollection<Row> expand(PCollection<Row> input) {
        return input.apply("MedTagger Concept Extraction",
                ParDo.of(new MedTaggerPipelineFunction(this.inputField, this.resources, this.mode)));
    }

    private static class MedTaggerPipelineFunction extends DoFn<Row, Row> {
        private final String resourceFolder;
        private final String textField;
        private final RunMode mode;

        // UIMA components are not serializable, and thus must be initialized per-executor via the @Setup annotation
        private transient AnalysisEngine aae;
        private transient ResourceManager resMgr;
        private transient CAS cas;

        public MedTaggerPipelineFunction(String textField, String resourceFolder, RunMode mode) {
            this.textField = textField;
            this.resourceFolder = resourceFolder;
            this.mode = mode;
        }

        @Setup
        public void init() throws IOException, InvalidXMLException, URISyntaxException, ResourceInitializationException {
            AggregateBuilder ae = new AggregateBuilder();
            // Tokenization, Sentence Splitting, Section Detection, etc.
            ae.add(createEngineDescription("desc.backbone.aes.PreConceptExtractionAE"));
            // Add the appropriate NER/normalization component depending on run mode
            switch (mode) {
                case OHNLPTK_DEFINED: // Ruleset from a web service
                    throw new UnsupportedOperationException("Remote Served IE Rulesets not yet implemented");
                case STANDALONE:
                    final URI uri = MedTaggerPipelineFunction.class.getResource("/resources/" + this.resourceFolder).toURI();
                    Map<String, String> env = new HashMap<>();
                    env.put("create", "true");
                    try {
                        // Ensure it is created, ignore if not
                        FileSystem fs = FileSystems.newFileSystem(uri, env);
                    } catch (FileSystemAlreadyExistsException ignored) {
                    }
                    ae.add(createEngineDescription("org.ohnlp.medtagger.ie.aes.MedTaggerIEAnnotatorAE", "Resource_dir", uri.toString()));
                    break;
                case GENERAL_CLINICAL:
                    ae.add(createEngineDescription("desc.backbone.aes.MedTaggerDictionaryLookupAE"));
                    break;
            }

            // Add Context handling
            ae.add(AnalysisEngineFactory.createEngineDescription(RuleContextAnnotator.class));

            this.resMgr = ResourceManagerFactory.newResourceManager();
            this.aae = UIMAFramework.produceAnalysisEngine(ae.createAggregateDescription(), resMgr, null);
            this.cas = CasCreationUtils.createCas(Collections.singletonList(aae.getMetaData()),
                    null, resMgr);
        }

        @ProcessElement
        public void processElement(@Element Row input, OutputReceiver<Row> output) {
            // First create the output row schema
            List<Schema.Field> fields = new LinkedList<>(input.getSchema().getFields());
            fields.add(Schema.Field.of("nlp_output_json", Schema.FieldType.STRING));
            Schema schema = Schema.of(fields.toArray(new Schema.Field[0]));
            String id = input.getString("note_id");
            String text = input.getString(this.textField);
            cas.reset();
            cas.setDocumentText(text);
            try {
                System.out.println("Running NLP on " + id);
                aae.process(cas);
                JCas jcas = cas.getJCas();
                Map<ConceptMention, Collection<Sentence>> sentenceIdx = JCasUtil.indexCovering(jcas, ConceptMention.class, Sentence.class);
                Map<ConceptMention, Collection<Segment>> sectionIdx = JCasUtil.indexCovering(jcas, ConceptMention.class, Segment.class);
                int runs = 0;
                for (ConceptMention cm : JCasUtil.select(jcas, ConceptMention.class)) {
                    runs++;
                    JsonNode json = toJSON(cm, sentenceIdx, sectionIdx);
                    Row out = Row.withSchema(schema).addValues(input.getValues()).addValue(json.toString()).build();
                    output.output(out);
                }
                System.out.println("Found " + runs + " NLP Artifacts in Document "  + id);
            } catch (AnalysisEngineProcessException | CASException e) {
                e.printStackTrace();
            }
        }

        private static ThreadLocal<SimpleDateFormat> sdf = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyy-MM-dd'T'HH:mm:ssXXX"));

        /*
         * Utility method that converts a concept mention to a JSON
         */
        private static JsonNode toJSON(
                ConceptMention cm,
                Map<ConceptMention, Collection<Sentence>> coveringSentenceMap,
                Map<ConceptMention, Collection<Segment>> coveringSectionsMap
        ) {
            ObjectNode ret = JsonNodeFactory.instance.objectNode();
            ret.put("matched_text", cm.getCoveredText());
            ret.put("concept_code", cm.getNormTarget());
            ret.put(
                    "matched_sentence",
                    coveringSentenceMap.get(cm)
                            .stream()
                            .map(Annotation::getCoveredText)
                            .collect(Collectors.joining(" ")));
            ret.put(
                    "section_id",
                  coveringSectionsMap.get(cm)
                            .stream()
                            .map(s -> {
                                try {
                                    return Integer.parseInt(s.getId());
                                } catch (Throwable t) {
                                    return -1;
                                }
                            })
                            .findFirst().orElse(0));
            ret.put("nlp_run_dtm", sdf.get().format(new Date()));
            ret.put("certainty", cm.getCertainty());
            ret.put("experiencer", cm.getExperiencer());
            ret.put("status", cm.getStatus());
            ret.put("offset", cm.getBegin());
            ret.put("semgroups", cm.getSemGroup());
            return ret;
        }
    }

    private enum RunMode {
        OHNLPTK_DEFINED, // Retrieve from web-based middleware server
        STANDALONE, // Embedded IE Ruleset
        GENERAL_CLINICAL // General Purpose SCT dictionary
    }
}
