package com.whsoul.jsch.appender.properties;

import com.google.common.base.CaseFormat;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeSpec;
import com.whsoul.jsch.TypePojoGenerator;
import com.whsoul.jsch.appender.NormalizedFieldAppendUtil;
import com.whsoul.jsch.common.JSONSCHEMA_POJO_FEATURE;
import com.whsoul.jsch.conf.ConfigRegistry;
import com.whsoul.jsch.exception.JschNotSupportException;
import com.whsoul.jsch.schema.draft4.SchemaObject;
import com.whsoul.jsch.schema.draft4.sub.definition.SimpleTypes;
import com.whsoul.jsch.schema.draft4.sub.definition.Value;

import javax.lang.model.element.Modifier;
import java.util.Set;

public class DefaultEnumPropertyAppender implements SchemaObjectPropertyAppender {

    @Override
    public SchemaObject.TYPE availableType() {
        return SchemaObject.TYPE.enumObject;
    }

    @Override
    public void append(TypePojoGenerator baseGenerator, String propertyName, SchemaObject data) {

        if(data.type != null
           &&!data.type.isSimpleType(SimpleTypes.TYPE.$string)){
            throw new JschNotSupportException("Only support enum with string now : " + data);
        }

        boolean enumStringEnable = baseGenerator.getContext().getConfigRegistry().getBaseConfig(JSONSCHEMA_POJO_FEATURE.STRING_ENUM_AS_ENUM);

        //Case : Enum type=string Enable
        if(enumStringEnable) {
            String innerClassName = propertyName;

            if(NormalizedFieldAppendUtil.canUseName(innerClassName)){
                innerClassName = CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_CAMEL, propertyName);
            }else{
                innerClassName = ConfigRegistry.defaultInnerEnumClassType;
            }
            ClassName anonymousInnerClass = baseGenerator.getClassName().nestedClass(innerClassName);

            Set<String> enumStrings = data.$enum.getItems();
            TypeSpec.Builder enumTypeBuilder = TypeSpec.enumBuilder(anonymousInnerClass).addModifiers(Modifier.PUBLIC, Modifier.STATIC);
            for (String enumStr : enumStrings) {
                enumTypeBuilder.addEnumConstant(enumStr);
            }

            NormalizedFieldAppendUtil.addInnerClassField(baseGenerator.getContext().getConfigRegistry(), baseGenerator.getBuildWrapper()
                    , anonymousInnerClass
                    , propertyName
                    , enumTypeBuilder);
        }
        //Case : Enum type=string Disable
        else{

            NormalizedFieldAppendUtil.addSimpleTypeField(
                    baseGenerator.getContext().getConfigRegistry(),
                    baseGenerator.getBuildWrapper()
                    ,propertyName
                    , new SimpleTypes("string"));
        }


    }

}
