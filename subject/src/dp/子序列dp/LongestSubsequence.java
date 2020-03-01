package dp.子序列dp;

/**
 * Created by Xursan on 2020/3/1.
 * 1218. 最长定差子序列
 * 输入：arr = [1,5,7,8,5,3,4,2,1], difference = -2
 */
public class LongestSubsequence {
    /**
     * 采用最长子序列的思想——O(n^2)
     *
     * @param arr
     * @param difference
     * @return
     */
    public static int longestSubsequence(int[] arr, int difference) {
        int[] dp = new int[arr.length];
        dp[0] = 1;
        int ans = 1;
        for (int i = 1; i < arr.length; i++) {
            dp[i] = 1;
            for (int j = 0; j < i; j++) {
                if (arr[j] + difference == arr[i]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
            ans = Math.max(ans, dp[i]);
        }
        return ans;
    }

    //类似最长递增子序列，由于涉及到+-变换，可以直接以数字nums[i]作为状态进行转移
    //dp算法，dp[nums[i]]表示  以num[i]结尾的最长子序列的长度
    public static int longestSubsequence2(int[] arr, int difference) {
        int[] dp = new int[40000];
        int max = 1;
        for (int i = 0; i < arr.length; ++i) {
            dp[20000 + arr[i]] = dp[arr[i] - difference + 20000] + 1;
            max = dp[20000 + arr[i]] > max ? dp[20000 + arr[i]] : max;
        }
        return max;
    }

    public static void main(String[] args) {
        int[] arr = {3, 4, -3, -2, -4};
        int difference = -5;
        System.out.println(longestSubsequence2(arr, difference));
    }
}
