package helpers;

import java.net.URL;
import java.util.Date;

import com.amazonaws.HttpMethod;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;

public class S3Helper {
    // This manner of creating the client assumes that your
    // 1. AWS credentials (aws_access_key_id, aws_secret_access_key) are stored in the [default] section of the file ~/.aws/credentials.
    // 2. Region is stored  in the [default] section of the file ~/.aws/config
    private final AmazonS3 s3Client = AmazonS3Client.builder().withCredentials(new ProfileCredentialsProvider()).build();

    public URL generateSignedUrl(String bucket, String key, HttpMethod method, long expirationMilliSecs) {
        GeneratePresignedUrlRequest urlRequest = new GeneratePresignedUrlRequest(bucket, key);
        urlRequest.setMethod(method);
        if (expirationMilliSecs > 0) {
            Date expiration = new Date();
            expiration.setTime(System.currentTimeMillis() + expirationMilliSecs);
            urlRequest.setExpiration(expiration);
        }
        URL url = s3Client.generatePresignedUrl(urlRequest);
        return url;
    }
}
