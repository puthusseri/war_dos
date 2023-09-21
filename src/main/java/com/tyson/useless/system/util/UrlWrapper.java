package com.tyson.useless.system.util;



import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

public class UrlWrapper {

    String urlString;
    private URL url;
    private HttpURLConnection httpURLConnection;
    private int maxRedirects = 1;
    private int connectTimeout = 10000;
    private int readTimeout = 10000;
    private String requestMethod = "GET";
    private long fixedContentLengthLong = -1L;
    private HashMap<String, List<String>> requestProperties = null;

    public UrlWrapper(String urlString) throws MalformedURLException {
        this.urlString = urlString;
        this.url = new URL(urlString);
    }
    public HttpURLConnection openConnection() throws IOException {
        return (HttpURLConnection) this.url.openConnection();
    }
    public void connect() throws IOException {
        HttpURLConnection httpURLConnection = openConnection();

    }

}
