package com.whsoul.jsch.schema.draft4.sub.definition;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.whsoul.jsch.schema.draft4.JsonSchema;
import com.whsoul.jsch.schema.draft4.SchemaRef;
import com.whsoul.jsch.schema.draft4.jackson.JsonSchemaObjectMapper;
import lombok.ToString;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class Value<T>{
    ObjectMapper om = JsonSchemaObjectMapper.get();
    T value;
    public T getValue(){
        return value;
    }

    public static class BooleanValue extends Value<Boolean> {

        public BooleanValue(boolean value){
            this.value = value;
        }
    }

    public static class StringArrayValue extends Value<StringArray> {

        public StringArrayValue(JsonNode node) throws IOException {
            JsonParser parser = om.treeAsTokens(node);
            List<String> listValue = om.readValue(parser, new TypeReference<List<String>>(){});
            this.value = new StringArray(listValue);
        }
    }

    public static class SchemaValue extends Value<JsonSchema> {

        public SchemaValue(JsonNode node) throws JsonProcessingException {
            this.value = om.treeToValue(node, JsonSchema.class);
        }
    }

    public static class SchemaArrayValue extends Value<SchemaArray>{

        public SchemaArrayValue(JsonNode node) throws JsonProcessingException {
            this.value = om.treeToValue(node, SchemaArray.class);
        }
    }

    @ToString
    public static class SimpleTypeValue extends Value<SimpleTypes>{

        //todo minItem 1
        public SimpleTypeValue(JsonNode node){
            this.value = new SimpleTypes(node.asText());
        }
    }


    public static class SimpleTypeArrayValue extends Value<SimpleTypeArrayValue>{
        Set<SimpleTypes.TYPE> simpleArrays;

        //todo minItem 1
        public SimpleTypeArrayValue(JsonNode node) throws IOException {
            JsonParser parser = om.treeAsTokens(node);
            List<String> listValue = om.readValue(parser, new TypeReference<List<String>>(){});
            this.simpleArrays = listValue.stream().map(s -> SimpleTypes.TYPE.valueOf(s)).collect(Collectors.toSet());
        }
    }

}
