package com.whsoul.jsch.schema.draft4.sub.ref;

import com.whsoul.jsch.schema.draft4.JsonSchema;

public interface RefRouter{
    JsonSchema refRoute(JsonSchema baseSchema, String refPath);
}