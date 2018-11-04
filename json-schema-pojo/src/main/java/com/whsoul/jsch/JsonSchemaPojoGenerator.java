package com.whsoul.jsch;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.whsoul.jsch.conf.ConfigRegistry;
import com.whsoul.jsch.context.JschContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

public class JsonSchemaPojoGenerator{
    //TODO spec
    // object type의 additionalProperties 처리
    // definitions allOf, enum 처리
    // class이름, property 이름 업퍼클레스 처리
    // getter, setter 처리
    // minItems, maxItems 처리

    private static Logger logger = LoggerFactory.getLogger(JsonSchemaPojoGenerator.class);

    private JschContext context;

    public JsonSchemaPojoGenerator(String pkgName, String className, InputStream jsonSchemaInputStream){
        ClassName cName = ClassName.get(pkgName, className);
        this.context = new JschContext(cName, jsonSchemaInputStream, ConfigRegistry.defaultConfigRegistry());
    }

    public JsonSchemaPojoGenerator(String pkgName, String className, InputStream jsonSchemaInputStream, ConfigRegistry configRegistry){
        ClassName cName = ClassName.get(pkgName, className);
        ConfigRegistry registry = ConfigRegistry.mergeConfigRegistry(configRegistry);
        this.context = new JschContext(cName, jsonSchemaInputStream, registry);
    }

    public Map<ClassName, String> generate(){
        Map<ClassName, String> result = new LinkedHashMap<>();

        this.context.getTypeRegistry().startMainProcess();
        result.put(this.context.getMainClassName(), this.context.getTypeRegistry().mainGenerate());

        this.context.getTypeRegistry().subTypesEntrySet().stream()
                .forEach(entry -> entry.getValue().startProcess());

        Map<ClassName, String> subResult = this.context.getTypeRegistry().subTypesEntrySet().stream()
                .map(entry -> new AbstractMap.SimpleEntry<>(entry.getKey(), entry.getValue().generate()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (ov, nv) -> nv, LinkedHashMap::new));

        Map<ClassName, String> subPolymorphicResult = this.context.getTypeRegistry().polymorphicTypesEntrySet().stream()
                .map(entry -> {
                    JavaFile javaFile = JavaFile.builder(entry.getKey().packageName(), entry.getValue().toBuilder().build())
                            .build();
                    return new AbstractMap.SimpleEntry<>(entry.getKey(), javaFile.toString());
                }).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (ov, nv) -> nv, LinkedHashMap::new));

        result.putAll(subPolymorphicResult);
        result.putAll(subResult);

        logger.debug("Result Class size : " + result.size());

        return result;
    }

}
