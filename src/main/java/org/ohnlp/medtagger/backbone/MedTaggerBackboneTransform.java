package org.ohnlp.medtagger.backbone;

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
import org.apache.uima.fit.factory.ExternalResourceFactory;
import org.apache.uima.fit.internal.ResourceManagerFactory;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.resource.ResourceManager;
import org.apache.uima.util.CasCreationUtils;
import org.apache.uima.util.InvalidXMLException;
import org.joda.time.Instant;
import org.ohnlp.backbone.api.annotations.ComponentDescription;
import org.ohnlp.backbone.api.annotations.ConfigurationProperty;
import org.ohnlp.backbone.api.components.OneToOneTransform;
import org.ohnlp.backbone.api.config.InputColumn;
import org.ohnlp.backbone.api.exceptions.ComponentInitializationException;
import org.ohnlp.medtagger.context.RuleContextAnnotator;
import org.ohnlp.medtagger.type.ConceptMention;
import org.ohnlp.typesystem.type.textspan.Segment;
import org.ohnlp.typesystem.type.textspan.Sentence;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngineDescription;

/**
 * An implementation of a MedTagger pipeline as an OHNLP Backbone Transform component
 */
@ComponentDescription(
        name = "MedTagger NLP",
        desc = "Executes a MedTagger IE or Dictionary-Based Ruleset as a Backbone Component"
)
public class MedTaggerBackboneTransform extends OneToOneTransform {

    private static final InputColumn NOTE_ID_COLUMN_DEF;

    @ConfigurationProperty(
            path = "input",
            desc = "Column to use as input"
    )
    private InputColumn inputField;

    @ConfigurationProperty(
            path = "mode",
            desc = "Whether to use IE or dictionary-based rulesets, or both, or retrieve from web. Defaults to STANDALONE_IE_ONLY",
            required = false
    )
    private RunMode mode = RunMode.STANDALONE_IE_ONLY;
    @ConfigurationProperty(
            path = "ruleset",
            desc = "The ruleset definition as located within the resources folder, or the dictionary name, " +
                    "or resources folder|dictionary name if both, or URL if in web mode"
    )
    private String resources;
    @ConfigurationProperty(
            path = "identifier_col",
            desc = "The column to use as a note identifier. Defaults to note_id",
            required = false
    )
    private InputColumn noteIdField = NOTE_ID_COLUMN_DEF;
    @ConfigurationProperty(
            path = "ruleset_context",
            desc = "Path to context definition file (contextRule.txt) relative to the resources folder, or \"DEFAULT\" " +
                    "to use Ruleset-Supplied if present, otherwise Global Defaults. " ,
            required = false
    )
    private String customContext = "DEFAULT";
    @ConfigurationProperty(
            path = "sectag",
            desc = "Path to section tagging definition relative to the resources folder. " +
                    "Can also use \"DEFAULT\" for SecTag defaults" ,
            required = false
    )
    private String secTag = "DEFAULT";
    private Schema outputSchema;
    private boolean outputJSON;

    static {
        NOTE_ID_COLUMN_DEF = new InputColumn();
        NOTE_ID_COLUMN_DEF.setSourceColumnName("note_id");
        NOTE_ID_COLUMN_DEF.setSourceTag("*");
    }

    @Override
    public void init() throws ComponentInitializationException {
    }

    @Override
    public Schema getRequiredColumns(String inputTag) {
        return Schema.of(
                Schema.Field.of(this.noteIdField.getSourceColumnName(), Schema.FieldType.STRING),
                Schema.Field.of(this.inputField.getSourceColumnName(), Schema.FieldType.STRING)
        );
    }

    @Override
    public Schema calculateOutputSchema(Schema schema) {
        List<Schema.Field> fields = new ArrayList<>(schema.getFields());
        fields.add(Schema.Field.of("matched_text", Schema.FieldType.STRING));
        fields.add(Schema.Field.of("concept_code", Schema.FieldType.STRING));
        fields.add(Schema.Field.of("matched_sentence", Schema.FieldType.STRING));
        fields.add(Schema.Field.of("section_id", Schema.FieldType.INT32));
        fields.add(Schema.Field.of("nlp_run_dtm", Schema.FieldType.DATETIME));
        fields.add(Schema.Field.of("certainty", Schema.FieldType.STRING));
        fields.add(Schema.Field.of("experiencer", Schema.FieldType.STRING));
        fields.add(Schema.Field.of("status", Schema.FieldType.STRING));
        fields.add(Schema.Field.of("offset", Schema.FieldType.INT32));
        fields.add(Schema.Field.of("offset_end", Schema.FieldType.INT32));
        fields.add(Schema.Field.of("sent_offset", Schema.FieldType.INT32));
        fields.add(Schema.Field.of("semgroups", Schema.FieldType.STRING).withNullable(true));
        fields.add(Schema.Field.of("sentid", Schema.FieldType.STRING).withNullable(true));
        this.outputSchema = Schema.of(fields.toArray(new Schema.Field[0]));
        return this.outputSchema;
    }

