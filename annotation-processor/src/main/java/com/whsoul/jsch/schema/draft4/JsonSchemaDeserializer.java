package com.whsoul.jsch.schema.draft4;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

public class JsonSchemaDeserializer extends StdDeserializer<JsonSchema> {

    public JsonSchemaDeserializer() {
        this(null);
    }

    public JsonSchemaDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public JsonSchema deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {

        ObjectMapper om = (ObjectMapper)jsonParser.getCodec();
        JsonNode node = om.readTree(jsonParser);

        if(node.get("$ref") != null) {
            return om.treeToValue(node, SchemaRef.class);
        }
        return om.treeToValue(node, SchemaObject.class);
    }


}
