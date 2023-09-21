package com.tyson.useless.system.util;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;


import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class WebhookActions {
    int connectionTimeout = 2000; // 2 seconds
    int readTimeout = 3000; // 5 seconds
    public boolean pingWebhook(String urlString) throws IOException {
        int responseCode = 0;
        try{
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(connectionTimeout);
            connection.setReadTimeout(readTimeout);
            responseCode = connection.getResponseCode();
            connection.disconnect();
        }
        catch(Exception e){
            System.out.println(e);
        }
        return(responseCode == 200);
    }

    public String pushWebhookEvent(String urlString, JSONObject eventData) throws IOException, JSONException {
        HttpURLConnection connection = getHttpURLConnection(urlString);
        dataToBePushed(eventData, connection);
        int responseCode = connection.getResponseCode();
        System.out.println("responseCode : "+ responseCode);
        String responseData = getResponseData(connection, responseCode);
        return responseData;
    }

    private static void dataToBePushed(JSONObject eventData, HttpURLConnection connection) throws IOException {
        String jsonData = eventData.toString();
        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = jsonData.getBytes("UTF-8");
            os.write(input, 0, input.length);
        }
    }

    private HttpURLConnection getHttpURLConnection(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(connectionTimeout);
        connection.setReadTimeout(readTimeout);
        connection.setDoInput(true);
        connection.setDoOutput(true);
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Zsec_user_import_url", "true");
        return connection;
    }
    private static String getResponseData(HttpURLConnection connection, int responseCode) throws IOException {
        String responseData = "";
        if(responseCode == 200){
            InputStream inputStream = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            StringBuilder response = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            responseData = response.toString();
            reader.close();
            inputStream.close();
        }
        return responseData;
    }
}
