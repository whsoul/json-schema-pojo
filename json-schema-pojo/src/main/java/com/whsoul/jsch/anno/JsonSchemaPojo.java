package com.whsoul.jsch.anno;

import com.whsoul.jsch.common.ANNOTATION_TYPE;
import com.whsoul.jsch.common.JSONSCHEMA_POJO_FEATURE;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.SOURCE)
public @interface JsonSchemaPojo {
    String packageName();
    String className();
    String schemaUrl();

    BaseConfig[] baseConfig() default {};

    @interface BaseConfig {
        JSONSCHEMA_POJO_FEATURE feature();
        boolean enable();
    }

    TypeMappingConfig[] typeMappingConfig() default {};

    @interface TypeMappingConfig {
        String typeName();
        Class<?> typeJavaClass();
    }

    AnnotationConfig[] annotationConfig() default {};

    @interface AnnotationConfig {
        ANNOTATION_TYPE type();
        Class<? extends Annotation> annotationClass();
        String valueNames() default "";
    }

    SuperConfig[] superclassConfig() default {};
    SuperConfig[] superinterfaceConfig() default {};

    @interface SuperConfig {
        Class<?> superclass();
        String targetClassNames();
    }

}

