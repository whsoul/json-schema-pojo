package com.whsoul.pojogen.schema.draft4;

import com.whsoul.pojogen.schema.draft4.sub.definition.Value;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.*;

public class JsonSchemaParserTest {

    @Test
    public void JsonSchemaParse() throws IOException {
        JsonSchemaParser parser = new JsonSchemaParser(getFileStream("test_schema_01.json"));
        Draft04Schema schema = parser.getParsedSchema();

        assertThat(schema, is(notNullValue()));
        assertThat(schema, instanceOf(SchemaObject.class));

        SchemaObject result = (SchemaObject)schema;
        System.out.println(result);

        SchemaObject documentId = (SchemaObject)result.properties.value.get("documentId");

        System.out.println(documentId.type.typeOf(Value.SimpleTypeArrayValue.class));
        System.out.println(documentId.type.getType());
        System.out.println((documentId.type.getValue(Value.SimpleTypeValue.class).simpleTypes));

    }

    @Test
    public void SchemaTest() throws IOException {
        JsonSchemaParser parser = new JsonSchemaParser(getFileStream("test_schema_01.json"));
        SchemaRef ref = new SchemaRef();
        ref.$ref = "#/definitions/Image";
        Draft04Schema result = parser.getRefSchema(ref);

        assertThat(result, is(notNullValue()));
        assertThat(result, instanceOf(SchemaObject.class));
    }


    private InputStream getFileStream(String filePath){
        return getClass().getClassLoader().getResourceAsStream(filePath);
    }
}