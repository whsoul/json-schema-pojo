package com.whsoul.jsch.conf;

import java.util.Map;

public interface Config<K,V> {
        Map<K, V> getConfigMap();
}
