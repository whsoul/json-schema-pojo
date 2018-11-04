package com.whsoul.jsch.appender;

import com.squareup.javapoet.ClassName;
import com.whsoul.jsch.TypePojoGenerator;
import com.whsoul.jsch.appender.properties.*;
import com.whsoul.jsch.context.JschContext;
import com.whsoul.jsch.schema.draft4.JsonSchema;
import com.whsoul.jsch.schema.draft4.SchemaObject;
import com.whsoul.jsch.schema.draft4.sub.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PropertiesGenerateExecutor implements SchemaGenerateExecutor<Properties> {
    private static Logger logger = LoggerFactory.getLogger(PropertiesGenerateExecutor.class);

    private JschContext context;
    private List<SchemaObjectPropertyAppender> objectPropertyAppenderList;
    private SchemaRefPropertyAppender refPropertyAppender;

    public static PropertiesGenerateExecutor defaultProcess(JschContext context){
        List<SchemaObjectPropertyAppender> objectPropertyAppenderList = new ArrayList<>();
        objectPropertyAppenderList.add(new DefaultSimpleTypeObjectPropertyAppender());
        objectPropertyAppenderList.add(new DefaultTypeObjectPropertyAppender());
        objectPropertyAppenderList.add(new DefaultOneOfPropertyAppender());
        objectPropertyAppenderList.add(new DefaultArrayObjectPropertyAppender());
        objectPropertyAppenderList.add(new DefaultEmptyObjectPropertyAppender());
        objectPropertyAppenderList.add(new DefaultEnumPropertyAppender());

        return new PropertiesGenerateExecutor(context, objectPropertyAppenderList);
    }

    public PropertiesGenerateExecutor(JschContext context
                            , List<SchemaObjectPropertyAppender> objectPropertyAppenderList){

        this.context = context;
        this.objectPropertyAppenderList = objectPropertyAppenderList;
        this.refPropertyAppender = new DefaultRefPropertyAppender();
    }

    @Override
    public void execute(TypePojoGenerator pojoGenerator, ClassName baseClassName, SchemaObject schema) {
        if(schema.properties == null) return;

        for(Map.Entry<String, JsonSchema> entry : schema.properties.map.entrySet()){
            if(entry.getValue().isSchemaObject()) {
                SchemaObject schemaObject = entry.getValue().asSchemaObject();
                SchemaObjectPropertyAppender targetAppender = objectPropertyAppenderList.stream()
                        .filter(appender -> schemaObject.schemaType().equals(appender.availableType()))
                        .findFirst()
                        .orElse(null);

                if(targetAppender == null){
                    logger.warn("Not Supported Property Process for {}, please defined & set CustomProcess or remove it from schema", schemaObject.schemaType());
                }else{
                    targetAppender.append(pojoGenerator, entry.getKey(), schemaObject);
                }
            }else if(entry.getValue().isRef()){
                refPropertyAppender.append(pojoGenerator, entry.getKey(), entry.getValue().asSchemaRef());
            }
        }
    }

}
