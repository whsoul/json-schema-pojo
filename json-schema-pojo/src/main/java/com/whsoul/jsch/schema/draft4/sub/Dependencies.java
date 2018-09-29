package com.whsoul.jsch.schema.draft4.sub;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.whsoul.jsch.schema.draft4.jackson.JsonSchemaObjectMapper;
import com.whsoul.jsch.schema.draft4.sub.definition.Value;
import com.whsoul.jsch.schema.draft4.sub.polym.AnyOfValue;

import java.io.IOException;
import java.util.*;

public class Dependencies extends AnyOfValue {
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

    @Override
    public List<Class<? extends Value>> availableItems() {
        return new ArrayList<Class<? extends Value>>(){{
            add(Value.SchemaArrayValue.class);
            add(Value.SchemaValue.class);
        }};
    }
}