package dp.jump_interval;

/**
 * Created By ShengXiang.Xiong on 2019/10/31
 * 你是一个专业的小偷，计划偷窃沿街的房屋。每间房内都藏有一定的现金，影响你偷窃的唯一制约因素就是相邻的房屋装有相互连通
 * 的防盗系统，如果两间相邻的房屋在同一晚上被小偷闯入，系统会自动报警。
 * <p>
 * 给定一个代表每个房屋存放金额的非负整数数组，计算你在不触动警报装置的情况下，能够偷窃到的最高金额。
 * <p>
 * 输入: [1,2,3,1]
 * 输出: 4
 * 解释: 偷窃 1 号房屋 (金额 = 1) ，然后偷窃 3 号房屋 (金额 = 3)。
 *      偷窃到的最高金额 = 1 + 3 = 4 。
 * <p>
 * 输入: [2,7,9,3,1]
 * 输出: 12
 * 解释: 偷窃 1 号房屋 (金额 = 2), 偷窃 3 号房屋 (金额 = 9)，接着偷窃 5 号房屋 (金额 = 1)。
 *      偷窃到的最高金额 = 2 + 9 + 1 = 12 。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/house-robber
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class Rob {
    /**
     * 很明显这是一个跳跃区间类DP问题，而这类问题，一般可以从以下几方面思考：
     * 1.是否可类比于 斐波那契数列
     * 2. 是否可按奇偶分开dp
     * 而这道题明显可看做是第一种情况，是一个单状态多阶段dp问题，起初对于位置i来说，其最大值只可能来自于Max(dp[i-2]....dp[0]),
     * 但是我们进一步思考，其实我们并不需要遍历完,因为dp[i-2]一定已经是包括了dp[i-4...2*n]的最优解，
     * 所以实际上就可以得到状态转移方程：dp[i] = Max(dp[i-2],dp[i-3])+nums[i]
     *
     * @param nums
     * @return
     */
    public static int rob(int[] nums) {
        if (nums.length == 0) {
            return 0;
        }
        if (nums.length == 1) {
            return nums[0];
        }
        if (nums.length == 2) {
            return Math.max(nums[0], nums[1]);
        }
        nums[2] += nums[0];
        for (int i = 3; i < nums.length; i++) {
            nums[i] = Math.max(nums[i - 2], nums[i - 3]) + nums[i];
        }
        return Math.max(nums[nums.length - 1], nums[nums.length - 2]);
    }

    public static void main(String[] args) {
        int[] nums = {1, 2, 3, 1};
        System.out.println(rob(nums));
    }
}
