package com.whsoul.jsch.appender.properties;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.whsoul.jsch.TypePojoGenerator;
import com.whsoul.jsch.appender.NormalizedFieldAppendUtil;
import com.whsoul.jsch.schema.draft4.SchemaObject;
import com.whsoul.jsch.schema.draft4.sub.definition.SimpleTypes;
import com.whsoul.jsch.schema.draft4.sub.definition.Value;

import java.util.Map;

public class DefaultSimpleTypeObjectPropertyAppender implements SchemaObjectPropertyAppender {

    @Override
    public SchemaObject.TYPE availableType() {
        return SchemaObject.TYPE.simpleTypeObject;
    }

    @Override
    public void append(TypePojoGenerator baseGenerator, String propertyName, SchemaObject data) {
        // For "object" as Generic Map with additionalProperties type
        if(SchemaObject.TYPE.simpleTypeObject.equals(data.schemaType())
           && data.additionalProperties != null
           && data.additionalProperties.typeOf(Value.SchemaValue.class)){

           Value.SchemaValue schemaValue = data.additionalProperties.getAnyOfValue(Value.SchemaValue.class);
           if(schemaValue.getValue().isSchemaObject()){
               SchemaObject schemaObject = schemaValue.getValue().asSchemaObject();
               if(SchemaObject.TYPE.simpleTypeObject.equals(schemaObject.schemaType())){
                   TypeName typeName = baseGenerator.getContext().getConfigRegistry().getTypeMapping(schemaObject.type.getAnyOfValue(Value.SimpleTypeValue.class).getValue().getOrginalName());

                   NormalizedFieldAppendUtil.addTypeFieldWithAnnotations(
                           baseGenerator.getContext().getConfigRegistry()
                           ,baseGenerator.getBuildWrapper()
                           ,propertyName
                           ,ParameterizedTypeName.get(ClassName.get(Map.class), ClassName.get(String.class), typeName)
                           ,null
                   );
               }
           }

        }else {

            NormalizedFieldAppendUtil.addSimpleTypeField(
                    baseGenerator.getContext().getConfigRegistry(),
                    baseGenerator.getBuildWrapper()
                    , propertyName
                    , data.type.getAnyOfValue(Value.SimpleTypeValue.class).getValue());
        }
    }
}
