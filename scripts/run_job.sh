#!/bin/sh

spark-submit \
  --class com.deb.processing.ProcessingDriver \
  --master yarn \
  ./spark-aws-example-1.0-SNAPSHOT-jar-with-dependencies.jar \
  config.properties