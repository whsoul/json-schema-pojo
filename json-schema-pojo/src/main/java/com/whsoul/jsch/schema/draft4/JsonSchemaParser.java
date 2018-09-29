package com.whsoul.jsch.schema.draft4;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.whsoul.jsch.exception.JschLibaryException;
import com.whsoul.jsch.schema.draft4.jackson.JsonSchemaObjectMapper;
import com.whsoul.jsch.schema.draft4.sub.ref.InternalRefRouter;
import com.whsoul.jsch.schema.draft4.sub.ref.RefRouter;

import java.io.IOException;
import java.io.InputStream;

public class JsonSchemaParser {
    private ObjectMapper om = JsonSchemaObjectMapper.get();
    private JsonSchema contextSchema;
    private JsonSchemaParser parentParser;

    public JsonSchemaParser(InputStream inputStream){
        try {
            contextSchema = om.readValue(inputStream, JsonSchema.class);
        } catch (IOException e) {
            throw new JschLibaryException("JsonSchemaParser constructor Fail " + e.getMessage());
        }
    }
    public JsonSchemaParser(String jsonSchemaStr){
        try {
            contextSchema = om.readValue(jsonSchemaStr, JsonSchema.class);
        } catch (IOException e) {
            throw new JschLibaryException("JsonSchemaParser constructor Fail " + e.getMessage());
        }
    }
    public JsonSchemaParser(JsonSchema schema){
        contextSchema = schema;
    }

    public void setParentParser(JsonSchemaParser parentParser) {
        this.parentParser = parentParser;
    }

    public JsonSchema getParsedSchema(){
        return contextSchema;
    }

    public JsonSchema getRefSchema(SchemaRef refSchema){
        return getRefSchema(this.contextSchema, refSchema);
    }

    public JsonSchema searchRefSchema(SchemaRef refSchema){
        JsonSchema targetSchema = this.contextSchema;
        JsonSchema result;
        do {
            result = getRefSchema(targetSchema, refSchema);
        }while(result == null && (targetSchema = this.parentParser.contextSchema) != null);

        return result;
    }

    private JsonSchema getRefSchema(JsonSchema targetSchema, SchemaRef refSchema){
        RefRouter refRouter = new InternalRefRouter();
        return refRouter.refRoute(targetSchema, refSchema.$ref);
    }

}
