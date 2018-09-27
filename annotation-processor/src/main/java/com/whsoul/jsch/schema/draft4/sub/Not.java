package com.whsoul.jsch.schema.draft4.sub;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.whsoul.jsch.schema.draft4.JsonSchema;
import com.whsoul.jsch.schema.draft4.jackson.JsonSchemaObjectMapper;

public class Not {
    ObjectMapper om = JsonSchemaObjectMapper.get();

    public JsonSchema schema;

    @JsonCreator
    public Not(JsonNode node) throws JsonProcessingException {
        this.schema = om.treeToValue(node, JsonSchema.class);

    }
}
