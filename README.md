# AWSS3Play by Sameer Adhikari
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

