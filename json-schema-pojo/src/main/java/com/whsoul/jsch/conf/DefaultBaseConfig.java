package com.whsoul.jsch.conf;

import com.whsoul.jsch.common.JSONSCHEMA_POJO_FEATURE;

import java.util.HashMap;
import java.util.Map;

public class DefaultBaseConfig implements Config<JSONSCHEMA_POJO_FEATURE, Boolean>{

    private static final Map<JSONSCHEMA_POJO_FEATURE, Boolean> configMap = new HashMap<JSONSCHEMA_POJO_FEATURE, Boolean>() {{
        put(JSONSCHEMA_POJO_FEATURE.AUTO_POLYMOPHIC_ONEOF, true);
        put(JSONSCHEMA_POJO_FEATURE.STRING_ENUM_AS_ENUM, true);
    }};

    @Override
    public Map<JSONSCHEMA_POJO_FEATURE, Boolean> getConfigMap() {
        return configMap;
    }

}
