package com.whsoul.jsch.schema.draft4.sub;

import com.fasterxml.jackson.annotation.JsonCreator;

public class Pattern{

    java.util.regex.Pattern pattern;

    @JsonCreator
    public Pattern(String patternStr){
        this.pattern = java.util.regex.Pattern.compile(patternStr);

    }
}
