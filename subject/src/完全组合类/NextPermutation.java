package 完全组合类;

import java.util.Arrays;

/**
 * Created by Xursan on 2020/2/28.
 * 31. 下一个排列
 */
public class NextPermutation {
    public static void swap(int[] nums, int i, int j) {
        nums[i] = nums[i] ^ nums[j];
        nums[j] = nums[i] ^ nums[j];
        nums[i] = nums[i] ^ nums[j];
    }

    public static void nextPermutation(int[] nums) {
        for (int i = nums.length - 1; i >= 1; i--) {
            //找到拐点i-1，反向（其右边）寻找比拐点大的最小的数
            if (nums[i] > nums[i - 1]) {
                int k = i;
                for (int j = i + 1; j < nums.length; j++) {
                    if (nums[j] > nums[i - 1] && nums[j] - nums[i - 1] < nums[k] - nums[i - 1]) {
                        k = j;
                    }
                }
                swap(nums, i - 1, k);
                //由于右边是降序的，并不满足最大的条件，所以需要逆序，从小到大，这样才满足刚好比它大的下一个数
                Arrays.sort(nums, i, nums.length);
                return;
            }
        }
        for (int i = 0; i < nums.length / 2; i++) {
            swap(nums, i, nums.length - 1 - i);
        }
    }

    public static void main(String[] args) {
        int[] nums = new int[]{5, 4, 3};
        nextPermutation(nums);
        for (int i : nums) {
            System.out.print(i + " ");
        }
    }
}
