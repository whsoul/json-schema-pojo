package com.whsoul.jsch.support;

import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;

import java.util.Map;

public class BaseAnnotationSpecFactory implements AnnotationSpecFactory<String, Object>{
    ClassName annotationClassName;

    public BaseAnnotationSpecFactory(ClassName annotaionClassName){
        this.annotationClassName = annotaionClassName;
    }

    public <T> BaseAnnotationSpecFactory(Class<T> annotaionClass){
        this.annotationClassName = ClassName.get(annotaionClass);
    }

    @Override
    public AnnotationSpec ofWithValue(Map<String, Object> valueMap) {
        AnnotationSpec.Builder builder = AnnotationSpec.builder(annotationClassName);
        valueMap.entrySet().stream().forEach(entry -> {
            if(entry.getValue() instanceof String) {
                builder.addMember(entry.getKey(), "$S", entry.getValue());
            }else{
                builder.addMember(entry.getKey(), "$L", entry.getValue());
            }
        });
        return builder.build();
    }
}
