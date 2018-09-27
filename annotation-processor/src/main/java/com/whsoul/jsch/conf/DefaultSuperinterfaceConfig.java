package com.whsoul.jsch.conf;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeName;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DefaultSuperinterfaceConfig implements Config<TypeName, List<ClassName>> {

    private static final Map<TypeName, List<ClassName>> superClassMap = new HashMap<TypeName, List<ClassName>>() {{
    }};

    @Override
    public Map<TypeName, List<ClassName>> getConfigMap() {
        return superClassMap;
    }

}
