package com.whsoul.jsch.schema.draft4.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.whsoul.jsch.schema.draft4.JsonSchemaDeserializer;
import com.whsoul.jsch.schema.draft4.JsonSchema;

public class JsonSchemaObjectMapper {

    private JsonSchemaObjectMapper(){};

    public static ObjectMapper get(){
        return JsonSchemaObjectMapperHolder.objectMapper;
    }

    public static class JsonSchemaObjectMapperHolder{
        private static final ObjectMapper objectMapper = new ObjectMapper();
        static{
            SimpleModule simpleModule = new SimpleModule();
            simpleModule.addDeserializer(JsonSchema.class, new JsonSchemaDeserializer());
            objectMapper.registerModule(simpleModule);
        }
    }
}
