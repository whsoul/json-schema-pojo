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
                                feature = JSONSCHEMA_POJO_FEATURE.AUTO_POLYMOPHIC_ONEOF
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


    public void setA(String x){
        System.out.println(1234);
    }

}
