package com.whsoul.pojogen.schema.draft4.sub.ref;

import com.whsoul.pojogen.schema.draft4.Draft04Schema;
import com.whsoul.pojogen.schema.draft4.SchemaObject;
import com.whsoul.pojogen.schema.draft4.SchemaRef;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InternalRefRouter implements RefRouter {

    //TODO null 처리
    public Draft04Schema refRoute(Draft04Schema baseSchema, String refPath){
        if(refPath == null || "".equals(refPath)){
            return null;
        }
        if("#".equals(refPath)){
            return baseSchema;
        }

        Pattern pattern = Pattern.compile("#/definitions/([^/]*)");
        Matcher m = pattern.matcher(refPath);
        if(m.find()){
            if(baseSchema instanceof SchemaObject){
                return ((SchemaObject)baseSchema).definitions.value.get(m.group(1));
            }else if(baseSchema instanceof SchemaRef){
                return refRoute(baseSchema, ((SchemaRef)baseSchema).$ref);
            }
        }

        return null;
    }
}