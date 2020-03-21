package com.github.supermoonie.java8;

import java.util.Optional;

/**
 * Optional 类是一个可以为null的容器对象。如果值存在则isPresent()方法会返回true，调用get()方法会返回该对象。
 * Optional 是个容器：它可以保存类型T的值，或者仅仅保存null。
 * Optional 提供很多有用的方法，这样我们就不用显式进行空值检测。
 * Optional 类的引入很好的解决空指针异常。
 *
 * @author supermoonie
 * @date 2020-03-21
 */
public class OptionalTester {

    public static void main(String[] args) {
        System.out.println(sum(null, null));
        System.out.println(sum(1, 1));
    }

    private static int sum(Integer a, Integer b) {
        a = Optional.ofNullable(a).orElse(0);
        b = Optional.ofNullable(b).map(num -> num + 1).orElse(0);
        return a + b;
    }
}