    @Override
    public PCollection<Row> expand(PCollection<Row> input) {
        return input.apply("MedTagger Concept Extraction",
                ParDo.of(new MedTaggerPipelineFunction(this.inputField.getSourceColumnName(), this.resources, this.mode, this.noteIdField.getSourceColumnName(), this.customContext, this.secTag, this.outputSchema)));
    }

    private static class MedTaggerPipelineFunction extends DoFn<Row, Row> {
//        private transient static final ReentrantLock INIT_MUTEX_LOCK = new ReentrantLock();

        private final String resourceFolder;
        private final String textField;
        private final RunMode mode;
        private final String noteIdField;
        private final Schema outputSchema;
        private final String context;
        private final String secTag;

        // UIMA components are not serializable, and thus must be initialized per-executor via the @Setup annotation
        private transient AnalysisEngine aae;
        private transient ResourceManager resMgr;
        private transient CAS cas;

        public MedTaggerPipelineFunction(String textField, String resourceFolder, RunMode mode, String noteIdField, String context, String secTag, Schema outputSchema) {
            this.textField = textField;
            this.resourceFolder = resourceFolder;
            this.mode = mode;
            this.noteIdField = noteIdField;
            this.context = context;
            this.secTag = secTag;
            this.outputSchema = outputSchema;
        }

