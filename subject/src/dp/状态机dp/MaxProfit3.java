package dp.状态机dp;


/**
 * Created by Xursan on 2020/2/29.
 * 123. 买卖股票的最佳时机 III
 */
public class MaxProfit3 {
    public int maxProfit(int[] prices) {
        if (prices.length < 2) {
            return 0;
        }
        int dp_i11 = -prices[0];
        int dp_i10 = 0;
        int dp_i21 = -prices[0];
        int dp_i20 = 0;

        for (int i = 1; i < prices.length; i++) {

            dp_i20 = Math.max(Math.max(dp_i20, dp_i10), dp_i21 + prices[i]);
            dp_i21 = Math.max(dp_i20 - prices[i], dp_i21);
        }
        return dp_i20;
    }
}
