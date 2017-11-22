package app;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.amazonaws.HttpMethod;

import helpers.HttpHelper;
import helpers.S3Helper;

public class S3App {
    private S3Helper s3Helper = new S3Helper();

    public String download(String bucket, String key, long expiration) throws IOException {
        URL url = s3Helper.generateSignedUrl(bucket, key, HttpMethod.GET, expiration);
        return HttpHelper.download(url);
    }

    public void upload(String bucket, String key, long expiration, String filePath) throws IOException {
        URL url = s3Helper.generateSignedUrl(bucket, key, HttpMethod.PUT, expiration);
        String contents = new String(Files.readAllBytes(Paths.get(filePath)));
        HttpHelper.upload(url, contents);
    }

    public static String getArgValue (String argKey) {
        String value = System.getProperty(argKey);
        if (value == null) {
            throw new IllegalStateException("The required property \"" + argKey + "\" is missing");
        }
        return value;
    }

    public static void main(String[] args) throws IOException {
        S3App s3App = new S3App();
        String key = S3App.getArgValue("key");
        String bucket = S3App.getArgValue("bucket");
        String path = S3App.getArgValue("path");
        long expiration = Integer.parseInt(S3App.getArgValue("expiration"));

        s3App.upload(bucket, key, expiration, path);
        System.out.println("Done 2");
        String content = s3App.download(bucket, key, expiration);
        System.out.println(content);
        System.out.println("Done 3");

    }
}
