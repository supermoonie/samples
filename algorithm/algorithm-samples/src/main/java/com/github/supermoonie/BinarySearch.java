package com.github.supermoonie;

/**
 * @author supermoonie
 * @date 2020-03-19
 */
public class BinarySearch {

    public static void main(String[] args) {
        int[] list = new int[]{1, 3, 5, 7, 9};
        System.out.println(BinarySearch.search(list, 1));
        System.out.println(BinarySearch.search(list, 3));
        System.out.println(BinarySearch.search(list, 5));
        System.out.println(BinarySearch.search(list, 7));
        System.out.println(BinarySearch.search(list, 9));
        System.out.println(BinarySearch.search(list, 10));
    }

    private static int search(int[] list, int key) {
        int low = 0;
        int high = list.length;
        while (low < high) {
            int mid = (low + high) >>> 1;
            int ele = list[mid];
            if (key == ele) {
                return mid;
            }
            if (ele < key) {
                low = mid + 1;
            } else {
                high = mid;
            }
        }
        return -1;
    }
}
