package com.github.supermoonie.java8;

/**
 * Lambda 表达式，也可称为闭包。
 * Lambda 允许把函数作为一个方法的参数（函数作为参数传递进方法中）。
 *
 * @author supermoonie
 * @date 2020-03-16
 */
public class LambdaTester {
    public static void main(String[] args) {
        // 完整写法
        MathOperation addition = (int a, int b) -> {
            return a + b;
        };
        // 不使用类型声明
        MathOperation subtraction = (a, b) -> {
            return a - b;
        };
        // 不使用大括号
        MathOperation multiplication = (int a, int b) -> a * b;
        // 不使用类型声明 & 不适用大括号
        MathOperation division = (a, b) -> a / b;
        LambdaTester tester = new LambdaTester();
        System.out.println("1 + 2 = " + tester.operate(1, 2, addition));
        System.out.println("2 - 1 = " + tester.operate(2, 1, subtraction));
        System.out.println("2 * 2 = " + tester.operate(2, 2, multiplication));
        System.out.println("4 / 2 = " + tester.operate(4, 2, division));

        GreetingService greetingService = System.out::println;
        greetingService.say("hello lambda!");
    }

    private int operate(int a, int b, MathOperation mathOperation) {
        return mathOperation.operation(a, b);
    }

    interface MathOperation {
        int operation(int a, int b);
    }

    interface GreetingService {
        void say(String message);
    }
}
