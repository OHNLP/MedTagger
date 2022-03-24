package org.ohnlp.medtagger.spark;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.util.InvalidXMLException;

import java.io.IOException;
import java.net.URISyntaxException;

public class MedTaggerSparkExecutable {
    public static void main(String... args) throws ResourceInitializationException, InvalidXMLException, IOException, URISyntaxException {
        SparkConf conf = new SparkConf()
                .set("spark.storage.memoryFraction", "0.02")
                .set("spark.shuffle.memoryFraction", "0.8");
        SparkSession session = SparkSession.builder().config(conf).enableHiveSupport().getOrCreate();
        String inputDir = conf.get("spark.ohnlptk.input_dir");
        String outputDir = conf.get("spark.ohnlptk.output_dir");
        String nlpconf = conf.get("spark.ohnlptk.nlpconf");
        String ohdsiconf = conf.get("spark.ohnlptk.ohdsiconf");

        JavaRDD<Row> rdd = session.read().format("parquet").load(inputDir).toJavaRDD()
                .mapPartitions(new MedTaggerFlatmapFunction(nlpconf, ohdsiconf));
        session.createDataFrame(rdd, MedTaggerFlatmapFunction.TYPE)
                .write()
                .format("parquet")
                .save(outputDir);
    }
}
