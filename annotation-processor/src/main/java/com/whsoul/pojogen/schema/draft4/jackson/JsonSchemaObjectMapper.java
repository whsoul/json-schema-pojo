package com.whsoul.pojogen.schema.draft4.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.whsoul.pojogen.schema.draft4.JsonSchemaDeserializer;
import com.whsoul.pojogen.schema.draft4.Draft04Schema;

public class JsonSchemaObjectMapper {

    private JsonSchemaObjectMapper(){};

    public static ObjectMapper get(){
        return JsonSchemaObjectMapperHolder.objectMapper;
    }

    public static class JsonSchemaObjectMapperHolder{
        private static final ObjectMapper objectMapper = new ObjectMapper();
        static{
            SimpleModule simpleModule = new SimpleModule();
            simpleModule.addDeserializer(Draft04Schema.class, new JsonSchemaDeserializer());
            objectMapper.registerModule(simpleModule);
        }
    }
}
