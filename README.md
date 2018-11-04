[![Englsh](https://img.shields.io/badge/language-English-orange.svg)](README.md) [![Korean](https://img.shields.io/badge/language-Korean-blue.svg)](README_kr.md)

# JAVA POJO Class Generate with jsonSchemaUrl Annotation

json-schema-pojo is a library that automatically generate JAVA POJO class file on compile time. (like lombok library)

Just add maven repository dependency,

~~~
    <dependency>
        <groupId>com.whsoul</groupId>
        <artifactId>json-schema-pojo</artifactId>
        <version>X.Y.Z</version>
    </dependency>
~~~

and make config class With Annotations,

then Java compiler make POJOs along annotation configs.


## Base Use

~~~ java
public class ConfigClass {

    @JsonSchemaPojo(packageName = "com.test.pojo"
                    , className = "Datas"
                    , schemaUrl = "https://whsoul.github.io/json-schema-pojo/test-schema01.json")
    public String fromExternalResource;

    @JsonSchemaPojo(packageName = "com.test.pojo2"
                    , className = "Sample"
                    , schemaUrl = "sample_schema_01.json")
    public String fromOwnResource;

~~~

there are 2 kind of options with target schema
1. internet resource reference ( offline mode, not available )
 - https://whsoul.github.io/json-schema-pojo/test-schema01.json
 
2. my own(or downloaded) schema ( under `src/main/resources` )
 - sample_schema_01.json


And also you can inject custom configurations, such as below


## Base Config Enable/Disable

~~~
    @JsonSchemaPojo(packageName = "com.test.sample1", className = "Student"
                    , schemaUrl = "sample_schema_01.json"
                    , baseConfig = {
                        @JsonSchemaPojo.BaseConfig(
                                feature = JSONSCHEMA_POJO_FEATURE.STRING_ENUM_AS_ENUM
                                ,enable = true
                        )
                    })
    public String enableDisableBaseConfig;
~~~

Control library generate default actions with config enable/disable


## Customize Type Mapping

~~~
    @JsonSchemaPojo(packageName = "com.test.sample1", className = "Student"
                    , schemaUrl = "sample_schema_01.json"
                    , typeMappingConfig = {
                        @JsonSchemaPojo.TypeMappingConfig(
                                typeName = "string"
                                , typeJavaClass = MyString.class
                        ),
                        @JsonSchemaPojo.TypeMappingConfig(
                                typeName = "integer"
                                , typeJavaClass = int.class
                        ),
                        @JsonSchemaPojo.TypeMappingConfig(
                                typeName = "customFloat"
                                , typeJavaClass = float.class
                        )
                    }
    )
    public String sampleClass1;
~~~

Standard json schema specification support only simpleTypes below

| type | default mapping |
|--------|--------------|
| string | String.class |
| boolean | boolean.class |
| long | long.class |
| integer | Integer.class |
| number | Double.class |
| array | List.class |
| object | Map.class |
| null | Void.class |

but customType supported. 
override default mapping also


## Customize Validation Annotation Config

~~~
    @JsonSchemaPojo(packageName = "com.test.sample1", className = "Student"
                    , schemaUrl = "sample_schema_01.json"
                    , annotationConfig = {
                        @JsonSchemaPojo.AnnotationConfig(
                                type = ANNOTATION_TYPE.FIELD_REQUIRED
                                , annotationClass = MyNotNull.class
                        )
                    }
    )
    public String sampleClass1;
~~~


## Add Interface, Superclass with
~~~
    @JsonSchemaPojo(packageName = "com.test.sample1", className = "Student"
                    , schemaUrl = "sample_schema_01.json"
                    ,superclassConfig = {
                        @JsonSchemaPojo.SuperConfig(
                                superclass = A.class
                                , targetClassNames = "com.test.sample1.Student" +
                                                     ",com.test.sample1.definitions.University" +
                                                     ",com.test.sample1.definitions.University.Location"
                        )
                    }
                    ,superinterfaceConfig = {
                        @JsonSchemaPojo.SuperConfig(
                                superclass = BInterface.class
                                , targetClassNames = "com.test.sample1.Student" +
                                ",com.test.sample1.definitions.University" +
                                ", com.test.sample1.definitions.University.Location "
                        )
                    }
    )
    public String sampleClass1;
~~~

if you want to add superclass or interface to generated classes, you can use `superclassConfig`, `superinterfaceConfig`


## Usage Example

### API Specifications exchange for caller(client with java)

Java base (Client - Server), (Server - Server) easily exchanges Request, Response data format.

#### API Server Application

1. Develop Java API Application with json Request, Response 
2. Generate jsonSchema file (Request Model, Response Model)
3. Create automatically generate with 3rd party POJO to Json schema library below Reference(#1)
( or Create jsonSchema Structure by your self)
4. Deploy the static files with your API Server application, or other static CDN (example] http://your.application.domain/my-api001-REQUEST-schema.json, ... my-api001-RESPONSE-schema.json  )
5. Let client know your Server API url with and the schema file download URL 

#### Java Client

1. Add this library at pom or gradle

2. Add @JsonSchemaPojo in your code, and schema download url 

~~~
    @JsonSchemaPojo(packageName = "com.test.pojo"
                    , className = "Api001RequestPojo"
                    , schemaUrl = "http://your.application.domain/my-api001-REQUEST-schema.json")
    public String yourApi001Request;
~~~



## References


### Jackson Json Schema Module (#1) 

#### JSON Schema Draft v3
https://github.com/FasterXML/jackson-module-jsonSchema

#### JSON Schmea Draft v4
https://github.com/mbknor/mbknor-jackson-jsonSchema