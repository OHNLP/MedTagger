package org.ohnlp.medtagger.backbone;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.beam.sdk.schemas.Schema;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.values.PCollection;
import org.apache.beam.sdk.values.Row;
import org.joda.time.Duration;
import org.joda.time.Instant;
import org.ohnlp.backbone.api.Transform;
import org.ohnlp.backbone.api.exceptions.ComponentInitializationException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

/**
 * Transforms MedTagger output as produced from {@link MedTaggerBackboneTransform} to an OHDSI-compliant format suitable
 * for the NOTE_NLP table.
 * <p>
 * Note: Assumes that rulesets supply a concept normalization -> OHDSI concept id mapping
 */
public class MedTaggerOutputToOHDSIFormatTransform extends Transform {
    private static ThreadLocal<SimpleDateFormat> sdf = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyy-MM-dd'T'HH:mm:ssXXX"));

    @Override
    public void initFromConfig(JsonNode jsonNode) throws ComponentInitializationException {
        // No initialization necessary
    }

    @Override
    public PCollection<Row> expand(PCollection<Row> input) {
        return input.apply(ParDo.of(new DoFn<Row, Row>() {
            private transient ObjectMapper om;

            @Setup
            public void init() {
                this.om = new ObjectMapper();
            }

            @ProcessElement
            public void processElement(@Element Row input, OutputReceiver<Row> output) throws JsonProcessingException, ParseException {
                // First transform row schemas
                List<Schema.Field> fields = new LinkedList<>(input.getSchema().getFields());
                fields.add(Schema.Field.of("section_concept_id", Schema.FieldType.INT32));
                fields.add(Schema.Field.of("lexical_variant", Schema.FieldType.STRING));
                fields.add(Schema.Field.of("snippet", Schema.FieldType.STRING));
                fields.add(Schema.Field.of("note_nlp_concept_id", Schema.FieldType.INT32));
                fields.add(Schema.Field.of("note_nlp_source_concept_id", Schema.FieldType.STRING));
                fields.add(Schema.Field.of("nlp_datetime", Schema.FieldType.DATETIME));
                fields.add(Schema.Field.of("term_modifiers", Schema.FieldType.STRING));
                Schema schema = Schema.of(fields.toArray(new Schema.Field[0]));

                JsonNode rawValues = om.readTree(input.getString("nlp_output_json"));

                // Now generate an output row
                Row out = Row.withSchema(schema)
                        .addValues(input.getValues())
                        .addValue(rawValues.get("section_id").asInt())
                        .addValue(rawValues.get("matched_text").asText())
                        .addValue(rawValues.get("matched_sentence").asText())
                        .addValue(0) // TODO map concept norms
                        .addValue(rawValues.get("concept_code").asText())
                        .addValue(new Instant(sdf.get().parse(rawValues.get("nlp_run_dtm").asText()).getTime()))
                        .addValue(
                                String.format("certainty=%1$s,experiencer=%2$s,status=%3$s",
                                        rawValues.get("certainty").asText(),
                                        rawValues.get("experiencer").asText(),
                                        rawValues.get("status").asText()
                                )
                        )
                        .build();
                output.output(out);
            }
        }));
    }

}
