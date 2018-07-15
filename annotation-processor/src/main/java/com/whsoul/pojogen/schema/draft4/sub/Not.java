package com.whsoul.pojogen.schema.draft4.sub;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.whsoul.pojogen.schema.draft4.Draft04Schema;
import com.whsoul.pojogen.schema.draft4.jackson.JsonSchemaObjectMapper;

public class Not {
    ObjectMapper om = JsonSchemaObjectMapper.get();

    Draft04Schema schema;

    @JsonCreator
    public Not(JsonNode node) throws JsonProcessingException {
        this.schema = om.treeToValue(node, Draft04Schema.class);

    }
}
