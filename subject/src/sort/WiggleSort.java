package sort;

/**
 * Created by Xursan on 2020/1/7.
 * 给定一个无序的数组 nums，将它重新排列成 nums[0] < nums[1] > nums[2] < nums[3]... 的顺序。
 * 输入: nums = [1, 5, 1, 1, 6, 4]
 * 输出: 一个可能的答案是 [1, 4, 1, 5, 1, 6]
 * <p>
 * 输入: nums = [1, 3, 2, 2, 3, 1]
 * 输出: 一个可能的答案是 [2, 3, 1, 3, 1, 2]
 * 说明:
 * 你可以假设所有输入都会得到有效的结果。
 * 进阶:
 * 你能用 O(n) 时间复杂度和 / 或原地 O(1) 额外空间来实现吗？
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/wiggle-sort-ii
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class WiggleSort {
    /**
     * 观察题目很容易得出一个结论，即最终结果一定是要保证大的数在偶数位。
     * 所以自然而然可以想到：先将原数组分为大小两部分，大的一部分分在偶数位，小的一部分放在奇数位
     * 即可保证这种摆动排序方式
     *
     * @param nums
     */
    public static void wiggleSort(int[] nums) {

    }

    public static void main(String[] args) {
        int[] nums = {1, 5, 1, 1, 6, 4, 1, 23, 4, 5, 7, 9, 5, 2, 3, 54, 6, 8, 9, 0, 1, 2, 3, 4, 5, 67, 8, 1, 2, 4, 6};
        wiggleSort(nums);
        for (int i : nums) {
            System.out.print(i + " ");
        }
    }
}
