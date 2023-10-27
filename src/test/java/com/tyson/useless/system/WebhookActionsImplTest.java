package com.tyson.useless.system;
//$Id$


import com.tyson.useless.system.util.WebhookActionsImpl;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
//import org.junit.Test;

//import static org.junit.Assert.assertFalse;
//import static org.junit.Assert.assertNotNull;
//import static org.junit.Assert.assertThrows;
//import static org.junit.Assert.assertTrue;
//import static org.junit.Assert.fail;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class WebhookActionsImplTest {

    @Test
    public void testPingWebhook_ValidURL_ReturnsTrue() throws IOException {
        WebhookActionsImpl webhookActions = new WebhookActionsImpl();
        String validURL = "http://example.com";
        boolean result = webhookActions.pingWebhook(validURL);
        assertTrue(result);
    }

    @Test
    public void testPingWebhook_InvalidURL_ReturnsFalse() throws IOException {
        WebhookActionsImpl webhookActions = new WebhookActionsImpl();
        String invalidURL = "invalid_url";
        boolean result = webhookActions.pingWebhook(invalidURL);
        assertFalse(result);
    }

    @Test
    public void testPushWebhookEvent_ValidURL_ReturnsResponseData() throws Exception {
        WebhookActionsImpl webhookActions = new WebhookActionsImpl();
        String validURL = "http://example.com";
        JSONObject eventData = new JSONObject("{\"key\": \"value\"}");
        String responseData = webhookActions.pushWebhookEvent(validURL, eventData);
        assertNotNull(responseData);
        // Add more assertions as needed based on the expected behavior
    }

    @Test
    public void testPushWebhookEvent_InvalidURL_ThrowsIOException() throws JSONException {
        WebhookActionsImpl webhookActions = new WebhookActionsImpl();
        String invalidURL = "invalid_url";
        JSONObject eventData = new JSONObject("{\"key\": \"value\"}");
        assertThrows(IOException.class, () -> webhookActions.pushWebhookEvent(invalidURL, eventData));
    }

    @Test
    public void testPushWebhookEvent_ValidPOSTURL_ShouldReturnData() throws Exception {
        WebhookActionsImpl webhookActions = new WebhookActionsImpl();
        String invalidURL = "https://tyson.requestcatcher.com/validPostURL";
        JSONObject eventData = new JSONObject("{\"key\": \"value\"}");
        String responseData = webhookActions.pushWebhookEvent(invalidURL, eventData);
        assertEquals(responseData, "request caught");
    }
}
