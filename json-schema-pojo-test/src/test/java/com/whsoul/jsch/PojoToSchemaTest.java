package com.whsoul.jsch;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kjetland.jackson.jsonSchema.JsonSchemaGenerator;
import com.squareup.javapoet.ClassName;
import com.whsoul.jsch.pojo.Pojo01;
import com.whsoul.jsch.pojo.Pojo02;
import com.whsoul.jsch.pojo.Pojo03;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

public class PojoToSchemaTest {

    @Test
    public void pojo01() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonSchemaGenerator jsonSchemaGenerator = new JsonSchemaGenerator(objectMapper);
        JsonNode jsonSchema = jsonSchemaGenerator.generateJsonSchema(Pojo01.class);

        String jsonSchemaAsString = objectMapper.writeValueAsString(jsonSchema);

        byte[] jsonSchemaByte = objectMapper.writeValueAsBytes(jsonSchema);

        ByteArrayInputStream isr = new ByteArrayInputStream(jsonSchemaByte);

        System.out.println(jsonSchemaAsString);

        JsonSchemaPojoGenerator pojoGenerator = new JsonSchemaPojoGenerator("com.whsoul.jsch.pojo", "Pojo01", isr);

        Map<ClassName, String> result = pojoGenerator.generate();

        System.out.println(result);
    }


    @Test
    public void pojo02() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonSchemaGenerator jsonSchemaGenerator = new JsonSchemaGenerator(objectMapper);

        JsonNode jsonSchema = jsonSchemaGenerator.generateJsonSchema(Pojo02.class);
        String jsonSchemaAsString = objectMapper.writeValueAsString(jsonSchema);
        byte[] jsonSchemaByte = objectMapper.writeValueAsBytes(jsonSchema);
        ByteArrayInputStream isr = new ByteArrayInputStream(jsonSchemaByte);

        System.out.println(jsonSchemaAsString);

        JsonSchemaPojoGenerator pojoGenerator = new JsonSchemaPojoGenerator("com.whsoul.jsch.pojo", "Pojo01", isr);
        Map<ClassName, String> result = pojoGenerator.generate();
        System.out.println(result);

    }


    @Test
    public void pojo03() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonSchemaGenerator jsonSchemaGenerator = new JsonSchemaGenerator(objectMapper);

        JsonNode jsonSchema = jsonSchemaGenerator.generateJsonSchema(Pojo03.class);
        String jsonSchemaAsString = objectMapper.writeValueAsString(jsonSchema);
        byte[] jsonSchemaByte = objectMapper.writeValueAsBytes(jsonSchema);
        ByteArrayInputStream isr = new ByteArrayInputStream(jsonSchemaByte);

        System.out.println(jsonSchemaAsString);

        JsonSchemaPojoGenerator pojoGenerator = new JsonSchemaPojoGenerator("com.whsoul.jsch.pojo", "Pojo03", isr);
        Map<ClassName, String> result = pojoGenerator.generate();
        System.out.println(result);

    }
}
