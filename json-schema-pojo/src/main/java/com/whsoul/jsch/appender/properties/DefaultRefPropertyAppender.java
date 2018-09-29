package com.whsoul.jsch.appender.properties;

import com.whsoul.jsch.TypePojoGenerator;
import com.whsoul.jsch.appender.NormalizedFieldAppendUtil;
import com.whsoul.jsch.exception.JschInvalidSchemaException;
import com.whsoul.jsch.registry.DefinitionRefRegistry;
import com.whsoul.jsch.schema.draft4.SchemaRef;

public class DefaultRefPropertyAppender implements SchemaRefPropertyAppender {

    @Override
    public void append(TypePojoGenerator generator, String propertyName, SchemaRef schemaRef) {
        DefinitionRefRegistry.DefinitionTypeDescription description = generator.getContext().getDefinitionRegistry().searchDefinition(schemaRef.$ref);
        if(description != null){
            if(description.isCustomClass) {
                NormalizedFieldAppendUtil.addClassField(generator.getContext().getConfigRegistry(), generator.getBuildWrapper(), propertyName, description.definitionName);
            }else{
                NormalizedFieldAppendUtil.addTypeFieldWithAnnotations(generator.getContext().getConfigRegistry(), generator.getBuildWrapper(), propertyName, description.definitionName, description.restrictAnnotationSpecList);
            }
        }else {
            throw new JschInvalidSchemaException("No Definition search For schemaRef : " + schemaRef.$ref);
        }
    }
}
