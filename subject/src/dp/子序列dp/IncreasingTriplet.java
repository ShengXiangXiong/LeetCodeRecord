package dp.子序列dp;

/**
 * Created by Xursan on 2019/12/29.
 * 度为 3 的递增子序列。
 * 数学表达式如下:
 * 如果存在这样的 i, j, k,  且满足 0 ≤ i < j < k ≤ n-1，
 * 使得 arr[i] < arr[j] < arr[k] ，返回 true ; 否则返回 false 。
 * 说明: 要求算法的时间复杂度为 O(n)，空间复杂度为 O(1) 。
 * <p>
 * 输入: [1,2,3,4,5]
 * 输出: true
 * <p>
 * 输入: [5,4,3,2,1]
 * 输出: false
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/increasing-triplet-subsequence
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class IncreasingTriplet {
    /**
     * 仔细思考，其实我们一开始想到的是，这实际上就是最长递增子序列的变形，
     * 其思路就是dp[i]表示以i结尾（递增子序列必须要包括i）的递增子序列长度，dp[i+1]则需要从dp[1]到dp[i]查看
     * 每一个结尾处的值和当前值的大小进行比对。
     * 而单纯使用最长递增子序列的普通dp时间复杂度为O（n^2）,就算采取分治法（也为O（nlogn））显然不行
     * <p>
     * 再仔细想一下，题目简化了要求，目标明确长度为3，对于序列0-i，我们只需要维护0-（i-1）序列长度为2的子序列
     * 若出现多个，选择末尾值最小的，保持子序列尽可能的小。所以关键问题就在于如何维护这个长度为2的序列的最小末尾值。
     * <p>
     * 使用two表示已存在的长度为2且结尾最小的序列m末尾值，one表示当前单个最小值（可能会作为two的候选）
     * 也就是说：利用两个变量，一个记录最小值one，另一个记录次大值two，如果有比次大值的元素出现则有递增三元子序列，
     * 如果出现的值小于次大值two，但大于最小值one，则更新次大值two，若比one最小值还小，则更新最小值，次大值不变。
     * <p>
     * 其实这道问题还可以扩展成k元子序列，但是我们仍然可以在O（k*n）的时间复杂度内解决，前面我们在单调队列中介绍过，
     * 我们用单调队列做题时都有一种需要  快速定位最大值或最小值  的感觉，且需要维护一个递增指针队列或递减指针队列来不断更新
     * 所以我们只需要使用这个单调队列即可完成  对长度为k的序列的最小末尾值更新。
     *
     * @param nums
     * @return
     */
    public static boolean increasingTriplet(int[] nums) {
        if (nums.length <= 3) {
            return false;
        }
        int one = nums[0];
        int two = Integer.MAX_VALUE;
        ;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] > two) {
                return true;
            } else if (nums[i] > one) {
                two = nums[i];
            } else {
                one = nums[i];
            }
        }
        return false;
    }

    public static void main(String[] args) {
        int[] nums = {2, 3, 1, 2, 0, 1};
        System.out.println(increasingTriplet(nums));
    }
}
