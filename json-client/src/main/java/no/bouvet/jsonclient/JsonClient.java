package no.bouvet.jsonclient;

import com.fasterxml.jackson.databind.ObjectMapper;
import no.bouvet.jsonclient.builders.HttpSSLClientBuilder;
import no.bouvet.jsonclient.http.HttpExecuter;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.util.List;
import java.util.Map;

public class JsonClient {

    private JsonConverter jsonConverter;
    private HttpClient httpClient;
    private HttpResponse response;

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
}