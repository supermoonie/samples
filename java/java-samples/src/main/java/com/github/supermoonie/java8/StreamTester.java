package com.github.supermoonie.java8;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Stream 使用一种类似用 SQL 语句从数据库查询数据的直观方式来提供一种对 Java 集合运算和表达的高阶抽象。
 *
 * @author supermoonie
 * @date 2020-03-20
 */
public class StreamTester {

    public static void main(String[] args) {
        // filter
        List<String> strings = Arrays.asList("abc", "", "bc", "efg", "adc", "", "jkl");
        List<String> filtered = strings.stream().filter(string -> !string.isEmpty()).collect(Collectors.toList());
        filtered.forEach(System.out::println);
        // forEach 迭代
        Random random = new Random();
        random.ints().limit(10).forEach(System.out::println);
        // map
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 3, 2, 1);
        numbers.stream().map(n -> n * n).distinct().forEach(System.out::println);
        // toMap
        Map<Integer, Integer> toMap = numbers.stream().distinct().collect(Collectors.toMap(key -> key, value -> value));
        toMap.forEach((key, value) -> System.out.println(key + ": " + value));
        // summaryStatistics
        IntSummaryStatistics statistics = numbers.stream().mapToInt(n -> n).summaryStatistics();
        System.out.println(statistics);
    }


}
