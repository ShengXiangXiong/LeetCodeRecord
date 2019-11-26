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
    /**
     * 典型的0-1背包问题，就是查看nums中能否找出填满sum(nums)/2的组合。
     *
     * @param nums
     * @return
     */
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
        if (n == 0) {
            return true;}
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
                if (j - nums[i] >= 0) {
                    dp[i][j] = Math.max(dp[i - 1][j - nums[i]] + nums[i], dp[i - 1][j]);
                } else {
                    dp[i][j] = dp[i - 1][j];
                }
            }
        }
        return dp[n - 1][cap] == cap;
    }

    /**
     * 我们再仔细想一下，虽然是0-1背包，但是这里我们并不需要计算最大能装多少，而是在于计算能否装满。
     * 因此，状态方程需要改一改：dp[i][j]表示前i个物品中能否装满j容量，true or false
     * 按照0-1背包的特性，dp[i][j]只会受dp[i-1][j]和dp[i-1][j-nums[i]]的影响
     * 故知道，如果dp[i-1][j]和dp[i-1][j-nums[i]]中只要有一个为true，则必然dp[i][j]也为true了
     * <p>
     * 因此状态转移方程为：if(j-nums[i]>=0) => dp[i][j] = dp[i-1][j] || dp[i-1][j-nums[i]]
     * else => dp[i][j] = dp[i-1][j]
     * <p>
     * 进一步空间优化：根据状态方程，我们知道dp[i][...]只会受dp[i-1][...]的影响，因此，我们可以只记录i-1阶段时所有容量下
     * 的状态，则有if(i-num>=0)   =>   dp[i] = dp[i] || dp[i-num]
     * else    =>      dp[i] = dp[i]
     * 另外值得注意的是，空间优化后cap需要从大到小，这样才不会造成 大cap 被 小cap影响。
     *
     * @param nums
     * @return
     */
    public static boolean canPartition1(int[] nums) {
        if (nums == null) {
            return false;
        }
        int n = nums.length;
        if (n == 0) {
            return true;
        }
        int sum = 0;
        for (int num : nums) {
            sum += num;
        }
        if (sum % 2 != 0) {
            return false;
        }
        int cap = sum / 2;
        boolean[] dp = new boolean[cap + 1];
        //注意这里一定得初始化容量为0时为true，因为后面的容量很可能会依赖于cap为0时的状态
        dp[0] = true;
        for (int num : nums) {
            for (int i = cap; i >= 0; i--) {
                if (i - num >= 0) {
                    dp[i] = dp[i] || dp[i - num];
                }
            }
        }
        return dp[cap];
    }

    public static void main(String[] args) {
        int[] nums = {3,3,3,4,5};
        System.out.println(canPartition1(nums));
    }
}
