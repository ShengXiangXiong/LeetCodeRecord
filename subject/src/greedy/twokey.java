package greedy;

/**
 * 贪心
 *
 * 最初在一个记事本上只有一个字符 'A'。你每次可以对这个记事本进行两种操作：
 *
 * Copy All (复制全部) : 你可以复制这个记事本中的所有字符(部分的复制是不允许的)。
 * Paste (粘贴) : 你可以粘贴你上一次复制的字符。
 * 给定一个数字 n 。你需要使用最少的操作次数，在记事本中打印出恰好 n 个 'A'。输出能够打印出 n 个 'A' 的最少操作次数。
 *
 * 示例 1:
 *
 * 输入: 3
 * 输出: 3
 * 解释:
 * 最初, 我们只有一个字符 'A'。
 * 第 1 步, 我们使用 Copy All 操作。
 * 第 2 步, 我们使用 Paste 操作来获得 'AA'。
 * 第 3 步, 我们使用 Paste 操作来获得 'AAA'。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/2-keys-keyboard
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class twokey {
    public static int minSteps(int n) {

        //better solution
        if(n == 1){
            return 0;
        }
        for(int i = 2; i <= Math.sqrt(n); i++){
            if(n % i == 0){
                return i + minSteps(n / i);
            }
        }
        return n;



/*        int[] dp = new int[n+1];
        int tmp = 1000000;
        dp[1]=0;
        dp[2]=2;
        for (int i = 3; i < n+1; i++) {
            dp[i]=tmp;
            for(int j = 1;j <= i/2;j++){
                if(i%j==0){
                    dp[i] = Math.min(dp[i],dp[j]+i/j);
                }
            }
        }
        return dp[n];*/
    }

    public static void main(String[] args) {
        int n = 1000;
        System.out.println(minSteps(n));
    }
}
