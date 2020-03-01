package dp.字符串dp;

/**
 * Created by Xursan on 2020/3/1.
 * 1143. 最长公共子序列
 * 给定两个字符串 text1 和 text2，返回这两个字符串的最长公共子序列。
 * <p>
 * 一个字符串的 子序列 是指这样一个新的字符串：它是由原字符串在不改变字符的相对顺序的情况下删除某些字符
 * （也可以不删除任何字符）后组成的新字符串。
 * 例如，"ace" 是 "abcde" 的子序列，但 "aec" 不是 "abcde" 的子序列。两个字符串的「公共子序列」是这两个字符串所共同拥有的子序列。
 * <p>
 * 若这两个字符串没有公共子序列，则返回 0。
 * <p>
 * 输入：text1 = "abcde", text2 = "ace"
 * 输出：3
 * 解释：最长公共子序列是 "ace"，它的长度为 3。
 */
public class LongestCommonSubsequence {
    /**
     * 明显字符串DP，于是直接按字符串DP的套路来即可，dp[i][j]往前推
     *
     * @param text1
     * @param text2
     * @return
     */
    public static int longestCommonSubsequence(String text1, String text2) {
        int[][] dp = new int[text1.length()][text2.length()];
        dp[0][0] = text1.charAt(0) == text2.charAt(0) ? 1 : 0;
        for (int i = 1; i < text2.length(); i++) {
            if (dp[0][i - 1] == 1 || text1.charAt(0) == text2.charAt(i)) {
                dp[0][i] = 1;
            }
        }
        for (int i = 1; i < text1.length(); i++) {
            if (dp[i - 1][0] == 1 || text2.charAt(0) == text1.charAt(i)) {
                dp[i][0] = 1;
            }
        }
        for (int i = 1; i < text1.length(); i++) {
            for (int j = 1; j < text2.length(); j++) {
                dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                if (text1.charAt(i) == text2.charAt(j)) {
                    dp[i][j] = Math.max(dp[i][j], dp[i - 1][j - 1] + 1);
                }
            }
        }
        return dp[text1.length() - 1][text2.length() - 1];
    }

    /**
     * 上面我们发现dp是按照逐层推来的，所以只需要用一个一维滚动数组记录上一层每一个j对应的值即可，比如0-1背包也是如此。
     * 但是由于dp[i-1][j-1]会被dp[j-1]给更新，所以需要额外用一个northwest记录其左上角的值
     *
     * @param text1
     * @param text2
     * @return
     */
    public int longestCommonSubsequence2(String text1, String text2) {
        char[] arrI = text1.toCharArray(), arrJ = text2.toCharArray();
        int lenI = arrI.length, lenJ = arrJ.length;
        if (lenI == 0 || lenJ == 0) return 0;

        int[] dp = new int[lenJ + 1];
        int northwest;
        for (int i = 1; i <= lenI; i++) {
            northwest = 0;
            for (int j = 1; j <= lenJ; j++) {
                int nextNorthwest = dp[j];
                if (arrI[i - 1] == arrJ[j - 1]) dp[j] = northwest + 1;
                else if (dp[j] < dp[j - 1]) dp[j] = dp[j - 1];
                northwest = nextNorthwest;
            }
        }
        return dp[lenJ];
    }

    public static void main(String[] args) {
        String text1 = "abcde";
        String text2 = "ace";
        System.out.println(longestCommonSubsequence(text1, text2));
    }
}
