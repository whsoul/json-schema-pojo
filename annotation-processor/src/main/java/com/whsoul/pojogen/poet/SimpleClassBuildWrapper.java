package com.whsoul.pojogen.poet;

import com.squareup.javapoet.*;

import javax.lang.model.element.Modifier;
import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SimpleClassBuildWrapper {

    private TypeSpec.Builder builder;

    private Config config;

    public static class ParameterizedClass implements JavaClass{
        ParameterizedClass(Class<?> baseClass, Class<?>... parmeterizeClasses){
            this.baseClass = baseClass;
            this.parmeterizeClasses = parmeterizeClasses;
        }

        public Class<?> baseClass;
        public Class<?>[] parmeterizeClasses;

        @Override
        public TypeName toTypeName() {
            ClassName baseClassName = ClassName.get(baseClass.getPackage().getName(), baseClass.getSimpleName());
            return ParameterizedTypeName.get(baseClassName, Arrays.stream(parmeterizeClasses).map(c -> ClassName.get(c.getPackage().getName(), c.getSimpleName())).collect(Collectors.toList()).toArray(new ClassName[0]));
        }
    }

    public static class PackageClass implements JavaClass{
        PackageClass(Class<?> baseClass){
            this.baseClass = baseClass;
        }

        public Class<?> baseClass;

        @Override
        public TypeName toTypeName() {
            return ClassName.get(baseClass.getPackage().getName(), baseClass.getSimpleName());
        }
    }

    public static class PrimitiveClass implements JavaClass{
        PrimitiveClass(TypeName typeName){
            this.typeName = typeName;
        }

        public TypeName typeName;

        @Override
        public TypeName toTypeName() {
            return this.typeName;
        }
    }


    public interface JavaClass{
        public TypeName toTypeName();
    }

    public static class Config{
        Config(Map<String, JavaClass> nameClassMap){
            this.nameClassMap = nameClassMap;
        }
        public Map<String, JavaClass> nameClassMap;
    }


    public void initClass(String name, Config config){
        if(builder != null){
            //todo
        }
        this.builder = TypeSpec.classBuilder(name);
        this.config = config;
    }

    public void addPublicField(String mappingNames, String name){
        String[] seperated = mappingNames.split(",");
        TypeName typeName;
        if(seperated.length == 1){
            typeName = config.nameClassMap.get(mappingNames).toTypeName();
        }else {
            ClassName baseTypeName = (ClassName)config.nameClassMap.get(seperated[0]).toTypeName();
            TypeName[] t = IntStream.range(1, seperated.length).mapToObj(i -> config.nameClassMap.get(seperated[i]).toTypeName()).collect(Collectors.toList()).toArray(new ClassName[0]);
            typeName = ParameterizedTypeName.get(baseTypeName, t);
        }

        this.builder.addModifiers(Modifier.PUBLIC)
                .addField(typeName, name, Modifier.PUBLIC);
    }

    public void addPublicField(Class<?> type, String name){
        this.builder.addModifiers(Modifier.PUBLIC)
                .addField(type, name, Modifier.PUBLIC);
    }

    public void addPublicField(String pkgName, String className, String name){
        ClassName returnClass = ClassName.get(pkgName, className);
        this.builder.addModifiers(Modifier.PUBLIC)
                .addField(returnClass, name, Modifier.PUBLIC);
    }

    public void addPublicFieldWithAnnotation(Class<?> type, String name, Annotation anno){

        FieldSpec fieldSpec = FieldSpec.builder(type, name)
                                    .addModifiers(Modifier.PUBLIC)
                                    .addAnnotation(AnnotationSpec.builder(anno.getClass()).build())
                                    .build();
        this.builder.addField(fieldSpec);
    }

    public void addPublicFieldWithAnnotation(String pkgName, String className, String name, Class<? extends Annotation> anno){
        ClassName returnClass = ClassName.get(pkgName, className);
        FieldSpec fieldSpec = FieldSpec.builder(returnClass, name)
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(AnnotationSpec.builder(anno).build())
                .build();
        this.builder.addField(fieldSpec);
    }

    public void addSetter(Class<?> type, String name){
        ParameterSpec field = ParameterSpec.builder(type, name)
                .build();
        MethodSpec method = MethodSpec.methodBuilder("set" + name.substring(0, 1).toUpperCase() + name.substring(1))
                .addParameter(field)
                .addStatement("this.$N = $N", name, name)
                .build();
        this.builder.addMethod(method);
    }

    public void addGetter(Class<?> type, String name){
        MethodSpec method = MethodSpec.methodBuilder("get" + name.substring(0, 1).toUpperCase() + name.substring(1))
                .addStatement("return this.$N", name)
                .returns(type)
                .build();
        this.builder.addMethod(method);
    }

    public TypeSpec build(){
        return this.builder.build();
    }

}
