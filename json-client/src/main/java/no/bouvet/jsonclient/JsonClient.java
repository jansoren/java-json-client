package no.bouvet.jsonclient;

import com.fasterxml.jackson.databind.ObjectMapper;
import no.bouvet.jsonclient.builders.HttpSSLClientBuilder;
import no.bouvet.jsonclient.http.HttpExecuter;
import no.bouvet.jsonclient.poller.Poller;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.util.List;
import java.util.Map;

public class JsonClient {

    private JsonConverter jsonConverter;
    private HttpClient httpClient;
    private HttpResponse response;
    private long sleepInMs = 500;

    public JsonClient() {
        jsonConverter = new JsonConverter();
    }

    public JsonClient(ObjectMapper objectMapper) {
        jsonConverter = new JsonConverter(objectMapper);
    }

    public JsonClient http() {
        httpClient = HttpClientBuilder.create().build();
        return this;
    }

    public JsonClient ssl() {
        httpClient = new HttpSSLClientBuilder().build();
        return this;
    }

    public JsonClient ssl(String username, String password){
        httpClient = new HttpSSLClientBuilder().withAuthentication(username, password).build();
        return this;
    }

    public HttpResponse response() {
        return response;
    }

    public <T> T object(Class<T> clz) {
        return jsonConverter.toObject(response.getEntity(), clz);
    }

    public <T> List<T> list(Class<T> clz) {
        return jsonConverter.toList(response.getEntity(), clz);
    }

    public <T> List<List<T>> listOfList(Class<T> clz) {
        return jsonConverter.toListOfList(response.getEntity(), clz);
    }

    public <T> Map<String, T> map(Class<T> clz) {
        return jsonConverter.toMap(response.getEntity(), clz);
    }

    public <T> T poll(String url, Class<T> clz, long timeoutInMs) {
        long startTime = System.currentTimeMillis();
        long endTime = startTime;

        T object = executeGet(url, clz);
        while (object == null && endTime - startTime < timeoutInMs) {
            threadSleep();
            object = executeGet(url, clz);
            endTime = System.currentTimeMillis();
        }
        return object;
    }

    public <T> T poll(String url, Class<? extends Poller> clz, long timeoutInMs, Object... conditions) {
        long startTime = System.currentTimeMillis();
        long endTime = startTime;

        Poller poller = executeGet(url, clz);
        boolean isConditionFulfilled = isConditionFulfilled(poller, conditions);
        while ((poller == null || !isConditionFulfilled) && endTime - startTime < timeoutInMs) {
            threadSleep();
            poller = executeGet(url, clz);
            isConditionFulfilled = isConditionFulfilled(poller, conditions);
            endTime = System.currentTimeMillis();
        }
        return (T) poller;
    }

    public JsonClient get(String url) {
        if(httpClient != null) {
            response = HttpExecuter.get(httpClient, url);
            return this;
        } else {
            throw new RuntimeException(getHttpClientIsNullError());
        }
    }

    public JsonClient post(String url, Object object) {
        if(httpClient != null) {
            String json = jsonConverter.toJson(object);
            response = HttpExecuter.post(httpClient, url, json);
            return this;
        } else {
            throw new RuntimeException(getHttpClientIsNullError());
        }
    }

    public JsonClient put(String url, Object object) {
        if(httpClient != null) {
            String json = jsonConverter.toJson(object);
            response = HttpExecuter.put(httpClient, url, json);
            return this;
        } else {
            throw new RuntimeException(getHttpClientIsNullError());
        }
    }

    public JsonClient delete(String url) {
        if(httpClient != null) {
            response = HttpExecuter.delete(httpClient, url);
            return this;
        } else {
            throw new RuntimeException(getHttpClientIsNullError());
        }
    }

    private String getHttpClientIsNullError() {
        return "Http client has not been created. Call method 'http()' or 'ssl()' on your JsonClient";
    }

    private <T> T executeGet(String url, Class<T> clz) {
        HttpResponse response = HttpExecuter.get(httpClient, url);
        return jsonConverter.toObject(response.getEntity(), clz);
    }

    private void threadSleep() {
        try {
            Thread.sleep(sleepInMs);
        } catch (InterruptedException e) {}
    }

    private boolean isConditionFulfilled(Poller poller, Object... conditions) {
        if(poller != null) {
            return poller.isConditionFulfilled(conditions);
        }
        return false;
    }
}