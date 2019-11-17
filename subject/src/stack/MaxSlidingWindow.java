package stack;

import java.util.LinkedList;

/**
 * Created By ShengXiang.Xiong on 2019/11/15
 * 给定一个数组 nums，有一个大小为 k 的滑动窗口从数组的最左侧移动到数组的最右侧。你只可以看到在滑动窗口内的 k 个数字。
 * 滑动窗口每次只向右移动一位。返回滑动窗口中的最大值。
 * <p>
 * 输入: nums = [1,3,-1,-3,5,3,6,7], 和 k = 3
 * 输出: [3,3,5,5,6,7]
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/sliding-window-maximum
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class MaxSlidingWindow {
    /**
     * 双指针+贪心，maxpos始终指向当前滑动窗口的最大值，can始终指向当前滑动窗口在maxpos后面的最大值（作为maxpos的候选）
     * 每当滑动窗口时，不断更新can，并判断 ：
     * if maxpos not in SlideWindows || nums[cnt]>=nums[maxpos]:
     * maxpos = can
     * can++
     * 即判断maxpos是否仍在滑动窗口内，若没有则maxpos = can，can++；若在滑动时can大于maxpos的值，则也maxpos = can，can++
     * 贪心在于，maxpos和can都要尽量处于窗口内的右侧位置，这样其才会尽肯能减小因窗口滑动带来的替换可能。
     * <p>
     * 然而上面的解法是错误的，尴尬。
     * <p>
     * 因为上面有可能出现can刚好变为maxpos，而can无法定位到比其小的第一个值，因为can只维护了比maxpos小的最大值，没有维护
     * 自己小的最大值，当然你可能说再来一个指针，指向比can小的，然而这肯定实不可取的，因为可能还会出现上述情况。
     *
     * @param nums
     * @param k
     * @return
     */
    public static int[] maxSlidingWindow(int[] nums, int k) {
        int maxpos = 0;
        int can = 0;
        int[] res = new int[nums.length - k + 1];
        for (int i = 0; i < k; i++) {
            if (nums[can] < nums[i]) {
                can = i;
            }
            if (nums[maxpos] < nums[can]) {
                maxpos = can;
                can++;
            }
        }
        res[0] = nums[maxpos];
        for (int i = k; i < nums.length; i++) {
            int left = i - k;
            if (nums[can] <= nums[i]) {
                can = i;
            }
            if (maxpos <= left || nums[maxpos] <= nums[can]) {
                maxpos = can;
                can++;
            }

            res[left + 1] = nums[maxpos];
        }
        return res;
    }

    /**
     * 单调队列（单调栈的升级版，单调栈只能操作栈顶，而单调队列既可以队首也可以队尾）
     * <p>
     * 最开始看到是滑动窗口，首先想到用双指针，但是这里还有个特征就是最值，而滑动窗口的最值就应该想到单调栈或单调队列。
     * <p>
     * 从最普通的想法开始，我们需要找的就是每一个窗口内的最大值，当窗口滑动时可能需要更新最大值位置，而如何更新，快速定位
     * 当前窗口的最大值位置，便是关键。
     * 于是自然就想到了维护一个递减队列（这里不能是单调栈，虽然是借助了单调栈的思想，但是我们必须要能够操纵栈尾元素）
     * 队首是当前窗口的最大值，后面是依次递减的后续元素的位置，这样当窗口滑动造成队首不在窗口内时，可以直接弹出队首元素，
     * 接下来的队首元素就是当前窗口内的最大值。
     * 其次，每当滑动窗口时，都要判断队尾元素是否比新增元素小，若小于新增元素则弹出，直到满足>=新增元素或者
     * 队列为空时（即表示当前元素是最大值，这时便会不断弹出，造成队列为空），然后add进队尾
     *
     * @param nums
     * @param k
     * @return
     */
    public static int[] maxSlidingWindow1(int[] nums, int k) {
        if (k == 1) {
            return nums;
        }
        if (k == 0) {
            return new int[0];
        }
        LinkedList<Integer> ls = new LinkedList<>();
        ls.add(0);
        int[] res = new int[nums.length - k + 1];
        for (int i = 1; i < nums.length; i++) {
            while (!ls.isEmpty() && nums[ls.peekLast()] <= nums[i]) {
                ls.pollLast();
            }
            ls.add(i);
            if (i - k + 1 > ls.getFirst()) {
                ls.poll();
            }
            if (i - k + 1 >= 0) {
                res[i - k + 1] = nums[ls.peek()];
            }
        }
        return res;
    }

    public static void main(String[] args) {
        int[] nums = {};
        int k = 0;
        for (int i : maxSlidingWindow1(nums, k)) {
            System.out.print(i + " ");
        }
    }
}
