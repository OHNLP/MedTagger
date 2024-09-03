package org.ohnlp.medtagger.fit;

import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.analysis_engine.metadata.AnalysisEngineMetaData;
import org.apache.uima.collection.CollectionReaderDescription;
import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.fit.factory.CollectionReaderFactory;
import org.apache.uima.fit.factory.ExternalResourceFactory;
import org.apache.uima.fit.pipeline.SimplePipeline;
import org.apache.uima.resource.impl.FileLanguageResource_impl;
import org.apache.uima.resource.metadata.ConfigurationParameterSettings;
import org.apache.uima.resource.metadata.ResourceManagerConfiguration;
import org.ohnlp.medtagger.cr.FileSystemReader;
import org.ohnlp.medtagger.ie.cc.MedTatorWriter;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngineDescription;

public class Main {

    public static void main(String[] args) throws Exception {

        Path inputDirPath = Paths.get(args[0]);
        Path outputDirPath = Paths.get(args[1]);
        Path ruleDirPath = Paths.get(args[2]);
        Path sectionPath = null;
        if (args.length > 3) {
            sectionPath = Paths.get(args[4]);
        }

        CollectionReaderDescription reader =
                CollectionReaderFactory.createReaderDescription(
                        FileSystemReader.class,
                        FileSystemReader.PARAM_INPUTDIR, inputDirPath.toString());

        System.out.println("Input Dir:\t" + inputDirPath.toAbsolutePath().toString());
        System.out.println("Output Dir:\t" + outputDirPath.toAbsolutePath().toString());
        System.out.println("IE Rules:\t" + ruleDirPath.toAbsolutePath().toUri().toString());
        if (sectionPath != null) {
            System.out.println("Section Rules:\t" + sectionPath.toAbsolutePath().toUri().toString());
        }

        AnalysisEngineDescription descMedTaggerTAE = createEngineDescription(
                "desc.medtaggeriedesc.aggregate_analysis_engine.MedTaggerIEAggregateTAE");
        if (sectionPath != null) {
            ResourceManagerConfiguration rmConfig = descMedTaggerTAE.getResourceManagerConfiguration();
            rmConfig.addExternalResource(
                    ExternalResourceFactory.createExternalResourceDescription(
                            "section_map",
                            "file:" + sectionPath.toFile().getAbsolutePath()
                    )
            );
        } else {
            ResourceManagerConfiguration rmConfig = descMedTaggerTAE.getResourceManagerConfiguration();
            rmConfig.addExternalResource(
                    ExternalResourceFactory.createExternalResourceDescription(
                            "section_map",
                            "file:medtaggerieresources/pad/Radiology_Section"
                    )
            );
        }
        AnalysisEngineMetaData metadata = descMedTaggerTAE.getAnalysisEngineMetaData();

        ConfigurationParameterSettings settings = metadata.getConfigurationParameterSettings();
        settings.setParameterValue("Resource_dir", ruleDirPath.toAbsolutePath().toUri().toString());
        metadata.setConfigurationParameterSettings(settings);

        AnalysisEngineDescription writer =
                AnalysisEngineFactory.createEngineDescription(
                        MedTatorWriter.class,
                       MedTatorWriter.PARAM_OUTPUTDIR, outputDirPath.toString());

        SimpleCliPipeline.runPipeline(reader, descMedTaggerTAE, writer);

    }
}
