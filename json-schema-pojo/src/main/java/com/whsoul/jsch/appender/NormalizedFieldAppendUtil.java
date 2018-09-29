package com.whsoul.jsch.appender;

import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import com.whsoul.jsch.common.ANNOTATION_TYPE;
import com.whsoul.jsch.conf.ConfigRegistry;
import com.whsoul.jsch.def.replacer.FieldNameReplacer;
import com.whsoul.jsch.poet.SimpleClassBuildWrapper;
import com.whsoul.jsch.schema.draft4.sub.definition.SimpleTypes;

import javax.lang.model.SourceVersion;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class NormalizedFieldAppendUtil {

    /**
     * For Class Type field
     */
    public static void addClassField(ConfigRegistry configRegistry, SimpleClassBuildWrapper simpleClassBuildWrapper, String fieldName, TypeName typeName){
        if(canUseName(fieldName)) {
            simpleClassBuildWrapper.addPublicField(typeName, fieldName);
        }else {
            String normalizedFieldName = normalizeName(fieldName);
            AnnotationSpec annotationSpec = configRegistry.getAnnotationType(ANNOTATION_TYPE.FIELD_NAME).ofWithValue(new HashMap() {{ put("value", fieldName); }});
            simpleClassBuildWrapper.addPublicFieldWithAnnotation(typeName, fieldName, normalizedFieldName, annotationSpec);
        }
    }

    /**
     * For Simple Type field
     */
    public static void addSimpleTypeField(ConfigRegistry configRegistry, SimpleClassBuildWrapper simpleClassBuildWrapper, String fieldName, SimpleTypes types){
        if(canUseName(fieldName)) {
            simpleClassBuildWrapper.addPublicFieldWithMappingName(types.getOrginalName(), fieldName);
        }else {
            String normalizedFieldName =  normalizeName(fieldName);
            AnnotationSpec annotationSpec = configRegistry.getAnnotationType(ANNOTATION_TYPE.FIELD_NAME).ofWithValue(new HashMap() {{ put("value", fieldName); }});
            simpleClassBuildWrapper.addPublicFieldWithMappingNameWithAnnotation(types.getOrginalName(), fieldName, normalizedFieldName, annotationSpec);
        }
    }

    public static void addTypeFieldWithAnnotations(ConfigRegistry configRegistry, SimpleClassBuildWrapper simpleClassBuildWrapper, String fieldName, TypeName typeName, List<AnnotationSpec> annotationSpecList){
        String normalizedFieldName;

        if(canUseName(fieldName)) {
            normalizedFieldName =  fieldName;
        }else{
            AnnotationSpec annotationSpec = configRegistry.getAnnotationType(ANNOTATION_TYPE.FIELD_NAME).ofWithValue(new HashMap() {{ put("value", fieldName); }});
            if(annotationSpecList == null){
                annotationSpecList = new ArrayList<AnnotationSpec>(){{ add(annotationSpec); }};
            }else{
                annotationSpecList.add(0, annotationSpec);
            }
            normalizedFieldName =  normalizeName(fieldName);
        }


        if (annotationSpecList == null || annotationSpecList.size() < 1) {
            simpleClassBuildWrapper.addPublicField(typeName, fieldName);
        } else {
            simpleClassBuildWrapper.addPublicFieldWithAnnotations(typeName, fieldName, normalizedFieldName, annotationSpecList);
        }
    }

    public static void addInnerClassField(ConfigRegistry configRegistry, SimpleClassBuildWrapper parentSimpleClassBuildWrapper, ClassName subClassName, String fieldName, TypeSpec.Builder subClassTypeSpecBuilder){
        String normalizedFieldName;

        parentSimpleClassBuildWrapper.addInnerClass(subClassName, subClassTypeSpecBuilder);
        if(canUseName(fieldName)) {
            parentSimpleClassBuildWrapper.addPublicField(subClassName, fieldName);
        }else{
            normalizedFieldName = normalizeName(fieldName);
            AnnotationSpec annotationSpec = configRegistry.getAnnotationType(ANNOTATION_TYPE.FIELD_NAME).ofWithValue(new HashMap() {{ put("value", fieldName); }});
            parentSimpleClassBuildWrapper.addPublicFieldWithAnnotation(subClassName, fieldName, normalizedFieldName, annotationSpec);
        }

    }

    public static boolean canUseName(String orgName){
        return SourceVersion.isName(orgName);
    }

    public static String normalizeName(String orgName){
        return new FieldNameReplacer().replace(orgName);
    }

}