        @Setup
        public void init() throws IOException, InvalidXMLException, URISyntaxException, ResourceInitializationException {
            try {
                AggregateBuilder ae = new AggregateBuilder();
                // Tokenization, Sentence Splitting, Section Detection, etc.
                if (this.secTag.equalsIgnoreCase("DEFAULT")) {
                    ae.add(createEngineDescription("desc.backbone.aes.PreConceptExtractionAE"));
                } else {
                    URI secTag = MedTaggerPipelineFunction.class.getResource("/resources/" + this.secTag).toURI();
                    Map<String, String> env = new HashMap<>();
                    env.put("create", "true");
                    try {
                        // Ensure it is created, ignore if not
                        FileSystem fs = FileSystems.newFileSystem(secTag, env);
                    } catch (FileSystemAlreadyExistsException ignored) {
                    }
                    ae.add(createEngineDescription("desc.backbone.aes.PreConceptExtractionAE",
                            "section_map",
                            secTag.toString()
                            ));
                }
                // Add the appropriate NER/normalization component depending on run mode
                URI uri = null;
                switch (mode) {
                    case OHNLPTK_DEFINED: // Ruleset from a web service
                        throw new UnsupportedOperationException("Remote Served IE Rulesets not yet implemented");
                    case STANDALONE:
                    case STANDALONE_IE_ONLY: {
                        uri = MedTaggerPipelineFunction.class.getResource("/resources/" + this.resourceFolder).toURI();
                        Map<String, String> env = new HashMap<>();
                        env.put("create", "true");
                        try {
                            // Ensure it is created, ignore if not
                            FileSystem fs = FileSystems.newFileSystem(uri, env);
                        } catch (FileSystemAlreadyExistsException ignored) {
                        }
                        ae.add(createEngineDescription("org.ohnlp.medtagger.ie.aes.MedTaggerIEAnnotatorAE", "Resource_dir", uri.toString()));
                        break;
                    }
                    case STANDALONE_DICT_ONLY: {
                        URI localUI = MedTaggerPipelineFunction.class.getResource("/resources/" + this.resourceFolder).toURI();
                        Map<String, String> env = new HashMap<>();
                        env.put("create", "true");
                        try {
                            // Ensure it is created, ignore if not
                            FileSystem fs = FileSystems.newFileSystem(localUI, env);
                        } catch (FileSystemAlreadyExistsException ignored) {
                        }
                        ae.add(createEngineDescription("desc.backbone.aes.MedTaggerDictionaryLookupAE", "dict_file", localUI.toString()));
                        break;
                    }
                    case STANDALONE_DICT_AND_IE: {
                        String[] parsed = this.resourceFolder.split("\\|");
                        uri = MedTaggerPipelineFunction.class.getResource("/resources/" + parsed[0]).toURI();
                        URI dictURI = null;
                        if (parsed.length > 1) {
                            dictURI = MedTaggerPipelineFunction.class.getResource("/resources/" + parsed[1]).toURI();
                        }
                        Map<String, String> env = new HashMap<>();
                        env.put("create", "true");
                        try {
                            // Ensure it is created, ignore if not
                            FileSystem fs = FileSystems.newFileSystem(uri, env);
                        } catch (FileSystemAlreadyExistsException ignored) {
                        }
                        ae.add(createEngineDescription("org.ohnlp.medtagger.ie.aes.MedTaggerIEAnnotatorAE", "Resource_dir", uri.toString()));
                        if (dictURI != null) {
                            ae.add(createEngineDescription("desc.backbone.aes.MedTaggerDictionaryLookupAE", "dict_file", dictURI.toString()));
                        } else {
                            ae.add(createEngineDescription("desc.backbone.aes.MedTaggerDictionaryLookupAE"));
                        }
                        break;
                    }
                    case GENERAL_CLINICAL:
                        ae.add(createEngineDescription("desc.backbone.aes.MedTaggerDictionaryLookupAE"));
                        break;
                }

                // Add Context handling
                if (uri != null && !mode.equals(RunMode.STANDALONE_DICT_ONLY) && !mode.equals(RunMode.GENERAL_CLINICAL)) {
                    if (this.context.equalsIgnoreCase("DEFAULT")) {
                        if (Files.exists(Paths.get(uri).resolve("context").resolve("contextRule.txt"))) {
                            ae.add(AnalysisEngineFactory.createEngineDescription(RuleContextAnnotator.class, "context_ruleset", uri.toString()));
                        } else {
                            ae.add(AnalysisEngineFactory.createEngineDescription(RuleContextAnnotator.class));
                        }
                    } else {
                        URI contextURI = MedTaggerPipelineFunction.class.getResource("/resources/" + context).toURI();
                        Map<String, String> env = new HashMap<>();
                        env.put("create", "true");
                        try {
                            // Ensure it is created, ignore if not
                            FileSystem fs = FileSystems.newFileSystem(contextURI, env);
                        } catch (FileSystemAlreadyExistsException ignored) {
                        }
                        ae.add(AnalysisEngineFactory.createEngineDescription(RuleContextAnnotator.class, "context_ruleset", contextURI.toString()));
                    }
                } else {
                    if (mode.equals(RunMode.STANDALONE_DICT_ONLY) || mode.equals(RunMode.GENERAL_CLINICAL)) {
                        if (this.context.equalsIgnoreCase("DEFAULT")) {
                            ae.add(AnalysisEngineFactory.createEngineDescription(RuleContextAnnotator.class));
                        } else {
                            URI contextURI = MedTaggerPipelineFunction.class.getResource("/resources/" + context).toURI();
                            Map<String, String> env = new HashMap<>();
                            env.put("create", "true");
                            try {
                                // Ensure it is created, ignore if not
                                FileSystem fs = FileSystems.newFileSystem(contextURI, env);
                            } catch (FileSystemAlreadyExistsException ignored) {
                            }
                            ae.add(AnalysisEngineFactory.createEngineDescription(RuleContextAnnotator.class, "context_ruleset", contextURI.toString()));
                        }
                    } else {
                        ae.add(AnalysisEngineFactory.createEngineDescription(RuleContextAnnotator.class));
                    }
                }

                this.resMgr = ResourceManagerFactory.newResourceManager();
                this.aae = UIMAFramework.produceAnalysisEngine(ae.createAggregateDescription(), resMgr, null);
                this.cas = CasCreationUtils.createCas(Collections.singletonList(aae.getMetaData()),
                        null, resMgr);
            } finally {
            }

        }

