import com.test.A;
import com.test.BInterface;
import com.whsoul.jsch.anno.JsonSchemaPojo;
import com.whsoul.jsch.common.ANNOTATION_TYPE;
import com.whsoul.jsch.common.JSONSCHEMA_POJO_FEATURE;

import javax.validation.constraints.NotEmpty;

public class TestPojo {

    @JsonSchemaPojo(packageName = "com.test.sample0", className = "JsonSchema"
            , schemaUrl = "http://json-schema.org/draft-04/schema")
    public String webSchemaClass;

    @JsonSchemaPojo(packageName = "com.test.sample1", className = "Student"
                    , schemaUrl = "sample_schema_01.json"
                    , baseConfig = {
                        @JsonSchemaPojo.BaseConfig(
                                feature = JSONSCHEMA_POJO_FEATURE.STRING_ENUM_AS_ENUM
                                ,enable = true
                        )
                    }
                    , typeMappingConfig = {
                            @JsonSchemaPojo.TypeMappingConfig(
                                    typeName = "string"
                                    , typeJavaClass = String.class
                            ),
                            @JsonSchemaPojo.TypeMappingConfig(
                                    typeName = "int"
                                    , typeJavaClass = int.class
                            ),
                            @JsonSchemaPojo.TypeMappingConfig(
                                    typeName = "xx"
                                    , typeJavaClass = String.class
                            )
                    }
                    , annotationConfig = {
                        @JsonSchemaPojo.AnnotationConfig(
                                type = ANNOTATION_TYPE.FIELD_REQUIRED
                                , annotationClass = NotEmpty.class
                                , valueNames = "test"
                        )
                    }
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

    @JsonSchemaPojo(packageName = "com.test.sample2", className = "DocumentWithMeta"
                    ,schemaUrl = "document_with_meta.json")
    public String sampleClass2;

    @JsonSchemaPojo(packageName = "com.test.pojo4", className = "Datas", schemaUrl = "https://whsoul.github.io/json-schema-pojo/test-schema01.json")
    public String Schema4;


}
