package dp.knapsack;

/**
 * Created by Xursan on 2019/11/25.
 * 给定一个只包含正整数的非空数组。是否可以将这个数组分割成两个子集，使得两个子集的元素和相等。
 * 每个数组中的元素不会超过 100
 * 数组的大小不会超过 200
 * <p>
 * 输入: [1, 5, 11, 5]
 * 输出: true
 * 解释: 数组可以分割成 [1, 5, 5] 和 [11].
 * <p>
 * 输入: [1, 2, 3, 5]
 * 输出: false
 * 解释: 数组不能分割成两个元素和相等的子集.
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/partition-equal-subset-sum
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class CanPartition {
    public static boolean canPartition(int[] nums) {
        int sum = 0;
        for (int num : nums) {
            sum += num;
        }
        if (sum % 2 != 0) {
            return false;
        }
        int cap = sum / 2;
        int n = nums.length;
        int[][] dp = new int[n][cap + 1];

        for (int i = 0; i <= cap; i++) {
            if (nums[0] <= i) {
                dp[0][i] = nums[0];
            } else {
                dp[0][i] = 0;
            }
        }
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < cap + 1; j++) {
                if (i >= 1 && j - nums[i] >= 0) {
                    dp[i][j] = Math.max(dp[i - 1][j - nums[i]] + nums[i], dp[i - 1][j]);
                } else {
                    dp[i][j] = dp[i - 1][j];
                }
            }
        }
        return dp[n - 1][cap] == cap;
    }

    public static void main(String[] args) {
        int[] nums = {1, 2, 4, 5};
        System.out.println(canPartition(nums));
    }
}
