package com.whsoul.jsch.context;

import com.squareup.javapoet.ClassName;
import com.whsoul.jsch.conf.ConfigRegistry;
import com.whsoul.jsch.registry.DefinitionRefRegistry;
import com.whsoul.jsch.registry.PendingTypeRegistry;
import com.whsoul.jsch.registry.SchemaExecutorRegistry;
import com.whsoul.jsch.registry.TypeRegistry;
import com.whsoul.jsch.schema.draft4.JsonSchema;

import java.io.InputStream;

public class JschContext {
    private ClassName mainClassName;
    private String definitionsPackage;
    private String polymorphicsPackage;
    private ConfigRegistry configRegistry;
    private TypeRegistry typeRegistry;
    private PendingTypeRegistry pendingTypeRegistry;
    private SchemaExecutorRegistry schemaProcessRegistry;
    private DefinitionRefRegistry definitionRegistry;

    public JschContext(ClassName mainClassName, InputStream jsonInputStream, ConfigRegistry configRegistry){
        this.mainClassName = mainClassName;
        this.definitionsPackage = mainClassName.packageName() + "." + ConfigRegistry.subPackagePath;
        this.polymorphicsPackage = mainClassName.packageName() + "." + ConfigRegistry.subPolymophicPackagePath;
        this.configRegistry = configRegistry != null ? configRegistry : ConfigRegistry.defaultConfigRegistry();
        this.typeRegistry = TypeRegistry.initTypeRegistry(this, mainClassName, jsonInputStream);
        this.schemaProcessRegistry = SchemaExecutorRegistry.defaultSchemaProcessRegistry(this);
        this.pendingTypeRegistry = PendingTypeRegistry.defaultPendingTypeRegistry();
        this.definitionRegistry = DefinitionRefRegistry.defaultDefinitionRefRegistry(mainClassName);
    }

    public JschContext(ClassName mainClassName, JsonSchema schema, ConfigRegistry configRegistry){
        this.mainClassName = mainClassName;
        this.definitionsPackage = mainClassName.packageName() + "." + ConfigRegistry.subPackagePath;
        this.polymorphicsPackage = mainClassName.packageName() + "." + ConfigRegistry.subPolymophicPackagePath;
        this.configRegistry = configRegistry != null ? configRegistry : ConfigRegistry.defaultConfigRegistry();
        this.typeRegistry = TypeRegistry.initTypeRegistry(this, mainClassName, schema);
        this.schemaProcessRegistry = SchemaExecutorRegistry.defaultSchemaProcessRegistry(this);
        this.pendingTypeRegistry = PendingTypeRegistry.defaultPendingTypeRegistry();
        this.definitionRegistry = DefinitionRefRegistry.defaultDefinitionRefRegistry(mainClassName);
    }

    public ClassName getMainClassName() {
        return mainClassName;
    }

    public String getDefinitionsPackage() {
        return definitionsPackage;
    }

    public String getPolymorphicsPackage() {
        return polymorphicsPackage;
    }

    public ConfigRegistry getConfigRegistry() {
        return configRegistry;
    }

    public TypeRegistry getTypeRegistry() {
        return typeRegistry;
    }

    public PendingTypeRegistry getPendingTypeRegistry() {
        return pendingTypeRegistry;
    }

    public SchemaExecutorRegistry getSchemaProcessRegistry() {
        return schemaProcessRegistry;
    }

    public DefinitionRefRegistry getDefinitionRegistry() {
        return definitionRegistry;
    }


    public void set(ConfigRegistry configRegistry){
        this.configRegistry = configRegistry;
    }

    public void set(TypeRegistry typeRegistry){
        this.typeRegistry = typeRegistry;
    }

    public void set(PendingTypeRegistry pendingTypeRegistry){
        this.pendingTypeRegistry = pendingTypeRegistry;
    }

    public void set(SchemaExecutorRegistry schemaProcessRegistry){
        this.schemaProcessRegistry = schemaProcessRegistry;
    }

}
