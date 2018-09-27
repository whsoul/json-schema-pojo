package com.whsoul.jsch.registry;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeSpec;
import com.whsoul.jsch.TypePojoGenerator;
import com.whsoul.jsch.context.JschContext;
import com.whsoul.jsch.poet.SimpleClassBuildWrapper;
import com.whsoul.jsch.schema.draft4.JsonSchema;
import com.whsoul.jsch.schema.draft4.JsonSchemaParser;

import java.io.InputStream;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class TypeRegistry {
    private TypePojoGenerator mainTypePojoGenerator;
    private ConcurrentMap<ClassName, TypePojoGenerator> subTypesMap = new ConcurrentHashMap<>();
    private ConcurrentMap<ClassName, TypeSpec> polymorphicTypesMap = new ConcurrentHashMap<>();

    private TypeRegistry(){
    }

    public Set<Map.Entry<ClassName, TypePojoGenerator>> subTypesEntrySet(){
        return this.subTypesMap.entrySet();
    }

    public Set<Map.Entry<ClassName, TypeSpec>> polymorphicTypesEntrySet(){
        return this.polymorphicTypesMap.entrySet();
    }

    public void startMainProcess(){
        mainTypePojoGenerator.startProcess();
    }

    public String mainGenerate(){
        return mainTypePojoGenerator.generate();
    }

    public TypePojoGenerator newSubTypePojoGenerator(TypePojoGenerator parentTypePojoGenerator, ClassName className, JsonSchema schema){
        TypePojoGenerator generator = newTypePojoGenerator(parentTypePojoGenerator.getContext(), className, schema);
        generator.getParser().setParentParser(parentTypePojoGenerator.getParser());
        return generator;
    }

    public void registPolymorphicTypePojoGenerator(ClassName className, TypeSpec typeSpec){
        this.addPolymorphicTypePojoGenerator(className, typeSpec);
    }

    public TypePojoGenerator registNewSubTypePojoGenerator(TypePojoGenerator parentTypePojoGenerator, ClassName className, JsonSchema schema){
        TypePojoGenerator generator = this.newSubTypePojoGenerator(parentTypePojoGenerator, className, schema);
        this.addTypePojoGenerator(className, generator);
        return generator;
    }

    public TypePojoGenerator registNewTypePojoGenerator(JschContext context, ClassName className, InputStream jsonSchemaInputStream){
        TypePojoGenerator generator = newTypePojoGenerator(context, className, jsonSchemaInputStream);
        this.addTypePojoGenerator(className, generator);
        return generator;
    }

    public TypePojoGenerator registNewTypePojoGenerator(JschContext context, ClassName className, String jsonSchemaStr){
        TypePojoGenerator generator = newTypePojoGenerator(context, className, jsonSchemaStr);
        this.addTypePojoGenerator(className, generator);
        return generator;
    }

    public TypePojoGenerator registNewTypePojoGenerator(JschContext context, ClassName className, JsonSchema schema){
        TypePojoGenerator generator = newTypePojoGenerator(context, className, schema);
        this.addTypePojoGenerator(className, generator);
        return generator;
    }

    public TypePojoGenerator registNewTypePojoGenerator(JschContext context, String basePackage, String mainClassName, InputStream jsonSchemaInputStream){
        return this.registNewTypePojoGenerator(context, ClassName.get(basePackage, mainClassName), jsonSchemaInputStream);
    }

    public TypePojoGenerator registNewTypePojoGenerator(JschContext context, String basePackage, String mainClassName, String jsonSchemaStr){
        return this.registNewTypePojoGenerator(context, ClassName.get(basePackage, mainClassName), jsonSchemaStr);
    }

    public TypePojoGenerator registNewTypePojoGenerator(JschContext context, String basePackage, String mainClassName, JsonSchema schema){
        return this.registNewTypePojoGenerator(context, ClassName.get(basePackage, mainClassName), schema);
    }



    public static TypeRegistry initTypeRegistry(JschContext context, ClassName className, InputStream inputStream){
        TypeRegistry typeRegistry = new TypeRegistry();
        TypePojoGenerator generator = newTypePojoGenerator(context, className, inputStream);
        typeRegistry.mainTypePojoGenerator = generator;
        return typeRegistry;
    }

    public static TypeRegistry initTypeRegistry(JschContext context,ClassName className, JsonSchema schema){
        TypeRegistry typeRegistry = new TypeRegistry();
        TypePojoGenerator generator = newTypePojoGenerator(context, className, schema);
        typeRegistry.mainTypePojoGenerator = generator;
        return typeRegistry;
    }


    public void addTypePojoGenerator(ClassName className, TypePojoGenerator pojoGenerator){
        this.subTypesMap.put(className, pojoGenerator);
    }

    public void addPolymorphicTypePojoGenerator(ClassName className, TypeSpec typeSpec){
        this.polymorphicTypesMap.put(className, typeSpec);
    }

    public TypePojoGenerator getTypePojoGenerator(ClassName typeClassName){
        return subTypesMap.get(typeClassName);
    }


    private static TypePojoGenerator newTypePojoGenerator(JschContext context, ClassName className, String schemaStr){
        TypePojoGenerator generator = TypePojoGenerator.builder()
                .context(context)
                .buildWrapper(new SimpleClassBuildWrapper())
                .className(className)
                .parser(new JsonSchemaParser(schemaStr))
                .build();
        generator.getBuildWrapper().initClass(className, context.getConfigRegistry());
        return generator;
    }

    private static TypePojoGenerator newTypePojoGenerator(JschContext context, ClassName className, JsonSchema schema){

        TypePojoGenerator generator = TypePojoGenerator.builder()
                                            .context(context)
                                            .buildWrapper(new SimpleClassBuildWrapper())
                                            .className(className)
                                            .parser(new JsonSchemaParser(schema))
                                            .build();
        generator.getBuildWrapper().initClass(className, context.getConfigRegistry());

        return generator;
    }

    private static TypePojoGenerator newTypePojoGenerator(JschContext context, ClassName className, InputStream dataStream){
        TypePojoGenerator generator = TypePojoGenerator.builder()
                .context(context)
                .buildWrapper(new SimpleClassBuildWrapper())
                .className(className)
                .parser(new JsonSchemaParser(dataStream))
                .build();
        generator.getBuildWrapper().initClass(className, context.getConfigRegistry());
        return generator;
    }

}
