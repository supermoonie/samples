package com.github.supermoonie;

import java.util.Arrays;
import java.util.Random;

/**
 * @author supermoonie
 * @since 2020/5/12
 */
public class HeapSort {

    public static void main(String[] args) {
        Random random = new Random();
        int[] randoms = random.ints(10, 1, 100).distinct().toArray();
        System.out.println(Arrays.toString(randoms));
        sort(randoms);
        System.out.println(Arrays.toString(randoms));
    }

    private static void sort(int[] arr) {
        buildMaxHeap(arr);
        for (int i = arr.length - 1; i > 0; i--) {
            swap(arr, 0, i);
            maxHeap(arr, 0, i);
        }
    }

    private static void buildMaxHeap(int[] arr) {
        for (int i = arr.length / 2; i >= 0; i--) {
            maxHeap(arr, i, arr.length);
        }
    }

    private static void maxHeap(int[] arr, int i, int high) {
        int l, r, largest = i;
        if (0 == i) {
            l = 1;
            r = 2;
        } else {
            l = i << 1;
            r = l + 1;
        }
        if (l < high && arr[l] > arr[i]) {
            largest = l;
        }
        if (r < high && arr[r] > arr[largest]) {
            largest = r;
        }
        if (largest != i) {
            swap(arr, i, largest);
            maxHeap(arr, largest, high);
        }
    }

    private static void swap(int[] list, int i, int j) {
        int temp = list[i];
        list[i] = list[j];
        list[j] = temp;
    }
}
