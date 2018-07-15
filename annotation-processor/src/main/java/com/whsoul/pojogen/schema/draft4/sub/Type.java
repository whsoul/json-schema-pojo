package com.whsoul.pojogen.schema.draft4.sub;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.JsonNode;
import com.whsoul.pojogen.schema.draft4.sub.definition.Value;
import com.whsoul.pojogen.schema.draft4.sub.polym.AnyOfValue2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Type extends AnyOfValue2 {

    //todo minItem1, only String? check
    @JsonCreator
    public Type(JsonNode node) throws IOException {
        if(node.isArray()){
            this.set(new Value.SimpleTypeArrayValue(node));
        }else{
            this.set(new Value.SimpleTypeValue(node));
        }
    }

    @Override
    public List<Class<? extends Value>> availableItems() {
        return new ArrayList<Class<? extends Value>>(){{
            add(Value.SimpleTypeValue.class);
            add(Value.SimpleTypeArrayValue.class);
        }};
    }
}
