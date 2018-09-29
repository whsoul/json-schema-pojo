package com.whsoul.jsch.appender.properties;

import com.squareup.javapoet.ClassName;
import com.whsoul.jsch.TypePojoGenerator;
import com.whsoul.jsch.appender.NormalizedFieldAppendUtil;
import com.whsoul.jsch.schema.draft4.SchemaObject;

import javax.lang.model.element.Modifier;

public class DefaultTypeObjectPropertyAppender implements SchemaObjectPropertyAppender {

    @Override
    public SchemaObject.TYPE availableType() {
        return SchemaObject.TYPE.typeObject;
    }

    @Override
    public void append(TypePojoGenerator baseGenerator, String propertyName, SchemaObject data) {
        // innerClass make self Directly
        ClassName anonymousInnerClass = baseGenerator.getClassName().nestedClass(propertyName);
        TypePojoGenerator innerClassGenerator = baseGenerator.getContext().getTypeRegistry().newSubTypePojoGenerator(baseGenerator, anonymousInnerClass, data);
        innerClassGenerator.startProcess();
        NormalizedFieldAppendUtil.addInnerClassField(baseGenerator.getContext().getConfigRegistry(), baseGenerator.getBuildWrapper()
                ,anonymousInnerClass
                ,propertyName
                ,innerClassGenerator.toTypeSpecBuilder(Modifier.PUBLIC, Modifier.STATIC));

    }
}
