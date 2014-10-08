package no.bouvet.jsonclient;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;

public class HttpExecuter {

    public static HttpResponse get(HttpClient httpClient, String url) {
        return execute(httpClient, createHttpGet(url));
    }

    public static HttpResponse post(HttpClient httpClient, String url, String json) {
        return execute(httpClient, createHttpPost(url, json));
    }

    public static HttpResponse put(HttpClient httpClient, String url, String json) {
        return execute(httpClient, createHttpPut(url, json));
    }

    public static HttpResponse delete(HttpClient httpClient, String url) {
        return execute(httpClient, createHttpDelete(url));
    }

    private static HttpResponse execute(HttpClient httpClient, HttpUriRequest request) {
        try {
            return httpClient.execute(request);
        } catch (Exception e) {
            throw new RuntimeException("Error when executing " + request, e);
        }
    }

    private static HttpGet createHttpGet(String url) {
        return new HttpGet(url);
    }

    private static HttpPost createHttpPost(String url, String json) {
        HttpPost request = new HttpPost(url);
        if (json != null) {
            request.setEntity(new StringEntity(json, ContentType.APPLICATION_JSON));
        }
        return request;
    }

    private static HttpPut createHttpPut(String url, String json) {
        HttpPut request = new HttpPut(url);
        if (json != null) {
            request.setEntity(new StringEntity(json, ContentType.APPLICATION_JSON));
        }
        return request;
    }

    private static HttpDelete createHttpDelete(String url) {
        return new HttpDelete(url);
    }
}
