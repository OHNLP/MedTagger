package org.ohnlp.medtagger.fit;

import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.analysis_engine.metadata.AnalysisEngineMetaData;
import org.apache.uima.collection.CollectionReaderDescription;
import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.fit.factory.CollectionReaderFactory;
import org.apache.uima.fit.pipeline.SimplePipeline;
import org.apache.uima.resource.metadata.ConfigurationParameterSettings;
import org.ohnlp.medtagger.cr.FileSystemReader;
import org.ohnlp.medtagger.ie.cc.IETabDelimitedWriter;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngineDescription;

public class Main {

    public static void main(String[] args) throws Exception {

        Path inputDirPath = Paths.get(args[0]);
        Path outputDirPath = Paths.get(args[1]);
        Path ruleDirPath = Paths.get(args[2]);

        CollectionReaderDescription reader =
                CollectionReaderFactory.createReaderDescription(
                        FileSystemReader.class,
                        FileSystemReader.PARAM_INPUTDIR, inputDirPath.toString());

        System.out.println("Input Dir:\t" + inputDirPath.toAbsolutePath().toString());
        System.out.println("Output Dir:\t" + outputDirPath.toAbsolutePath().toString());
        System.out.println("IE Rules:\t" + ruleDirPath.toAbsolutePath().toString());

        AnalysisEngineDescription descMedTaggerTAE = createEngineDescription(
                "desc.medtaggeriedesc.aggregate_analysis_engine.MedTaggerIEAggregateTAE");

        AnalysisEngineMetaData metadata = descMedTaggerTAE.getAnalysisEngineMetaData();

        ConfigurationParameterSettings settings = metadata.getConfigurationParameterSettings();
        settings.setParameterValue("Resource_dir", ruleDirPath.toString());
        metadata.setConfigurationParameterSettings(settings);

        AnalysisEngineDescription writer =
                AnalysisEngineFactory.createEngineDescription(
                        IETabDelimitedWriter.class,
                        IETabDelimitedWriter.PARAM_OUTPUTDIR, outputDirPath.toString());

        SimpleCliPipeline.runPipeline(reader, descMedTaggerTAE, writer);

    }
}
