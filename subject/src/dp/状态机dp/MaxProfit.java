package dp.状态机dp;

/**
 * Created By ShengXiang.Xiong on 2019/11/19
 * 给定一个整数数组，其中第 i 个元素代表了第 i 天的股票价格 。​
 * 设计一个算法计算出最大利润。在满足以下约束条件下，你可以尽可能地完成更多的交易（多次买卖一支股票）:
 * 你不能同时参与多笔交易（你必须在再次购买前出售掉之前的股票）。
 * 卖出股票后，你无法在第二天买入股票 (即冷冻期为 1 天)。
 * <p>
 * 输入: [1,2,3,0,2]
 * 输出: 3
 * 解释: 对应的交易状态为: [买入, 卖出, 冷冻期, 买入, 卖出]
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/best-time-to-buy-and-sell-stock-with-cooldown
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class MaxProfit {
    /**
     * 状态机DP+跳跃DP
     * 每一天两种状态，未持有0、持有1，然后则有dp[i][0]：第i天未持有股票情况下的最大值，dp[i][1]:第i天持有股票下的最大值
     * 然后通过分析每天的操作（buy-买,sell-卖,fix-不操作）对状态的影响，画状态转移图即可。
     *
     * 需要考虑的一点是，冷冻期该如何体现，dp[i][0]显然不会受冷冻期影响    dp[i][0] = max(dp[i-1][0],dp[i-1][1]+nums[i])
     * 但是dp[i][1]会受冷冻期影响，不考虑冷冻期则为       dp[i][1] = max(dp[i-1][1],dp[i-1][0]-nums[i])
     * 但如果dp[i-1][0]是由dp[i-2][1]卖出造成的，则dp[i][0]无法使用dp[i-1][0]，若dp[i-1][0]不是由卖出造成的则  dp[i-1][0] = dp[i-2][0]
     * 显然我们只能考虑dp[i-1][0]不是i-2阶段卖出造成的情况，于是dp[i][1] = max(dp[i-1][1],dp[i-2][0])。
     *
     * 另外需要注意初始值和买卖操作对应的加减情况，买即 -prices[i],卖即 +prices[i],初始未持有一定>=0
     *
     * @param prices
     * @return
     */
    public static int maxProfit1(int[] prices) {
        if (prices.length < 2) {
            return 0;
        }
        int lastSell = 0;
        int last2sell = 0;
        int lastFix = -prices[0];
        for (int i = 1; i < prices.length; i++) {
            int tmp = lastSell;
            //卖或继续不持有
            lastSell = Math.max(lastSell, lastFix + prices[i]);
            //买或继续持有
            lastFix = Math.max(lastFix, last2sell - prices[i]);
            last2sell = tmp;
        }
        return lastSell;
    }

    /**
     * 区间Dp（O（n^3）——超时）
     *
     * @param prices
     * @return
     */
    public static int maxProfit(int[] prices) {
        int n = prices.length;
        if (n == 0) {
            return 0;
        }
        int[][] dp = new int[n][n];
        int res = 0;
        for (int i = n - 1; i >= 0; i--) {
            for (int j = i + 1; j <= n - 1; j++) {
                dp[i][j] = prices[j] - prices[i];
                for (int k = i; k <= j; k++) {
                    if (k + 2 < j) {
                        dp[i][j] = Math.max(dp[i][j], dp[i][k] + dp[k + 2][j]);
                    } else {
                        dp[i][j] = Math.max(dp[i][j], Math.max(dp[k][j], dp[i][k]));
                    }
                }
                res = Math.max(res, dp[i][j]);
            }
        }
        return res;
    }

    public static void main(String[] args) {
        int[] nums = {1, 2, 3, 0, 2};
        System.out.println(maxProfit1(nums));
    }
}
