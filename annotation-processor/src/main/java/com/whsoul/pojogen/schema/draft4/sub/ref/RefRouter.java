package com.whsoul.pojogen.schema.draft4.sub.ref;

import com.whsoul.pojogen.schema.draft4.Draft04Schema;

public interface RefRouter{
    Draft04Schema refRoute(Draft04Schema baseSchema, String refPath);
}