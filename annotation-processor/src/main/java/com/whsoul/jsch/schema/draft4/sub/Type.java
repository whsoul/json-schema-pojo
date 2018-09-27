package com.whsoul.jsch.schema.draft4.sub;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.JsonNode;
import com.whsoul.jsch.schema.draft4.sub.definition.SimpleTypes;
import com.whsoul.jsch.schema.draft4.sub.definition.Value;
import com.whsoul.jsch.schema.draft4.sub.polym.AnyOfValue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Type extends AnyOfValue {
    public boolean isObjectSimpleType(){
        return isSimpleType() && SimpleTypes.TYPE.$object.equals(this.getAnyOfValue(Value.SimpleTypeValue.class).getValue().type);
    }

    public boolean isSimpleType(){
        return this.typeOf(Value.SimpleTypeValue.class);
    }

    public boolean isSimpleTypeExcludeObject(){
        return isSimpleType() && !isObjectSimpleType();
    }

    public boolean isSchemaArrayType(){
        return isSimpleType()
                && SimpleTypes.TYPE.$array.equals(this.getAnyOfValue(Value.SimpleTypeValue.class).getValue().type);
    }

    public boolean isSimpleType(SimpleTypes.TYPE simpleType){
        if(isSimpleType() && simpleType.equals(this.getAnyOfValue(Value.SimpleTypeValue.class).getValue().type)){
            return true;
        }
        return false;
    }

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
