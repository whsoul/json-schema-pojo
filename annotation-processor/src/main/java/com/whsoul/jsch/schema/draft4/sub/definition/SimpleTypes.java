package com.whsoul.jsch.schema.draft4.sub.definition;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.ToString;

@ToString
public class SimpleTypes {

    public TYPE type;
    String typeStr;

    @JsonCreator
    public SimpleTypes(String typeStr){
        try {
            this.type = TYPE.valueOf("$" + typeStr);
        }catch (IllegalArgumentException illarg){
            this.type = TYPE.$custom;
        }
        this.typeStr = typeStr;
    }

    public enum TYPE{
        $boolean
        ,$integer
        ,$null
        ,$number
        ,$object
        ,$string
        ,$array       //why array is simpleType...??

        ,$custom;      //no entry in spec
    }

    public String getOrginalName(){
        return this.typeStr;
    }
}
