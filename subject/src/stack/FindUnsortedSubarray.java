package stack;

import java.util.Arrays;
import java.util.Stack;

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

    /**
     * 单调栈。
     * 其实在一开始考虑问题解决方案的时候就有点单调栈的意思。
     * 首先一开始的想法是分别找出逆序对，即考虑每一个数，然后寻找其左边比他大的最远的位置，右边比他小的最远的位置。
     * 从而每一个数都会形成一个待排序子数组区间，最后取这些子数组区间的并集就是最终需要排序的子数组区间。
     * 而这正符合单调栈的用法。
     * 于是有，维护left和right变量，分别指向左右两边最远的逆序位置：
     * 1.正向扫描，找到最左边的逆序位置
     * 2.反向扫描，找到最右边的逆序位置
     *
     * @param nums
     * @return
     */
    public static int findUnsortedSubarray2(int[] nums) {
        Stack<Integer> s = new Stack<>();
        int left = nums.length;
        int right = 0;
        s.add(-1);
        for (int i = 0; i < nums.length; i++) {
            while (s.peek() != -1 && nums[s.peek()] > nums[i]) {
                left = Math.min(left, s.peek());
                s.pop();
            }
            s.add(i);
            if (left == 0) {
                break;
            }
        }
        s = new Stack<>();
        s.add(-1);
        for (int i = nums.length - 1; i >= 0; i--) {
            while (s.peek() != -1 && nums[s.peek()] < nums[i]) {
                right = Math.max(right, s.peek());
                s.pop();
            }
            s.add(i);
            if (right == nums.length - 1) {
                break;
            }
        }
        return right > left ? right - left + 1 : 0;
    }

    /**
     * 更简单的思路，只需要判断每一个位置的数是否需要调整即可，然后维护左右两个指针记录最远的需要调整的位置。
     * <p>
     * 从左到右循环，记录最大值为 max，若 nums[i] < max, 则表明位置 i 需要调整, 循环结束，记录需要调整的最大位置 i 为 high;
     * 同理，从右到左循环，记录最小值为 min, 若 nums[i] > min, 则表明位置 i 需要调整，循环结束，记录需要调整的最小位置 i 为 low.
     *
     * @param nums
     * @return
     */
    public static int findUnsortedSubArray3(int[] nums) {
        int len = nums.length;
        if (len <= 1) return 0;
        int high = 0, low = len - 1, max = nums[0], min = nums[len - 1];
        for (int i = 1; i < len; i++) {
            max = Math.max(max, nums[i]);
            min = Math.min(min, nums[len - 1 - i]);
            if (nums[i] < max) high = i;
            if (nums[len - 1 - i] > min) low = len - 1 - i;
        }
        return high > low ? high - low + 1 : 0;
    }

    public static void main(String[] args) {
        int[] nums = {2, 3, 8, 9, 7};
        System.out.println(findUnsortedSubarray2(nums));
    }
}
