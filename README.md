# AWSS3Play by Sameer Adhikari

## Introduction
This repository provides an implementation of a service which generates pre-signed AWS S3 URLs. It is designed to be scalable at the both the web layer and the datastore layer. The web layer is scalable because each instance is session-less. The datastore is scalable by the choice of Cassandra. 

The service supports the following methods.

```
POST  /asset
Body:  empty
Sample  response
{    
    “upload_url”:  <s3-signed-url-for-upload>,    
    “id”:  <asset-id>
}

PUT  /asset/<asset-id> 
Body:
{    “Status”:  “uploaded” }

GET  /asset/<asset-id>?timeout=100
Sample  response
{    “download_url”:  <s3-signed-url-for-upload> }
```
Here is what you can do with the pre-signed urls:
* You can upload an object to the upload_url using a POST call
* You can download the object from the dowload_url using a GET call if you have set the status to uploaded

## Pre-requisites
### Build
To build this service you need 
* Java 1.8
* Maven (this was developed with 3.3.9)

### Execute
To execute this service you need 
* A running Cassandra installation
* AWS credentials stored in ~/.aws/credentials
```
[default]
aws_access_key_id = <your aws access key id> 
aws_secret_access_key = <your aws access key>
```
* AWS region information stored in ~/.aws/config
```
[default]
region = <your aws region>
```
* Cassandra cluster
* Application config file (e.g. config.yml)
```
cassandraConfig:
  clusterNode: <IP of a node of your cluster>
  port: <Port of the cluster node>

s3bucket: <your s3 bucket name>

server:
  applicationConnectors:
  - type: http
    port: <Port on which you want the service to run>

```

## Building the Service
The steps to build the service are:
* Ensure that the pre-requisites to build the service are met.
* Clone this repository
* Go to the AWSS3Play directory
* Run the following command
```
mvn package
```

## Running the Service
The steps to run the service are.
* Ensure that your Cassandra cluster is running
* Deploy the following where you want to run the service
** ~/.aws/credentials
** ~/.aws/config
** config.yml
** AWSS3Play-1.0.jar
* Run the following command 
```
java -jar <path to AWSS3Play-1.0.jar> server <path to config.yml>
```
* Execute clients calls (POST/PUT/GET) to the service
