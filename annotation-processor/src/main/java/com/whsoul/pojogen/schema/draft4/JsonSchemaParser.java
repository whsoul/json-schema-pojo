package com.whsoul.pojogen.schema.draft4;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.whsoul.pojogen.schema.draft4.jackson.JsonSchemaObjectMapper;
import com.whsoul.pojogen.schema.draft4.sub.ref.InternalRefRouter;
import com.whsoul.pojogen.schema.draft4.sub.ref.RefRouter;

import java.io.IOException;
import java.io.InputStream;

public class JsonSchemaParser {
    private ObjectMapper om = JsonSchemaObjectMapper.get();
    private Draft04Schema contextSchema;

    public JsonSchemaParser(InputStream inputStream) throws IOException {
        contextSchema = om.readValue(inputStream, Draft04Schema.class);
    }
    public JsonSchemaParser(String jsonSchemaStr) throws IOException {
        contextSchema = om.readValue(jsonSchemaStr, Draft04Schema.class);
    }

    public Draft04Schema getParsedSchema(){
        return contextSchema;
    }

    public Draft04Schema getRefSchema(SchemaRef refSchema){
        RefRouter refRouter = new InternalRefRouter();
        return refRouter.refRoute(this.contextSchema, refSchema.$ref);
    }




}
