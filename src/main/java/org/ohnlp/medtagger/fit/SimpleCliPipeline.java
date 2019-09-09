//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.ohnlp.medtagger.fit;

import me.tongfei.progressbar.ProgressBar;
import org.apache.uima.UIMAException;
import org.apache.uima.UIMAFramework;
import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.cas.CAS;
import org.apache.uima.collection.CollectionReader;
import org.apache.uima.collection.CollectionReaderDescription;
import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.fit.internal.ResourceManagerFactory;
import org.apache.uima.fit.util.LifeCycleUtil;
import org.apache.uima.resource.Resource;
import org.apache.uima.resource.ResourceManager;
import org.apache.uima.util.CasCreationUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.Properties;

/**
 *  Duplicate with SimplePipeline from UIMA-fit, to allow CLI callbacks for progress bar
 *  The original class is final thus cannot be extended
 *
 */
public class SimpleCliPipeline {


    private SimpleCliPipeline() {}

    /**
     * Add callbacks to reflect progress
     * @param readerDesc
     * @param descs
     * @throws UIMAException
     * @throws IOException
     */
    public static void runPipeline(CollectionReaderDescription readerDesc, AnalysisEngineDescription... descs)
            throws UIMAException, IOException {
        CollectionReader reader = null;
        AnalysisEngine aae = null;

        try {
            ResourceManager resMgr = ResourceManagerFactory.newResourceManager();
            reader = UIMAFramework.produceCollectionReader(readerDesc, resMgr, null);
            AnalysisEngineDescription aaeDesc = AnalysisEngineFactory.createEngineDescription(descs);
            aae = UIMAFramework.produceAnalysisEngine(aaeDesc, resMgr, null);
            CAS cas = CasCreationUtils.createCas(Arrays.asList(reader.getMetaData(), aae.getMetaData()),
                    null, resMgr);
            reader.typeSystemInit(cas.getTypeSystem());

            // name, initial max and try with statement for the progress bar
            try(ProgressBar pb = new ProgressBar(
                    "Processing documents: ", reader.getProgress()[0].getTotal())) {
                while (reader.hasNext()) {
                    reader.getNext(cas);
                    aae.process(cas);
                    cas.reset();
                    pb.step();
                }

                aae.collectionProcessComplete();
            }
        } finally {
            LifeCycleUtil.destroy(new Resource[]{reader});
            LifeCycleUtil.destroy(new Resource[]{aae});
        }
    }
}
