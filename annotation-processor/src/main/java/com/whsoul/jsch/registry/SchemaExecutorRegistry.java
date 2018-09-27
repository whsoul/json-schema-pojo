package com.whsoul.jsch.registry;

import com.whsoul.jsch.appender.DefinitionGenerateExecutor;
import com.whsoul.jsch.appender.PropertiesGenerateExecutor;
import com.whsoul.jsch.appender.RequiredGenerateExecutor;
import com.whsoul.jsch.appender.SchemaGenerateExecutor;
import com.whsoul.jsch.context.JschContext;

import java.util.ArrayList;
import java.util.List;

public class SchemaExecutorRegistry {
    private List<SchemaGenerateExecutor> schemaProcessList = new ArrayList<>();

    private SchemaExecutorRegistry(){
    }

    public static SchemaExecutorRegistry defaultSchemaProcessRegistry(JschContext context){
        SchemaExecutorRegistry typeRegistry = new SchemaExecutorRegistry();
        typeRegistry.schemaProcessList = new ArrayList<>();
        typeRegistry.schemaProcessList.add(new DefinitionGenerateExecutor(context));
        typeRegistry.schemaProcessList.add(PropertiesGenerateExecutor.defaultProcess(context));
        typeRegistry.schemaProcessList.add(new RequiredGenerateExecutor(context));

        return typeRegistry;
    }

    public List<SchemaGenerateExecutor> getSchemaProcessList() {
        return schemaProcessList;
    }

    public void add(SchemaGenerateExecutor process){
        this.schemaProcessList.add(process);
    }

    public SchemaGenerateExecutor get(int index){
        return schemaProcessList.get(index);
    }

}
