package array;

import java.util.ArrayList;
import java.util.List;

/**
 * Created By ShengXiang.Xiong on 2019/11/28
 * 给定一个范围在  1 ≤ a[i] ≤ n ( n = 数组大小 ) 的 整型数组，数组中的元素一些出现了两次，另一些只出现一次。
 * 找到所有在 [1, n] 范围之间没有出现在数组中的数字。
 * 您能在不使用额外空间且时间复杂度为O(n)的情况下完成这个任务吗? 你可以假定返回的数组不算在额外空间内。
 * <p>
 * 输入:
 * [4,3,2,7,8,2,3,1]
 * <p>
 * 输出:
 * [5,6]
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/find-all-numbers-disappeared-in-an-array
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class FindDisappearedNumbers {
    /**
     * 遍历数组，对于nums[i]nums[i]：
     * 循环条件，若nums[i]!=i+1并且nums[i]!=nums[nums[i]-1]。
     * 意思是若当前位置的元素不等于i+1，将它放到对应的下标nums[i]-1处。
     * 为了避免死循环，若正确的位置上已经有了正确的元素，则结束。
     *
     * @param nums
     * @return
     */
    public static List<Integer> findDisappearedNumbers(int[] nums) {
        List<Integer> res = new ArrayList<>();
//        for (int i = 0; i < nums.length ; i++) {
//            nums[i]--;
//        }
        for (int i = 0; i < nums.length; i++) {
            while (nums[i] != i + 1 && nums[nums[i] - 1] != nums[i]) {
                int tmp = nums[nums[i] - 1];
                nums[nums[i] - 1] = nums[i];
                nums[i] = tmp;
            }
        }
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != i + 1) {
                res.add(i + 1);
            }
        }
        return res;
    }

    /**
     * 另一种思想，我们将出现过的数字对应的数组下标上的元素值始终变为负数，那么数组中正数的元素的下标可以表示缺失的元素。
     *
     * @param nums
     * @return
     */
    public static List<Integer> findDisappearedNumbers1(int[] nums) {
        List<Integer> res = new ArrayList<>();
        for (int i = 0; i < nums.length; i++) {
            nums[Math.abs(nums[i]) - 1] = -Math.abs(nums[Math.abs(nums[i]) - 1]);
        }
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] > 0) {
                res.add(i + 1);
            }
        }
        return res;
    }

    public static void main(String[] args) {
        int[] nums = {2, 2, 2};
        for (Integer disappearedNumber : findDisappearedNumbers1(nums)) {
            System.out.print(disappearedNumber + " ");
        }
    }
}
