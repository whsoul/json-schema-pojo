package com.whsoul.pojogen.poet;

import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
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

        Map<String, SimpleClassBuildWrapper.JavaClass> configMap = new HashMap<String, SimpleClassBuildWrapper.JavaClass>() {{
            put("string", new SimpleClassBuildWrapper.PackageClass(String.class));
            put("int", new SimpleClassBuildWrapper.PrimitiveClass(TypeName.INT));
            put("Integer", new SimpleClassBuildWrapper.PackageClass(Integer.class));
            put("array", new SimpleClassBuildWrapper.PackageClass(List.class));
            put("map", new SimpleClassBuildWrapper.PackageClass(Map.class));
        }};
        wrapper.initClass("Document", new SimpleClassBuildWrapper.Config(configMap));
        wrapper.addPublicField("string", "docType");
        wrapper.addPublicField("string", "title");
        wrapper.addPublicField("string", "theme");
        wrapper.addPublicField("int", "gen");
        wrapper.addPublicField("array,string", "components");
        wrapper.addPublicField("map,string,string", "map");
        wrapper.addPublicField("com.whsoul.poet", "TestPojo", "testPojo");
        wrapper.addPublicFieldWithAnnotation("com.whsoul.poet", "Pojo", "pojo", NotNull.class);
        TypeSpec typeSpec = wrapper.build();

        JavaFile javaFile = JavaFile.builder("com.example.helloworld", typeSpec)
                .build();

        javaFile.writeTo(System.out);
    }



}
