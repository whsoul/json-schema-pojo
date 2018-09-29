package com.whsoul.jsch.schema.draft4;

public class SchemaRef implements JsonSchema {
    public boolean isSchemaObject(){ return false; }
    public boolean isRef(){ return true; }

    public String $ref;

}
