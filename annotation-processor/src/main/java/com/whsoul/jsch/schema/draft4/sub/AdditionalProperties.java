package com.whsoul.jsch.schema.draft4.sub;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.whsoul.jsch.schema.draft4.sub.definition.Value;
import com.whsoul.jsch.schema.draft4.sub.polym.AnyOfValue;

import java.util.ArrayList;
import java.util.List;

public class AdditionalProperties extends AnyOfValue{

    @JsonCreator
    public AdditionalProperties(JsonNode node) throws JsonProcessingException {
        if(node.isBoolean()){
            this.set(new Value.BooleanValue(node.asBoolean()));
        }else{
            this.set(new Value.SchemaValue(node));
        }
    }

    @Override
    public List<Class<? extends Value>> availableItems() {
        return new ArrayList<Class<? extends Value>>(){{
            add(Value.BooleanValue.class);
            add(Value.SchemaValue.class);
        }};
    }
}
