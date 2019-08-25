package dp;

/**
 * Created by Xursan on 2019/8/25.
 * Leetcode 62
 * 一个机器人位于一个 m x n 网格的左上角 （起始点在下图中标记为“Start” ）。机器人每次只能向下或者向右移动一步。
 * 机器人试图达到网格的右下角（在下图中标记为“Finish”）。问总共有多少条不同的路径？
 * 输入: m = 3, n = 2
 * 输出: 3
 * 解释:
 * 从左上角开始，总共有 3 条路径可以到达右下角。
 * 1. 向右 -> 向右 -> 向下
 * 2. 向右 -> 向下 -> 向右
 * 3. 向下 -> 向右 -> 向右
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/unique-paths
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class UniquePaths {
    /**
     * 假设矩阵为nums，仔细分析发现，到nums[m][n]处的路径个数其实是等于起始点到nums[m-1][n]和nums[m][n-1]的路径和。
     * 所以很显然这个问题依赖于前一阶段的小问题，是一个dp问题。画出矩阵网格，我们可以很轻松地列出状态转移方程：
     * dp[m][n] = dp[m-1][n]+dp[m][n-1]，接下来就是找出初始阶段的状态，结合图发现初始状态其实就是dp[m][1]=1和dp[1][n]=1。
     * 然后通过自底向上的地推形式进行编程即可。
     *
     * @param m
     * @param n
     * @return
     */
    public static int uniquePaths(int m, int n) {
        if (m == 0 || n == 0) {
            return 0;
        }
        int[][] dp = new int[m][n];
        for (int i = 0; i < m; i++) {
            dp[i][0] = 1;
        }
        for (int i = 0; i < n; i++) {
            dp[0][i] = 1;
        }
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
            }
        }
        return dp[m - 1][n - 1];
    }

    public static void main(String[] args) {
        int m = 5;
        int n = 3;
        System.out.println(uniquePaths(m, n));
    }
}
