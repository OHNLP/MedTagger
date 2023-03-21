package org.ohnlp.medtagger.backbone;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.beam.sdk.schemas.Schema;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.values.PCollection;
import org.apache.beam.sdk.values.Row;
import org.joda.time.Instant;
import org.ohnlp.backbone.api.Transform;
import org.ohnlp.backbone.api.annotations.ComponentDescription;
import org.ohnlp.backbone.api.annotations.ConfigurationProperty;
import org.ohnlp.backbone.api.components.OneToOneTransform;
import org.ohnlp.backbone.api.exceptions.ComponentInitializationException;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Transforms MedTagger output as produced from {@link MedTaggerBackboneTransform} to an OHDSI-compliant format suitable
 * for the NOTE_NLP table.
 * <p>
 * Note: Assumes that rulesets supply a concept normalization -> OHDSI concept id mapping in ohdsi_mappings.txt
 */
@ComponentDescription(
        name = "MedTagger to OHDSI CDM Format Transform",
        desc = "Converts MedTagger Output into one complaint with the OHDSI OMOP CDM note_nlp table",
        requires = {"org.ohnlp.medtagger.backbone.MedTaggerBackboneTransform"}
)
public class MedTaggerOutputToOHDSIFormatTransform extends OneToOneTransform {
    private static ThreadLocal<SimpleDateFormat> sdf = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyy-MM-dd'T'HH:mm:ssXXX"));

    @ConfigurationProperty(
            path = "ruleset",
            desc = "The ruleset folder containing a ohdsi_mappings.txt to map output concepts to OHDSI Athena concept codes, or NONE if output already in Athena concept code format"
    )
    private String resources;
    private Schema schema;

    @Override
    public Schema calculateOutputSchema(Schema schema) {
        List<Schema.Field> fields = new LinkedList<>(schema.getFields());
        fields.removeIf(f -> f.getName().equalsIgnoreCase("offset"));
        fields.add(Schema.Field.of("section_concept_id", Schema.FieldType.INT32));
        fields.add(Schema.Field.of("lexical_variant", Schema.FieldType.STRING));
        fields.add(Schema.Field.of("snippet", Schema.FieldType.STRING));
        fields.add(Schema.Field.of("note_nlp_concept_id", Schema.FieldType.INT32));
        fields.add(Schema.Field.of("note_nlp_source_concept_id", Schema.FieldType.INT32));
        fields.add(Schema.Field.of("nlp_datetime", Schema.FieldType.DATETIME));
        fields.add(Schema.Field.of("term_modifiers", Schema.FieldType.STRING));
        fields.add(Schema.Field.of("offset", Schema.FieldType.INT32));
        fields.add(Schema.Field.of("nlp_system", Schema.FieldType.STRING));
        this.schema = Schema.of(fields.toArray(new Schema.Field[0]));
        return this.schema;
    }

    @Override
    public Schema getRequiredColumns(String inputTag) {
        return Schema.of(
                Schema.Field.of("nlp_run_dtm", Schema.FieldType.DATETIME),
                Schema.Field.of("certainty", Schema.FieldType.STRING),
                Schema.Field.of("experiencer", Schema.FieldType.STRING),
                Schema.Field.of("status", Schema.FieldType.STRING),
                Schema.Field.of("offset", Schema.FieldType.INT32),
                Schema.Field.of("concept_code", Schema.FieldType.STRING),
                Schema.Field.of("section_id", Schema.FieldType.INT32),
                Schema.Field.of("matched_text", Schema.FieldType.STRING),
                Schema.Field.of("matched_sentence", Schema.FieldType.STRING)
        );
    }

    @Override
    public PCollection<Row> expand(PCollection<Row> input) {
        return input.apply(ParDo.of(new DoFn<Row, Row>() {
            private transient ObjectMapper om;
            private transient Map<String, Integer> ohdsiConceptMap;
            private transient String version;

            @Setup
            public void init() {
                this.version = new BufferedReader(
                        new InputStreamReader(
                                Objects.requireNonNull(MedTaggerOutputToOHDSIFormatTransform.class
                                        .getResourceAsStream("/medtagger-version.txt")),
                                StandardCharsets.UTF_8))
                        .lines().collect(Collectors.joining(" ")).trim();
                this.om = new ObjectMapper();
                this.ohdsiConceptMap = new HashMap<>();
                switch (resources.toUpperCase(Locale.ROOT)) {
                    case "NONE": {
                        break;
                    }
                    default: {
                        try (InputStream resource = MedTaggerOutputToOHDSIFormatTransform.class.getResourceAsStream("/resources/" + resources + "/ohdsi_mappings.txt")) {
                            List<String> mappings =
                                    new BufferedReader(new InputStreamReader(resource,
                                            StandardCharsets.UTF_8)).lines().collect(Collectors.toList());
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

            @ProcessElement
            public void processElement(@Element Row input, OutputReceiver<Row> output) throws JsonProcessingException, ParseException {

                // Generate an output row
                Row.Builder rowBuild = Row.withSchema(schema)
                        .addValues(input.getSchema().getFields().stream().flatMap(f -> {
                            if (f.getName().equalsIgnoreCase("offset")) {
                                return Stream.empty();
                            } else {
                                return Stream.of((Object)input.getValue(f.getName()));
                            }
                        }).collect(Collectors.toList()))
                        .addValue(input.getInt32("section_id"))
                        .addValue(input.getString("matched_text"))
                        .addValue(input.getString("matched_sentence"));
                switch (resources.toUpperCase(Locale.ROOT)) {
                    case "NONE": {
                        try {
                            rowBuild = rowBuild.addValue(Integer.valueOf(Optional.ofNullable(input.getString("concept_code")).orElse("0")));
                        } catch (NumberFormatException e) {
                            throw new IllegalArgumentException("OHDSI requires integer concept codes, value "
                                    + input.getString("concept_code") + " was instead provided with mapping ruleset 'NONE'");
                        }
                        break;
                    }
                    case "UMLS": {
                        String conceptCode = input.getString("concept_code");
                        // Only take first portion as CUI, remainder is top freq lexeme in current dict format.
                        String cui = conceptCode.contains(":") ? conceptCode.split(":")[0].toUpperCase(Locale.ROOT)
                                : conceptCode.toUpperCase(Locale.ROOT);
                        int ohdsicid = ohdsiConceptMap.getOrDefault(cui, -99999);
                        rowBuild = rowBuild.addValue(ohdsicid);
                    }
                    default: {
                        rowBuild = rowBuild.addValue(ohdsiConceptMap.getOrDefault(input.getString("concept_code"), 0));
                    }
                }
                Row out = rowBuild
                        .addValue(0)
                        .addValue(input.getDateTime("nlp_run_dtm"))
                        .addValue(
                                String.format("certainty=%1$s,experiencer=%2$s,status=%3$s",
                                        input.getString("certainty"),
                                        input.getString("experiencer"),
                                        input.getString("status")
                                )
                        )
                        .addValue(input.getInt32("offset"))
                        .addValue(version.trim())
                        .build();
                output.output(out);
            }

        }));
    }


    @Override
    public void init() throws ComponentInitializationException {

    }
}
