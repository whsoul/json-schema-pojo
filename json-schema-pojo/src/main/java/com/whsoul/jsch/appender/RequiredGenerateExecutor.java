package com.whsoul.jsch.appender;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.whsoul.jsch.TypePojoGenerator;
import com.whsoul.jsch.support.AnnotationSpecFactory;
import com.whsoul.jsch.common.ANNOTATION_TYPE;
import com.whsoul.jsch.context.JschContext;
import com.whsoul.jsch.exception.JschInvalidSchemaException;
import com.whsoul.jsch.schema.draft4.SchemaObject;
import com.whsoul.jsch.schema.draft4.sub.Required;
import com.whsoul.jsch.schema.draft4.sub.definition.StringArray;

public class RequiredGenerateExecutor implements SchemaGenerateExecutor<Required> {
    private AnnotationSpecFactory fieldRequredAnnotationSpec;

    private JschContext context;

    public RequiredGenerateExecutor(JschContext context){
        this.context = context;
        this.fieldRequredAnnotationSpec = context.getConfigRegistry().getAnnotationType(ANNOTATION_TYPE.FIELD_REQUIRED);
    }

    @Override
    public void execute(TypePojoGenerator pojoGenerator, ClassName baseClassName, SchemaObject schema) {
        if(schema.required == null || schema.required.fieldNames == null){
            return;
        }

        StringArray required = schema.required.fieldNames;
        for(String fieldName : required.items){

            if(pojoGenerator == null){
                throw new JschInvalidSchemaException("Required BaseClass Not Found" + baseClassName);
            }

            FieldSpec.Builder fieldBuilder = pojoGenerator.getBuildWrapper().findFieldSpecByName(fieldName);
            if(fieldBuilder == null){
                throw new JschInvalidSchemaException("Required Field Not Found : " + fieldName + " : " + baseClassName);
            }
            fieldBuilder.addAnnotation(fieldRequredAnnotationSpec.of());
        }
    }

}
