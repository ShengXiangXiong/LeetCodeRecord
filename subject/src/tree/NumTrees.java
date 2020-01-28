package tree;

/**
 * Created by Xursan on 2020/1/23.
 * 96. 不同的二叉搜索树
 * 给定一个整数 n，求以 1 ... n 为节点组成的二叉搜索树有多少种？
 */
public class NumTrees {
    public static int numTrees(int n) {
        int[] f = new int[n + 1];
        f[0] = 1;
        f[1] = 1;
        for (int i = 2; i <= n; i++) {
            int tmp = i / 2;
            int cnt = i - 1;
            while (cnt > tmp) {
                f[i] += 2 * f[cnt] * f[i - 1 - cnt];
                cnt--;
            }
            if ((i & 1) == 0) {
                f[i] += 2 * f[tmp] * f[i - tmp - 1];
            } else {
                f[i] += f[tmp] * f[tmp];
            }
        }
        return f[n];
    }

    public static int numTrees1(int n) {
        int[] dp = new int[n + 1];
        dp[0] = 1;
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= i; j++) {
                dp[i] += dp[j - 1] * dp[i - j];
            }
        }
        return dp[n];
    }

    public static void main(String[] args) {
        int n = 3;
        System.out.println(numTrees1(n));
        System.out.println(numTrees1(n));
    }
}
