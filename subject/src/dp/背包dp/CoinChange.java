package dp.背包dp;

/**
 * Created by Xursan on 2019/12/26.
 * 给定不同面额的硬币 coins 和一个总金额 amount。编写一个函数来计算可以凑成总金额所需的最少的硬币个数。
 * 如果没有任何一种硬币组合能组成总金额，返回 -1。
 * 输入: coins = [1, 2, 5], amount = 11
 * 输出: 3
 * 解释: 11 = 5 + 5 + 1
 * <p>
 * 输入: coins = [2], amount = 3
 * 输出: -1
 * 说明:
 * 你可以认为每种硬币的数量是无限的。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/coin-change
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class CoinChange {
    /**
     * 拿到题目就想到是装箱类完全背包。那自然而然的就是用装箱类完全背包的模板即可。
     *
     * @param coins
     * @param amount
     * @return
     */
    public static int coinChange(int[] coins, int amount) {
        if (amount < 0 || coins.length == 0) {
            return -1;
        }
        int maxValue = 100000000;
        int res = maxValue;
        int[] dp = new int[amount + 1];
        for (int i = 1; i <= amount; i++) {
            dp[i] = maxValue;
        }
        for (int coin : coins) {
            for (int i = 1; i <= amount; i++) {
                if (i - coin >= 0) {
                    dp[i] = Math.min(dp[i - coin] + 1, dp[i]);
                }
            }
            res = Math.min(res, dp[amount]);
        }
        return res == maxValue ? -1 : res;
    }

    public static void main(String[] args) {
        int[] coins = {1};
        int amount = 0;
        System.out.println(coinChange(coins, amount));
    }
}
