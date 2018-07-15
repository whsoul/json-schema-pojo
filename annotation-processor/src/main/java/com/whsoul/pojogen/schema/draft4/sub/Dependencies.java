package com.whsoul.pojogen.schema.draft4.sub;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.whsoul.pojogen.schema.draft4.jackson.JsonSchemaObjectMapper;
import com.whsoul.pojogen.schema.draft4.sub.definition.Value;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
public class Dependencies {
    ObjectMapper om = JsonSchemaObjectMapper.get();

    Map<String, Value> value;

    @JsonCreator
    public Dependencies(JsonNode node) throws IOException {
        //TODO Map anyOf 처리
        this.value = new LinkedHashMap<>();
        for (Iterator<String> it = node.fieldNames(); it.hasNext(); ) {
            String key = it.next();
            JsonNode dataNode = node.get(key);

            if(dataNode.isArray()) {
                this.value.put(key, new Value.StringArrayValue(dataNode));
            }else{
                this.value.put(key, new Value.SchemaValue(dataNode));
            }
        }
    }

}