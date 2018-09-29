package com.whsoul.jsch.appender;

import com.squareup.javapoet.ClassName;
import com.whsoul.jsch.TypePojoGenerator;
import com.whsoul.jsch.schema.draft4.SchemaObject;

public interface SchemaGenerateExecutor<T> {
    void execute(TypePojoGenerator pojoGenerator, ClassName baseClassName, SchemaObject schema);
}
