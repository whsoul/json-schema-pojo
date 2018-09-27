package com.whsoul.jsch.util;

import com.whsoul.jsch.schema.draft4.SchemaRef;

public class ContextUtil {

    public static String refClassNamePart(SchemaRef refSchema){
        return refSchema.$ref.substring(refSchema.$ref.lastIndexOf("/")+1);
    }

}
