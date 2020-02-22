package greedy;

/**
 * Created by Xursan on 2020/2/22.
 */
public class CuttingRope {
    /**
     * 面试题14- I. 剪绳子
     * dp——背包
     * 视容量为n，各item容量为1到n-1的完全背包。目的就是找出装满背包情况下各物品容量乘积的最大值。
     *
     * @param n
     * @return
     */
    public static int cuttingRope(int n) {
        int[] dp = new int[n + 1];
        dp[0] = 1;
        for (int i = 1; i < n; i++) {
            for (int j = i; j <= n; j++) {
                dp[j] = Math.max(dp[j], dp[j - i] * i);
            }
        }
        return dp[n];
    }

    /**
     * 贪心
     * 尽可能分成3，其次分成2.
     * 所以目的就在于找到最多可以分多少个3，然后多少个2。即 n = 3a + b
     * 特殊情况，4要分为2个2更大:
     * 所以当b==1时，其结果应该等于 3^(a-1)*4
     * b==0时，即代表全为3，故结果等于3^a
     * b==2时，结果即为3^a*2
     *
     * @param n
     * @return
     */
    public int cuttingRope1(int n) {
        if (n <= 3) return n - 1;
        int a = n / 3, b = n % 3;
        if (b == 0) return (int) Math.pow(3, a);
        if (b == 1) return (int) Math.pow(3, a - 1) * 4;
        return (int) Math.pow(3, a) * 2;
    }


    public static void main(String[] args) {
        System.out.println(cuttingRope(11));
    }
}
