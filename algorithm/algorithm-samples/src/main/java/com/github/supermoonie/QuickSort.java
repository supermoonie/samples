package com.github.supermoonie;

import java.util.Arrays;
import java.util.Random;

/**
 * @author supermoonie
 * @date 2020-03-22
 */
public class QuickSort {

    public static void main(String[] args) {
        Random random = new Random();
//        int[] randoms = random.ints(6, 1, 10).distinct().toArray();
        int[] randoms = new int[]{7, 6, 4, 9};
        System.out.println(Arrays.toString(randoms));
        sort(randoms, 0, randoms.length - 1);
        System.out.println(Arrays.toString(randoms));
    }

    private static void sort(int[] list, int low, int high) {
        if (low >= high) {
            return;
        }
        int pa = partition(list, low, high);
        sort(list, low, pa - 1);
        sort(list, pa + 1, high);
    }

    private static int partition(int[] list, int low, int high) {
        int pivot = list[low];
        int start = low;
        int end = high;
        while (start < end) {
            while (start < end && list[start] >= pivot) {
                start += 1;
            }
            list[end] = list[start];
            while (start < end && list[end] >= pivot) {
                end -= 1;
            }
            list[start] = list[end];
        }
        list[start] = pivot;
        return start;
    }
}
