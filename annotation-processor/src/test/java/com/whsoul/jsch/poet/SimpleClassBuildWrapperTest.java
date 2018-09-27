package com.whsoul.jsch.poet;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import com.whsoul.jsch.conf.ConfigRegistry;
import org.junit.Test;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SimpleClassBuildWrapperTest {

    @Test
    public void test() throws IOException {
        SimpleClassBuildWrapper wrapper = new SimpleClassBuildWrapper();

        Map<String, TypeName> configMap = new HashMap<String, TypeName>() {{
            put("string", ClassName.get(String.class));
            put("int", TypeName.INT);
            put("Integer", ClassName.get(Integer.class));
            put("array", ClassName.get(List.class));
            put("map", ClassName.get(Map.class));
        }};

        ConfigRegistry registry = new ConfigRegistry(null, () -> configMap, null, null, null);
        wrapper.initClass(ClassName.get("com.whsoul.test","Document"), registry);
        wrapper.addPublicFieldWithMappingName("string", "docType");
        wrapper.addPublicFieldWithMappingName("string", "title");
        wrapper.addPublicFieldWithMappingName("string", "theme");
        wrapper.addPublicFieldWithMappingName("int", "gen");
        wrapper.addPublicFieldWithMappingName("array", "components");
        wrapper.addPublicFieldWithMappingName("map", "map");
        wrapper.addPublicField(ClassName.get("com.whsoul.poet", "TestPojo"), "testPojo");
        wrapper.addPublicFieldWithAnnotation("com.whsoul.poet", "Pojo", "pojo", NotNull.class);

        SimpleClassBuildWrapper subClassWrapper = new SimpleClassBuildWrapper();
        subClassWrapper.initClass(ClassName.get("com.whsoul.test","SubDocument"), registry);
        subClassWrapper.addPublicFieldWithMappingName("int", "id");
        subClassWrapper.addPublicFieldWithMappingName("string", "title1");
        subClassWrapper.addPublicFieldWithMappingName("string", "theme2");

        wrapper.addPublicInnerClass(subClassWrapper);

        TypeSpec typeSpec = wrapper.build();

        JavaFile javaFile = JavaFile.builder("com.example.helloworld", typeSpec)
                .build();

        javaFile.writeTo(System.out);
    }



}
