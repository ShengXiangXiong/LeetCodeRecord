package dp.区间dp;

/**
 * Created By ShengXiang.Xiong on 2019/12/14
 * 数字存在数组 nums 中。
 * 现在要求你戳破所有的气球。每当你戳破一个气球 i 时，你可以获得 nums[left] * nums[i] * nums[right] 个硬币。 
 * 这里的 left 和 right 代表和 i 相邻的两个气球的序号。注意当你戳破了气球 i 后，气球 left 和气球 right 就变成了相邻的气球。
 * 求所能获得硬币的最大数量。
 * <p>
 * 说明:
 * 你可以假设 nums[-1] = nums[n] = 1，但注意它们不是真实存在的所以并不能被戳破。
 * 0 ≤ n ≤ 500, 0 ≤ nums[i] ≤ 100
 * <p>
 * 输入: [3,1,5,8]
 * 输出: 167
 * 解释: nums = [3,1,5,8] --> [3,5,8] -->   [3,8]   -->  [8]  --> []
 *      coins =  3*1*5      +  3*5*8    +  1*3*8      + 1*8*1   = 167
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/burst-balloons
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class MaxCoins {
    /**
     * 这道题是一道典型的区间类dp问题，自上而下，我们知道dp最后阶段所处的位置并不一定在数组的末尾，显然不可能是线性dp。
     * 所以自然而然的想到区间dp，而对于区间dp，其基本步骤就是自上而下：
     * 枚举分割点（假设第k个气球为分割点）
     * 模拟问题动作（戳第k个气球）
     * 假设最终状态最优解（当前区间内 最后 戳第k个气球时的解为最优解，自然而然要求其左边的区间和其右边的区间都需要最优）
     * 找出状态转移方程（理解了上述原理，自然就可以写出状态转移方程）
     * 而这道题最为关键的一点是，其区间状态dp[i][j]的定义，i，j定义不同，会造成状态转移方程也不同。
     * 以及在nums前后添加[1],哨兵（nums前后添加[1]）很关键。
     * <p>
     * dp[i][j]定义1：
     * dp[i][j] 表示戳破 [i+1...j-1] 号气球的最大收益。k表示在i+1,j-1这个区间内最后戳破的气球，
     * 状态转移方程dp[i][j]=max(dp[i][j],dp[i][k]+dp[k][j]+nums[i]*nums[k]*nums[j])
     * 这个dp[i][j]含义是不包含i,j的i-j之间的数组获得的最大乘积。
     * <p>
     * dp[i][j]定义2：
     * dp[i][j] 表示戳破 [i...j] 号气球的最大收益。k表示在i,j这个区间内最后戳破的气球.
     * dp[i][j]=max(dp[i][j],dp[i][k-1]+dp[k+1][j]+nums[i-1]*nums[k]*nums[j+1])
     * 注意这种定义方式，在自上而下考虑，即从最后阶段往前推时，容易被最后阶段的特殊情况影响，
     * 所以最好直接考虑一般性递推情况
     * <p>
     * @param nums
     * @return
     */
    public static int maxCoins(int[] nums) {
        int n = nums.length;
        int[][] dp = new int[n + 2][n + 2];
        int[] newNums = new int[n + 2];
        newNums[0] = newNums[n + 1] = 1;
        System.arraycopy(nums, 0, newNums, 1, n);
        for (int i = n + 1; i >= 0; i--) {
            for (int j = i + 2; j < n + 2; j++) {
                for (int k = i + 1; k < j; k++) {
                    dp[i][j] = Math.max(dp[i][j], dp[i][k] + dp[k][j] + newNums[i] * newNums[k] * newNums[j]);
                }
            }
        }
        return dp[0][n + 1];
    }

    public static void main(String[] args) {
        int[] nums = {3, 1, 5, 8};
        System.out.println(maxCoins(nums));
    }
}
