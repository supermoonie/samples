package com.github.supermoonie.java8.function_interface;

/**
 * 函数式接口(Functional Interface)就是一个有且仅有一个抽象方法，但是可以有多个非抽象方法的接口。
 * 函数式接口可以被隐式转换为 lambda 表达式。
 *
 * @author supermoonie
 * @date 2020-03-20
 */
@FunctionalInterface
public interface GreetingService {

    void say(String message);
}
