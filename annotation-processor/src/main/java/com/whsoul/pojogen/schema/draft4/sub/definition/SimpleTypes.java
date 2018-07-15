package com.whsoul.pojogen.schema.draft4.sub.definition;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.ToString;

@ToString
public class SimpleTypes {

    TYPE type;

    @JsonCreator
    public SimpleTypes(String typeStr) throws JsonProcessingException {
        this.type = TYPE.valueOf("$" + typeStr);
    }

    public enum TYPE{
        $array
        ,$boolean
        ,$integer
        ,$null
        ,$number
        ,$object
        ,$string
    }

}
