package com.tyson.useless.system.util;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLPeerUnverifiedException;
import java.io.IOException;
import java.net.URL;
import java.security.cert.Certificate;

public class CustomHttpConnection extends HttpsURLConnection {
    /**
     * Constructor for the HttpURLConnection.
     *
     * @param u the URL
     */
    protected CustomHttpConnection(URL u) {
        super(u);
    }

    @Override
    public String getCipherSuite() {
        return null;
    }

    @Override
    public Certificate[] getLocalCertificates() {
        return new Certificate[0];
    }

    @Override
    public Certificate[] getServerCertificates() throws SSLPeerUnverifiedException {
        return new Certificate[0];
    }

    @Override
    public void disconnect() {

    }

    @Override
    public boolean usingProxy() {
        return false;
    }

    @Override
    public void connect() throws IOException {

    }
}
