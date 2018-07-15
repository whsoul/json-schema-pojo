package com.whsoul.pojogen.schema.draft4.sub.definition;

import com.fasterxml.jackson.annotation.JsonCreator;

import javax.validation.constraints.Min;

public class PositiveIntegerDefault0 {

    @Min(0)
    int value = 0;

    @JsonCreator
    public PositiveIntegerDefault0(int value){
        this.value = value;
    }
}
