package com.whsoul.jsch.schema.draft4.sub;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.whsoul.jsch.schema.draft4.JsonSchema;
import com.whsoul.jsch.schema.draft4.jackson.JsonSchemaObjectMapper;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

public class Enum {
    ObjectMapper om = JsonSchemaObjectMapper.get();

    private Set<String> items;

    public Set<String> getItems() {
        return items;
    }

    //todo minItem1, only String? check
    @JsonCreator
    public Enum(JsonNode node) throws IOException {
        JsonParser parser = om.treeAsTokens(node);
        items = om.readValue(parser, new TypeReference<Set<String>>(){});
    }

    @Override
    public String toString(){
        try {
            return om.writeValueAsString(items);
        } catch (JsonProcessingException e) {
            return items.toString();
        }
    }

}
