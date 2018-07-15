package com.whsoul.pojogen.schema.draft4.sub;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.whsoul.pojogen.schema.draft4.sub.definition.Value;
import com.whsoul.pojogen.schema.draft4.sub.polym.AnyOfValue;

public class Items{
    AnyOfValue anyValue = new AnyOfValue(Value.SchemaValue.class, Value.SchemaValue.class);

    @JsonCreator
    public Items(JsonNode node) throws JsonProcessingException {
        if(node.isArray()){
            this.anyValue.set(new Value.SchemaArrayValue(node));
        }else{
            this.anyValue.set(new Value.SchemaValue(node));
        }
    }

}
