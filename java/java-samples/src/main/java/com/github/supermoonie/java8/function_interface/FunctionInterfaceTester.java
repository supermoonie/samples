package com.github.supermoonie.java8.function_interface;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

/**
 * @author supermoonie
 * @date 2020-03-20
 */
public class FunctionInterfaceTester {

    public static void main(String[] args) {
        GreetingService greetingService = System.out::println;
        greetingService.say("hello world!");
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6);
        eval(list, item -> true);
        System.out.println();
        eval(list, item -> item % 2 == 0);
    }

    private static void eval(List<Integer> list, Predicate<Integer> predicate) {
        list.forEach(item -> {
            if (predicate.test(item)) {
                System.out.println(item);
            }
        });
    }
}
