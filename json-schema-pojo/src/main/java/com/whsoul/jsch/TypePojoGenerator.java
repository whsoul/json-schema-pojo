package com.whsoul.jsch;

import com.squareup.javapoet.*;
import com.whsoul.jsch.appender.SchemaGenerateExecutor;
import com.whsoul.jsch.context.JschContext;
import com.whsoul.jsch.poet.SimpleClassBuildWrapper;
import com.whsoul.jsch.schema.draft4.JsonSchemaParser;
import com.whsoul.jsch.schema.draft4.SchemaObject;

import javax.lang.model.element.Modifier;
import java.util.List;

public class TypePojoGenerator {
    private SimpleClassBuildWrapper buildWrapper;
    private JsonSchemaParser parser;
    private ClassName className;
    private JschContext context;

    private TypePojoGenerator(SimpleClassBuildWrapper buildWrapper
                              ,JsonSchemaParser parser
                              ,ClassName className
                              ,JschContext context){
        this.buildWrapper = buildWrapper;
        this.parser = parser;
        this.className = className;
        this.context = context;
    }

    public void startProcess(){
        SchemaObject schema = (SchemaObject)this.parser.getParsedSchema();
        for(SchemaGenerateExecutor executor : this.context.getSchemaProcessRegistry().getSchemaProcessList()) {
            executor.execute(this, className, schema);
        }
    }

    public String generate(){
        JavaFile javaFile = JavaFile.builder(this.className.packageName(), this.toTypeSpecBuilder().build())
                .build();
        return javaFile.toString();
    }

    public TypeSpec.Builder toTypeSpecBuilder(Modifier... modifiers){

        this.buildWrapper.addModifilers(modifiers);

        //1. add config superinterface
        List<ClassName> polymorphicSuperinterfaceList = this.context.getPendingTypeRegistry().getSuperinterfaceType(className);
        for(ClassName superInterface : polymorphicSuperinterfaceList) {
            this.buildWrapper.addSuperinterface(superInterface);
        }

        //2. add config superclass
        List<TypeName> superClassList = this.context.getConfigRegistry().getSuperClassListBySubClassName(this.className);
        for(TypeName superClassName : superClassList) {
            this.buildWrapper.addSuperclass(superClassName);
        }

        //3. add audo polymorphic superinterface
        List<TypeName> superInterfaceList = this.context.getConfigRegistry().getSuperInterfaceBySubClassName(this.className);
        for(TypeName superInterfaceName : superInterfaceList) {
            this.buildWrapper.addSuperinterface(superInterfaceName);
        }

        return this.buildWrapper.builder();
    }


    public JschContext getContext(){
        return this.context;
    }

    public JsonSchemaParser getParser() {
        return parser;
    }

    public SimpleClassBuildWrapper getBuildWrapper() {
        return buildWrapper;
    }

    public ClassName getClassName() {
        return className;
    }


    public static TypePojoGenerator.Builder builder(){
        return new Builder();
    }

    public static class Builder{
        SimpleClassBuildWrapper buildWrapper;
        JsonSchemaParser parser;
        ClassName className;
        JschContext context;

        public Builder buildWrapper(SimpleClassBuildWrapper buildWrapper){
            this.buildWrapper = buildWrapper;
            return this;
        }

        public Builder parser(JsonSchemaParser parser){
            this.parser = parser;
            return this;
        }

        public Builder className(ClassName className){
            this.className = className;
            return this;
        }

        public Builder context(JschContext context){
            this.context = context;
            return this;
        }

        public TypePojoGenerator build(){
            return new TypePojoGenerator(buildWrapper, parser, className, context);
        }

    }
}
