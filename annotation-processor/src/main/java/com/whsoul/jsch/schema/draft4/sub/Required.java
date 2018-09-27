package com.whsoul.jsch.schema.draft4.sub;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.whsoul.jsch.schema.draft4.sub.definition.StringArray;
import lombok.ToString;

import java.util.Collections;
import java.util.List;

@ToString
public class Required {
    public StringArray fieldNames;

    @JsonCreator
    public Required(List<String> stringList){
        if(stringList == null){
            this.fieldNames = new StringArray(Collections.EMPTY_LIST);
        }else {
            this.fieldNames = new StringArray(stringList);
        }
    }
}
