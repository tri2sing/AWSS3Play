# AWSS3Play by Sameer Adhikari

## Introduction
This repository provides an implementation of a service which generates pre-signed AWS S3 URLs. It supports the following methods.

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

## Pre-requisites
### Build
To build this service you need 
* Java 1.8
* Maven (this was developed with 3.3.9)

### Execute
To execute this service you need your spefic details for the following configuration files
* AWS credentials stored in ~/.aws/credentials
```
[default]
aws_access_key_id = <your aws access key id> 
aws_secret_access_key = <your aws access key>
```
* AWS region information stored in ~/.aws/config
```
[default]
region = us-east-1
```
* Cassandra cluster
* Application config file (e.g. config.yml)
```
cassandraConfig:
  clusterNode: 127.0.0.1
  port: 9042

s3bucket: <your s3 bucket name>

server:
  applicationConnectors:
  - type: http
    port: 9080

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
