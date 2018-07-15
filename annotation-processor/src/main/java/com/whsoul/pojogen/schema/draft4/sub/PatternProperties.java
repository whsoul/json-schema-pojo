package com.whsoul.pojogen.schema.draft4.sub;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.whsoul.pojogen.schema.draft4.Draft04Schema;
import com.whsoul.pojogen.schema.draft4.jackson.JsonSchemaObjectMapper;

import java.io.IOException;
import java.util.Map;

public class PatternProperties {
    ObjectMapper om = JsonSchemaObjectMapper.get();

    Map<String, Draft04Schema> value;

    @JsonCreator
    public PatternProperties(JsonNode node) throws IOException {
        JsonParser parser = om.treeAsTokens(node);
        this.value = om.readValue(parser, new TypeReference<Map<String, Draft04Schema>>(){});

    }
}
