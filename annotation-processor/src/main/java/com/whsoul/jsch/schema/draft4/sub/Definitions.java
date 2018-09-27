package com.whsoul.jsch.schema.draft4.sub;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.whsoul.jsch.schema.draft4.JsonSchema;
import com.whsoul.jsch.schema.draft4.jackson.JsonSchemaObjectMapper;

import java.io.IOException;
import java.util.Map;

public class Definitions {
    ObjectMapper om = JsonSchemaObjectMapper.get();

    public Map<String, JsonSchema> map;

    public JsonSchema get(String key){
        return this.map.get(key);
    }

    @JsonCreator
    public Definitions(JsonNode node) throws IOException {
        JsonParser parser = om.treeAsTokens(node);
        this.map = om.readValue(parser, new TypeReference<Map<String, JsonSchema>>(){});
    }
}
