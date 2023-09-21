package com.tyson.useless.system.util;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;

public interface WebhookActions {
    boolean pingWebhook(String urlString) throws IOException;

    String pushWebhookEvent(String urlString, JSONObject eventData) throws Exception;
    String getResponseData(HttpURLConnection connection, int responseCode) throws Exception ;
}
