package com.whsoul.pojogen.poet;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CommonTest {

    @Test
    public void test2() throws IOException {

        Map<String, String> map = new HashMap<>();
        //map.put("@type", "x");
        map.put("y", "wow");
        ObjectMapper om = new ObjectMapper();
        String test = om.writeValueAsString(map);

        Animal data = om.readValue(test, Animal.class);
        System.out.println(data);
        System.out.println(om.writeValueAsString(data));
    }

    @JsonTypeInfo(
            use = JsonTypeInfo.Id.NAME,
            include = JsonTypeInfo.As.WRAPPER_OBJECT)
    @JsonSubTypes({
            @JsonSubTypes.Type(value = Dog.class, name = "x"),
            @JsonSubTypes.Type(value = Cat.class, name = "y")
    })
    public static class Animal{
        public String a;
    }

    public static class Dog extends Animal{
        public String x;

        public Dog(String wow){
            System.out.println("dog");

        }
    }

    public static class Cat extends Animal{
        public String y;
        public String v;
        public Cat(String wow){
            System.out.println("cat");
        }
    }
}
