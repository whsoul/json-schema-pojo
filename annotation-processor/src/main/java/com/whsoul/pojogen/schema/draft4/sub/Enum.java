package com.whsoul.pojogen.schema.draft4.sub;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.whsoul.pojogen.schema.draft4.jackson.JsonSchemaObjectMapper;

import java.io.IOException;
import java.util.Set;

public class Enum {
    ObjectMapper om = JsonSchemaObjectMapper.get();

    Set<String> value;
    //todo minItem1, only String? check
    @JsonCreator
    public Enum(JsonNode node) throws IOException {
        JsonParser parser = om.treeAsTokens(node);
        value = om.readValue(parser, new TypeReference<Set<String>>(){});
    }

}
