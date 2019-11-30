package dp.knapsack;

import java.util.Arrays;

/**
 * Created By ShengXiang.Xiong on 2019/11/29
 * 个符号 + 和 -。对于数组中的任意一个整数，你都可以从 + 或 -中选择一个符号添加在前面。
 * 返回可以使最终数组和为目标数 S 的所有添加符号的方法数。
 * <p>
 * 输入: nums: [1, 1, 1, 1, 1], S: 3
 * 输出: 5
 * <p>
 * 解释:
 * -1+1+1+1+1 = 3
 * +1-1+1+1+1 = 3
 * +1+1-1+1+1 = 3
 * +1+1+1-1+1 = 3
 * +1+1+1+1-1 = 3
 * <p>
 * 一共有5种方法让最终目标和为3。
 * 注意:
 * 数组非空，且长度不会超过20。
 * 初始的数组的和不会超过1000。
 * 保证返回的最终结果能被32位整数存下。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/target-sum
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class FindTargetSumWays {


//    public static int dfs(int index,int tar,int cnt,int[] nums,int[] max){
//        if(tar==0&&index==nums.length){
//            cnt++;
//            return cnt;
//        }
//        if(index<nums.length&&max[index]>=tar&&-max[index]<=tar){
//            cnt+=dfs(index+1,tar-nums[index],cnt,nums,max)+dfs(index+1,tar+nums[index],cnt,nums,max);
//        }
//        return cnt;
//    }

    /**
     * 优化1：记忆数组，记录在每个位置后缀和为tar的方法数--mem[index][tar] ,由于tar可能为负数，于是统一加一个偏移量变为正数
     */
    public static int dfs(int index, int tar, int cnt, int[] nums, int[] max, int[][] mem) {
        if (tar == 0 && index == nums.length) {
            cnt++;
            return cnt;
        }
        if (index < nums.length && mem[index][tar + 1001] > 0) {
            cnt += mem[index][tar + 1001];
        } else if (index < nums.length && max[index] >= tar && -max[index] <= tar) {
            cnt += dfs(index + 1, tar - nums[index], cnt, nums, max, mem) + dfs(index + 1, tar + nums[index], cnt, nums, max, mem);
        }
        if (cnt > 0) {
            mem[index][tar + 1001] = cnt;
        }
        return cnt;
    }

    /**
     * dfs+剪枝+记忆数组
     * 记录每个位置的后缀最大值和最小值，如果tar不在最小值和最大值之间，则提前终止
     *
     * @param nums
     * @param S
     * @return
     */
    public static int findTargetSumWays(int[] nums, int S) {
        Arrays.sort(nums);
        int n = nums.length;
        int[] max = new int[n];
        int[][] mem = new int[n + 1][3000];
        max[n - 1] = nums[n - 1];
        for (int i = n - 2; i >= 0; i--) {
            max[i] = nums[i] + max[i + 1];
        }
        return dfs(0, S, 0, nums, max, mem);
    }

    /**
     * 公式变形+0-1 背包
     * 原问题是给定一些数字，加加减减，使得它们等于targert。例如，1 - 2 + 3 - 4 + 5 = target(3)。
     * 如果我们把加的和减的结合在一起，可以写成
     * (1+3+5)  +  (-2-4) = target(3)
     * -------     ------
     * -> 正数    -> 负数
     * 所以，我们可以将原问题转化为： 找到nums一个正子集和一个负子集，使得总和等于target，统计这种可能性的总数。
     * <p>
     * 我们假设P是正子集，N是负子集。让我们看看如何将其转换为子集求和问题：
     * sum(P) - sum(N) = target
     * （两边同时加上sum(P)+sum(N)）
     * sum(P) + sum(N) + sum(P) - sum(N) = target + sum(P) + sum(N)
     * (因为 sum(P) + sum(N) = sum(nums))
     * 2 * sum(P) = target + sum(nums)
     * 因此，原来的问题已转化为一个求子集的和问题： 找到nums的一个子集 P，使得
     * sum(P) = （target + sum(nums)）/2
     * 根据公式，若target + sum(nums)不是偶数，就不存在答案，即返回0个可能解。
     * 因此题目转化为01背包，也就是能组合成容量为sum(P)的方式有多少种,一种组合中每个数字只能取一次。
     *
     * @param nums
     * @param S
     * @return
     */
    public static int sol2(int[] nums, int S) {
        int sum = 0;
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
        }
        int tgt = sum + S;
        if (S > sum || tgt % 2 == 1) return 0;
        int[] dp = new int[tgt / 2 + 1];
        dp[0] = 1;
        for (int num : nums) {
            for (int i = tgt / 2; i >= num; i--) {
                dp[i] += dp[i - num];
            }
        }
        return dp[tgt / 2];
    }

    /**
     * 负数 装箱类 0-1 背包. 每一个数字只有两种选择，+或-，其状态会影响目标和。从后往前推，自然而然想到0-1背包。
     * dp[pos][tar]——代表在前pos个位置形成目标和tar的最大方法数
     * 于是有状态转移方程：dp[pos][tar] = dp[pos-1][tar-nums[i]]+dp[pos-1][tar+nums[i]]
     * <p>
     * 由于目标和可能存在负值，数组不能存在负的下标,所以提前设置一个偏移量将目标和 转换为正值.
     * 我们知道0-1背包的阶段特征cap必须要表示所有可能的容量，而对于这道题而言，也必须要满足所有可能的tar，由于加了偏移量
     * 自然初始tar即为offset-sum(全为负数时，目标和最小)，最大tar即为offset+sum，这样既可表示所有可能取到的阶段特征。
     * <p>
     * 空间优化：每一阶段的目标和只会受前一阶段的影响，因此可以优化成为一维数组，只存储上一层的所有目标和对应的方法数.
     * 注意由于负数的存在，不能在原dp上进行状态转移，即使从大到小，小tar仍然会受大tar的影响。因此只能开辟两个数组，一个
     * 存储当前dp，一个存储上一个dp
     *
     * @param nums
     * @param S
     * @return
     */
    public static int dpSol(int[] nums, int S) {
        int n = nums.length;
        int offset = 1000;
        if (S > 1000 || S < -1000) {
            return 0;
        }
        int[] last = new int[2001];
        S += offset;
        int sum = 0;
        for (int i = 0; i < n; i++) {
            sum += nums[i];
        }
        last[offset] = 1;
        for (int num : nums) {
            int[] dp = new int[2001];
            for (int j = sum + offset; j >= offset - sum; j--) {
                //注意一定要判断j+num和j-num是否在tar范围内，不在范围内的必然为0，直接跳过，避免了数组越界
                if (j - num >= offset - sum) {
                    dp[j] += last[j - num];
                }
                if (j + num <= sum + offset) {
                    dp[j] += last[j + num];
                }
            }
            last = dp;
        }
        return last[S];
//        int[] dp = new int[2001];
//        dp[nums[0] + 1000] = 1;
//        dp[-nums[0] + 1000] += 1;
//        for (int i = 1; i < nums.length; i++) {
//            int[] next = new int[2001];
//            for (int sum = -1000; sum <= 1000; sum++) {
//                //如果dp[sum + 1000]>0，则一定有sum + nums[i] + 1000 < 2001，因为前面保证过sum和一定小于等于1000
//                //如果sum和大于1000了，必有dp[sum + 1000]=0；自然跳过这种情况
//                if (dp[sum + 1000] > 0) {
//                    next[sum + nums[i] + 1000] += dp[sum + 1000];
//                    next[sum - nums[i] + 1000] += dp[sum + 1000];
//                }
//            }
//            dp = next;
//        }
//        return S > 1000 ? 0 : dp[S + 1000];

    }

    public static void main(String[] args) {
        int[] nums = {1, 1, 1, 1, 996};
        int S = 1000;
        System.out.println(dpSol(nums, S));
    }
}
