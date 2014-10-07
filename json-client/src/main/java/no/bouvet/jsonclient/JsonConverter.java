package no.bouvet.jsonclient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.apache.http.HttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.List;

public class JsonConverter {

    private ObjectMapper objectMapper;

    public JsonConverter() {
        objectMapper = new ObjectMapper();
    }

    public JsonConverter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public void registerModule(Module module) {
        objectMapper.registerModule(module);
    }

    public <T> T toObject(HttpEntity entity, Class<T> clz) {
        return toObject(entity, ContentType.APPLICATION_JSON, clz);
    }

    public <T> T toObject(HttpEntity entity, ContentType contentType, Class<T> clz) {
        if(entity != null) {
            try {
                String entityStr = EntityUtils.toString(entity);
                if (contentType == ContentType.APPLICATION_JSON) {
                    return toObject(entityStr, clz);
                } else {
                    return (T)entityStr;
                }
            } catch (Exception e) {
                throw new RuntimeException("Error when parsing response entity to " + clz, e);
            }
        }
        return null;
    }

    public <T> List<T> toList(HttpEntity entity, Class<T> clz) {
        try {
            String entityStr = EntityUtils.toString(entity);
            return toList(entityStr, clz);
        } catch (Exception e) {
            throw new RuntimeException("Error when parsing response entity to list of " + clz, e);
        }
    }

    public String toJson(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error when converting " + object.getClass() + " to json", e);
        }
    }

    public <T> T toObject(String json, Class<T> clz) {
        try {
            return objectMapper.readValue(json, clz);
        } catch (IOException e) {
            throw new RuntimeException("Error when converting json to " + clz, e);
        }
    }

    public <T> List<T> toList(String json, Class<T> clz) {
        try {
            return objectMapper.readValue(json, TypeFactory.defaultInstance().constructParametricType(List.class, clz));
        } catch (IOException e) {
            throw new RuntimeException("Error when converting json to List<" + clz + ">", e);
        }
    }


}
