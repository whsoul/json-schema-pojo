package com.whsoul.pojogen.schema.draft4.sub;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.whsoul.pojogen.schema.draft4.sub.definition.Value;
import com.whsoul.pojogen.schema.draft4.sub.polym.AnyOfValue;

public class AdditionalProperties{
    AnyOfValue anyValue = new AnyOfValue(Value.BooleanValue.class, Value.SchemaValue.class);

    @JsonCreator
    public AdditionalProperties(JsonNode node) throws JsonProcessingException {
        if(node.isBoolean()){
            this.anyValue.set(new Value.BooleanValue(node.asBoolean()));
        }else{
            this.anyValue.set(new Value.SchemaValue(node));
        }
    }
}
