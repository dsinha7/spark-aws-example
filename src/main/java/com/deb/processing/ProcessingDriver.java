package com.deb.processing;

import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import java.io.IOException;


public class ProcessingDriver {

    public static void main(String[] args) throws Exception {

        if (args.length == 0) {
            throw new Exception("Configuration file location should be passed as parameter");
        }
        SparkConf defaultConfig = new SparkConf();

        //trick that same code will work in IDE and runtime
        if(!defaultConfig.contains("spark.master")){
            defaultConfig.setMaster("local[2]");
        }

        SparkSession spark = SparkSession
                .builder()
                .appName("Credit Processor")
                .config(defaultConfig)
                .getOrCreate();

        JavaSparkContext context = new JavaSparkContext(spark.sparkContext());
        //set error level
        spark.sparkContext().setLogLevel("ERROR");

        //application code
        CreditProcessor processor = new CreditProcessor(args[0]);

        //Dataset<Row> s3Data = processor.readCSVLocalFile(spark);

        //Ideally the pipeline steps should be configuration driven
        Dataset<Row> s3Data = processor.readFromS3(spark);
        Dataset<Row> filteredData  = processor.filterByStatusAndPurpose(s3Data);
        Dataset<Row> transformedData  = processor.filterByCreditScore(filteredData);

        //filteredData.show(10, false);
        //TODO use logger
        System.out.println(transformedData.count());

        processor.saveToDB(transformedData);

        spark.stop();

    }
}
