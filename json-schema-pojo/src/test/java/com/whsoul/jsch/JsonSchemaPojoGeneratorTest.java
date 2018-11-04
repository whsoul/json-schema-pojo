package com.whsoul.jsch;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.whsoul.jsch.conf.*;
import com.whsoul.jsch.test.TestAbstractClass1;
import com.whsoul.jsch.test.TestAbstractParameterizedClass1;
import com.whsoul.jsch.test.TestInterface1;
import org.junit.Test;

import java.io.InputStream;
import java.util.*;

public class JsonSchemaPojoGeneratorTest {

    @Test
    public void TypePojoGeneratorTest1(){
        JsonSchemaPojoGenerator pojoGenerator = new JsonSchemaPojoGenerator("com.whsoul.pojo", "Student", getFileStream("sample_schema_01.json"));
        System.out.println(pojoGenerator.generate());
    }

    @Test
    public void TypePojoGeneratorTest1_withExtendsImplements(){
        JsonSchemaPojoGenerator pojoGenerator = new JsonSchemaPojoGenerator(
                "com.whsoul.pojo",
                "Student",
                getFileStream("sample_schema_01.json")
                ,new ConfigRegistry(
                    new DefaultBaseConfig()
                    , new DefaultTypeMappingConfig()
                    , new DefaultAnnotationConfig()
                    , () -> new HashMap<TypeName, List<ClassName>>(){{
                        put(TypeName.get(TestAbstractClass1.class), Arrays.asList(ClassName.get("com.whsoul.pojo", "Student")));
                        put(ParameterizedTypeName.get(TestAbstractParameterizedClass1.class, String.class, Integer.class)
                                , Arrays.asList(ClassName.get("com.whsoul.pojo", "University")
                                        ,ClassName.get("com.whsoul.pojo", "University").nestedClass("Location")
                                ));
                    }}
                    , () -> new HashMap<TypeName, List<ClassName>>(){{
                                put(TypeName.get(TestInterface1.class), Arrays.asList(ClassName.get("com.whsoul.pojo", "Student")));
                                put(ParameterizedTypeName.get(Config.class, String.class, String.class), Arrays.asList(ClassName.get("com.whsoul.pojo", "University")));
                                put(ParameterizedTypeName.get(Comparable.class, String.class), Arrays.asList(ClassName.get("com.whsoul.pojo", "University")
                                                                                                            ,ClassName.get("com.whsoul.pojo", "University").nestedClass("Location")
                                                                                                            ,ClassName.get("com.whsoul.pojo", "University").nestedClass("Location").nestedClass("position")));
                    }}
            )
        );
        System.out.println(pojoGenerator.generate());
    }

    private InputStream getFileStream(String filePath){
        return getClass().getClassLoader().getResourceAsStream(filePath);
    }

}