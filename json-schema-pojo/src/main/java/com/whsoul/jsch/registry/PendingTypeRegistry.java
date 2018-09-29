package com.whsoul.jsch.registry;

import com.squareup.javapoet.ClassName;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class PendingTypeRegistry {

    private ConcurrentMap<ClassName, List<ClassName>> superinterfaceMap = new ConcurrentHashMap<>();

    public static PendingTypeRegistry defaultPendingTypeRegistry() {
        PendingTypeRegistry registry = new PendingTypeRegistry();
        return registry;
    }

    Set<Map.Entry<ClassName, List<ClassName>>> superinterfaceMap(){
        return this.superinterfaceMap.entrySet();
    }

    public void putSuperinterfaceType(ClassName targetClassName, List<ClassName> superinterfaceClassNameList){
        this.superinterfaceMap.put(targetClassName, superinterfaceClassNameList);
    }

    public void addSuperinterfaceType(ClassName targetClassName, ClassName superinterfaceClassName){
        if(this.superinterfaceMap.get(targetClassName) != null){
            this.superinterfaceMap.get(targetClassName).add(superinterfaceClassName);
        }else{
            this.superinterfaceMap.putIfAbsent(targetClassName, new ArrayList<ClassName>(){{ add(superinterfaceClassName); }});
        }
    }

    public List<ClassName> getSuperinterfaceType(ClassName className){
        return this.superinterfaceMap.getOrDefault(className, Collections.EMPTY_LIST);
    }

}
