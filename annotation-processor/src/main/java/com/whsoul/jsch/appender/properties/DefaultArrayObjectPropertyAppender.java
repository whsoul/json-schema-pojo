package com.whsoul.jsch.appender.properties;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.whsoul.jsch.TypePojoGenerator;
import com.whsoul.jsch.appender.NormalizedFieldAppendUtil;
import com.whsoul.jsch.context.JschContext;
import com.whsoul.jsch.exception.JschLibaryException;
import com.whsoul.jsch.exception.JschNotSupportException;
import com.whsoul.jsch.registry.DefinitionRefRegistry;
import com.whsoul.jsch.schema.draft4.JsonSchema;
import com.whsoul.jsch.schema.draft4.SchemaObject;
import com.whsoul.jsch.schema.draft4.SchemaRef;
import com.whsoul.jsch.schema.draft4.jackson.JacksonPolymorphicTypeRegister;
import com.whsoul.jsch.schema.draft4.sub.definition.Value;
import com.whsoul.jsch.util.ContextUtil;

import java.util.Map;

public class DefaultArrayObjectPropertyAppender implements SchemaObjectPropertyAppender {

    @Override
    public SchemaObject.TYPE availableType() {
        return SchemaObject.TYPE.arrayObject;
    }

    @Override
    public void append(TypePojoGenerator baseGenerator, String propertyName, SchemaObject data) {
        ClassName baseTypeName = (ClassName)baseGenerator.getContext().getConfigRegistry().getTypeMapping("array");

        TypeName genericType;
        if(data.items == null){
            genericType = TypeName.OBJECT;
        }else if(data.items.typeOf(Value.SchemaArrayValue.class)){
            throw new JschNotSupportException("type=array, items : [ ... , ...] type not supported now : " + data.items);
        }else{
            JsonSchema schema = data.items.getAnyOfValue(Value.SchemaValue.class).getValue();

            if(schema.isRef()) {
                SchemaRef subSchemaRef = schema.asSchemaRef();
                DefinitionRefRegistry.DefinitionTypeDescription description = baseGenerator.getContext().getDefinitionRegistry().searchDefinition(subSchemaRef.$ref);
                genericType = description.definitionName;
            }else {
                SchemaObject subSchemaObject = schema.asSchemaObject();
                //TODO CustomProcess  (and anyOf, allOf)
                if (subSchemaObject.schemaType().equals(SchemaObject.TYPE.oneOfObject)) {
                    JacksonPolymorphicTypeRegister generator = new JacksonPolymorphicTypeRegister(baseGenerator, baseGenerator.getClassName(), propertyName, subSchemaObject);
                    generator.regist();

                    NormalizedFieldAppendUtil.addClassField(baseGenerator.getContext().getConfigRegistry(), baseGenerator.getBuildWrapper(), propertyName, generator.getPolymorphicClassName());
                    genericType = generator.getPolymorphicClassName();

                    this.addSuperinterfaceToSubTypes(baseGenerator.getContext(), generator.getPolymorphicClassName(), generator.getSubTypeMap());

                } else if (subSchemaObject.type.isSimpleType()) {
                    String simpleTypeName = subSchemaObject.type.getAnyOfValue(Value.SimpleTypeValue.class).getValue().getOrginalName();
                    genericType = baseGenerator.getContext().getConfigRegistry().getTypeMapping(simpleTypeName);
                } else {
                    throw new JschNotSupportException("Not supported now : " + data.items);
                }
            }
        }

        TypeName typeName = ParameterizedTypeName.get(baseTypeName, genericType);

        NormalizedFieldAppendUtil.addClassField(baseGenerator.getContext().getConfigRegistry(), baseGenerator.getBuildWrapper(), propertyName, typeName);
    }


    private void addSuperinterfaceToSubTypes(JschContext context, ClassName superinterfaceClassName, Map<String, ClassName> subTypeMap){

        for(Map.Entry<String, ClassName> entry : subTypeMap.entrySet()){
            context.getPendingTypeRegistry().addSuperinterfaceType(entry.getValue(), superinterfaceClassName);
        }
    }
}
