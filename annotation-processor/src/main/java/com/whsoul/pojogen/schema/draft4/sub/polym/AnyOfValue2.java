package com.whsoul.pojogen.schema.draft4.sub.polym;

import com.whsoul.pojogen.schema.draft4.sub.definition.Value;

import java.security.InvalidParameterException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class AnyOfValue2 {
    Set<Class<? extends Value>> availables;
    private Value value;

    public abstract List<Class<? extends Value>> availableItems();

    public AnyOfValue2() {
        this.availables = new HashSet<>(availableItems());
    }

    public void set(Value v) {
        if (availables.stream().anyMatch(available -> v.getClass().isAssignableFrom(available))) {
            this.value = v;
        } else {
            throw new InvalidParameterException("Only AnyOfValue in " + availables + " but not available Type : " + v.getClass().getSimpleName());
        }
    }

    public <T extends Value> T getValue(Class<T> wantedType){
        if(availables.contains(wantedType)){
            return wantedType.cast(value);
        }
        throw new InvalidParameterException("Only AnyOfValue in " + availables + " but not available Type : " + wantedType.getClass().getSimpleName());
    }

    public Class<? extends Value> getType(){
        return this.value.getClass();
    }

    public boolean typeOf(Class<? extends Value> wantedType){
        if (!availables.stream().anyMatch(available -> wantedType.isAssignableFrom(available))) {
            throw new InvalidParameterException("Only AnyOfValue in " + availables + " but not available Type : " + wantedType.getSimpleName());
        }
        return this.value.getClass().equals(wantedType);
    }
}
