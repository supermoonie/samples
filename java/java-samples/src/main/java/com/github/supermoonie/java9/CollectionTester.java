package com.github.supermoonie.java9;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 集合工厂方法
 *
 * @author supermoonie
 * @since 2020-03-25
 */
public class CollectionTester {

    public static void main(String[] args) {
        Set<String> set = Set.of("a", "b", "c");
        set = Collections.unmodifiableSet(set);
        System.out.println(set);
        List<String> list = List.of("a", "b", "c");
        list = Collections.unmodifiableList(list);
        System.out.println(list);
        Map<String, Integer> map = Map.of("a", 1, "b", 2, "c", 3);
        map = Collections.unmodifiableMap(map);
        System.out.println(map);
    }

}
