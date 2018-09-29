package com.whsoul.jsch;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;
import com.whsoul.jsch.conf.ConfigRegistry;
import com.whsoul.jsch.poet.SimpleClassBuildWrapper;
import com.whsoul.jsch.schema.draft4.JsonSchemaParser;
import com.whsoul.jsch.schema.draft4.SchemaObject;
import com.whsoul.jsch.schema.draft4.sub.definition.SimpleTypes;
import com.whsoul.jsch.schema.draft4.sub.definition.Value;
import org.junit.Test;

import javax.lang.model.SourceVersion;
import java.io.IOException;
import java.io.InputStream;
import java.util.AbstractMap;

import static org.junit.Assert.assertThat;

public class JsonSchema2PoetPojoTest {

    private SimpleClassBuildWrapper initCommonClassBuildWrapper(String basePackageName, String mainClassName){
        SimpleClassBuildWrapper wrapper = new SimpleClassBuildWrapper();
        wrapper.initClass(ClassName.get(basePackageName, mainClassName), ConfigRegistry.defaultConfigRegistry());

        return wrapper;

    }

    @Test
    public void test01() throws IOException {
        JsonSchemaParser parser = new JsonSchemaParser(getFileStream("sample_schema_01.json"));
        SchemaObject schema = (SchemaObject)parser.getParsedSchema();
        SimpleClassBuildWrapper simpleClassBuildWrapper = initCommonClassBuildWrapper("com.whsoul.test", "Student");

        schema.properties.map.entrySet().stream()
                .filter(e -> e.getValue() instanceof SchemaObject)
                .map(e -> new AbstractMap.SimpleEntry<String, SchemaObject>(e.getKey(), (SchemaObject)e.getValue()))
                .filter(entry -> (entry.getValue()).type != null)
                .forEach(entry -> {
                    if ((entry.getValue()).type.isSimpleType()) {
                        addSimpleTypeField(simpleClassBuildWrapper, entry.getKey(), (entry.getValue()).type.getAnyOfValue(Value.SimpleTypeValue.class).getValue());
                    }
                });

        TypeSpec typeSpec = simpleClassBuildWrapper.build();
        JavaFile javaFile = JavaFile.builder("com.example.helloworld", typeSpec)
                .build();

        javaFile.writeTo(System.out);
    }


    @Test
    public void test02() throws IOException {
        JsonSchemaParser parser = new JsonSchemaParser(getFileStream("document_with_meta.json"));
        SchemaObject schema = (SchemaObject)parser.getParsedSchema();
        SimpleClassBuildWrapper simpleClassBuildWrapper = initCommonClassBuildWrapper("com.whsoul.test","DocumentWithMeta");

        schema.properties.map.entrySet().stream()
                .filter(e -> e.getValue() instanceof SchemaObject)
                .map(e -> new AbstractMap.SimpleEntry<String, SchemaObject>(e.getKey(), (SchemaObject)e.getValue()))
                .forEach(entry -> {
                    if ((entry.getValue()).type.isSimpleType()) {
                        addSimpleTypeField(simpleClassBuildWrapper, entry.getKey(), (entry.getValue()).type.getAnyOfValue(Value.SimpleTypeValue.class).getValue());
                    }
                });

        TypeSpec typeSpec = simpleClassBuildWrapper.build();
        JavaFile javaFile = JavaFile.builder("com.example.helloworld", typeSpec)
                .build();

        javaFile.writeTo(System.out);
    }


    private void addClassField(SimpleClassBuildWrapper simpleClassBuildWrapper, String fieldName, String packageName, String className) {
        simpleClassBuildWrapper.addPublicField(ClassName.get(packageName, className), fieldName);
    }

    private void addSimpleTypeField(SimpleClassBuildWrapper simpleClassBuildWrapper, String fieldName, SimpleTypes types){
        switch (types.type){
            case $string: {
                simpleClassBuildWrapper.addPublicFieldWithMappingName("string", fieldName);
                break;
            }
            case $number: {
                simpleClassBuildWrapper.addPublicFieldWithMappingName("long", fieldName);
                break;
            }
            case $integer: {
                simpleClassBuildWrapper.addPublicFieldWithMappingName("int", fieldName);
                break;
            }
            case $boolean: {
                simpleClassBuildWrapper.addPublicFieldWithMappingName("boolean", fieldName);
                break;
            }
            case $object:{
                simpleClassBuildWrapper.addPublicFieldWithMappingName("object", fieldName);
            }
        }
    }

    @Test
    public void sss(){
        System.out.println(SourceVersion.isIdentifier("@ctype"));
    }

    private InputStream getFileStream(String filePath){
        return getClass().getClassLoader().getResourceAsStream(filePath);
    }


}