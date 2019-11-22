package dp;

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
     * sell[i]表示在第i天卖出时的最大收益
     * fix[i]表示在第i天不卖出的最大收益
     * buy[i][j]表示在i到j天当中的最低买点
     * <p>
     * 我们仔细分析，其实每一个阶段，就只有两个状态，卖sell与不卖fix,而对于fix不卖而言，还需要绑定一个最低买入点buy
     * <p>
     * 对于sell[i]而言，由于冷冻期的原因，sell[i]会受sell[i-3]的影响，以及fix
     * <p>
     * 转移方程：sell[i]只会受如下四方面的影响
     * sell[i-3]+nums[i]-buy[i-1][i-1]——第i-3天时卖出的收益，以及i-1天到i-1天买入的最低价，i天卖出时的收益和
     * sell[i-4]+nums[i]-buy[i-2][i-1]——第i-2天时卖出的收益，以及i-2天到i-1天买入的最低价，i天卖出时的收益和
     * sell[i-5]+nums[i]-buy[i-3][i-1]——第i-2天时卖出的收益，以及i-3天到i-1天买入的最低价，i天卖出时的收益和
     * 中间缺的一天即为冷冻期
     * nums[i]-buy[0][i-1]——前面一直都没有卖出操作，从第0天开始到i-1天的最低买入价，i天卖出
     * <p>
     * 为什么不需要考虑sell[0]到sell[i-6]，因为sell[i-6]是sell[i-3]的子问题，sell[i-3]已经是包括sell[i-6]在内的
     * 最优解了。
     * 只需要遍历一遍就可以知道，在每一天卖出时的收益情况，然后通过维护一个maxProfit取最大值即可。
     *
     * @param prices
     * @return
     */
    public static int maxProfit(int[] prices) {
        int n = prices.length;
        if (n == 0) {
            return 0;
        }
        int[] sell = new int[n];
        int[][] buy = new int[n][n];
        int maxProfit = 0;
        buy[0][0] = prices[0];
        sell[0] = 0;
        for (int i = 1; i < n; i++) {
            if (i - 2 >= 0) {
                buy[0][i - 1] = Math.min(buy[0][i - 2], prices[i - 1]);
            }
            buy[i][i] = prices[i];
            for (int k = 1; k <= 3; k++) {
                if (i - k >= 0) {
                    buy[i - k][i] = Math.min(buy[i - k + 1][i], prices[i - k]);
                }
            }
            sell[i] = prices[i] - buy[0][i - 1];
            for (int j = i - 3; j >= i - 5 && j >= 0; j--) {
                sell[i] = Math.max(sell[j] + prices[i] - buy[j + 2][i - 1], sell[i]);
            }
            maxProfit = Math.max(sell[i], maxProfit);
        }
        return maxProfit;
    }

    public static int maxProfit2(int[] prices) {
        int n = prices.length;
        if (n <= 1) {
            return 0;
        }
        int[] fix = new int[n];
        int[] buy = new int[n];
        int[] sell = new int[n];
        buy[0] = prices[0];
        sell[1] = prices[1] - buy[0] + fix[0];
        buy[1] = Math.min(prices[0], prices[1]);
        for (int i = 2; i < n; i++) {
            if (fix[i - 1] < sell[i - 1]) {
                buy[i] = Integer.MAX_VALUE;
                fix[i] = sell[i - 1];
            } else {
                fix[i] = fix[i - 1];
                buy[i] = Math.min(buy[i - 1], prices[i]);
            }
            sell[i] = Math.max(prices[i] - buy[i - 3] + fix[i - 3], prices[i] - prices[i - 1] + sell[i - 3]);
        }
        return Math.max(sell[n - 1], fix[n - 1]);
    }

    public static void main(String[] args) {
        int[] nums = {2, 6, 8, 7, 8, 7, 9, 4, 1, 2, 4, 5, 8};
        System.out.println(maxProfit2(nums));
    }
}
