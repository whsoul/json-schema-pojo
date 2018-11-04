package com.whsoul.jsch.appender.properties;

import com.whsoul.jsch.TypePojoGenerator;
import com.whsoul.jsch.appender.NormalizedFieldAppendUtil;
import com.whsoul.jsch.schema.draft4.SchemaObject;
import com.whsoul.jsch.schema.draft4.sub.definition.SimpleTypes;
import com.whsoul.jsch.schema.draft4.sub.definition.Value;

public class DefaultEmptyObjectPropertyAppender implements SchemaObjectPropertyAppender {

    @Override
    public SchemaObject.TYPE availableType() {
        return SchemaObject.TYPE.emptyObject;
    }

    @Override
    public void append(TypePojoGenerator baseGenerator, String propertyName, SchemaObject data) {
        NormalizedFieldAppendUtil.addSimpleTypeField(
                baseGenerator.getContext().getConfigRegistry(),
                baseGenerator.getBuildWrapper()
                ,propertyName
                , new SimpleTypes("object"));

    }

}
