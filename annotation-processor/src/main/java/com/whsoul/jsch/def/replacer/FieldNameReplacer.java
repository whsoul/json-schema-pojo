package com.whsoul.jsch.def.replacer;

import javax.lang.model.SourceVersion;

public class FieldNameReplacer implements StringReplacer {

    //TODO SourceVersion.isIdentifier 를 사용해서 전부 호환하도록 처리
    @Override
    public String replace(String org) {
        String newStr = org;
        if(!SourceVersion.isIdentifier(org)) {
            newStr = org.replaceAll("\\@", "\\$")
                    .replaceAll("\\#", "\\$")
                    .replaceAll("\\%", "\\$")
                    .replaceAll("\\&", "\\$");
        }
        if(SourceVersion.isKeyword(org)){
            newStr = "$" + org;
        }

        return newStr;
    }
}
