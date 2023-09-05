package org.ohnlp.medtagger.backbone;

import org.apache.beam.sdk.coders.BigEndianIntegerCoder;
import org.apache.beam.sdk.coders.KvCoder;
import org.apache.beam.sdk.coders.RowCoder;
import org.apache.beam.sdk.coders.StringUtf8Coder;
import org.apache.beam.sdk.schemas.Schema;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.transforms.Sum;
import org.apache.beam.sdk.values.KV;
import org.apache.beam.sdk.values.PCollection;
import org.apache.beam.sdk.values.Row;
import org.ohnlp.backbone.api.annotations.ComponentDescription;
import org.ohnlp.backbone.api.annotations.ConfigurationProperty;
import org.ohnlp.backbone.api.components.OneToOneTransform;
import org.ohnlp.backbone.api.config.InputColumn;
import org.ohnlp.backbone.api.exceptions.ComponentInitializationException;

import java.util.Arrays;
import java.util.Locale;

@ComponentDescription(
        name = "Section Header Candidate Identifier",
        desc = "Parses input documents to generate header candidates (e.g., for use with SecTag)"
)
public class SectionResolutionTransform extends OneToOneTransform {

    @ConfigurationProperty(
            path="input",
            desc="Column containing note text"
    )
    public InputColumn noteText;

    @ConfigurationProperty(
            path="threshold",
            desc="Minimum number of distinct documents containing header candidate for inclusion"
    )
    public Integer threshold;

    @Override
    public Schema calculateOutputSchema(Schema schema) {
        return Schema.of(
                Schema.Field.of("header", Schema.FieldType.STRING),
                Schema.Field.of("count", Schema.FieldType.INT32));
    }

    @Override
    public PCollection<Row> expand(PCollection<Row> input) {
        Schema returnSchema = Schema.of(
                Schema.Field.of("header", Schema.FieldType.STRING),
                Schema.Field.of("count", Schema.FieldType.INT32));
        return input.apply("Extract Header Candidates", ParDo.of(new DoFn<Row, KV<String, Integer>>() {
            @ProcessElement
            public void process(ProcessContext pc) {
                String raw = pc.element().getString(noteText.getSourceColumnName());
                if (raw == null) {
                    return;
                }
                raw = raw.toLowerCase(Locale.ROOT);
                // First, split by newlines
                Arrays.stream(raw.split("\\r?\\n")).forEach(s -> {
                    // Now split by colon and add first part to candidates
                    String cand = s.split(":")[0].trim();
                    if (cand.length() > 0) {
                        // And output
                        pc.output(KV.of(cand, 1));
                    }
                });

            }
        })).setCoder(KvCoder.of(StringUtf8Coder.of(), BigEndianIntegerCoder.of())).apply(
                "Get counts per header",
                Sum.integersPerKey()
        ).setCoder(KvCoder.of(StringUtf8Coder.of(), BigEndianIntegerCoder.of())
        ).apply("Convert back to Rows",
                ParDo.of(
                        new DoFn<KV<String, Integer>, Row>() {
                            @ProcessElement
                            public void process(ProcessContext pc) {
                                KV<String, Integer> e = pc.element();
                                if (e.getValue() >= threshold) {
                                    pc.output(Row.withSchema(returnSchema).addValues(e.getKey(), e.getValue()).build());
                                }
                            }
                        }
                )
        ).setCoder(RowCoder.of(returnSchema));
    }

    @Override
    public void init() throws ComponentInitializationException {

    }
}
