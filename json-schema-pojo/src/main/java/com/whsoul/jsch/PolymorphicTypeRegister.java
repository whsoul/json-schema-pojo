package com.whsoul.jsch;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeSpec;
import com.whsoul.jsch.common.ANNOTATION_TYPE;

import javax.lang.model.element.Modifier;
import java.util.Collections;
import java.util.Map;

public class PolymorphicTypeRegister {
    protected ClassName interfaceClassName;
    protected TypePojoGenerator baseTypeGenerator;

    public PolymorphicTypeRegister(TypePojoGenerator baseTypeGenerator, ClassName interfaceClassName){
        this.interfaceClassName = interfaceClassName;
        this.baseTypeGenerator = baseTypeGenerator;
    }

    public TypeSpec generateTypeSpec(Map<String, ClassName> subTypes){
        TypeSpec spec = TypeSpec.interfaceBuilder(this.interfaceClassName)
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(this.baseTypeGenerator.getContext().getConfigRegistry().getAnnotationType(ANNOTATION_TYPE.TYPE_POLYMORPHIC).ofWithValue(Collections.EMPTY_MAP))
                .addAnnotation(this.baseTypeGenerator.getContext().getConfigRegistry().getAnnotationType(ANNOTATION_TYPE.TYPE_POLYMORPHIC_SUB).ofWithValue(subTypes))
                .build();

        return spec;
    }

    public void regist(Map<String, ClassName> subTypes){
        this.baseTypeGenerator.getContext().getTypeRegistry().registPolymorphicTypePojoGenerator(this.interfaceClassName, generateTypeSpec(subTypes));
    }

}