        @ProcessElement
        public void processElement(@Element Row input, OutputReceiver<Row> output) {
            Object id = input.getBaseValue(this.noteIdField);
            String text = input.getString(this.textField);
            try {
                cas.reset();
            } catch (Throwable t) {
                // CAS reset failed, just create a whole new one
                try {
                    this.cas = CasCreationUtils.createCas(Collections.singletonList(aae.getMetaData()),
                            null, resMgr);
                } catch (ResourceInitializationException e) {
                    throw new RuntimeException(e);
                }
            }
            cas.setDocumentText(text);
            final CAS casRef = cas; // a final reference for cross-ref access
            System.out.println("Running NLP on " + id);
            // Run NLP in a separate thread so that we can interrupt it if it takes too long
            // We create a new executor service instead of sharing it in case interrupt fails
            ExecutorService nlpExecutor = Executors.newSingleThreadExecutor();
            FutureTask<Throwable> future = new FutureTask<>(() -> {
                try {
                    aae.process(casRef);
                    return null;
                } catch (AnalysisEngineProcessException e) {
                    return e;
                }
            });
            nlpExecutor.submit(future);
            try {
                Throwable t = future.get(25000, TimeUnit.MILLISECONDS);
                if (t != null) {
                    throw new RuntimeException(t);
                }
                nlpExecutor.shutdownNow();
            } catch (InterruptedException | ExecutionException | TimeoutException e) {
                System.out.println("Skipping document " + id + " due to NLP run taking longer than 30 seconds");
                future.cancel(true);
                nlpExecutor.shutdownNow();
                return;
            } catch (Throwable t) {
                System.out.println("Skipping document " + id + " due to error. Note that this may be expected if NLP run " +
                        "takes longer than a certain amount of time and it short circuits");
                t.printStackTrace();
                future.cancel(true);
                nlpExecutor.shutdownNow();
                return;
            }
            try {
                JCas jcas = casRef.getJCas();
                Map<ConceptMention, Collection<Sentence>> sentenceIdx = JCasUtil.indexCovering(jcas, ConceptMention.class, Sentence.class);
                Map<ConceptMention, Collection<Segment>> sectionIdx = JCasUtil.indexCovering(jcas, ConceptMention.class, Segment.class);
                int runs = 0;
                for (ConceptMention cm : JCasUtil.select(jcas, ConceptMention.class)) {
                    runs++;
                    List<Object> values = toRowObjects(cm, sentenceIdx, sectionIdx);
                    Row out = Row.withSchema(outputSchema).addValues(input.getValues()).addValues(values).build();
                    output.output(out);
                }
                System.out.println("Found " + runs + " NLP Artifacts in Document " + id);
            } catch (CASException e) {
                throw new RuntimeException(e);
            }
        }

        private static ThreadLocal<SimpleDateFormat> sdf = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyy-MM-dd'T'HH:mm:ssXXX"));

        /*
         * Utility method that converts a concept mention to a list of Objects conforming with the Schema
         */

        private static List<Object> toRowObjects(
                ConceptMention cm,
                Map<ConceptMention, Collection<Sentence>> coveringSentenceMap,
                Map<ConceptMention, Collection<Segment>> coveringSectionsMap
        ) {
            List<Object> ret = new ArrayList<>();
            ret.add(cm.getCoveredText());
            ret.add(cm.getNormTarget());
            ret.add(coveringSentenceMap.get(cm)
                    .stream()
                    .map(Annotation::getCoveredText)
                    .collect(Collectors.joining(" ")));
            ret.add(coveringSectionsMap.get(cm)
                    .stream()
                    .map(s -> {
                        try {
                            return Integer.parseInt(s.getId().split(":")[0]);
                        } catch (Throwable t) {
                            return -1;
                        }
                    })
                    .findFirst().orElse(0));
            ret.add(new Instant(new Date()));
            ret.add(cm.getCertainty());
            ret.add(cm.getExperiencer());
            ret.add(cm.getStatus());
            ret.add(cm.getBegin());
            ret.add(cm.getEnd());
            ret.add(cm.getSentence().getBegin());
            ret.add(cm.getSemGroup());
            ret.add(cm.getSentence().getId());
            return ret;
        }
    }

    private enum RunMode {
        OHNLPTK_DEFINED, // Retrieve from web-based middleware server
        STANDALONE, // Embedded IE Ruleset (Legacy)
        STANDALONE_IE_ONLY,
        STANDALONE_DICT_ONLY,
        STANDALONE_DICT_AND_IE,
        GENERAL_CLINICAL // General Purpose SCT dictionary
    }
}
