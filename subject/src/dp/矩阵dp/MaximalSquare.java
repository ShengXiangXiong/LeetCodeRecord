package dp.矩阵dp;

import java.util.Stack;

/**
 * Created By ShengXiang.Xiong on 2019/11/6
 * 在一个由 0 和 1 组成的二维矩阵内，找到只包含 1 的最大正方形，并返回其面积。
 * 输入:
 * 1 0 1 0 0
 * 1 0 1 1 1
 * 1 1 1 1 1
 * 1 0 0 1 0
 * 输出: 4
 */
public class MaximalSquare {
    /**
     * 方法一：使用单调栈
     * 基本上和前面求最大矩形那道题一模一样，只是加了一个长宽要相等的限制，计算时，取长宽的最小值的平方即是其面积
     *
     * @param matrix
     * @return
     */
    public int maximalSquare(char[][] matrix) {
        if (matrix.length == 0 || matrix[0].length == 0) {
            return 0;
        }
        int[][] polars = new int[matrix[0].length][matrix.length];
        int ans = 0;
        for (int i = 0; i < matrix.length; i++) {
            int j = 0;
            int cnt = 0;
            while (j < matrix[i].length) {
                if (matrix[i][j] == '0') {
                    cnt = 0;
                    polars[j][i] = 0;
                } else {
                    polars[j][i] = ++cnt;
                }
                j++;
            }
        }
        for (int i = 0; i < polars.length; i++) {
            ans = Math.max(ans, incStack(polars[i]));
        }
        return ans;
    }

    /**
     * 单调栈，前面做的时候总是通过判空的方式进行计算，实际上通过设置一个-1位置的哨兵来做为结束条件是更合理的
     *
     * @param nums
     * @return
     */
    public int incStack(int[] nums) {
        if (nums.length == 0) {
            return 0;
        }
        int ans = 0;
        Stack<Integer> inc = new Stack<>();
        inc.add(-1);
        for (int i = 0; i < nums.length; i++) {
            while (inc.peek() != -1 && nums[i] < nums[inc.peek()]) {
                int top = inc.pop();
                if (i - inc.peek() - 1 >= nums[top]) {
                    ans = Math.max(ans, nums[top] * nums[top]);
                }
            }
            inc.add(i);
        }
        while (inc.peek() != -1) {
            int tmp = inc.pop();
            if (nums.length - inc.peek() - 1 >= nums[tmp]) {
                ans = Math.max(ans, nums[tmp] * nums[tmp]);
            }
        }
        return ans;
    }


    /**
     * 方法2：动态规划法
     * 对于这种网格矩阵类型求最优解，一般都可以用动态规划来做，而这种矩阵类的动态规划，基本上都是以网格的坐标点作为状态，
     * 所以接下来就是如何将网格点抽象成为问题的数学模型表达（这里即为正方形），那么目标就清晰了，我们可以将右下角作为
     * 正方形范围的标志，dp[i][j]即表示在位置i，j处的最大正方形边长。那么我们从上至下的思考，对于整个网格的右下角m、n
     * 如果其要为正方形，首先得满足grid[m][n]=1，其次由于是dp自然想到它怎么从它的子问题可以得到他，而这类矩阵问题，一般
     * 状态都是具有邻接性的，所以只需要从它的邻接网格的位置思考即可。这里如果画出一个矩阵图，很容易看出来，必须要保证其
     * 左、上、左上这三个邻接位置均为正方形才能使此位置为正方形，否则其边长只能为这3个位置的最小正方形的边长+1，为什么
     * 一定满足三者最小边长+1呢，你只需想一下，从该位置向这三个方向发出射线，满足最小就意味着其余两个也一定满足。
     * 于是得到状态转移方程：
     * if grid[i][j]==1:
     * dp(i, j)=min(dp(i−1, j), dp(i−1, j−1), dp(i, j−1))+1
     * else{
     * dp(i, j) = 0
     * }
     *
     * @param matrix
     * @return
     */
    public int maximalSquare2(char[][] matrix) {
        int rows = matrix.length, cols = rows > 0 ? matrix[0].length : 0;
        int[][] dp = new int[rows][cols];
        int maxb = 0;
        boolean flag = false;
        for (int i = 0; i < rows; i++) {
            dp[i][0] = matrix[i][0] - '0';
            if (dp[i][0] > 0) {
                flag = true;
            }
        }
        for (int i = 0; i < cols; i++) {
            dp[0][i] = matrix[0][i] - '0';
            if (dp[0][i] > 0) {
                flag = true;
            }
        }
        if (flag) {
            maxb = 1;
        }
        for (int i = 1; i < matrix.length; i++) {
            for (int j = 1; j < matrix[0].length; j++) {
                if (matrix[i][j] == '1') {
                    dp[i][j] = Math.min(Math.min(dp[i - 1][j], dp[i][j - 1]), dp[i - 1][j - 1]) + 1;
                    maxb = Math.max(maxb, dp[i][j]);
                } else {
                    dp[i][j] = 0;
                }
            }
        }
        return maxb * maxb;

        //这里并不同于上面的思路，这里采用了递推的方式，且将空间优化成了一维数组
//        int rows = matrix.length, cols = rows > 0 ? matrix[0].length : 0;
//        int[] dp = new int[cols + 1];
//        int maxsqlen = 0, prev = 0;
//        for (int i = 1; i <= rows; i++) {
//            for (int j = 1; j <= cols; j++) {
//                int temp = dp[j];
//                if (matrix[i - 1][j - 1] == '1') {
//                    dp[j] = Math.min(Math.min(dp[j - 1], prev), dp[j]) + 1;
//                    maxsqlen = Math.max(maxsqlen, dp[j]);
//                } else {
//                    dp[j] = 0;
//                }
//                prev = temp;
//            }
//        }
//        return maxsqlen * maxsqlen;
    }


    public static void main(String[] args) {
        String s = "[[\"1\"]]";
        char[][] cs = utils.matrixTrans.str2CharArray(s);
        MaximalSquare m = new MaximalSquare();
        System.out.println(m.maximalSquare2(cs));
    }
}
