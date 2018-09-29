package com.whsoul.jsch.schema.draft4.sub.ref;

import com.whsoul.jsch.schema.draft4.JsonSchema;
import com.whsoul.jsch.schema.draft4.SchemaObject;
import com.whsoul.jsch.schema.draft4.SchemaRef;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InternalRefRouter implements RefRouter {

    //TODO null 처리
    public JsonSchema refRoute(JsonSchema baseSchema, String refPath){
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
                if(((SchemaObject)baseSchema).definitions == null){
                    return null;
                }else {
                    return ((SchemaObject) baseSchema).definitions.get(m.group(1));
                }
            }else if(baseSchema instanceof SchemaRef){
                return refRoute(baseSchema, ((SchemaRef)baseSchema).$ref);
            }
        }

        return null;
    }
}