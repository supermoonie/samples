package com.github.supermoonie;

import java.util.Arrays;

/**
 * @author Administrator
 * @since 2022/3/13
 */
public class ShellSort {

    public void sort(int[] arr) {
        int n = arr.length;
        for (int h = n / 2; h > 0; h /= 2) {
            for (int i = h; i < n; i++) {
                for (int j = i - h; j >= 0; j -= h) {
                    if (arr[j] > arr[j + h]) {
                        int temp = arr[j];
                        arr[j] = arr[j + h];
                        arr[j + h] = temp;
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        int[] arr = new int[]{1, 100, 2, 200, 3, 300};
        new ShellSort().sort(arr);
        System.out.println(Arrays.toString(arr));
    }
}
