package com.whsoul.jsch.anno;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeName;
import com.whsoul.jsch.JsonSchemaPojoGenerator;
import com.whsoul.jsch.common.ANNOTATION_TYPE;
import com.whsoul.jsch.common.JSONSCHEMA_POJO_FEATURE;
import com.whsoul.jsch.conf.ConfigRegistry;
import com.whsoul.jsch.exception.JschLibaryException;
import com.whsoul.jsch.support.AnnotationSpecFactory;
import com.whsoul.jsch.support.BaseAnnotationSpecFactory;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.*;
import javax.lang.model.type.TypeMirror;
import javax.tools.JavaFileObject;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SupportedAnnotationTypes(
        "com.whsoul.jsch.anno.JsonSchemaPojo")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@AutoService(Processor.class)
public class JsonSchemaPojoProcessor extends AbstractProcessor {
    private enum JsonSchemaPojoType{
        packageName
        ,className
        ,schemaUrl
        ,typeMappingConfig
        ,baseConfig
        ,annotationConfig
        ,superinterfaceConfig
        ,superclassConfig;

        public static class MappingConfig{
            static String KEY = "typeName";
            static String VALUE = "typeJavaClass";
        }

        public static class BaseConfig{
            static String KEY = "feature";
            static String VALUE = "enable";
        }

        public static class AnnotationConfig{
            static String KEY = "type";
            static String VALUE = "annotationClass";
            static String EXTRA = "valueNames";
        }

        public static class SuperclassConfig{
            static String KEY = "superclass";
            static String VALUE = "targetClassNames";
        }

    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations,
                           RoundEnvironment roundEnv) {

        for(Element element : roundEnv.getElementsAnnotatedWith(JsonSchemaPojo.class)){
            JsonSchemaPojo pojogen = element.getAnnotation(JsonSchemaPojo.class);

            try {
                List<? extends AnnotationMirror> annotationMirrors = element.getAnnotationMirrors();
                Map<String, TypeName> typeNameConfig = null;
                Map<ANNOTATION_TYPE, AnnotationSpecFactory> annotationConfig = null;
                Map<JSONSCHEMA_POJO_FEATURE, Boolean> baseConfig = null;
                Map<TypeName, List<ClassName>> superclassConfig = null;
                Map<TypeName, List<ClassName>> superinterfaceConfig = null;

                for (AnnotationMirror annotationMirror : annotationMirrors) {
                    Map<? extends ExecutableElement, ? extends AnnotationValue> elementValues
                            = annotationMirror.getElementValues();
                    for (Map.Entry<? extends ExecutableElement, ? extends AnnotationValue> entry
                            : elementValues.entrySet()) {
                        String key = entry.getKey().getSimpleName().toString();
                        Object value = entry.getValue().getValue();

                        switch (JsonSchemaPojoType.valueOf(key)){
                            case typeMappingConfig : {
                                Map<String, TypeName> map = toTypeMappingConfigMap(value);
                                typeNameConfig = map;
                                break;
                            }
                            case baseConfig: {
                                Map<JSONSCHEMA_POJO_FEATURE, Boolean> map = toBaseConfigMap(value);
                                baseConfig = map;
                                break;
                            }
                            case annotationConfig: {
                                Map<ANNOTATION_TYPE, AnnotationSpecFactory> map = toAnnotationConfigMap(value);
                                annotationConfig = map;
                                break;
                            }
                            case superclassConfig: {
                                Map<TypeName, List<ClassName>> map = toSuperConfigMap(value);
                                superclassConfig = map;
                                break;
                            }
                            case superinterfaceConfig: {
                                Map<TypeName, List<ClassName>> map = toSuperConfigMap(value);
                                superinterfaceConfig = map;
                                break;
                            }

                        }
                    }
                }

                final Map<String, TypeName> typeMappingData = typeNameConfig;
                final Map<JSONSCHEMA_POJO_FEATURE, Boolean> baseConfigData = baseConfig;
                final Map<ANNOTATION_TYPE, AnnotationSpecFactory> annotationConfigData = annotationConfig;
                final Map<TypeName, List<ClassName>> superclassConfigData = superclassConfig;
                final Map<TypeName, List<ClassName>> superinterfaceConfigData = superinterfaceConfig;

                InputStream inputStream = null;
                try {
                    if(pojogen.schemaUrl().startsWith("http")){
                        inputStream = getInputStreamFromUrl(pojogen.schemaUrl());
                    }else{
                        inputStream = getInputStreamFromFileSchema(pojogen.schemaUrl());
                    }
                } catch (IOException e) {
                    throw new JschLibaryException("Can not get schema resource, check network or file path");
                }

                JsonSchemaPojoGenerator pojoGenerator = new JsonSchemaPojoGenerator(
                        pojogen.packageName(), pojogen.className()
                        , inputStream
                        , new ConfigRegistry(
                            () -> baseConfigData
                            ,() -> typeMappingData
                            ,() -> annotationConfigData
                            ,() -> superclassConfigData
                            ,() -> superinterfaceConfigData)
                        );

                writeBuilderFile(pojoGenerator.generate());
            } catch (IOException e) {
                throw new JschLibaryException("Class File Generate Error", e);
            }

        }

        return true;
    }

    private InputStream getInputStreamFromFileSchema(String classPathUri){
        return getClass().getClassLoader().getResourceAsStream(classPathUri);
    }

    private InputStream getInputStreamFromUrl(String urlStr) throws IOException {
        URL url = new URL(urlStr);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        return con.getInputStream();
    }

