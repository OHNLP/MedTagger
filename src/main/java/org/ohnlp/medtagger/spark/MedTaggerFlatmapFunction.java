package org.ohnlp.medtagger.spark;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.Metadata;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;
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
import org.ohnlp.medtagger.ae.AhoCorasickLookupAnnotator;
import org.ohnlp.medtagger.context.RuleContextAnnotator;
import org.ohnlp.medtagger.type.ConceptMention;
import org.ohnlp.typesystem.type.textspan.Segment;
import org.ohnlp.typesystem.type.textspan.Sentence;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystemAlreadyExistsException;
import java.nio.file.FileSystems;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngineDescription;

public class MedTaggerFlatmapFunction implements FlatMapFunction<Iterator<Row>, Row> {
    private static ThreadLocal<SimpleDateFormat> sdf = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyy-MM-dd'T'HH:mm:ssXXX"));

    private transient static final ReentrantLock INIT_MUTEX_LOCK = new ReentrantLock();
    private final String conf;
    private final String conf2;

    private transient String resourceFolder;
    private transient String textField;
    private transient RunMode mode;
    private transient String noteIdField;

    // UIMA components are not serializable, and thus must be initialized per-executor via the @Setup annotation
    private transient AnalysisEngine aae;
    private transient ResourceManager resMgr;
    private transient CAS cas;
    private transient ObjectMapper om;
    private transient HashMap<String, Integer> ohdsiConceptMap;
    private transient String version;
    private transient String resources;

    public MedTaggerFlatmapFunction(String conf, String conf2) throws InvalidXMLException, IOException, ResourceInitializationException, URISyntaxException {
        this.conf = conf;
        this.conf2 = conf2;
    }

