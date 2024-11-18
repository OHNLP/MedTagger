package org.ohnlp.medtagger.backbone;

import org.apache.beam.sdk.coders.KvCoder;
import org.apache.beam.sdk.coders.RowCoder;
import org.apache.beam.sdk.coders.StringUtf8Coder;
import org.apache.beam.sdk.schemas.Schema;
import org.apache.beam.sdk.schemas.transforms.Join;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.transforms.GroupByKey;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.values.*;
import org.checkerframework.checker.initialization.qual.Initialized;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.UnknownKeyFor;
import org.ohnlp.backbone.api.annotations.ComponentDescription;
import org.ohnlp.backbone.api.annotations.ConfigurationProperty;
import org.ohnlp.backbone.api.annotations.InputColumnProperty;
import org.ohnlp.backbone.api.components.LoadFromMany;
import org.ohnlp.backbone.api.config.InputColumn;
import org.ohnlp.backbone.api.exceptions.ComponentInitializationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.ohnlp.medtagger.ie.cc.MedTatorWriter.writeXml;

@ComponentDescription(
        name = "Medtator Output Transform",
        desc = "Transforms MedTagger IE Output (and Similar) to MedTator format"
)
public class MedtatorOutputTransform extends LoadFromMany {
    @ConfigurationProperty(
            path = "fileSystemPath",
            desc = "The file system path to write to"
    )
    private String workingDir;

    @ConfigurationProperty(
            path = "taskName",
            desc = "Task Name (the Schema Definition Name for MedTator)"
    )
    private String taskName;

    @ConfigurationProperty(
            path = "note_id_raw_col",
            desc = "The input column to use containing the note identifier from the raw text collection"
    )
    @InputColumnProperty(
            sourceTags = {"Raw Text"}
    )
    private InputColumn note_identifer_raw_col;

    @ConfigurationProperty(
            path = "note_text_raw_col",
            desc = "The input column to use containing the note text from the annotated entities"
    )
    @InputColumnProperty(
            sourceTags = {"Entity Annotations"}
    )
    private InputColumn note_text_raw_col;

    @ConfigurationProperty(
            path = "note_id_ann_col",
            desc = "The input column to use containing the note identifier from the annotated entities"
    )
    @InputColumnProperty(
            sourceTags = {"Entity Annotations"}
    )
    private InputColumn note_identifer_ann_col;

    @ConfigurationProperty(
            path = "note_ann_start_col",
            desc = "The input column to use containing the annotation start index from the annotated entities"
    )
    @InputColumnProperty(
            sourceTags = {"Entity Annotations"}
    )
    private InputColumn ann_start_ann_col;

    @ConfigurationProperty(
            path = "note_ann_end_col",
            desc = "The input column to use containing the annotation end index from the annotated entities"
    )
    @InputColumnProperty(
            sourceTags = {"Entity Annotations"}
    )
    private InputColumn ann_end_ann_col;

    @ConfigurationProperty(
            path = "note_ann_type_col",
            desc = "The input column to use containing the annotation type from the annotated entities. " +
                    "Defaults to \"ConceptMention\" if left blank",
            required = false
    )
    @InputColumnProperty(
            sourceTags = {"Entity Annotations"}
    )
    private InputColumn ann_type_col;

    @Override
    public void init() throws ComponentInitializationException {
        new File(this.workingDir).mkdirs();
    }

