package com.whsoul.jsch;

import com.test.A;
import com.test.BInterface;
import com.test.pojo01.GeneratedPojo01;
import com.test.pojo02.GeneratedPojo02;
import com.test.pojo03.GeneratedPojo03;
import com.test.sample1.Student;
import com.test.sample1.definitions.University;
import com.whsoul.jsch.anno.JsonSchemaPojo;
import org.junit.Test;

import java.util.ArrayList;

public class TestClassTest {

    @JsonSchemaPojo( packageName = "com.test.pojo01", className = "GeneratedPojo01", schemaUrl = "pojo01-schema.json")
    public String pojo01;

    @JsonSchemaPojo( packageName = "com.test.pojo02", className = "GeneratedPojo02", schemaUrl = "pojo02-schema.json")
    public String pojo02;

    @JsonSchemaPojo( packageName = "com.test.pojo03", className = "GeneratedPojo03", schemaUrl = "pojo03-schema.json")
    public String pojo03;

    @Test
    public void test1_pojo01_schema(){
        com.test.pojo01.GeneratedPojo01 pojo01 = new GeneratedPojo01();
        //TODO Detail field check
    }

    @Test
    public void test2_pojo01_schema(){
        com.test.pojo02.GeneratedPojo02 pojo02 = new GeneratedPojo02();
        //TODO Detail field check
    }

    @Test
    public void test3_pojo01_schema(){
        com.test.pojo03.GeneratedPojo03 pojo03 = new GeneratedPojo03();
        //TODO Detail field check
    }

}
