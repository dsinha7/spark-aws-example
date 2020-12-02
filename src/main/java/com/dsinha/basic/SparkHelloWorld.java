package com.dsinha.basic;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

public class SparkHelloWorld {

    public static void main(String[] args) {
        SparkSession spark = SparkSession
                .builder().master("local[*]")
                .appName("SparkHelloWorld")
                .getOrCreate();

        //create a dataset using sparks range() function
        Dataset<Row> myRange = spark.range(100).toDF();
        long count = myRange.count();
        System.out.println(count);

        spark.close();

    }
}
