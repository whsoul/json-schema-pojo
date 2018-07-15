package com.whsoul.pojogen.schema.draft4.sub;

import com.fasterxml.jackson.annotation.JsonCreator;

import javax.validation.constraints.Min;

//TODO implement
public class MultipleOf {

    @Min(0)
    long value;

    @JsonCreator
    public MultipleOf(long value){
        this.value = value;
    }

}
