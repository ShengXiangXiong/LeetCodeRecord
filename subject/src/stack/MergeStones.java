package stack;

/**
 * Created by Xursan on 2020/2/22.
 * 1000. 合并石头的最低成本
 * 有 N 堆石头排成一排，第 i 堆中有 stones[i] 块石头。
 * 每次移动（move）需要将连续的 K 堆石头合并为一堆，而这个移动的成本为这 K 堆石头的总数。
 * 找出把所有石头合并成一堆的最低成本。如果不可能，返回 -1 。
 * <p>
 * 输入：stones = [3,2,4,1], K = 2
 * 输出：20
 * 解释：
 * 从 [3, 2, 4, 1] 开始。
 * 合并 [3, 2]，成本为 5，剩下 [5, 4, 1]。
 * 合并 [4, 1]，成本为 5，剩下 [5, 5]。
 * 合并 [5, 5]，成本为 10，剩下 [10]。
 * 总成本 20，这是可能的最小值。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/minimum-cost-to-merge-stones
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class MergeStones {
    /**
     * 区间DP
     * 这道题类似于戳气球，只是戳气球的k固定为3，且每次只会缩减一个，最终状态只会剩一个，但无论如何都离不开区间DP的框架。
     * 枚举分割点——（假设第mid堆处分割）
     * <p>
     * 模拟问题动作——（合并 区间（i--mid） 与 区间 （mid+1--j）所形成的的k堆成为1堆的最优解，由于是k堆合并为一堆，所以
     * 还需要枚举k，即区间（i--mid）合并为1堆，区间 （mid+1--j）合并为K-1堆的最优解）
     * <p>
     * 假设最终状态最优解——（最后在第mid堆处分割为左右两区间，左右两区间分别合并为1堆、K-1堆时合并成1堆的最优解）
     * <p>
     * 写状态转移方程——（从假设最优解的形式就可以看出，有3个变量，所以需要3维DP，即dp[i][j][k],代表区间ij内合并为k堆的
     * 最优解，所以有：
     * dp[i][j][1] = min(dp[i][mid][m]+dp[mid+1][j][K-m]+sum(i...j))
     * 即
     * dp[i][j][1] = dp[i][j][K] + sum[i][j]
     * <p>
     * 这里值得注意的一点是sum(i...j)，虽然合并 区间（i--mid） 与 区间 （mid+1--j）所形成的的k堆为1堆时，无法
     * 知道每堆的具体数值，但是我们知道他们一定是由其内部数字之和组成的，所以（i...j）无论怎么合并，其最终合并
     * 的结果都是sum(i...j).
     * <p>
     * dp[i][j][k] = min(dp[i][j][k],dp[i][mid][m]+dp[mid+1][j][k-m])
     * ）
     *
     * @param stones
     * @param K
     * @return
     */
    public static int mergeStones(int[] stones, int K) {
        int len = stones.length;
        if ((len - 1) % (K - 1) != 0) {
            return -1;
        }

        int i, j, k, l, t;

        int[] prefixSum = new int[len + 1];
        for (i = 1; i <= len; i++) {
            prefixSum[i] = prefixSum[i - 1] + stones[i - 1];
        }

        int max = 99999999;
        int[][][] dp = new int[len + 1][len + 1][K + 1];
        for (i = 1; i <= len; i++) {
            for (j = 1; j <= len; j++) {
                for (k = 1; k <= K; k++) {
                    dp[i][j][k] = max;
                }
            }
        }

        for (i = 1; i <= len; i++) {
            dp[i][i][1] = 0;
        }

        for (l = 2; l <= len; l++) {
            for (i = 1; i <= len - l + 1; i++) {
                j = i + l - 1;
                for (k = 2; k <= K; k++) {
                    for (t = i; t < j; t++) {
                        dp[i][j][k] = Math.min(dp[i][j][k], dp[i][t][k - 1] + dp[t + 1][j][1]);
                    }
                }

                dp[i][j][1] = dp[i][j][K] + prefixSum[j] - prefixSum[i - 1];
            }
        }

        return dp[1][len][1];
    }

    public static void main(String[] args) {
        int[] stones = {3, 1, 5, 2, 3, 4};
        int K = 3;
        System.out.println(mergeStones(stones, K));
    }
}
