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

    public HttpResponse get(String url) {
        return HttpExecuter.get(httpClient, url);
    }

    public <T> T get(String url, Class<T> clz) {
        return jsonConverter.toObject(get(url).getEntity(), clz);
    }

    public <T> List<T> getList(String url, Class<T> clz) {
        return jsonConverter.toList(get(url).getEntity(), clz);
    }

    public <T> Map<String, T> getMap(String url, Class<T> clz) {
        return jsonConverter.toMap(get(url).getEntity(), clz);
    }

    public HttpResponse post(String url, Object object) {
        String json = jsonConverter.toJson(object);
        return HttpExecuter.post(httpClient, url, json);
    }

    public <T> T post(String url, Object object, Class<T> clz) {
        return jsonConverter.toObject(post(url, object).getEntity(), clz);
    }

    public <T> List<T> postList(String url, Object object, Class<T> clz) {
        return jsonConverter.toList(post(url, object).getEntity(), clz);
    }

    public <T> List<List<T>> postListOfList(String url, Object object, Class<T> clz) {
        return jsonConverter.toListOfList(post(url, object).getEntity(), clz);
    }

    public <T> Map<String, T> postMap(String url, Object object, Class<T> clz) {
        return jsonConverter.toMap(post(url, object).getEntity(), clz);
    }

    public HttpResponse put(String url, Object object) {
        String json = jsonConverter.toJson(object);
        return HttpExecuter.put(httpClient, url, json);
    }

    public <T> T put(String url, Object object, Class<T> clz) {
        return jsonConverter.toObject(put(url, object).getEntity(), clz);
    }

    public HttpResponse delete(String url) {
        return HttpExecuter.delete(httpClient, url);
    }

    public <T> T delete(String url, Class<T> clz) {
        return jsonConverter.toObject(delete(url).getEntity(), clz);
    }
}