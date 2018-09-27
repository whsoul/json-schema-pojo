package com.whsoul.jsch.schema.draft4;

import com.whsoul.jsch.exception.JschLibaryException;

public interface JsonSchema {

    boolean isSchemaObject();
    boolean isRef();

    default SchemaObject asSchemaObject(){
        if(this.isSchemaObject()){
            return (SchemaObject)this;
        }else{
            throw new JschLibaryException("Invalid Cast As SchemaObject");
        }
    }

    default SchemaRef asSchemaRef(){
        if(this.isRef()){
            return (SchemaRef)this;
        }else{
            throw new JschLibaryException("Invalid Cast As SchemaRef");
        }
    }
}
