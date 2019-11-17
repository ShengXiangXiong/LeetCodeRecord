package twoPointer;

/**
 * Created by Xursan on 2019/11/17.
 * 给定一个数组 nums，编写一个函数将所有 0 移动到数组的末尾，同时保持非零元素的相对顺序。
 * <p>
 * 输入: [0,1,0,3,12]
 * 输出: [1,3,12,0,0]
 * 说明:
 * 必须在原数组上操作，不能拷贝额外的数组。
 * 尽量减少操作次数。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/move-zeroes
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class MoveZeroes {
    /**
     * 双指针。
     * 慢指针p指向第一个0的位置，快指针q指向不为0的位置，每当q为0则q++；否则令p = nums[q]，然后p++，q++
     * (表示前面慢指针的0已经和快指针的非0元素进行了交换)
     *
     * @param nums
     */
    public static void moveZeroes(int[] nums) {
        int p = 0;
        while (p < nums.length) {
            if (nums[p] == 0) {
                break;
            }
            p++;
        }
        int q = p + 1;
        while (q < nums.length) {
            if (nums[q] != 0) {
                nums[p] = nums[q];
                nums[q] = 0;
                p++;
            }
            q++;
        }
    }

    public static void main(String[] args) {
        int[] nums = {0, 2, 1, 0};
        moveZeroes(nums);
        for (int num : nums) {
            System.out.print(num + " ");
        }
    }
}
