package com.github.supermoonie;

import java.util.Arrays;

/**
 * @author supermoonie
 * @date 2020-03-22
 */
public class QuickSort {

    public void sort(int[] arr, int left, int right) {
        if (left < right) {
            int pivot = arr[left];
            int low = left;
            int high = right;
            while (low < high) {
                while (low < high && arr[high] >= pivot) {
                    high--;
                }
                arr[low] = arr[high];
                while (low < high && arr[low] <= pivot) {
                    low++;
                }
                arr[high] = arr[low];
            }
            arr[low] = pivot;
            sort(arr, left, low - 1);
            sort(arr, low + 1, right);
        }
    }

    public static void main(String[] args) {
        int[] arr = new int[]{1, 100, 2, 200, 3, 300};
        new QuickSort().sort(arr, 0, arr.length - 1);
        System.out.println(Arrays.toString(arr));
    }
}
