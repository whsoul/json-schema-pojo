package com.whsoul.jsch.conf;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeName;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DefaultTypeMappingConfig implements Config<String, TypeName>{

    private static final Map<String, TypeName> configMap = new HashMap<String, TypeName>() {{
        put("string", ClassName.get(String.class));
        put("boolean", TypeName.BOOLEAN);
        put("long", TypeName.LONG);
        put("integer", ClassName.get(Integer.class));
        put("number", ClassName.get(Double.class));
        put("array", ClassName.get(List.class));
        put("object", TypeName.OBJECT);
        put("null", ClassName.get(Void.class));
    }};

    @Override
    public Map<String, TypeName> getConfigMap() {
        return configMap;
    }

}
