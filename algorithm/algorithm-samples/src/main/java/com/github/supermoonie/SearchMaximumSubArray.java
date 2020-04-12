package com.github.supermoonie;

import java.util.Arrays;
import java.util.Random;

/**
 * 最大子数组查找
 *
 * @author supermoonie
 * @date 2020-04-09
 */
public class SearchMaximumSubArray {

    public static void main(String[] args) {
        Random random = new Random();
        int[] randoms = random.ints(10, -50, 50).distinct().toArray();
        System.out.println(Arrays.toString(randoms));
        System.out.println(searchMaxSubArrayByTwoFor(randoms));
    }

    /**
     * 暴力解法，两层循环遍历
     *
     * @param array 数组
     * @return 最大子数组
     */
    private static MaximumSubArray searchMaxSubArrayByTwoFor(int[] array) {
        int max = array[0], left = 0, right = 0;
        for (int i = 0; i < array.length; i++) {
            int sum = 0;
            for (int j = i; j < array.length; j++) {
                sum = sum + array[j];
                if (sum > max) {
                    max = sum;
                    left = i;
                    right = j;
                }
            }
        }
        return new MaximumSubArray(left, right, max);
    }

    private static MaximumSubArray searchMaxCrossingSubArray(int[] array, int low, int mid, int high) {
        int leftSum = array[low];
        int sum = 0;
        int maxLeftIndex = -1;
        for (int i = mid; i >= low; i--) {
            sum = sum + array[i];
            if (sum > leftSum) {
                leftSum = sum;
                maxLeftIndex = i;
            }
        }
        return null;
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

        @Override
        public String toString() {
            return "MaximumSubArray{" +
                    "leftIndex=" + leftIndex +
                    ", rightIndex=" + rightIndex +
                    ", sum=" + sum +
                    '}';
        }
    }
}
