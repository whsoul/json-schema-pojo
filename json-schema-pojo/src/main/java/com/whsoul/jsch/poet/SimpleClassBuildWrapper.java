package com.whsoul.jsch.poet;

import com.squareup.javapoet.*;
import com.whsoul.jsch.conf.ConfigRegistry;
import com.whsoul.jsch.exception.JschLibaryException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.lang.model.element.Modifier;
import java.lang.annotation.Annotation;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SimpleClassBuildWrapper {
    private static Logger logger = LoggerFactory.getLogger(SimpleClassBuildWrapper.class);

    //private TypeSpec.Builder builder;
    private ClassName className;
    private TypeSpec.Builder typeSpecBuilder;
    private Map<String, FieldSpec.Builder> fieldMap = new LinkedHashMap<>();
    private Map<String, TypeSpec.Builder> innerClassMap = new LinkedHashMap<>();
    private Map<String, MethodSpec.Builder> methodMap = new LinkedHashMap<>();

    private ConfigRegistry configRegistry;

    public void initClass(ClassName className, ConfigRegistry configRepository){
        this.className = className;
        this.typeSpecBuilder = TypeSpec.classBuilder(className).addModifiers(Modifier.PUBLIC);
        this.configRegistry = configRepository == null ? ConfigRegistry.defaultConfigRegistry() : configRepository;
    }

    public void addPublicInnerClass(SimpleClassBuildWrapper buildWrapper){
        buildWrapper.addModifilers(Modifier.PUBLIC, Modifier.STATIC);
        this.innerClassMap.put(buildWrapper.className.simpleName(), buildWrapper.typeSpecBuilder);
    }


    public void addInnerClass(ClassName nestedClassName, TypeSpec.Builder subClassTypeSpecBuilder){
        this.innerClassMap.put(nestedClassName.simpleName(), subClassTypeSpecBuilder);
    }

    public void addModifilers(Modifier... modifiers){
        this.typeSpecBuilder.addModifiers(modifiers);
    }

    public void addPublicFieldWithMappingName(String mappingName, String name){
        this.addPublicFieldWithMappingNameWithAnnotation(mappingName, name, name, null);
    }

    public void addPublicFieldWithMappingNameWithAnnotation(String mappingName, String saveName, String fieldName, AnnotationSpec annotationSpec){
        if(this.configRegistry.getTypeMappingConfig().get(mappingName) == null){
            throw new JschLibaryException(mappingName + " is not supported with Config");
        }
        TypeName typeName = this.configRegistry.getTypeMappingConfig().get(mappingName);
        if(annotationSpec == null) {
            this.addPublicField(typeName, saveName, fieldName);
        }else{
            this.addPublicFieldWithAnnotation(typeName, saveName, fieldName, annotationSpec);
        }
    }

    public void addPublicField(Class<?> type, String fieldName){
        this.addPublicField(type, fieldName, fieldName);
    }

    public void addPublicField(Class<?> type, String saveName, String fieldName){
        this.addPublicField(ClassName.get(type), saveName, fieldName);
    }


    public void addPublicField(TypeName returnTypeName, String fieldName){
        this.addPublicField(returnTypeName, fieldName, fieldName);
    }

    public void addPublicField(TypeName returnTypeName, String saveName, String fieldName){
        this.fieldMap.put(saveName, FieldSpec.builder(returnTypeName, fieldName, Modifier.PUBLIC));
    }


    public void addPublicFieldWithAnnotation(Class<?> type, String name, Annotation anno){
        this.addPublicFieldWithAnnotation(ClassName.get(type), name, anno);
    }

    public void addPublicFieldWithAnnotation(ClassName className, String name, Annotation anno){
        this.addPublicFieldWithAnnotation(className, name, AnnotationSpec.builder(anno.getClass()).build());
    }

    public void addPublicFieldWithAnnotation(String pkgName, String className, String name, Class<? extends Annotation> anno){
        ClassName returnClass = ClassName.get(pkgName, className);
        this.addPublicFieldWithAnnotation(returnClass, name, AnnotationSpec.builder(anno).build());
    }

    public void addPublicFieldWithAnnotation(ClassName className, String saveName, String fieldName, AnnotationSpec annoSpec){
        this.fieldMap.put(saveName, FieldSpec.builder(className, fieldName)
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(annoSpec));
    }


    public void addPublicFieldWithAnnotation(ClassName className, String name, AnnotationSpec annoSpec){
        this.addPublicFieldWithAnnotation(className, name, name, annoSpec);
    }

    public void addPublicFieldWithAnnotation(TypeName typeName, String name, AnnotationSpec annoSpec){
        this.addPublicFieldWithAnnotation(typeName, name, name, annoSpec);
    }

    public void addPublicFieldWithAnnotations(TypeName typeName, String name, List<AnnotationSpec> annoSpec){
        this.addPublicFieldWithAnnotations(typeName, name, name, annoSpec);
    }

    public void addPublicFieldWithAnnotation(TypeName typeName, String saveName, String fieldName, AnnotationSpec annoSpec){
        this.fieldMap.put(saveName, FieldSpec.builder(typeName, fieldName)
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(annoSpec));
    }

    public void addPublicFieldWithAnnotations(TypeName typeName, String saveName, String fieldName, List<AnnotationSpec> annoSpecList){
        this.fieldMap.put(saveName, FieldSpec.builder(typeName, fieldName)
                .addModifiers(Modifier.PUBLIC)
                .addAnnotations(annoSpecList));
    }

    public void addAnnotationToField(String fieldName, AnnotationSpec annotationSpec){
        if(this.fieldMap.get(fieldName) == null){
            throw new JschLibaryException("field not found at SimpleClassBuilderWrapper fieldMap : " + fieldName);
        }
        this.fieldMap.get(fieldName).addAnnotation(annotationSpec);
    }


    public void addAnnotationToInnerClass(String fieldName, AnnotationSpec annotationSpec){
        if(this.innerClassMap.get(fieldName) == null){
            throw new JschLibaryException("innerClass not found at SimpleClassBuilderWrapper fieldMap : " + fieldName);
        }
        this.innerClassMap.get(fieldName).addAnnotation(annotationSpec);
    }

    public void addAnnotation(AnnotationSpec annotationSpec){
        this.typeSpecBuilder.addAnnotation(annotationSpec);
    }

    public void addSuperclass(TypeName superClassType){
        logger.debug("add " + superClassType + " to SuperClass : " + this.className);
        this.typeSpecBuilder.superclass(superClassType);
    }

    public void addSuperinterface(TypeName superInterfaceType){
        this.typeSpecBuilder.addSuperinterface(superInterfaceType);
    }

    public void addSetter(Class<?> type, String name){
        ParameterSpec field = ParameterSpec.builder(type, name)
                .build();
        MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder("set" + name.substring(0, 1).toUpperCase() + name.substring(1))
                .addParameter(field)
                .addStatement("this.$N = $N", name, name);

        this.methodMap.put(name, methodBuilder);
    }

    public void addGetter(Class<?> type, String name){
        MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder("get" + name.substring(0, 1).toUpperCase() + name.substring(1))
                .addStatement("return this.$N", name)
                .returns(type);
        this.methodMap.put(name, methodBuilder);
    }


    public FieldSpec.Builder findFieldSpecByName(String fieldName){
        return fieldMap.get(fieldName);
    }

    public MethodSpec.Builder findMethodSpecByName(String methodName){
        return methodMap.get(methodName);
    }

    public TypeSpec.Builder findInnerClassByName(String className){
        return innerClassMap.get(className);
    }


    public TypeSpec build(){
        buildSubSpec();
        return this.typeSpecBuilder.build();
    }

    public TypeSpec.Builder builder(){
        buildSubSpec();
        return this.typeSpecBuilder;
    }

    private void buildSubSpec(){
        List<FieldSpec> fieldSpecList = this.fieldMap.entrySet().stream().map(entry -> entry.getValue().build()).collect(Collectors.toList());
        List<TypeSpec> innerClassTypeSpecList = this.innerClassMap.entrySet().stream().map(entry -> entry.getValue().build()).collect(Collectors.toList());
        List<MethodSpec> methodSpecList = this.methodMap.entrySet().stream().map(entry -> entry.getValue().build()).collect(Collectors.toList());

        this.typeSpecBuilder.addFields(fieldSpecList);
        this.typeSpecBuilder.addTypes(innerClassTypeSpecList);
        this.typeSpecBuilder.addMethods(methodSpecList);
    }

}
