package dp.线性dp.continuous_interval;

/**
 * Created By ShengXiang.Xiong on 2019/10/31
 * 给定一个整数数组 nums ，找出一个序列中乘积最大的连续子序列（该序列至少包含一个数）。
 *
 * 输入: [2,3,-2,4]
 * 输出: 6
 * 解释: 子数组 [2,3] 有最大乘积 6。
 *
 * 输入: [-2,0,-1]
 * 输出: 0
 * 解释: 结果不能为 2, 因为 [-2,-1] 不是子数组。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/maximum-product-subarray
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class MaxProduct {
    /**
     * 很简单的一个连续区间内dp问题，对于这类问题，我们首先想到的就应该是从后往前，dp[i]表示以nums[i]作为连续区间的结尾的
     * 最大值。但我们仔细分析，发现其实每一各阶段不仅需要保存最大值这个状态，还需要保存最小值这个状态，因为很有可能前一
     * 阶段的最小负数*当前位置的负数，就成为了最大值，所以我们可以知道状态有两个：pos——代表最大值，neg——代表最小值
     * 状态转移方程：pos[i] = Max(pos[i-1]*nums[i],neg[i-1]*nums[i],nums[i])
     * neg[i] = Min(pos[i-1]*nums[i],neg[i-1]*nums[i],nums[i])
     * 然后在每个阶段不断更新ans，ans = Max(pos[i],ans)
     *
     * @param nums
     * @return
     */
    public static int maxProduct(int[] nums) {
        int[] pos = new int[nums.length];
        int[] neg = new int[nums.length];
        int ans = nums[0];
        pos[0] = nums[0];
        neg[0] = nums[0];
        for (int i = 1; i < nums.length; i++) {
            int tmp1 = neg[i - 1] * nums[i];
            int tmp2 = pos[i - 1] * nums[i];
            neg[i] = Math.min(tmp1, Math.min(tmp2, nums[i]));
            pos[i] = Math.max(tmp1, Math.max(nums[i], tmp2));
            ans = Math.max(ans, pos[i]);
        }
        return ans;
    }

    public static void main(String[] args) {
        int[] nums = {-2, 3, -4, -1, 5, 3, 0};
        System.out.println(maxProduct(nums));
    }
}
