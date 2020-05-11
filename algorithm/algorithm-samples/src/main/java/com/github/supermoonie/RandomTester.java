package com.github.supermoonie;

import java.util.Random;

/**
 * @author supermoonie
 * @since 2020/4/27
 */
public class RandomTester {

    public static void main(String[] args) {
        int[] array = new Random().ints(10, 0, 100).toArray();
        for (int i = 0; i < array.length; i ++) {
//            swap(array, i, );
        }
        LinearCongruentialGenerator random = new LinearCongruentialGenerator();
        for (int i = 0; i < 100; i ++) {
            System.out.println(random.next());
        }
    }

    private static void swap(int[] list, int i, int j) {
        int temp = list[i];
        list[i] = list[j];
        list[j] = temp;
    }

    /**
     * 线性同余法生成随机数
     */
    public static class LinearCongruentialGenerator {

        private double x = System.currentTimeMillis();

        private double next() {
            int m = 2 ^ 31;
            int a = 48271;
            int c = 0;
            x = (a * x + c) % m;
            return x / m;
        }
    }
}
