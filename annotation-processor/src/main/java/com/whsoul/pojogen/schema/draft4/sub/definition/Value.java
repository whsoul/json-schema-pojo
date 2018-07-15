package com.whsoul.pojogen.schema.draft4.sub.definition;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.whsoul.pojogen.schema.draft4.Draft04Schema;
import com.whsoul.pojogen.schema.draft4.jackson.JsonSchemaObjectMapper;
import lombok.ToString;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Value{
    ObjectMapper om = JsonSchemaObjectMapper.get();

    public static class BooleanValue extends Value {
        public boolean value;

        public BooleanValue(boolean value){
            this.value = value;
        }
    }

    public static class StringArrayValue extends Value {
        public StringArray stringArray;

        public StringArrayValue(JsonNode node) throws IOException {
            JsonParser parser = om.treeAsTokens(node);
            List<String> listValue = om.readValue(parser, new TypeReference<List<String>>(){});
            this.stringArray = new StringArray(listValue);
        }
    }

    public static class SchemaValue extends Value {
        public Draft04Schema schema;

        public SchemaValue(JsonNode node) throws JsonProcessingException {
            this.schema = om.treeToValue(node, Draft04Schema.class);
        }
    }

    public static class SchemaArrayValue extends Value{
        public SchemaArray schemaArray;

        public SchemaArrayValue(JsonNode node) throws JsonProcessingException {
            this.schemaArray = new SchemaArray(om.treeToValue(node, List.class));
        }
    }

    @ToString
    public static class SimpleTypeValue extends Value{
        public SimpleTypes simpleTypes;

        //todo minItem 1
        public SimpleTypeValue(JsonNode node) throws IOException {
            this.simpleTypes = new SimpleTypes(node.asText());
        }
    }


    public static class SimpleTypeArrayValue extends Value{
        Set<SimpleTypes.TYPE> simpleArrays;

        //todo minItem 1
        public SimpleTypeArrayValue(JsonNode node) throws IOException {
            JsonParser parser = om.treeAsTokens(node);
            List<String> listValue = om.readValue(parser, new TypeReference<List<String>>(){});
            this.simpleArrays = listValue.stream().map(s -> SimpleTypes.TYPE.valueOf(s)).collect(Collectors.toSet());
        }
    }

}
