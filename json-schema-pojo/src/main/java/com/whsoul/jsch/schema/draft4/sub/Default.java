package com.whsoul.jsch.schema.draft4.sub;

import com.fasterxml.jackson.annotation.JsonCreator;

//TODO implement no schema restrict @ http://json-schema.org/draft-04/schema#
public class Default {

    public Object value;

    @JsonCreator
    public Default(Object value){
        this.value = value;
    }

}
