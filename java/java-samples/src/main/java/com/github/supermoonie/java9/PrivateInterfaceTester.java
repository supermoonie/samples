package com.github.supermoonie.java9;

/**
 * 私有接口方法
 *
 * @author supermoonie
 * @since 2020-03-25
 */
public class PrivateInterfaceTester {

    public static void main(String[] args) {
        PrivateInterface privateInterface = new PrivateInterface() {};
        privateInterface.sayHello();
        privateInterface.sayHelloWorld();
    }

}

interface PrivateInterface {

    default void sayHello() {
        say("hello");
    }

    default void sayHelloWorld() {
        say("hello world!");
    }

    private void say(String msg) {
        System.out.println(msg);
    }
}