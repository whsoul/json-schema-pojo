package com.whsoul.jsch.schema.draft4;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.javapoet.ClassName;
import com.whsoul.jsch.conf.Config;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasItems;

public class UtilTest {
    Config<ClassName, String[]> polymorphismConfig = new Config<ClassName, String[]>() {
        @Override
        public Map<ClassName, String[]> getConfigMap() {
            return new HashMap<ClassName, String[]>(){{
                put(ClassName.get(ObjectMapper.class), Arrays.asList("a", "b", "c").toArray(new String[0]));
                put(ClassName.get(JsonNode.class), Arrays.asList("b", "c").toArray(new String[0]));
                put(ClassName.get(JsonProperty.class), Arrays.asList("a", "c").toArray(new String[0]));
            }};
        }
    };

    @Test
    public void containAggregateTest(){
        String fieldName = "a";

        List<ClassName> dataList = polymorphismConfig.getConfigMap().entrySet().stream()
                .filter(entry -> Arrays.binarySearch(entry.getValue(), fieldName) > -1)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        assertThat(dataList, hasItems(ClassName.get(ObjectMapper.class), ClassName.get(JsonProperty.class)));
    }

}
