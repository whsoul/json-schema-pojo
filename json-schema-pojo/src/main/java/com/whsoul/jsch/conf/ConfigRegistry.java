package com.whsoul.jsch.conf;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeName;
import com.whsoul.jsch.support.AnnotationSpecFactory;
import com.whsoul.jsch.common.ANNOTATION_TYPE;
import com.whsoul.jsch.common.JSONSCHEMA_POJO_FEATURE;
import com.whsoul.jsch.exception.JschLibaryException;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ConfigRegistry {
    public static final String subPackagePath = "definitions";
    public static final String subPolymophicPackagePath = "polymorphics";
    public static final String subPolymophicPrefix = "TypeGroup";
    public static final String invalidNamePrefix = "$";
    public static final String definitionBasePath = "#/definitions/";
    public static final String defaultInnerEnumClassType = "ENUM";

    Config<JSONSCHEMA_POJO_FEATURE, Boolean> baseConfig;
    Config<String, TypeName> typeMappingConfig;
    Config<ANNOTATION_TYPE, AnnotationSpecFactory> annotationConfig;
    Config<TypeName, List<ClassName>> superclassConfig;
    Config<TypeName, List<ClassName>> superinterfaceConfig;

    private ConfigRegistry(){
    }

    public static ConfigRegistry defaultConfigRegistry() {
        ConfigRegistry registry = new ConfigRegistry();
        registry.baseConfig = new DefaultBaseConfig();
        registry.typeMappingConfig = new DefaultTypeMappingConfig();
        registry.annotationConfig = new DefaultAnnotationConfig();
        registry.superclassConfig = new DefaultSuperclassConfig();
        registry.superinterfaceConfig = new DefaultSuperinterfaceConfig();
        return registry;
    }

    public static ConfigRegistry mergeConfigRegistry(ConfigRegistry configRegistry){
        ConfigRegistry mergedRegistry = ConfigRegistry.defaultConfigRegistry();
        if(configRegistry.getTypeMappingConfig() != null)       mergedRegistry.getTypeMappingConfig().putAll(configRegistry.getTypeMappingConfig());
        if(configRegistry.getBaseConfig() != null)              mergedRegistry.getBaseConfig().putAll(configRegistry.getBaseConfig());
        if(configRegistry.getAnnotationConfig() != null)        mergedRegistry.getAnnotationConfig().putAll(configRegistry.getAnnotationConfig());
        if(configRegistry.getSuperInterfaceConfig() != null)    mergedRegistry.getSuperInterfaceConfig().putAll(configRegistry.getSuperInterfaceConfig());
        if(configRegistry.getSuperClassConfig() != null)        mergedRegistry.getSuperClassConfig().putAll(configRegistry.getSuperClassConfig());
        return mergedRegistry;
    }

    public ConfigRegistry(Config<JSONSCHEMA_POJO_FEATURE, Boolean> baseConfig
                            , Config<String, TypeName> typeMappingConfig
                            , Config<ANNOTATION_TYPE, AnnotationSpecFactory> annotationConfig
                            , Config<TypeName, List<ClassName>> superclassConfig
                            , Config<TypeName, List<ClassName>> superinterfaceConfig) {

        this.baseConfig = baseConfig != null ? baseConfig : new DefaultBaseConfig();
        this.typeMappingConfig = typeMappingConfig != null ? typeMappingConfig : new DefaultTypeMappingConfig();
        this.annotationConfig = annotationConfig != null ? annotationConfig : new DefaultAnnotationConfig();
        this.superclassConfig = superclassConfig != null ? superclassConfig : new DefaultSuperclassConfig();
        this.superinterfaceConfig = superinterfaceConfig != null ? superinterfaceConfig : new DefaultSuperinterfaceConfig();
    }

    public void setPolymorphismSuperClassConfig(Config<TypeName, List<ClassName>> polymorphismSuperClassConfig) {
        this.superclassConfig = polymorphismSuperClassConfig;
    }

    public void setSuperinterfaceConfig(Config<TypeName, List<ClassName>> superinterfaceConfig) {
        this.superinterfaceConfig = superinterfaceConfig;
    }

    public void setTypeMappingConfig(Config<String, TypeName> typeMappingConfig) {
        this.typeMappingConfig = typeMappingConfig;
    }

    public void setAnnotationConfig(Config<ANNOTATION_TYPE, AnnotationSpecFactory> annotationConfig){
        this.annotationConfig = annotationConfig;
    }

    public Map<JSONSCHEMA_POJO_FEATURE, Boolean> getBaseConfig() {
        return baseConfig.getConfigMap();
    }

    public Map<String, TypeName> getTypeMappingConfig() {
        return typeMappingConfig.getConfigMap();
    }

    public Map<TypeName, List<ClassName>> getSuperClassConfig() {
        return superclassConfig.getConfigMap();
    }

    public Map<TypeName, List<ClassName>> getSuperInterfaceConfig() {
        return superinterfaceConfig.getConfigMap();
    }


    public Map<ANNOTATION_TYPE, AnnotationSpecFactory> getAnnotationConfig() {
        return annotationConfig.getConfigMap();
    }


    public boolean getBaseConfig(JSONSCHEMA_POJO_FEATURE feature){
        if(this.baseConfig.getConfigMap() == null){
            throw new JschLibaryException("base config Not found : " + feature);
        }

        return this.baseConfig.getConfigMap().get(feature);
    }

    public TypeName getTypeMapping(String typeMappingName){
        if(this.typeMappingConfig.getConfigMap() == null){
            throw new JschLibaryException("typeMapping config Not found : " + typeMappingName);
        }

        return this.typeMappingConfig.getConfigMap().get(typeMappingName);
    }

    public AnnotationSpecFactory getAnnotationType(ANNOTATION_TYPE annotationType){
        if(this.annotationConfig.getConfigMap() == null){
            throw new JschLibaryException("ANOTATION_TYPE config Not found : " + annotationType.name());
        }
        return this.annotationConfig.getConfigMap().get(annotationType);
    }

    public List<ClassName> getSubClassListBySuperClassName(ClassName className){
        if(this.superclassConfig.getConfigMap() == null){
            throw new JschLibaryException("ANOTATION_TYPE config Not found : " + className);
        }
        return this.superclassConfig.getConfigMap().get(className);
    }

    public List<TypeName> getSuperClassListBySubClassName(ClassName className){
        if(this.superclassConfig.getConfigMap() == null){
            throw new JschLibaryException("SuperClass config Not found with fieldName: " + className);
        }

        return superclassConfig.getConfigMap().entrySet().stream()
                .filter(entry -> entry.getValue().contains(className))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    public List<ClassName> getSubClassListBySuperInterfaceName(ClassName className){
        if(this.superinterfaceConfig.getConfigMap() == null){
            throw new JschLibaryException("ANOTATION_TYPE config Not found : " + className);
        }
        return this.superinterfaceConfig.getConfigMap().get(className);
    }

    public List<TypeName> getSuperInterfaceBySubClassName(ClassName className){
        if(this.superinterfaceConfig.getConfigMap() == null){
            throw new JschLibaryException("SuperInterface config Not found with fieldName: " + className);
        }

        return superinterfaceConfig.getConfigMap().entrySet().stream()
                .filter(entry -> entry.getValue().contains(className))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }
}
