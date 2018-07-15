package com.whsoul.pojogen.schema.draft4.sub;

import com.fasterxml.jackson.annotation.JsonCreator;

//TODO implement
public class Default {

    Object value;

    @JsonCreator
    public Default(Object value){
        this.value = value;
    }

}
