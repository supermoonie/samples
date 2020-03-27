package com.github.supermoonie;

import java.util.Arrays;
import java.util.Random;

/**
 * @author supermoonie
 * @since 2020-03-27
 */
public class InsertSort {

    public static void main(String[] args) {
        Random random = new Random();
        int[] randoms = random.ints(10, 1, 100).distinct().toArray();
        System.out.println(Arrays.toString(randoms));
        sort(randoms);
        System.out.println(Arrays.toString(randoms));
    }

    private static void sort(int[] array) {
        if (array.length <= 1) {
            return;
        }
        for (int i = 1; i < array.length; i ++) {
            int key = array[i];
            int j = i - 1;
            while (j >= 0 && array[j] > key) {
                array[j + 1] = array[j];
                j = j - 1;
            }
            array[j + 1] = key;
        }
    }
}