    private void initMedTaggerConf(String conf) throws InvalidXMLException, IOException, ResourceInitializationException, URISyntaxException {
        JsonNode config = new ObjectMapper().readTree(conf);
        this.textField = config.get("input").asText();
        this.mode = config.has("mode") ? RunMode.valueOf(config.get("mode").textValue().toUpperCase(Locale.ROOT)) : RunMode.STANDALONE;
        this.resourceFolder = config.get("ruleset").asText();
        this.noteIdField = config.has("identifier_col") ? config.get("identifier_col").asText() : "note_id";

        try {
            INIT_MUTEX_LOCK.lock();
            AggregateBuilder ae = new AggregateBuilder();
            // Tokenization, Sentence Splitting, Section Detection, etc.
            ae.add(createEngineDescription("desc.backbone.aes.PreConceptExtractionAE"));
            // Add the appropriate NER/normalization component depending on run mode
            URI uri = null;
            switch (mode) {
                case OHNLPTK_DEFINED: // Ruleset from a web service
                    throw new UnsupportedOperationException("Remote Served IE Rulesets not yet implemented");
                case STANDALONE:
                case STANDALONE_IE_ONLY: {
                    uri = MedTaggerFlatmapFunction.class.getResource("/medtaggerieresources/" + this.resourceFolder).toURI();
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
                    uri = MedTaggerFlatmapFunction.class.getResource("/medtaggerieresources/" + this.resourceFolder).toURI();
                    Map<String, String> env = new HashMap<>();
                    env.put("create", "true");
                    try {
                        // Ensure it is created, ignore if not
                        FileSystem fs = FileSystems.newFileSystem(uri, env);
                    } catch (FileSystemAlreadyExistsException ignored) {
                    }
                    ae.add(createEngineDescription(AhoCorasickLookupAnnotator.class, "dict_file", uri.toString()));
                    break;
                }
                case STANDALONE_DICT_AND_IE: {
                    String[] parsed = this.resourceFolder.split("\\|");
                    uri = MedTaggerFlatmapFunction.class.getResource("/medtaggerieresources/" + parsed[0]).toURI();
                    URI dictURI = null;
                    if (parsed.length > 1) {
                        dictURI = MedTaggerFlatmapFunction.class.getResource("/medtaggerieresources/" + parsed[1]).toURI();
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
            if (uri != null) {
                ae.add(AnalysisEngineFactory.createEngineDescription(RuleContextAnnotator.class, "context_ruleset", uri.toString()));
            } else {
                ae.add(AnalysisEngineFactory.createEngineDescription(RuleContextAnnotator.class));
            }

            this.resMgr = ResourceManagerFactory.newResourceManager();
            this.aae = UIMAFramework.produceAnalysisEngine(ae.createAggregateDescription(), resMgr, null);
            this.cas = CasCreationUtils.createCas(Collections.singletonList(aae.getMetaData()),
                    null, resMgr);
        } finally {
            INIT_MUTEX_LOCK.unlock();
        }
    }

    private void initOHDSIMapperConf(String conf) throws UnsupportedEncodingException, JsonProcessingException {
        this.om = new ObjectMapper();
        JsonNode config = om.readTree(conf);
        this.resources = config.get("ruleset").asText();
        this.version = new BufferedReader(
                new InputStreamReader(
                        Objects.requireNonNull(MedTaggerFlatmapFunction.class
                                .getResourceAsStream("/medtagger-version.txt")),
                        "UTF-8"))
                .lines().collect(Collectors.joining(" ")).trim();
        this.ohdsiConceptMap = new HashMap<>();
        switch (resources.toUpperCase(Locale.ROOT)) {
            case "NONE": {
                break;
            }
            default: {
                try (InputStream resource = MedTaggerFlatmapFunction.class.getResourceAsStream("/medtaggerieresources/" + resources + "/ohdsi_mappings.txt")) {
                    List<String> mappings =
                            new BufferedReader(new InputStreamReader(resource, "UTF-8")).lines().collect(Collectors.toList());
                    mappings.forEach(s -> {
                        String[] args = s.trim().split("\\|");
                        ohdsiConceptMap.put(args[0], Integer.parseInt(args[1]));
                    });
                } catch (Throwable e) {
                    throw new RuntimeException(e);
                }
                break;
            }
        }
    }

    @Override
    public Iterator<Row> call(Iterator<Row> rows) throws Exception {
        if (this.cas == null) {
            initMedTaggerConf(conf);
            initOHDSIMapperConf(conf2);
        }
        Iterable<Row> inputIterable = () -> rows;

        Stream<Row> stream = StreamSupport.stream(inputIterable.spliterator(), false).flatMap(nextDoc -> {
            List<Row> accumulated = new LinkedList<>();
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
            Object id = nextDoc.get(nextDoc.fieldIndex(this.noteIdField));
            cas.setDocumentText(nextDoc.getString(nextDoc.fieldIndex(this.textField)));
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
            } catch (InterruptedException | ExecutionException | TimeoutException e) {
                System.out.println("Skipping document " + id + " due to NLP run taking longer than 30 seconds");
                future.cancel(true);
                nlpExecutor.shutdownNow();
                return Stream.empty();
            } catch (Throwable t) {
                System.out.println("Skipping document " + id + " due to error. Note that this may be expected if NLP run " +
                        "takes longer than a certain amount of time and it short circuits");
                t.printStackTrace();
                future.cancel(true);
                nlpExecutor.shutdownNow();
                return Stream.empty();
            }
            try {
                JCas jcas = casRef.getJCas();
                Map<ConceptMention, Collection<Sentence>> sentenceIdx = JCasUtil.indexCovering(jcas, ConceptMention.class, Sentence.class);
                Map<ConceptMention, Collection<Segment>> sectionIdx = JCasUtil.indexCovering(jcas, ConceptMention.class, Segment.class);
                int runs = 0;
                for (ConceptMention cm : JCasUtil.select(jcas, ConceptMention.class)) {
                    runs++;
                    JsonNode jsonRep = toJSON(cm, sentenceIdx, sectionIdx); // inefficient, but done this way for ease of maintenance with main branch
                    accumulated.add(toOHDSIRow(id, jsonRep));
                }
                System.out.println("Found " + runs + " NLP Artifacts in Document " + id);
            } catch (CASException e) {
                throw new RuntimeException(e);
            }
            return accumulated.stream();
        });
        return stream.iterator();
    }


    private enum RunMode {
        OHNLPTK_DEFINED, // Retrieve from web-based middleware server
        STANDALONE, // Embedded IE Ruleset (Legacy)
        STANDALONE_IE_ONLY,
        STANDALONE_DICT_ONLY,
        STANDALONE_DICT_AND_IE,
        GENERAL_CLINICAL // General Purpose SCT dictionary
    }

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

    private Row toOHDSIRow(Object noteId, JsonNode rawValues) {
        int outConcept = -1;
        switch (resources.toUpperCase(Locale.ROOT)) {
            case "NONE": {
                try {
                    outConcept = Integer.parseInt(rawValues.get("concept_code").asText("0"));
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("OHDSI requires integer concept codes, value "
                            + rawValues.get("concept_code").asText() + " was instead provided with mapping ruleset 'NONE'");
                }
                break;
            }
            case "UMLS": {
                String conceptCode = rawValues.get("concept_code").asText();
                // Only take first portion as CUI, remainder is top freq lexeme in current dict format.
                String cui = conceptCode.contains(":") ? conceptCode.split(":")[0].toUpperCase(Locale.ROOT)
                        : conceptCode.toUpperCase(Locale.ROOT);
                outConcept = ohdsiConceptMap.getOrDefault(cui, -99999);
            }
            default: {
                outConcept = ohdsiConceptMap.getOrDefault(rawValues.get("concept_code").asText(), 0);
            }
        }
        Timestamp nlpDtm = new Timestamp(System.currentTimeMillis());
        String termExists = rawValues.get("experiencer").asText().equals("Patient")
                && rawValues.get("certainty").asText().equals("Positive") ? "Y" : "N";
        String termTemporal = rawValues.get("status").asText().contains("HistoryOf") ? "Historical" : "Present";
        return RowFactory.create(
                noteId + "",
                rawValues.get("section_id").asInt(),
                rawValues.get("matched_sentence").asText(),
                rawValues.get("offset").asInt(),
                rawValues.get("matched_text").asText(),
                outConcept,
                "",
                version.trim(),
                new Date(nlpDtm.getTime()),
                nlpDtm,
                termExists,
                termTemporal,
                String.format("Subject=%2$s;Certainty=%1$s;Status=%3$s",
                        rawValues.get("certainty").asText(),
                        rawValues.get("experiencer").asText(),
                        rawValues.get("status").asText()
                )
        );
    }

    public static final StructType TYPE = new StructType(
            new StructField[] {
                    new StructField("note_id", DataTypes.StringType, false, Metadata.empty()),
                    new StructField("section_concept_id", DataTypes.IntegerType, false, Metadata.empty()),
                    new StructField("snippet", DataTypes.StringType, false, Metadata.empty()),
                    new StructField("offset", DataTypes.IntegerType, false, Metadata.empty()),
                    new StructField("lexical_variant", DataTypes.StringType, false, Metadata.empty()),
                    new StructField("note_nlp_concept_id", DataTypes.IntegerType, false, Metadata.empty()),
                    new StructField("note_nlp_source_concept_id", DataTypes.StringType, false, Metadata.empty()),
                    new StructField("nlp_system", DataTypes.StringType, false, Metadata.empty()),
                    new StructField("nlp_date", DataTypes.DateType, false, Metadata.empty()),
                    new StructField("nlp_datetime", DataTypes.TimestampType, false, Metadata.empty()),
                    new StructField("term_exists", DataTypes.StringType, false, Metadata.empty()),
                    new StructField("term_temporal", DataTypes.StringType, false, Metadata.empty()),
                    new StructField("term_modifiers", DataTypes.StringType, false, Metadata.empty())
            }
    );
}