    private void writeBuilderFile(Map<ClassName, String> classNameMap) throws IOException {
        for(Map.Entry<ClassName, String> entry : classNameMap.entrySet()){
            JavaFileObject builderFile = processingEnv.getFiler().createSourceFile(entry.getKey().toString());
            try (PrintWriter out = new PrintWriter(builderFile.openWriter())) {
                out.println(entry.getValue());
            }
        }
    }


    private Map<JSONSCHEMA_POJO_FEATURE, Boolean> toBaseConfigMap(Object typeMappingValue){
        List typeMappingAnnotationList = (List)typeMappingValue;
        Map<JSONSCHEMA_POJO_FEATURE, Boolean> map = new HashMap<>();
        for (Object item : typeMappingAnnotationList) {
            AnnotationMirror annotationMirror = (AnnotationMirror) item;
            Map<? extends ExecutableElement, ? extends AnnotationValue> elementValues
                    = annotationMirror.getElementValues();
            JSONSCHEMA_POJO_FEATURE simpleMappingName = null;
            Boolean enable = null;

            for (Map.Entry<? extends ExecutableElement, ? extends AnnotationValue> entry
                    : elementValues.entrySet()) {
                String key = entry.getKey().getSimpleName().toString();
                Object value = entry.getValue().getValue();

                if (JsonSchemaPojoType.BaseConfig.KEY.equals(key)) {
                    simpleMappingName = JSONSCHEMA_POJO_FEATURE.valueOf(value.toString());
                }
                if (JsonSchemaPojoType.BaseConfig.VALUE.equals(key)) {
                    enable = (Boolean)value;
                }
            }
            map.put(simpleMappingName, enable);
        }
        return map;
    }


    private Map<String, TypeName> toTypeMappingConfigMap(Object typeMappingValue){
        List typeMappingAnnotationList = (List)typeMappingValue;
        Map<String, TypeName> map = new HashMap<>();
        for (Object item : typeMappingAnnotationList) {
            AnnotationMirror annotationMirror = (AnnotationMirror) item;
            Map<? extends ExecutableElement, ? extends AnnotationValue> elementValues
                    = annotationMirror.getElementValues();
            String simpleMappingName = null;
            TypeName typeName = null;

            for (Map.Entry<? extends ExecutableElement, ? extends AnnotationValue> entry
                    : elementValues.entrySet()) {
                String key = entry.getKey().getSimpleName().toString();
                Object value = entry.getValue().getValue();

                if (JsonSchemaPojoType.MappingConfig.KEY.equals(key)) {
                    simpleMappingName = (String) value;
                }
                if (JsonSchemaPojoType.MappingConfig.VALUE.equals(key)) {
                    typeName = ClassName.get((TypeMirror) value);
                }
            }
            map.put(simpleMappingName, typeName);
        }
        return map;
    }

    private Map<ANNOTATION_TYPE, AnnotationSpecFactory> toAnnotationConfigMap(Object annotationValue){
        List annotationList = (List)annotationValue;
        Map<ANNOTATION_TYPE, AnnotationSpecFactory> map = new HashMap<>();
        for (Object item : annotationList) {
            AnnotationMirror annotationMirror = (AnnotationMirror) item;
            Map<? extends ExecutableElement, ? extends AnnotationValue> elementValues
                    = annotationMirror.getElementValues();
            ANNOTATION_TYPE annotationType = null;
            TypeName typeName = null;
            List<String> valueNames = null;

            for (Map.Entry<? extends ExecutableElement, ? extends AnnotationValue> entry
                    : elementValues.entrySet()) {
                String key = entry.getKey().getSimpleName().toString();
                Object value = entry.getValue().getValue();

                if (JsonSchemaPojoType.AnnotationConfig.KEY.equals(key)) {
                    annotationType = ANNOTATION_TYPE.valueOf(value.toString());
                }
                if (JsonSchemaPojoType.AnnotationConfig.VALUE.equals(key)) {
                    typeName = ClassName.get((TypeMirror) value);
                }
                if (JsonSchemaPojoType.AnnotationConfig.EXTRA.equals(key)) {
                    //TODO attach annotation with valueNames
                    valueNames = Stream.of(((String)value).split(",")).collect(Collectors.toList());
                }
            }
            map.put(annotationType, new BaseAnnotationSpecFactory((ClassName)typeName));
        }
        return map;
    }



    private Map<TypeName, List<ClassName>> toSuperConfigMap(Object typeMappingValue){
        List typeMappingAnnotationList = (List)typeMappingValue;
        Map<TypeName, List<ClassName>> map = new HashMap<>();
        for (Object item : typeMappingAnnotationList) {
            AnnotationMirror annotationMirror = (AnnotationMirror) item;
            Map<? extends ExecutableElement, ? extends AnnotationValue> elementValues
                    = annotationMirror.getElementValues();
            TypeName superClass = null;
            List<ClassName> targetClassNames = null;

            for (Map.Entry<? extends ExecutableElement, ? extends AnnotationValue> entry
                    : elementValues.entrySet()) {
                String key = entry.getKey().getSimpleName().toString();
                Object value = entry.getValue().getValue();

                if (JsonSchemaPojoType.SuperclassConfig.KEY.equals(key)) {
                    superClass = ClassName.get((TypeMirror) value);
                }
                if (JsonSchemaPojoType.SuperclassConfig.VALUE.equals(key)) {
                    String x = (String)value;
                    targetClassNames = Stream.of(x.split(","))
                                        .map(className -> ClassName.get(extractPackagePart(className), extractClassPart(className)))
                                        .collect(Collectors.toList());
                }
            }

            map.put(superClass, targetClassNames);
        }
        return map;
    }


    private String extractPackagePart(String fullName){
        return fullName.substring(0, fullName.lastIndexOf(".")).trim();
    }

    private String extractClassPart(String fullName){
        return fullName.substring(fullName.lastIndexOf(".") + 1).trim();
    }

}