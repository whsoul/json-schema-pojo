package com.whsoul.pojogen.schema.draft4;

import java.util.HashMap;
import java.util.Map;

//LazyHolder
public class DefinitionRepository{
    Map<String, Draft04Schema> repoMap = new HashMap<>();

    private DefinitionRepository(){};

    public static DefinitionRepository get(){
        return LazyHolder.INSTANCE;
    }

    private static class LazyHolder{
        private static final DefinitionRepository INSTANCE = new DefinitionRepository();

    }

}
