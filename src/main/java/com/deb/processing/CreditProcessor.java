package com.deb.processing;

import com.deb.processing.utils.Configuration;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.*;

import java.io.IOException;
import java.util.Properties;

public class CreditProcessor {

    private static int LOWER_CREDIT_LIMIT = 700;
    private static int MAX_CREDIT_LIMIT = 1000;
    private static String CREDIT_SCORE_COLUMN = "fico_range_low";

    private Configuration config;
    /*
        full configuratin file path
     */
    public CreditProcessor(String configFile) throws IOException {
        this.config = new Configuration(configFile);
    }

    public Dataset<Row> readCSVLocalFile(SparkSession spark) {

        Dataset<Row>  data =  spark.read().option("header", "true")
                .option("inferschema", "true")
                .csv("file://" + config.getConfigValue("localFile"));
        data.printSchema();
        return data;

    }


    public Dataset<Row> filterByStatusAndPurpose(Dataset<Row> dataset) {
        return dataset.where("purpose != 'other'")
                .where("loan_status != 'Charged Off'");

    }

    public Dataset<Row> filterByCreditScore(Dataset<Row> dataset) {
        return dataset.where("fico_range_low > 700")
                .withColumnRenamed("desc", "description")
                ;

    }

    public Dataset<Row> readFromS3(SparkSession spark){

        final String aws_key = config.getConfigValue("s3.aws.key");
        final String aws_secret = config.getConfigValue("s3.aws.secret");
        final String fileLocation = config.getConfigValue("s3.aws.file");

        spark.sparkContext().hadoopConfiguration().set("fs.s3a.access.key", aws_key);
        spark.sparkContext().hadoopConfiguration().set("fs.s3a.secret.key", aws_secret);

        Dataset<Row> data =  spark.read().option("header", "true")
                .option("inferschema", "true")
                .csv("s3a://" + fileLocation);

        data.printSchema();

        return data;
    }

    public void saveToDB(Dataset<Row> dataset){

        dataset.write().format("jdbc")
                .option("driver", "org.postgresql.Driver")
                .option("url", "jdbc:postgresql://database-1.cm52utzmehtn.us-east-2.rds.amazonaws.com/")
                .option("dbtable", "credit_log")
                .option("user", "postgres")
                .option("password", "postgres")
                //.option("createTableOptions", "PRIMARY KEY (id)")
                .mode(SaveMode.Append).save();

    }


}

