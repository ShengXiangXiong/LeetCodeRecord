package dp.子序列dp.subArraySum;

import java.util.HashMap;

/**
 * Created By ShengXiang.Xiong on 2019/12/5
 * 给定一个整数数组和一个整数 k，你需要找到该数组中和为 k 的连续的子数组的个数。
 * <p>
 * 输入:nums = [1,1,1], k = 2
 * 输出: 2 , [1,1] 与 [1,1] 为两种不同的情况。
 * 说明 :
 * <p>
 * 数组的长度为 [1, 20,000]。
 * 数组中元素的范围是 [-1000, 1000] ，且整数 k 的范围是 [-1e7, 1e7]。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/subarray-sum-equals-k
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class SubarraySum {
    /**
     * 画二维矩阵，sum[i][j]表示在区间i到j之间的和
     * 任意区间值的和都可以通过前缀和得出，比如区间和sum[i,j] = pre[j]-pre[i]。
     * 时间复杂度O(n^2)
     *
     * @param nums
     * @param k
     * @return
     */
    public static int subarraySum(int[] nums, int k) {
        if (nums.length == 0) {
            return 0;
        }
        int res = 0;
        int cnt = 0;
        if (nums[0] == k) {
            res++;
        }
        for (int i = 1; i < nums.length; i++) {
            nums[i] += nums[i - 1];
            if (nums[i] == k) {
                res++;
            }
        }
        while (cnt < nums.length) {
            for (int i = nums.length - 1; i > cnt; i--) {
                nums[i] -= nums[cnt];
                if (nums[i] == k) {
                    res++;
                }
            }
            cnt++;
        }
        return res;
    }

    /**
     * 优化：hash表
     * 我们是否需要计算每一个区间的和，如果计算每一个区间的和，无疑是n^2的时间复杂度，但是如果能够直接用题目的目标值来
     * 反推区间值是否存在，那就可以使用hash表来优化时间。
     * <p>
     * 无疑这道题，是可以直接根据，目标值来反推区间是否存在，对于任意一个区间和sum[i,j] = pre[j]-pre[i].（i<j）
     * 如果sum[i,j] = tar -----> pre[j]-pre[i] = tar ---------> pre[i] = pre[j]-tar
     * 由于前面我们已经计算过pre[i]了，所以如果能够找到和为 pre[j]-tar的前缀和pre[i]，则必然有sum[i,j] = k，
     * 然后count += map.get(pre[j]-tar), 即pre[j]-tar的前缀和的值对应的前缀有多少个。
     * <p>
     * 基于这些想法，可以使用了一个哈希表 map，它用于存储所有可能的索引的累积总和以及相同累加和发生的次数。
     * 我们以以下形式存储数据：(sum_i，sum_i的出现次数)。我们遍历数组nums并继续寻找累积总和。
     * 每当我们遇到一个新的和时，我们在hashmap中创建一个与该总和相对应的新条目。
     * 如果再次出现相同的和，我们增加与map中的和相对应的计数。此外，对于遇到的每个总和，我们还确定已经发生 sum-k
     * 总和的次数，因为它将确定具有总和 k的子列发生到当前索引的次数。我们将 count增加相同的量。
     * 在完成便利数组后，count记录了所需结果
     *
     * @param nums
     * @param k
     * @return
     */
    public static int subarraySum1(int[] nums, int k) {
        int count = 0, sum = 0;
        HashMap<Integer, Integer> map = new HashMap<>();
        map.put(0, 1);
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
            if (map.containsKey(sum - k))
                count += map.get(sum - k);
            map.put(sum, map.getOrDefault(sum, 0) + 1);
        }
        return count;
    }

    public static void main(String[] args) {
        int[] nums = {1};
        int k = 1;
        System.out.println(subarraySum(nums, k));
    }
}
