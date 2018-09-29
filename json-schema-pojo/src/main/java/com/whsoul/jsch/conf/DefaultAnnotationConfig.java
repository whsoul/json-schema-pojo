package com.whsoul.jsch.conf;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.whsoul.jsch.support.AnnotationSpecFactory;
import com.whsoul.jsch.support.BaseAnnotationSpecFactory;
import com.whsoul.jsch.common.ANNOTATION_TYPE;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

public class DefaultAnnotationConfig implements Config<ANNOTATION_TYPE, AnnotationSpecFactory>{
    private static final Map<ANNOTATION_TYPE, AnnotationSpecFactory> configMap = new HashMap<ANNOTATION_TYPE, AnnotationSpecFactory>() {{
        put(ANNOTATION_TYPE.FIELD_NAME, new BaseAnnotationSpecFactory(JsonProperty.class));
        put(ANNOTATION_TYPE.FIELD_REQUIRED, new BaseAnnotationSpecFactory(NotNull.class));

        put(ANNOTATION_TYPE.FIELD_MINIMUM, new BaseAnnotationSpecFactory(Min.class));
        put(ANNOTATION_TYPE.FIELD_MAXIMUM, new BaseAnnotationSpecFactory(Max.class));

        put(ANNOTATION_TYPE.TYPE_POLYMORPHIC, new AnnotationSpecFactory<String, String>() {
            @Override
            public AnnotationSpec ofWithValue(Map<String, String> valueMap) {
                return AnnotationSpec.builder(JsonTypeInfo.class)
                        .addMember("use", "JsonTypeInfo.Id.NAME")
                        .addMember("include", "JsonTypeInfo.As.PROPERTY")
                        .addMember("property", "$S", "type")
                        .build();
            }
        });
        put(ANNOTATION_TYPE.TYPE_POLYMORPHIC_SUB, new AnnotationSpecFactory<String, ClassName>() {
            @Override
            public AnnotationSpec ofWithValue(Map<String, ClassName> valueMap) {
                AnnotationSpec.Builder mainSpecBuilder = AnnotationSpec.builder(JsonSubTypes.class);
                for(Entry<String, ClassName> entry : valueMap.entrySet()) {
                    AnnotationSpec.Builder specBuilder = AnnotationSpec.builder(JsonSubTypes.Type.class);
                    specBuilder.addMember("value", "$L", entry.getValue() + ".class");
                    specBuilder.addMember("name", "$S", entry.getKey());
                    mainSpecBuilder.addMember("value", "$L", specBuilder.build());
                }
                return mainSpecBuilder.build();
            }
        });

    }};

    @Override
    public Map<ANNOTATION_TYPE, AnnotationSpecFactory> getConfigMap() {
        return this.configMap;
    }
}
