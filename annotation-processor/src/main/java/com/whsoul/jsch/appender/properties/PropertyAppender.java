package com.whsoul.jsch.appender.properties;

import com.whsoul.jsch.TypePojoGenerator;

public interface PropertyAppender<S> {
    void append(TypePojoGenerator generator, String propertyName, S data);

}
