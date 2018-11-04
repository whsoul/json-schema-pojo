package com.whsoul.jsch.common.util;

import java.util.*;
import java.util.stream.Collectors;

public class ClassUtil {

    public static Set<Class<?>> findSuperClassList(Class<?> claz){
        Set<Class<?>> superClassList = new LinkedHashSet<>();
        Class<?> superClaz = claz;
        while((superClaz = superClaz.getSuperclass()) != null){
            superClassList.add(superClaz);
        }
        return superClassList;
    }

    public static Set<Class<?>> findInterfaceList(Class<?> claz){
        Class<?> superClaz = claz;
        Set<Class<?>> interfaceList = new LinkedHashSet<>(Arrays.asList(superClaz.getInterfaces()));
        while((superClaz = superClaz.getSuperclass()) != null){
            interfaceList.addAll(Arrays.asList(superClaz.getInterfaces()));
        }
        return interfaceList;
    }

    public static Set<Class<?>> findIntersectionClassList(Set<Class<?>>... classList){
        Set<Class<?>> intersections = Arrays.stream(classList).reduce((clist, aggregated) -> {
            Set<Class<?>> newList = new LinkedHashSet<>(clist);
            newList.retainAll(aggregated);
            return newList;
        }).get();
        return intersections;
    }

    public static Class<?> guessIntersectionSuperClass(Class<?>... claz){
        List<Set<Class<?>>> superClassList = Arrays.stream(claz).map(ClassUtil::findSuperClassList).collect(Collectors.toList());
        Set<Class<?>> intersectClassList = findIntersectionClassList(superClassList.toArray(new Set[0]));

        return intersectClassList.stream().findFirst().get();
    }

    public static Class<?> guessIntersectionInterface(Class<?>... claz){
        List<Set<Class<?>>> superClassList = Arrays.stream(claz).map(ClassUtil::findInterfaceList).collect(Collectors.toList());
        Set<Class<?>> intersectClassList = findIntersectionClassList(superClassList.toArray(new Set[0]));

        return intersectClassList.stream().findFirst().get();
    }

    public static void main(String[] args){

        Set<Class<?>> classes0 = new LinkedHashSet<>(Arrays.asList(ArrayList.class, Map.class));
        Set<Class<?>> classes1 = new LinkedHashSet<>(Arrays.asList(LinkedHashMap.class, Map.class, List.class, ArrayList.class));
        Set<Class<?>> classes2 = new LinkedHashSet<>(Arrays.asList(LinkedHashMap.class, Map.class, List.class));
        Set<Class<?>> classes3 = new LinkedHashSet<>(Arrays.asList(LinkedHashMap.class, Map.class));
        Set<Class<?>> classes4 = new LinkedHashSet<>(Arrays.asList(LinkedHashMap.class, Map.class));

        Set<Class<?>> result = findIntersectionClassList(classes0, classes1, classes2, classes3, classes4);

        System.out.println(result);

        Class<?> r = guessIntersectionSuperClass(NavigableMap.class);
        System.out.println(r);

        Class<?> in = guessIntersectionInterface(LinkedHashMap.class, HashMap.class, SortedMap.class);
        System.out.println(in);

    }
}
