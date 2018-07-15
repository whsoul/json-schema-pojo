package com.whsoul.pojogen.schema.draft4.sub.definition;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.whsoul.pojogen.schema.draft4.Draft04Schema;

import java.util.List;

public class SchemaArray{

    //todo minItem1
    List<Draft04Schema> value;

    @JsonCreator
    public SchemaArray(List<Draft04Schema> value){
        this.value = value;
    }
}
