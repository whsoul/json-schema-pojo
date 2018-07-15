package com.whsoul.pojogen.schema.draft4;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.whsoul.pojogen.schema.draft4.sub.*;
import com.whsoul.pojogen.schema.draft4.sub.*;
import com.whsoul.pojogen.schema.draft4.sub.Enum;
import com.whsoul.pojogen.schema.draft4.sub.definition.PositiveInteger;
import com.whsoul.pojogen.schema.draft4.sub.definition.PositiveIntegerDefault0;
import com.whsoul.pojogen.schema.draft4.sub.definition.StringArray;
import com.whsoul.pojogen.schema.draft4.sub.polym.AllOf;
import com.whsoul.pojogen.schema.draft4.sub.polym.AnyOf;
import com.whsoul.pojogen.schema.draft4.sub.polym.OneOf;
import lombok.ToString;

@ToString
public class SchemaObject implements Draft04Schema{

    public String id;

    public String $schema;

    public String title;

    public String description;

    @JsonProperty("default")
    public Default $defalut;

    public MultipleOf multipleOf;

    public long maximum;

    public boolean exclusiveMaximum;

    public long minimum;

    public boolean exclusiveMinimum;

    public PositiveInteger maxLength;

    public PositiveIntegerDefault0 minLength;

    public Pattern pattern;

    public AdditionalItems additionalItems;

    public Items items;

    public PositiveInteger maxItems;

    public PositiveIntegerDefault0 minItems;

    public boolean uniqueItems;

    public PositiveInteger maxProperties;

    public PositiveIntegerDefault0 minProperties;

    //min item 1
    public StringArray required;

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

}
