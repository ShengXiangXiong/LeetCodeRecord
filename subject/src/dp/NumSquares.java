package dp;

/**
 * Created By ShengXiang.Xiong on 2019/11/17
 * 给定正整数 n，找到若干个完全平方数（比如 1, 4, 9, 16, ...）使得它们的和等于 n。你需要让组成和的完全平方数的个数最少。
 * <p>
 * 输入: n = 12
 * 输出: 3
 * 解释: 12 = 4 + 4 + 4.
 * <p>
 * 输入: n = 13
 * 输出: 2
 * 解释: 13 = 4 + 9.
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/perfect-squares
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class NumSquares {
    /**
     * 这道题很容易想到要用dp来做。如果不用dp，则需要枚举各种组合，而各种组合间会有很多相同的子问题，所以自然而然想到
     * 采取记忆数组。例如，对于n，dp[n] = min{dp[i],dp[n-i]},i需要从1遍历到n/2。于是我们可以自底向上构建递推过程。
     * 但实际上，我们还可以进一步优化，并不需要按1递增遍历到n/2。题目明确了，组成n的必然是完全平方数，那么按完全平方数
     * 递增即可，即dp[n] = min{dp[n],dp[n-i*i]+dp[i*i]},i<sqrt(n)  由于 dp[i*i]=1，
     * 于是dp[i] = min(dp[i], dp[i - j * j] + 1)
     *
     * @param n
     * @return
     */
    public static int numSquares(int n) {
        int[] mem = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            mem[i] = i;
            double tmp = Math.floor(Math.sqrt(i));
            for (int j = 1; j <= tmp; j++) {
                mem[i] = Math.min(mem[i], mem[i - j * j] + 1);
            }
        }
        return mem[n];
    }

    /**
     * 四平方定理： 任何一个正整数都可以表示成不超过四个整数的平方之和。
     * 推论：满足四数平方和定理的数n（四个整数的情况），必定满足 n=4^a(8b+7)
     *
     * @param n
     * @return
     */
    public static int numSquares1(int n) {
        //先根据上面提到的公式来缩小n
        while (n % 4 == 0)
            n /= 4;
        //如果满足公式 则返回4
        if (n % 8 == 7)
            return 4;
        int i = 0;
        //在判断缩小后的数是否可以由一个数的平方或者两个数平方的和组成
        while (i * i <= n) {
            int j = (int) (Math.sqrt(n - i * i));
            if (j * j + i * i == n) {
                if (i != 0 && j != 0) {
                    return 2;
                } else
                    return 1;

            }
            i++;
        }
        return 3;
    }

    public static void main(String[] args) {
        int n = 4;
        System.out.println(numSquares(n));
    }
}
