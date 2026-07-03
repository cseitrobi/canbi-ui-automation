package com.pilla.automation.mail;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.time.Duration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Mail.tm implementation using only the REST API (no Mercure/SSE).
 * Avoids 502 errors from Mail.tm's Mercure real-time stream by polling GET /messages.
 *
 * @see <a href="https://docs.mail.tm/getting-started/authentication">Mail.tm Authentication</a>
 * @see <a href="https://docs.mail.tm/api/messages">Mail.tm Messages API</a>
 */
public final class MailTmService {

    private static final String API_BASE = "https://api.mail.tm";
    private static final String PASSWORD = "aVeryStrongPassword!123";
    private static final Pattern ACTIVATION_LINK = Pattern.compile("Activate my account\\s*\\[(https?://[^\\s\\]]+)\\]");
    private static final int POLL_TIMEOUT_SECONDS = 120;
    private static final int POLL_INTERVAL_SECONDS = 5;

    private final String emailAddress;
    private final String bearerToken;
    private final HttpClient httpClient;

    public MailTmService() {
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(15))
                .build();
        String domain = fetchDomain();
        String localPart = "canb" + (100_000 + new SecureRandom().nextInt(899_000));
        this.emailAddress = localPart + "@" + domain;
        createAccount(emailAddress, PASSWORD);
        this.bearerToken = fetchToken(emailAddress, PASSWORD);
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    /**
     * Polls the Mail.tm API for new messages and extracts the activation URL from the first
     * matching email. Does not use Mercure (avoids 502 from mercure.mail.tm).
     */
    public String getActivationUrl() {
        long deadline = System.currentTimeMillis() + POLL_TIMEOUT_SECONDS * 1000L;
        while (System.currentTimeMillis() < deadline) {
            String url = findActivationUrlInMessages();
            if (url != null) {
                return url;
            }
            try {
                Thread.sleep(POLL_INTERVAL_SECONDS * 1000L);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException("Interrupted while waiting for activation email", e);
            }
        }
        return null;
    }

    private String fetchDomain() {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_BASE + "/domains"))
                .GET()
                .build();
        HttpResponse<String> response = send(request);
        if (response.statusCode() != 200) {
            throw new RuntimeException("Failed to fetch domains: " + response.statusCode() + " " + response.body());
        }
        JsonObject json = JsonParser.parseString(response.body()).getAsJsonObject();
        JsonArray members = json.getAsJsonArray("hydra:member");
        if (members == null || members.isEmpty()) {
            throw new RuntimeException("No domains returned by Mail.tm");
        }
        return members.get(0).getAsJsonObject().get("domain").getAsString();
    }

    private void createAccount(String address, String password) {
        String body = String.format("{\"address\":\"%s\",\"password\":\"%s\"}",
                address.replace("\"", "\\\""),
                password.replace("\\", "\\\\").replace("\"", "\\\""));
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_BASE + "/accounts"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(body, StandardCharsets.UTF_8))
                .build();
        HttpResponse<String> response = send(request);
        if (response.statusCode() != 201) {
            throw new RuntimeException("Failed to create Mail.tm account: " + response.statusCode() + " " + response.body());
        }
    }

    private String fetchToken(String address, String password) {
        String body = String.format("{\"address\":\"%s\",\"password\":\"%s\"}",
                address.replace("\"", "\\\""),
                password.replace("\\", "\\\\").replace("\"", "\\\""));
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_BASE + "/token"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(body, StandardCharsets.UTF_8))
                .build();
        HttpResponse<String> response = send(request);
        if (response.statusCode() != 200) {
            throw new RuntimeException("Failed to get Mail.tm token: " + response.statusCode() + " " + response.body());
        }
        JsonObject json = JsonParser.parseString(response.body()).getAsJsonObject();
        return json.get("token").getAsString();
    }

    private String findActivationUrlInMessages() {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_BASE + "/messages"))
                .header("Authorization", "Bearer " + bearerToken)
                .GET()
                .build();
        HttpResponse<String> response = send(request);
        if (response.statusCode() != 200) {
            return null;
        }
        JsonObject json = JsonParser.parseString(response.body()).getAsJsonObject();
        JsonArray members = json.getAsJsonArray("hydra:member");
        if (members == null || members.isEmpty()) {
            return null;
        }
        for (JsonElement el : members) {
            String id = el.getAsJsonObject().get("id").getAsString();
            String body = fetchMessageBody(id);
            if (body != null) {
                Matcher m = ACTIVATION_LINK.matcher(body);
                if (m.find()) {
                    return m.group(1);
                }
            }
        }
        return null;
    }

    private String fetchMessageBody(String messageId) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_BASE + "/messages/" + messageId))
                .header("Authorization", "Bearer " + bearerToken)
                .GET()
                .build();
        HttpResponse<String> response = send(request);
        if (response.statusCode() != 200) {
            return null;
        }
        JsonObject json = JsonParser.parseString(response.body()).getAsJsonObject();
        if (json.has("text") && !json.get("text").isJsonNull()) {
            return json.get("text").getAsString();
        }
        if (json.has("html")) {
            JsonElement html = json.get("html");
            if (html.isJsonArray()) {
                StringBuilder sb = new StringBuilder();
                for (JsonElement e : html.getAsJsonArray()) {
                    sb.append(e.getAsString());
                }
                return sb.toString();
            }
            return html.getAsString();
        }
        return null;
    }

    private HttpResponse<String> send(HttpRequest request) {
        try {
            return httpClient.send(request, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
        } catch (Exception e) {
            throw new RuntimeException("Mail.tm API request failed: " + e.getMessage(), e);
        }
    }
}
