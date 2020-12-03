## Overview 

This application reads a csv file from aws s3, applies transformation and business logic and finally saves the filtered/transformed records into postgres database.
The solution uses spark datasource, and dataframe api. Runtime parameters such as database host etc. are externalized into configuration file.

Build is automated using Maven, dependency jars are packaged with the final jar ( except spark jar as they are available in AWS EMR) 
Automated testing is done using junit

### Steps to run the end to end example
 
####  Setup the database 
For this exercise a postgresql instance of AWS-RDS is required
(I created mini instance with free tier)

For the end to end sample to run, edit the security group of the database, add a new inbound rule to allow tcp traffic to port 5432 from the security group of EMR Cluster slaves nodes (e.g. ElasticMapReduce-slave)
if using Sql clients to check the data, add the ip of test machine as well d
create the table using the ddl in <project_root>/scripts/ddl.sql


####  Build the project 
 Java 8 and mvn install. 
 check out the project 
 in root dir run
 
    man clean package 

this will create the jar spark application code along with the dependencies in /target folder

#### Set up aws cli   
1. Install aws cli 
    you will need python in your local environment, then run 
    
        pip install awscli

2. Create a user <emr-user> api only user with admin policy
3. Configure the aws cli for you laptop (I am using mac os) with aws key and secret key

#### Create a S3 Bucket

    aws s3api create-bucket --bucket <bucket name> --region us-east-2 --create-bucket-configuration LocationConstraint=us-east-2
(bucket name should be unique across aws)

upload the example data file

#### Create EMR cluster 
Create a s3 bucket for emr log

Edit the create_cluster.sh file <project_root>/scripts folder

    #your vars
    emr_version='emr-5.31.0'
    cluster_name='EMR_CLUSTER'
    aws_subnet=''
    ec2_key=''
    s3_log_bucket='emr log bucket'

run 

    ./create_cluster.sh

To check status of cluster 

    aws emr list-cluster --cluster-states RUNNING
#### Set the config parameter 
When cluster is ready, ssh to emr master

(note that, the emr master security group might require allowing ssj traffic from laptop outside aws network)

create/copy the <project-root>config/config.properties file to master node and modify following configuration parameters 
    
    #postgrasql
    #make sure it has a ending /
    jdbc.host=[database host] 
    jdbc.user=[uesr id]
    jdbc.password=[password]
    jdbc.dbtable=credit_log
    
    
    #s3
    s3.aws.key=[aws.key]
    s3.aws.secret=[aws.secret]
    #mybucket/xyz.csv
    s3.aws.file=[s3 bucket and file name]    

#### run the job 
Create/copy the <project-root>/target/*-jar-with-dependencies.jar master node

Create/copy the <project-root>scripts/run_job.sh file to master node and run 

    ./run_job.sh

validate that records are inserted into credit_log postgres table 

###Terminate the cluster
    
    aws emr terminate-clusters --cluster-id $cluster_id 
    
    
#### TO DO's 
1. add a reconciliation step after the job finish 
2. Tighten up the securities aspects 
3. run the job for large data set and tune the spark submit parameters 
4. port the project to scala and python
5. further automate the manual steps using jenkins 