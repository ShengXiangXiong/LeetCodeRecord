package sort;

import java.util.Arrays;

/**
 * Created By ShengXiang.Xiong on 2019/12/7
 * 给定一个整数数组，你需要寻找一个连续的子数组，如果对这个子数组进行升序排序，那么整个数组都会变为升序排序。
 * 你找到的子数组应是最短的，请输出它的长度。
 * <p>
 * 输入: [2, 6, 4, 8, 10, 9, 15]
 * 输出: 5
 * 解释: 你只需要对 [6, 4, 8, 10, 9] 进行升序排序，那么整个表都会变为升序排序。
 * 说明 :
 * <p>
 * 输入的数组长度范围在 [1, 10,000]。
 * 输入的数组可能包含重复元素 ，所以升序的意思是<=。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/shortest-unsorted-continuous-subarray
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class FindUnsortedSubarray {
    /**
     * 排序。
     * 从一开始，我们应该想到既然最终是有序的，那自然排序后，若某个数不在其排序后在的位置上，
     * 便说明该数字一定在待排序的子数组中，自然而然就想到先排序，然后分别从前后两端比较原数组和排序后的数组，
     * 看其对应位置的数字是否相等，若不相等，则一定在排序子数组中。
     * 从而即可找到左边的第一个待排序位置，和右边的第一个待排序位置，然后这两个位置区间内即是需要排序的子数组。
     *
     * @param nums
     * @return
     */
    public static int findUnsortedSubarray(int[] nums) {
        int[] tmp = nums.clone();
        Arrays.sort(tmp);
        int p = 0;
        int q = nums.length - 1;
        while (p < q && nums[p] == tmp[p]) {
            p++;
        }
        while (q >= p && nums[q] == tmp[q]) {
            q--;
        }
        return q - p + 1;
    }

    public static void main(String[] args) {
        int[] nums = {1, 2, 3, 4};
        System.out.println(findUnsortedSubarray(nums));
    }
}
