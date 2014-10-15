package no.bouvet.jsonclient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;
import org.springframework.http.converter.json.Jackson2ObjectMapperFactoryBean;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class JsonConverter {

    private ObjectMapper objectMapper;

    public JsonConverter() {
        objectMapper = createObjectMapper();
        objectMapper.registerModule(new JodaModule());
    }

    public JsonConverter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public void registerModule(Module module) {
        objectMapper.registerModule(module);
    }

    public <T> T toObject(HttpEntity entity, Class<T> clz) {
        try {
            String entityStr = EntityUtils.toString(entity);
            if(entityStr != null && !entityStr.isEmpty()) {
                return toObject(entityStr, clz);
            }
            return null;
        } catch (Exception e) {
            throw new RuntimeException("Error when parsing response entity to " + clz, e);
        } finally {
            EntityUtils.consumeQuietly(entity);
        }
    }

    public <T> List<T> toList(HttpEntity entity, Class<T> clz) {
        try {
            String entityStr = EntityUtils.toString(entity);
            if(entityStr != null && !entityStr.isEmpty()) {
                return toList(entityStr, clz);
            }
            return null;
        } catch (Exception e) {
            throw new RuntimeException("Error when parsing response entity to list of " + clz, e);
        } finally {
            EntityUtils.consumeQuietly(entity);
        }
    }

    public <T> List<List<T>> toListOfList(HttpEntity entity, Class<T> clz) {
        try {
            String entityStr = EntityUtils.toString(entity);
            if(entityStr != null && !entityStr.isEmpty()) {
                return toListOfList(entityStr, clz);
            }
            return null;
        } catch (Exception e) {
            throw new RuntimeException("Error when parsing response entity to list of " + clz, e);
        } finally {
            EntityUtils.consumeQuietly(entity);
        }
    }

    public <T> Map<String, T> toMap(HttpEntity entity, Class<T> clz) {
        try {
            String entityStr = EntityUtils.toString(entity);
            if(entityStr != null && !entityStr.isEmpty()) {
                return toMap(entityStr, clz);
            }
            return null;
        } catch (Exception e) {
            throw new RuntimeException("Error when parsing response entity to map of " + clz, e);
        } finally {
            EntityUtils.consumeQuietly(entity);
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
            return objectMapper.readValue(json, getTypeFactory().constructParametricType(List.class, clz));
        } catch (IOException e) {
            throw new RuntimeException("Error when converting json to List<" + clz + ">", e);
        }
    }

    public <T> List<List<T>> toListOfList(String json, Class<T> clz) {
        try {
            return objectMapper.readValue(json, getTypeFactory().constructParametricType(List.class, getTypeFactory().constructParametricType(List.class, clz)));
        } catch (IOException e) {
            throw new RuntimeException("Error when converting json to List<" + clz + ">", e);
        }
    }

    public <T> Map<String, T> toMap(String json, Class<T> clz) {
        try {
            return objectMapper.readValue(json, new TypeReference<Map<String, T>>(){});
        } catch (IOException e) {
            throw new RuntimeException("Error when converting json to Map<String, " + clz + ">", e);
        }
    }

    private TypeFactory getTypeFactory() {
        return objectMapper.getTypeFactory();
    }

    private ObjectMapper createObjectMapper() {
        Jackson2ObjectMapperFactoryBean bean = new Jackson2ObjectMapperFactoryBean();
        bean.setIndentOutput(true);
        bean.setSimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        bean.afterPropertiesSet();
        return bean.getObject();
    }

}
