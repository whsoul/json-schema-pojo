package com.whsoul.jsch.schema.draft4.sub.definition;

import com.fasterxml.jackson.annotation.JsonCreator;

import javax.validation.constraints.Min;

public class PositiveInteger{

    @Min(0)
    int value;

    @JsonCreator
    public PositiveInteger(int value){
        this.value = value;
    }
}
