package com.whsoul.jsch.schema.draft4.sub.definition;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.whsoul.jsch.exception.JschLibaryException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class StringArray {

    //todo min item 1
    public Set<String> items;

    @JsonCreator
    public StringArray(List<String> stringList){
        if(stringList.size() < 0){
            throw new JschLibaryException("StringArray field must has more than 1 item");
        }
        this.items = new HashSet<>(stringList);
    }
}
