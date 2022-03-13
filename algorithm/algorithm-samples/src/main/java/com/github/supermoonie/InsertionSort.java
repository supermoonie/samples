package com.github.supermoonie;

import java.util.Arrays;

/**
 * @author Administrator
 * @since 2022/3/13
 */
public class InsertionSort {

    public void sort(int[] arr) {
        int len = arr.length;
        int preIndex, current;
        for (int i = 1; i < len; i++) {
            preIndex = i - 1;
            current = arr[i];
            while (preIndex >= 0 && arr[preIndex] > current) {
                arr[preIndex + 1] = arr[preIndex];
                preIndex--;
            }
            arr[preIndex + 1] = current;
        }
    }

    public static void main(String[] args) {
        int[] arr = new int[]{1, 100, 2, 200, 3, 300};
        new InsertionSort().sort(arr);
        System.out.println(Arrays.toString(arr));
    }
}
