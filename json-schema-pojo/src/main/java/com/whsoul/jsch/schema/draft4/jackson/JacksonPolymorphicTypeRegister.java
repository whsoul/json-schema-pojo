package com.whsoul.jsch.schema.draft4.jackson;

import com.google.common.base.CaseFormat;
import com.squareup.javapoet.ClassName;
import com.whsoul.jsch.PolymorphicTypeRegister;
import com.whsoul.jsch.TypePojoGenerator;
import com.whsoul.jsch.context.JschContext;
import com.whsoul.jsch.exception.JschNotSupportException;
import com.whsoul.jsch.registry.DefinitionRefRegistry;
import com.whsoul.jsch.schema.draft4.JsonSchema;
import com.whsoul.jsch.schema.draft4.SchemaObject;
import com.whsoul.jsch.schema.draft4.SchemaRef;
import com.whsoul.jsch.util.ContextUtil;

import java.util.HashMap;
import java.util.Map;

public class JacksonPolymorphicTypeRegister extends PolymorphicTypeRegister {
    private SchemaObject schemaObject;
    private ClassName baseClassName;

    public JacksonPolymorphicTypeRegister(TypePojoGenerator baseTypeGenerator, ClassName baseClassName, String propertyName, SchemaObject schemaObject){
        super(baseTypeGenerator, generateClassName(baseClassName, propertyName, baseTypeGenerator.getContext()));
        this.schemaObject = schemaObject;
        this.baseClassName = baseClassName;

    }

    public static ClassName generateClassName(ClassName baseClassName, String propertyName, JschContext context) {
        String className = CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_CAMEL, propertyName) + context.getConfigRegistry().subPolymophicPrefix;
        String packageName = context.getPolymorphicsPackage();
        return ClassName.get(packageName, className);
    }

    public Map<String, ClassName> getSubTypeMap(){
        Map<String, ClassName> subTypeMap = new HashMap<>();
        for(JsonSchema schemaItem : schemaObject.oneOf.items) {
            if (schemaItem.isRef()) {
                SchemaRef ref = (SchemaRef)schemaItem;

                DefinitionRefRegistry.DefinitionTypeDescription description = this.baseTypeGenerator.getContext().getDefinitionRegistry().searchDefinition(ref.$ref);
                ClassName subTypeClassName = (ClassName)description.definitionName;

                JsonSchema searchedSchema = this.baseTypeGenerator.getParser().searchRefSchema(ref);
                SchemaObject searchedSchemaObject = (SchemaObject) searchedSchema;
                subTypeMap.put(searchedSchemaObject.title, subTypeClassName);
            }
            //TODO object나 simpleType인 경우?
            else{
                throw new JschNotSupportException("OneOf item Only $ref type supported now");
            }
        }
        return subTypeMap;
    }

    public ClassName getPolymorphicClassName(){
        return this.interfaceClassName;
    }

    public void regist(){
        Map<String, ClassName> subTypeMap = getSubTypeMap();
        super.regist(subTypeMap);
    }

}
