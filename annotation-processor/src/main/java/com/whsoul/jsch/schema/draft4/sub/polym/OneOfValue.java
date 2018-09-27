package com.whsoul.jsch.schema.draft4.sub.polym;

import com.whsoul.jsch.schema.draft4.sub.definition.Value;

import java.security.InvalidParameterException;
import java.util.HashSet;
import java.util.Set;

public class OneOfValue {
    Set<Class<? extends Value>> availables = new HashSet<>();
    Set<Value> values = new HashSet<>();

    public OneOfValue(Class<? extends Value>... availableValueType) {
        for (Class<? extends Value> v : availableValueType) {
            availables.add(v);
        }
    }

    public void add(Value v) {
        if (availables.stream().anyMatch(available -> v.getClass().isAssignableFrom(available))) {
            this.values.add(v);
        } else {
            throw new InvalidParameterException("Not available to Type AnyOfValue" + availables + "Type : " + v.getClass().getSimpleName());
        }
    }


}
