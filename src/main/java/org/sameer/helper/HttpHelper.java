package org.sameer.helper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpHelper {
    public static int upload(URL url, String content) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoOutput(true);
        connection.setRequestMethod("PUT");
        OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
        out.write(content);
        out.close();
        // Weird behavior. If I request a response code it seems to guarantee that the file shows up in S3 right away.
        // If I don't request the response code, the file does not always upload even though there is no exception.
        return connection.getResponseCode();
    }

    public static String download(URL url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuffer result = new StringBuffer();
        String line;
        while ((line = in.readLine()) != null) {
            result.append(line);
        }
        return result.toString();
    }

}
