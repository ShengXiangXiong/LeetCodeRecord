package 回文类;

/**
 * Created by Xursan on 2020/1/30.
 * 132. 分割回文串 II
 * 给定一个字符串 s，将 s 分割成一些子串，使每个子串都是回文串。
 * 返回符合要求的最少分割次数。
 * 示例:
 * <p>
 * 输入: "aab"
 * 输出: 1
 * 解释: 进行一次分割就可将 s 分割成 ["aa","b"] 这样两个回文子串。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/palindrome-partitioning-ii
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class MinCut {
    /**
     * 区间dp，dp[i][j]代表i...j范围内字符串的最小回文分割次数，对于dp[i][j]，需要枚举任意分割点k，然后求得最佳分割位置
     * 即 dp[i][j] = min(dp[i][j],dp[i][k]+dp[k+1][j])，注意特殊情况，当i...j整个为回文串时需特殊考虑
     * 时间复杂度——O(n^3)
     *
     * @param s
     * @return
     */
    public static int minCut(String s) {
        if (s.length() == 0) {
            return 0;
        }
        char[] cs = s.toCharArray();
        int n = cs.length;
        int[][] dp = new int[n][n];
        for (int i = n - 2; i >= 0; i--) {
            for (int j = i + 1; j < n; j++) {
                if (cs[i] == cs[j] && dp[i + 1][j - 1] == 0) {
                    dp[i][j] = 0;
                } else {
                    if (i + 1 == j) {
                        dp[i][j] = 1;
                    } else {
                        dp[i][j] = Integer.MAX_VALUE;
                        for (int k = i; k < j; k++) {
                            dp[i][j] = Math.min(dp[i][j], dp[i][k] + dp[k + 1][j] + 1);
                        }
                    }
                }
            }
        }
        return dp[0][n - 1];
    }

    /**
     * 中心扩展+线性dp，dp[i]代表0...i范围内的字符串的最小回文分割次数，枚举所有的中心点位置（分奇偶），求得每一个中心
     * 点的左右扩展位置start和end，因为start...end为回文，所以对于每一个end必然有：dp[end] = dp[start-1]+1
     * 枚举中心点需要O(n)，扩展需要O(n)，故时间复杂度为O(n^2)
     *
     * @param s
     * @return
     */
    public static int minCut2(String s) {
        if (s.length() == 0) {
            return 0;
        }
        int n = s.length();
        char[] cs = s.toCharArray();
        int[] dp = new int[n];
        for (int i = 1; i < n; i++) {
            dp[i] = n - 1;
        }
        for (int i = 0; i < n; i++) {
            countSegment(cs, i, i, dp);
            countSegment(cs, i, i + 1, dp);
        }
        return dp[n - 1];
    }

    public static void countSegment(char[] s, int start, int end, int[] dp) {
        while (start >= 0 && end < s.length && s[start] == s[end]) {
            if (start == 0) {
                dp[end] = 0;
            } else {
                dp[end] = Math.min(dp[start - 1] + 1, dp[end]);
            }
            start--;
            end++;
        }
    }

    public static void main(String[] args) {
        String s = "abcddehcgiascbcbabcbabababacbebebedududqiqiqi";
        System.out.println(minCut(s));
        System.out.println(minCut2(s));
    }
}