    @Override
    public POutput expand(PCollectionRowTuple input) {
        PCollection<Row> rawText = input.get("Raw Text");
        PCollection<Row> entitydf = input.get("Entity Annotations");

        PCollection<KV<String, Row>> keyedRawText = rawText.apply("Extract Raw Text Keys", ParDo.of(new DoFn<Row, KV<String, Row>>() {
            @ProcessElement
            public void process(@Element Row input, OutputReceiver<KV<String, Row>> output) {
                output.output(KV.of(input.getValue(note_identifer_raw_col.getSourceColumnName()).toString(), input));
            }
        })).setCoder(
                KvCoder.of(StringUtf8Coder.of(), RowCoder.of(rawText.getSchema()))
        );

        // Compressed annotation Schema
        Schema schema = Schema.of(
                Schema.Field.of(note_identifer_ann_col.getSourceColumnName(), Schema.FieldType.STRING),
                Schema.Field.of("annotations", Schema.FieldType.iterable(Schema.FieldType.row(entitydf.getSchema())))
        );

        PCollection<Row> groupedKeyedAnnotations =
                entitydf.apply("Extract Annotation Keys", ParDo.of(new DoFn<Row, KV<String, Row>>() {
            @ProcessElement
            public void process(@Element Row input, OutputReceiver<KV<String, Row>> output) {
                output.output(KV.of(input.getValue(note_identifer_ann_col.getSourceColumnName()).toString(), input));
            }
        })).setCoder(
                KvCoder.of(StringUtf8Coder.of(), RowCoder.of(entitydf.getSchema()))
        ).apply(
                "Group by ID",
                GroupByKey.create()
        ).apply(
                "Transform to Row",
                        ParDo.of(new DoFn<KV<String, Iterable<Row>>, Row>() {
                            @ProcessElement
                            public void process(@Element KV<String, Iterable<Row>> input, OutputReceiver<Row> out) {
                                out.output(
                                        Row.withSchema(
                                                schema
                                        ).addValues(input.getKey(), input.getValue()).build()
                                );
                            }
                        })
                ).setCoder(RowCoder.of(schema));

        // Join with raw text
        groupedKeyedAnnotations.apply(
                "Join annotations with raw text",
                Join.<Row, Row>innerJoin(rawText)
                        .on(Join.FieldsEqual.left(note_identifer_ann_col.getSourceColumnName()).right(note_identifer_raw_col.getSourceColumnName()))
        ).apply(
                "Convert to XML and Write",
                ParDo.of(new DoFn<Row, String>() {
                    private transient DocumentBuilder db;
                    private transient DocumentBuilderFactory dbf;

                    @Setup
                    public void init() {
                        dbf = DocumentBuilderFactory.newInstance();
                        try {
                            db = dbf.newDocumentBuilder();
                        } catch (ParserConfigurationException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    @ProcessElement
                    public void process(@Element Row input, OutputReceiver<String> out) {
                        String note_id = input.getRow("rhs").getString(note_identifer_raw_col.getSourceColumnName());
                        Iterable<Row> anns = input.getRow("lhs").getIterable("annotations");
                        String rawText = input.getRow("rhs").getString(note_text_raw_col.getSourceColumnName());
                        Document doc = db.newDocument();
                        org.w3c.dom.Element rootElement = doc.createElement(taskName);
                        doc.appendChild(rootElement);
                        Node cdata = doc.createCDATASection(rawText);
                        org.w3c.dom.Element textElement = doc.createElement("TEXT");
                        textElement.appendChild(cdata);
                        rootElement.appendChild(textElement);
                        org.w3c.dom.Element tagsElement = doc.createElement("TAGS");
                        int tagId = 0;
                        for (Row ann : anns) {
                            int start = Integer.valueOf(ann.getString(ann_start_ann_col.getSourceColumnName()));
                            int end = Integer.valueOf(ann.getString(ann_end_ann_col.getSourceColumnName()));
                            String type = "CM";
                            if (ann_type_col != null) {
                                type = ann.getString(ann_type_col.getSourceColumnName());
                            }
                            org.w3c.dom.Element tagElement = doc.createElement(type);
                            tagElement.setAttribute("spans", start+"~"+end);
                            tagElement.setAttribute("text", rawText.substring(start, end));
                            tagElement.setAttribute("id", "P"+tagId);
                            tagsElement.appendChild(tagElement);
                            tagId++;
                        }
                        rootElement.appendChild(tagsElement);

                        try{
                            FileOutputStream output =
                                    new FileOutputStream(new File(workingDir, note_id + ".xml"));
                            writeXml(doc, output);
                        } catch (TransformerException e) {
                            throw new RuntimeException(e);
                        } catch (FileNotFoundException e) {
                            throw new RuntimeException(e);
                        }
                    }
                })
        ).setCoder(StringUtf8Coder.of());

        return PDone.in(input.getPipeline());
    }

    @Override
    public List<String> getInputTags() {
        return Arrays.asList("Raw Text", "Entity Annotations");
    }
}
