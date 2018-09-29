package com.whsoul.jsch.conf;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeName;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DefaultSuperclassConfig implements Config<TypeName, List<ClassName>> {

    private static final Map<TypeName, List<ClassName>> superInterfaceMap = new HashMap<TypeName, List<ClassName>>() {{
    }};

    @Override
    public Map<TypeName, List<ClassName>> getConfigMap() {
        return superInterfaceMap;
    }

}
