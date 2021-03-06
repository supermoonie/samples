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
        System.out.println(searchMaxCrossingSubArray(randoms, 0, randoms.length >>> 1, randoms.length - 1));
        System.out.println(searchMaxSubArrayByRecursive(randoms, 0, randoms.length - 1));
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

    private static int counter = 1;

    private static MaximumSubArray searchMaxSubArrayByRecursive(int[] array, int low, int high) {
        if (low == high) {
            return new MaximumSubArray(low, high, array[low]);
        }
        int mid = (low + high) / 2;
        MaximumSubArray leftArray = searchMaxSubArrayByRecursive(array, low, mid);
        MaximumSubArray rightArray = searchMaxSubArrayByRecursive(array, mid + 1, high);
        MaximumSubArray crossingArray = searchMaxCrossingSubArray(array, low, mid, high);
        counter = counter + 1;
        if (leftArray.sum >= crossingArray.sum && leftArray.sum >= rightArray.sum) {
            return leftArray;
        } else if (leftArray.sum <= rightArray.sum && crossingArray.sum <= rightArray.sum) {
            return rightArray;
        } else {
            return crossingArray;
        }
    }

    /**
     * 查找跨越中点的最大字数组
     *
     * @param array 数组
     * @param low   low
     * @param mid   mid
     * @param high  high
     * @return 跨越中点的最大字数组
     */
    private static MaximumSubArray searchMaxCrossingSubArray(int[] array, int low, int mid, int high) {
        int leftSum = 0, rightSum = 0, sum = 0, maxLeftIndex = low, maxRightIndex = mid;
        for (int i = mid; i >= low; i--) {
            sum = sum + array[i];
            if (sum > leftSum) {
                leftSum = sum;
                maxLeftIndex = i;
            }
        }
        sum = 0;
        for (int i = mid + 1; i <= high; i ++) {
            sum = sum + array[i];
            if (sum > rightSum) {
                rightSum = sum;
                maxRightIndex = i;
            }
        }
        return new MaximumSubArray(maxLeftIndex, maxRightIndex, leftSum + rightSum);
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
