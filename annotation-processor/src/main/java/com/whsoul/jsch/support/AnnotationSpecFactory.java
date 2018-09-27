package com.whsoul.jsch.support;

import com.squareup.javapoet.AnnotationSpec;

import java.util.Collections;
import java.util.Map;

public interface AnnotationSpecFactory<T,S> {
    default AnnotationSpec of() {
        return this.ofWithValue(Collections.EMPTY_MAP);
    }

    AnnotationSpec ofWithValue(Map<T, S> valueMap);
}
