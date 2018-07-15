package com.whsoul.pojogen.schema.draft4.sub.definition;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class StringArray {

    //todo min item 1
    Set<String> value;

    @JsonCreator
    public StringArray(List<String> stringList){
        this.value = new HashSet<>(stringList);
    }
}
