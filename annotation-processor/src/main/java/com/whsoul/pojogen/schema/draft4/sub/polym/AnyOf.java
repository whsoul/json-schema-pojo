package com.whsoul.pojogen.schema.draft4.sub.polym;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.whsoul.pojogen.schema.draft4.Draft04Schema;
import com.whsoul.pojogen.schema.draft4.jackson.JsonSchemaObjectMapper;

import java.io.IOException;
import java.util.List;

public class AnyOf {
    ObjectMapper om = JsonSchemaObjectMapper.get();

    List<Draft04Schema> value;

    @JsonCreator
    public AnyOf(JsonNode node) throws IOException {
        JsonParser parser = om.treeAsTokens(node);
        value = om.readValue(parser, new TypeReference<List<Draft04Schema>>(){});
    }

}
