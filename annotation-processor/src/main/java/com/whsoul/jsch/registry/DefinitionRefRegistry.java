package com.whsoul.jsch.registry;

import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeName;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class DefinitionRefRegistry {

    private ConcurrentMap<DefinitionRef, DefinitionTypeDescription> definitionMap = new ConcurrentHashMap<>();

    public static DefinitionRefRegistry defaultDefinitionRefRegistry(ClassName mainclassName) {
        DefinitionRefRegistry registry = new DefinitionRefRegistry();
        DefinitionRef ref = new DefinitionRef();
        ref.definitionFullPath = "#";
        ref.definitionSimpleName = "#";
        DefinitionTypeDescription typeDescription = new DefinitionTypeDescription();
        typeDescription.definitionName = mainclassName;
        registry.addDefinition(ref, typeDescription);
        return registry;
    }

    public void addDefinition(DefinitionRef key, DefinitionTypeDescription value){
        this.definitionMap.put(key, value);
    }

    public DefinitionTypeDescription searchDefinition(String definitionRefFullStr){
        //TODO 향후 definition 상대경로에 대한 부분도 고려해야 할듯...
        DefinitionRef ref = new DefinitionRef();
        ref.definitionFullPath = definitionRefFullStr;
        return definitionMap.get(ref);
    }

    public static class DefinitionRef{
        public String definitionFullPath;
        public String definitionSimpleName;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            DefinitionRef that = (DefinitionRef) o;
            return Objects.equals(definitionFullPath, that.definitionFullPath);
        }

        @Override
        public int hashCode() {
            return Objects.hash(definitionFullPath);
        }
    }

    public static class DefinitionTypeDescription{
        public boolean isCustomClass;
        public TypeName definitionName;
        public List<AnnotationSpec> restrictAnnotationSpecList;
    }

}
