package com.whsoul.jsch.schema.draft4;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.whsoul.jsch.schema.draft4.jackson.JsonSchemaObjectMapper;
import com.whsoul.jsch.schema.draft4.sub.*;
import com.whsoul.jsch.schema.draft4.sub.Enum;
import com.whsoul.jsch.schema.draft4.sub.definition.*;
import com.whsoul.jsch.schema.draft4.sub.polym.AllOf;
import com.whsoul.jsch.schema.draft4.sub.polym.AnyOf;
import com.whsoul.jsch.schema.draft4.sub.polym.OneOf;
import lombok.ToString;

@ToString
public class SchemaObject implements JsonSchema {
    static ObjectMapper om = JsonSchemaObjectMapper.get();
    //TODO 향후 SchemaObject와 Ref를 통합관리필요(draft상위 버젼에는 $ref가 통합됨)

    public enum TYPE{
        simpleTypeObject
        ,typeObject
        ,enumObject
        ,arrayObject
        ,anyOfObject
        ,oneOfObject
        ,allOfObject
        ,emptyObject
    }

    public boolean isSchemaObject(){ return true; }
    public boolean isRef(){ return false; }

    public TYPE schemaType(){
        if($enum != null) {
            return TYPE.enumObject;
        }else if(anyOf != null) {
            return TYPE.anyOfObject;
        }else if(allOf != null) {
            return TYPE.allOfObject;
        }else if(oneOf != null){
            return TYPE.oneOfObject;
        }else if(type != null && type.isSchemaArrayType()){
            return TYPE.arrayObject;
        }else if(type != null && type.typeOf(Value.SimpleTypeValue.class) && this.properties == null){
            return TYPE.simpleTypeObject;
        }else if(type != null && type.typeOf(Value.SimpleTypeValue.class) && this.properties != null){
            //simpleType has own property
            return TYPE.typeObject;
        }else{
            return TYPE.emptyObject;
        }
    };

    public String id;

    public String $schema;

    public String title;

    public String description;

    @JsonProperty("default")
    public Default $defalut;

    public MultipleOf multipleOf;

    public Long maximum;

    public Boolean exclusiveMaximum;

    public Long minimum;

    public Boolean exclusiveMinimum;

    public PositiveInteger maxLength;

    public PositiveIntegerDefault0 minLength;

    public Pattern pattern;

    public AdditionalItems additionalItems;

    public Items items;

    public PositiveInteger maxItems;

    public PositiveIntegerDefault0 minItems;

    public Boolean uniqueItems;

    public PositiveInteger maxProperties;

    public PositiveIntegerDefault0 minProperties;

    //min item 1
    public Required required;

    public AdditionalProperties additionalProperties; //true, or ref

    public Definitions definitions; //pro

    public Properties properties;

    public PatternProperties patternProperties;

    public Dependencies dependencies; // Property or stringArray

    @JsonProperty("enum")
    public Enum $enum; // string or property or all

    public Type type; // SimpleType or SimpleType array

    public String format;

    public AllOf allOf;

    public AnyOf anyOf;

    public OneOf oneOf;

    public Not not;


    @Override
    public String toString(){
        try {
            return om.writeValueAsString(items);
        } catch (JsonProcessingException e) {
            return items.toString();
        }
    }

}
