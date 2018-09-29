package com.whsoul.jsch.appender.properties;

import com.squareup.javapoet.ClassName;
import com.whsoul.jsch.TypePojoGenerator;
import com.whsoul.jsch.appender.NormalizedFieldAppendUtil;
import com.whsoul.jsch.context.JschContext;
import com.whsoul.jsch.schema.draft4.SchemaObject;
import com.whsoul.jsch.schema.draft4.jackson.JacksonPolymorphicTypeRegister;

import java.util.Map;

public class DefaultOneOfPropertyAppender implements SchemaObjectPropertyAppender {

    @Override
    public SchemaObject.TYPE availableType() {
        return SchemaObject.TYPE.oneOfObject;
    }

    @Override
    public void append(TypePojoGenerator baseGenerator, String propertyName, SchemaObject data) {

        JacksonPolymorphicTypeRegister generator = new JacksonPolymorphicTypeRegister(baseGenerator, baseGenerator.getClassName(), propertyName, data);
        generator.regist();

        NormalizedFieldAppendUtil.addClassField(baseGenerator.getContext().getConfigRegistry(), baseGenerator.getBuildWrapper(), propertyName, generator.getPolymorphicClassName());

        this.addSuperinterfaceToSubTypes(baseGenerator.getContext(), generator.getPolymorphicClassName(), generator.getSubTypeMap());
    }

    private void addSuperinterfaceToSubTypes(JschContext context, ClassName superinterfaceClassName, Map<String, ClassName> subTypeMap){

        for(Map.Entry<String, ClassName> entry : subTypeMap.entrySet()){
            context.getPendingTypeRegistry().addSuperinterfaceType(entry.getValue(), superinterfaceClassName);
        }
    }

}
