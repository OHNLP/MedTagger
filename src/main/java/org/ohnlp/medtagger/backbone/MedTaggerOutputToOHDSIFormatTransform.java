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
public class MedTaggerOutputToOHDSIFormatTransform extends Transform {
    private static ThreadLocal<SimpleDateFormat> sdf = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyy-MM-dd'T'HH:mm:ssXXX"));
    private String resources;
    private Schema schema;


    @Override
    public void initFromConfig(JsonNode config) throws ComponentInitializationException {
        this.resources = config.get("ruleset").asText();
    }

    @Override
    public Schema calculateOutputSchema(Schema schema) {
        List<Schema.Field> fields = new LinkedList<>(schema.getFields());
        fields.removeIf(f -> f.getName().toLowerCase(Locale.ROOT).equals("offset"));
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
                // First transform row schemas
                JsonNode rawValues = om.readTree(input.getString("nlp_output_json"));

                // Now generate an output row
                Row.Builder rowBuild = Row.withSchema(schema)
                        .addValues(input.getSchema().getFields().stream().flatMap(f -> {
                            if (f.getName().equalsIgnoreCase("offset")) {
                                return Stream.empty();
                            } else {
                                return input.getValue(f.getName());
                            }
                        }))
                        .addValue(rawValues.get("section_id").asInt())
                        .addValue(rawValues.get("matched_text").asText())
                        .addValue(rawValues.get("matched_sentence").asText());
                switch (resources.toUpperCase(Locale.ROOT)) {
                    case "NONE": {
                        try {
                            rowBuild = rowBuild.addValue(Integer.valueOf(rawValues.get("concept_code").asText("0")));
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
                        int ohdsicid = ohdsiConceptMap.getOrDefault(cui, -99999);
                        rowBuild = rowBuild.addValue(ohdsicid);
                    }
                    default: {
                        rowBuild = rowBuild.addValue(ohdsiConceptMap.getOrDefault(rawValues.get("concept_code").asText(), 0));
                    }
                }
                Row out = rowBuild
                        .addValue(0)
                        .addValue(new Instant(sdf.get().parse(rawValues.get("nlp_run_dtm").asText()).getTime()))
                        .addValue(
                                String.format("certainty=%1$s,experiencer=%2$s,status=%3$s",
                                        rawValues.get("certainty").asText(),
                                        rawValues.get("experiencer").asText(),
                                        rawValues.get("status").asText()
                                )
                        )
                        .addValue(rawValues.get("offset").asInt())
                        .addValue(version.trim())
                        .build();
                output.output(out);
            }

        }));
    }


}
