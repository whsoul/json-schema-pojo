package com.whsoul.jsch.schema.draft4;

import com.whsoul.jsch.schema.draft4.sub.definition.SimpleTypes;
import com.whsoul.jsch.schema.draft4.sub.definition.Value;
import com.whsoul.jsch.util.ContextUtil;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class JsonSchemaParserTest {

    @Test
    public void SampleSchema01() throws IOException {
        JsonSchemaParser parser = new JsonSchemaParser(getFileStream("sample_schema_01.json"));
        JsonSchema schema = parser.getParsedSchema();

        assertThat(schema, is(notNullValue()));
        assertThat(schema, instanceOf(SchemaObject.class));

        SchemaObject result = (SchemaObject)schema;
        System.out.println(result);

        assertThat(result.required.fieldNames.items, hasItems("name", "address", "age"));
        assertThat(result.properties, is(notNullValue()));

        assertThat(result.properties.get("name"), instanceOf(SchemaObject.class));
        assertThat(((SchemaObject)result.properties.get("name")).type.getAnyOfValue(Value.SimpleTypeValue.class).getValue().type , is(SimpleTypes.TYPE.$string));

        assertThat(result.properties.get("address"), instanceOf(SchemaObject.class));
        assertThat(((SchemaObject)result.properties.get("address")).type.getAnyOfValue(Value.SimpleTypeValue.class).getValue().type , is(SimpleTypes.TYPE.$string));

        assertThat(result.properties.get("age"), instanceOf(SchemaRef.class));
        assertThat(((SchemaRef)result.properties.get("age")).$ref, is("#/definitions/intMinMax"));

        assertThat(result.properties.get("father"), instanceOf(SchemaRef.class));
        assertThat(((SchemaRef)result.properties.get("father")).$ref , is("#"));

        JsonSchema fatherSchema = parser.getRefSchema((SchemaRef)result.properties.get("father"));
        assertThat(fatherSchema , instanceOf(SchemaObject.class));
        assertThat(((SchemaObject)fatherSchema).properties.get("name") , instanceOf(SchemaObject.class));


        assertThat(result.properties.get("university"), instanceOf(SchemaRef.class));
        assertThat(((SchemaRef)result.properties.get("university")).$ref , is("#/definitions/University"));

        JsonSchema universitySchema = parser.getRefSchema((SchemaRef)result.properties.get("university"));
        assertThat(universitySchema , instanceOf(SchemaObject.class));
        SchemaObject universitySchemaObject = ((SchemaObject)universitySchema);
        SchemaObject locationSchemaObject = (SchemaObject)universitySchemaObject.properties.get("Location");

        SchemaObject positionSchemaObject = (SchemaObject)locationSchemaObject.properties.get("position");
        assertThat(((SchemaObject)positionSchemaObject.properties.get("latitude")).type.getAnyOfValue(Value.SimpleTypeValue.class).getValue().type , is(SimpleTypes.TYPE.$string));
        assertThat(((SchemaObject)positionSchemaObject.properties.get("longitude")).type.getAnyOfValue(Value.SimpleTypeValue.class).getValue().type , is(SimpleTypes.TYPE.$string));

        assertThat(positionSchemaObject.title , is("pos"));
        assertThat(positionSchemaObject.required.fieldNames.items , hasItems("latitude", "longitude"));
    }

    @Test
    public void SchemaRefTest1() throws IOException {
        JsonSchemaParser parser = new JsonSchemaParser(getFileStream("test_schema_01.json"));
        SchemaRef ref = new SchemaRef();
        ref.$ref = "#/definitions/Image";
        JsonSchema result = parser.getRefSchema(ref);

        assertThat(result, is(notNullValue()));
        assertThat(result, instanceOf(SchemaObject.class));
    }

    @Test
    public void schemaRefNameTest(){
        SchemaRef ref = new SchemaRef();
        ref.$ref = "#/definitions/Image";
        assertThat("Image", is(ContextUtil.refClassNamePart(ref)));
    }


    private InputStream getFileStream(String filePath){
        return getClass().getClassLoader().getResourceAsStream(filePath);
    }
}