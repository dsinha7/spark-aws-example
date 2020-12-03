#!/bin/sh

#your vars
emr_version='emr-5.31.0'
cluster_name='EMR_CLUSTER'
aws_subnet='subnet-0b236835c5cf3708d'
ec2_key='Ohio'
s3_log_bucket='s3://monetsgraden-s3-emr-logs/'

#spin up
aws emr create-cluster \
--name=$cluster_name \
--release-label $emr_version \
--instance-groups InstanceGroupType=MASTER,InstanceCount=1,InstanceType=m4.large InstanceGroupType=CORE,InstanceCount=2,InstanceType=m4.large \
--applications Name=Hadoop Name=HIVE Name=Spark Name=Zeppelin \
--use-default-roles \
--ec2-attributes SubnetIds=$aws_subnet,KeyName=$ec2_key \
--log-uri $s3_log_bucket

