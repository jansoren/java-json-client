package no.bouvet.jsonclient;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;

public class JsonClientObjectMapper extends ObjectMapper {

    public JsonClientObjectMapper() {
        registerModule(new JodaModule());
    }
}
