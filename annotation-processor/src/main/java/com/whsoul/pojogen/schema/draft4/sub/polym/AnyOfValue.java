package com.whsoul.pojogen.schema.draft4.sub.polym;

import com.whsoul.pojogen.schema.draft4.sub.definition.Value;

import java.security.InvalidParameterException;
import java.util.HashSet;
import java.util.Set;

public class AnyOfValue {
    Set<Class<? extends Value>> availables = new HashSet<>();
    public Value value;

    public AnyOfValue(Class<? extends Value>... availableValueType) {
        for (Class<? extends Value> v : availableValueType) {
            availables.add(v);
        }
    }

    public void set(Value v) {
        if (availables.stream().anyMatch(available -> v.getClass().isAssignableFrom(available))) {
            this.value = v;
        } else {
            throw new InvalidParameterException("Only AnyOfValue in " + availables + " but not available Type : " + v.getClass().getSimpleName());
        }
    }
}
