package com.whsoul.jsch;

import com.test.A;
import com.test.BInterface;
import com.test.pojo01.GeneratedPojo01;
import com.test.pojo02.GeneratedPojo02;
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

}
