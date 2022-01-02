package org.ohnlp.medtagger.fit;

import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.collection.CollectionReaderDescription;
import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.fit.factory.CollectionReaderFactory;
import org.ohnlp.medtagger.dict.DictCollectionReader;
import org.ohnlp.medtagger.dict.DictWriter;
import org.apache.uima.fit.pipeline.SimplePipeline;

import java.io.File;


import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngineDescription;

public class DictMain {

    public static void main(String[] args) throws Exception {

        File inputDictFile = new File(args[0]);
        File outputDictFile = new File(args[1]);

        CollectionReaderDescription reader =
                CollectionReaderFactory.createReaderDescription(
                        DictCollectionReader.class,
                        DictCollectionReader.PARAM_DICTFILE, inputDictFile.toString());

        System.out.println("Input Dictionary:\t" + inputDictFile.toString());
        System.out.println("Output Dir:\t" + outputDictFile.toString());

        AnalysisEngineDescription descDictPrepareTAE = createEngineDescription(
                "desc.medtaggerdesc.aggregate_analysis_engine.DictPrepareTAE");


        AnalysisEngineDescription writer =
                AnalysisEngineFactory.createEngineDescription(
                        DictWriter.class,
                        DictWriter.PARAM_DICTOUTPUT, outputDictFile.toString());

        SimplePipeline.runPipeline(reader, descDictPrepareTAE, writer);
    }
}
