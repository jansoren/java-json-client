package no.bouvet.jsonclient.spring;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import org.springframework.http.converter.json.Jackson2ObjectMapperFactoryBean;

public class JsonClientJackson2ObjectMapperFactoryBean extends Jackson2ObjectMapperFactoryBean {

    public JsonClientJackson2ObjectMapperFactoryBean() {
        setIndentOutput(true);
        afterPropertiesSet();
        ObjectMapper objectMapper = getObject();
        objectMapper.registerModule(new JodaModule());
    }
}