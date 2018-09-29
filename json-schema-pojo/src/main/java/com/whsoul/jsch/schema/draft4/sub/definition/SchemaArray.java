package com.whsoul.jsch.schema.draft4.sub.definition;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.whsoul.jsch.schema.draft4.SchemaRef;

import java.util.List;

public class SchemaArray{

    //todo minItem1
    public List<SchemaRef> schemaRefList;

    @JsonCreator
    public SchemaArray(List<SchemaRef> schemaList){
        this.schemaRefList = schemaList;
    }
}
