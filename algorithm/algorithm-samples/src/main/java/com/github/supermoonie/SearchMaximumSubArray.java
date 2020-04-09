package com.github.supermoonie;

/**
 * 最大子数组查找
 *
 * @author supermoonie
 * @date 2020-04-09
 */
public class SearchMaximumSubArray {

    public static void main(String[] args) {

    }

    private static MaximumSubArray searchMaxCrossingSubArray(int[] array, int low, int mid, int high) {
        int leftSum = array[low];
        int sum = 0;
        int maxLeftIndex = -1;
        for (int i = mid; i >= low; i --) {
            sum = sum + array[i];
            if (sum > leftSum) {
                leftSum = sum;
                maxLeftIndex = i;
            }
        }
    }

    static class MaximumSubArray {
        /**
         * 最大子数组的左边界
         */
        int leftIndex;
        /**
         * 最大子数组的有边界
         */
        int rightIndex;
        /**
         * 最大子数组的和
         */
        int sum;

        public MaximumSubArray(int leftIndex, int rightIndex, int sum) {
            this.leftIndex = leftIndex;
            this.rightIndex = rightIndex;
            this.sum = sum;
        }
    }
}
