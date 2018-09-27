package com.whsoul.jsch.appender;

import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.whsoul.jsch.TypePojoGenerator;
import com.whsoul.jsch.common.ANNOTATION_TYPE;
import com.whsoul.jsch.conf.ConfigRegistry;
import com.whsoul.jsch.context.JschContext;
import com.whsoul.jsch.registry.DefinitionRefRegistry;
import com.whsoul.jsch.schema.draft4.SchemaObject;
import com.whsoul.jsch.schema.draft4.sub.Definitions;
import com.whsoul.jsch.schema.draft4.sub.definition.Value;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DefinitionGenerateExecutor implements SchemaGenerateExecutor<Definitions> {
    private static Logger logger = LoggerFactory.getLogger(DefinitionGenerateExecutor.class);

    private JschContext context;

    public DefinitionGenerateExecutor(JschContext context){
        this.context = context;
    }

    @Override
    public void execute(TypePojoGenerator pojoGenerator, ClassName baseClassName, SchemaObject schema){
        if(schema.definitions == null) return;
        schema.definitions.map.entrySet().stream()
                .forEach(entry -> {

                    //do with schemaObject
                    if(entry.getValue().isSchemaObject()) {
                        SchemaObject schemaObject = (SchemaObject) entry.getValue();

                        // 1. regist definitions for ref
                        DefinitionRefRegistry.DefinitionTypeDescription refResult = registDefinitionRef(schemaObject, pojoGenerator.getContext(), entry.getKey());

                        // 2. definition class regist for new class
                        if (refResult.isCustomClass) {
                            this.registTypeGenerator(schemaObject, pojoGenerator, (ClassName) refResult.definitionName);
                        }
                    }
                    //warn (considering with ref type?)
                });
    }

    // Regist Definitions to Registry
    private DefinitionRefRegistry.DefinitionTypeDescription registDefinitionRef(SchemaObject definitionSchemaObject, JschContext context, String definitionName){
        DefinitionRefRegistry.DefinitionRef definitionRef = new DefinitionRefRegistry.DefinitionRef();
        definitionRef.definitionSimpleName = definitionName;
        definitionRef.definitionFullPath   = ConfigRegistry.definitionBasePath + definitionName;

        DefinitionRefRegistry.DefinitionTypeDescription result = extractDefinitionTypeDescription(definitionSchemaObject, context.getConfigRegistry(), definitionName);
        context.getDefinitionRegistry().addDefinition(definitionRef, result);
        return result;
    }

    // Regist Type Class
    private void registTypeGenerator(SchemaObject definitionSchemaObject, TypePojoGenerator pojoGenerator, ClassName definitionClassName){
        pojoGenerator.getContext().getTypeRegistry().registNewSubTypePojoGenerator(pojoGenerator, definitionClassName, definitionSchemaObject);
    }

    private DefinitionRefRegistry.DefinitionTypeDescription extractDefinitionTypeDescription(SchemaObject definitionSchemaObject, ConfigRegistry configRegistry, String definitionName){
        //1. SimpleType ( exclude object )
//        public boolean isObjectSimpleType()
//        public boolean isSimpleType()
//        public boolean isSchemaArrayType()

        if(definitionSchemaObject.type != null && definitionSchemaObject.type.isSimpleTypeExcludeObject()) {
            DefinitionRefRegistry.DefinitionTypeDescription description = new DefinitionRefRegistry.DefinitionTypeDescription();
            String simpleTypeName = definitionSchemaObject.type.getAnyOfValue(Value.SimpleTypeValue.class).getValue().getOrginalName();
            description.definitionName = configRegistry.getTypeMapping(simpleTypeName);
            description.restrictAnnotationSpecList = this.extractAnnotationSpecList(definitionSchemaObject, configRegistry);
            description.isCustomClass = false;
            return description;
        }
        //2. ClassType ( + simpleType[object] )
        else{
            DefinitionRefRegistry.DefinitionTypeDescription description = new DefinitionRefRegistry.DefinitionTypeDescription();
            description.isCustomClass = true;
            ClassName definitionClassName = ClassName.get(context.getDefinitionsPackage(), definitionName);
            description.definitionName = definitionClassName;

            //warn Class Annotation?
            return description;
        }
    }


    private List<AnnotationSpec> extractAnnotationSpecList(SchemaObject definitionSchemaObject, ConfigRegistry configRegistry){
        List<AnnotationSpec> annotationSpecList = new ArrayList<>();
        if(definitionSchemaObject.minimum != null){
            annotationSpecList.add(configRegistry.getAnnotationType(ANNOTATION_TYPE.FIELD_MINIMUM).ofWithValue(new HashMap(){{ put("value", definitionSchemaObject.minimum); }}));
        }

        if(definitionSchemaObject.maximum != null){
            annotationSpecList.add(configRegistry.getAnnotationType(ANNOTATION_TYPE.FIELD_MAXIMUM).ofWithValue(new HashMap(){{ put("value", definitionSchemaObject.maximum); }}));
        }
        return annotationSpecList;
    }
}
