package com.github.supermoonie;

import java.util.Arrays;

/**
 * @author supermoonie
 * @date 2020-03-20
 */
public class SimpleSelectSort {

    public static void main(String[] args) {
        int[] list = new int[]{3, 2, 1, 5, 4};
        sort(list);
        System.out.println(Arrays.toString(list));
    }

    private static void sort(int[] list) {
        for (int i = 0; i < list.length; i ++) {
            int min = list[i];
            int minIndex = i;
            // 1. 查找最小值
            for (int j = i + 1; j < list.length; j ++) {
                if (list[j] < min) {
                    minIndex = j;
                    min = list[j];
                }
            }
            // 2. 与最小值互换位置
            if (minIndex != i) {
                list[minIndex] = list[i];
                list[i] = min;
            }
        }
    }
}
