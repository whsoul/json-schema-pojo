package com.whsoul.jsch;

import com.test.A;
import com.test.BInterface;
import com.test.sample1.Student;
import com.test.sample1.definitions.University;
import org.junit.Test;

public class BaseClassTest {

    @Test
    public void test1_JsonSchema_POJO(){
        com.test.sample0.JsonSchema jsonSchema = new com.test.sample0.JsonSchema();
    }


    @Test
    public void test2_Student_POJO(){
        com.test.sample1.Student student = new com.test.sample1.Student();
        student.name = "test1";
        student.address = "seoul Korea";
        student.age = 18;
        student.father = new Student();
        student.mother = new Student();
        student.nvalue = null;
        student.university = new University();
        A a = student.university;
        BInterface b = student.university;
        student.university.Location = new University.Location();
    }


    @Test
    public void test3_Pojo01_POJO() {
        com.test.pojo4.Datas pojo01 = new com.test.pojo4.Datas();
    }
}
