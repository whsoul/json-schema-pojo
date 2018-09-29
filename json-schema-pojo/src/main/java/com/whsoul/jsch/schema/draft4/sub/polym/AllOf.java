package com.whsoul.jsch.schema.draft4.sub.polym;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.whsoul.jsch.schema.draft4.JsonSchema;
import com.whsoul.jsch.schema.draft4.jackson.JsonSchemaObjectMapper;

import java.io.IOException;
import java.util.List;

public class AllOf {
    ObjectMapper om = JsonSchemaObjectMapper.get();

    List<JsonSchema> value;

    @JsonCreator
    public AllOf(JsonNode node) throws IOException {
        JsonParser parser = om.treeAsTokens(node);
        value = om.readValue(parser, new TypeReference<List<JsonSchema>>(){});
    }

}
