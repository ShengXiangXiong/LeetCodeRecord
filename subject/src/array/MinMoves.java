package array;

/**
 * Created by Xursan on 2019/9/18.
 * 给定一个长度为 n 的非空整数数组，找到让数组所有元素相等的最小移动次数。每次移动可以使 n - 1 个元素增加 1。
 * 输入:
 * [1,2,3]
 * 输出:
 * 3
 * <p>
 * 解释:
 * 只需要3次移动（注意每次移动会增加两个元素的值）：
 * [1,2,3]  =>  [2,3,3]  =>  [3,4,3]  =>  [4,4,4]
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/minimum-moves-to-equal-array-elements
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class MinMoves {
    /**
     * 贪心：我们仔细分析发现无论如何都要让最小的值等于最大值才行。那每次让最小的向最大的靠拢，不断更新当前阶段数组的
     * 最小值和最大值（这里需要保存数组待+1的不同元素的信息）直至最小值等于最大值。
     * 但是我们再仔细分析，发现对于当前的排序数组a[1,n]每次都是让最小的值加上到当前最大值的距离，相当于每次加了之后都会
     * 让当前的最大值等于最小值，这时无论后面怎样加a[0]恒等于a[n]，相当于规模减小了1；在分析一下加了之后最大值与最小值
     * 的位置变化，发现最小值依然是a[0]，但是最大值却变成了a[n-1]，而他们之间的距离仍是最初的距离；所以接下来的循环都是
     * 在不断弥补最小值和最大值之间的距离，而他们的距离在数组一开始时就确定了。
     * 所以这就变成了计算最小值到各个数的距离之和。
     *
     * @param nums
     * @return
     */
    public int minMoves(int[] nums) {
        //Arrays.sort(nums);
        //优化，不需要排序，我们只需要计算差值，所以我们可以一次循环当中找到最小值min，并记录数组和，而他们的差值必然是
        //sum-length()*min
        int ans = 0;
        int min = Integer.MAX_VALUE;
        for (int num : nums) {
            ans += num;
            min = min > num ? num : min;
        }
        return ans - min * nums.length;
    }

    public static void main(String[] args) {
        MinMoves a = new MinMoves();
        int[] b = {2, 2, 3};
        System.out.println(a.minMoves(b));
    }
}
