package com.whsoul.jsch.appender.properties;

import com.whsoul.jsch.schema.draft4.SchemaObject;

public interface SchemaObjectPropertyAppender extends PropertyAppender<SchemaObject> {

    SchemaObject.TYPE availableType();

}
