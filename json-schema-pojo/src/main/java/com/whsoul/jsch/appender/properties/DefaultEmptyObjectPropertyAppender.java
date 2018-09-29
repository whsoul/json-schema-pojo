package com.whsoul.jsch.appender.properties;

import com.whsoul.jsch.TypePojoGenerator;
import com.whsoul.jsch.schema.draft4.SchemaObject;
public class DefaultEmptyObjectPropertyAppender implements SchemaObjectPropertyAppender {

    @Override
    public SchemaObject.TYPE availableType() {
        return SchemaObject.TYPE.emptyObject;
    }

    @Override
    public void append(TypePojoGenerator baseGenerator, String propertyName, SchemaObject data) {
    }

}
