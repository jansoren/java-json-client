package no.bouvet.jsonclient;

import com.fasterxml.jackson.databind.ObjectMapper;
import no.bouvet.jsonclient.builders.HttpSSLClientBuilder;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.util.List;
import java.util.Map;

public class JsonClient {

    private JsonConverter jsonConverter;
    private HttpClient httpClient;

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

    public <T> T get(String url, Class<T> clz) {
        HttpResponse response = HttpExecuter.get(httpClient, url);
        return jsonConverter.toObject(response.getEntity(), clz);
    }

    public <T> List<T> getList(String url, Class<T> clz) {
        HttpResponse response = HttpExecuter.get(httpClient, url);
        return jsonConverter.toList(response.getEntity(), clz);
    }

    public <T> Map<String, T> getMap(String url, Class<T> clz) {
        HttpResponse response = HttpExecuter.get(httpClient, url);
        return jsonConverter.toMap(response.getEntity(), clz);
    }

    public <T> T post(String url, String json, Class<T> clz) {
        HttpResponse response = HttpExecuter.post(httpClient, url, json);
        return jsonConverter.toObject(response.getEntity(), clz);
    }

    public <T> T put(String url, String json, Class<T> clz) {
        HttpResponse response = HttpExecuter.put(httpClient, url, json);
        return jsonConverter.toObject(response.getEntity(), clz);
    }

    public <T> T delete(String url, Class<T> clz) {
        HttpResponse response = HttpExecuter.delete(httpClient, url);
        return jsonConverter.toObject(response.getEntity(), clz);
    }
}