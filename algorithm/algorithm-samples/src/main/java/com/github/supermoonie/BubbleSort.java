package com.github.supermoonie;

import java.util.Arrays;

/**
 * @author Administrator
 * @since 2022/3/13
 */
public class BubbleSort {

    public void sort(int[] arr) {
        int len = arr.length;
        for (int i = 0; i < len - 1; i++) {
            for (int j = 0; j < len - 1 - i; j++) {
                // 相邻元素两两对比
                if (arr[j] > arr[j + 1]) {
                    // 元素交换
                    int temp = arr[j + 1];
                    arr[j + 1] = arr[j];
                    arr[j] = temp;
                }
            }
        }
    }

    public static void main(String[] args) {
        int[] arr = new int[]{1, 100, 2, 200, 3, 300};
        new BubbleSort().sort(arr);
        System.out.println(Arrays.toString(arr));
    }
}