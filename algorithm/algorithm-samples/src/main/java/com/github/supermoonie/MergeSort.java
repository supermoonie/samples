package com.github.supermoonie;

import java.util.Arrays;

/**
 * @author Administrator
 * @since 2022/3/13
 */
public class MergeSort {

    public void sort(int[] array, int low, int high) {
        int middle = (low + high) / 2;
        if (low < high) {
            sort(array, low, middle);
            sort(array, middle + 1, high);
            merge(array, low, middle, high);
        }
    }

    public void merge(int[] array, int low, int middle, int high){
        int[] temp = new int[high - low + 1];
        int i = low;
        int j = middle + 1;
        int k = 0;
        while (i <= middle && j <= high) {
            if (array[i] < array[j]) {
                temp[k++] = array[i++];
            } else {
                temp[k++] = array[j++];
            }
        }
        while (i <= middle) {
            temp[k++] = array[i++];
        }
        while (j <= high) {
            temp[k++] = array[j++];
        }
        for (int m = 0; m < temp.length; m++) {
            array[m + low] = temp[m];
        }
    }

    public static void main(String[] args) {
        int[] arr = new int[]{1, 100, 2, 200, 3, 300};
        new MergeSort().sort(arr, 0, arr.length - 1);
        System.out.println(Arrays.toString(arr));
    }
}
