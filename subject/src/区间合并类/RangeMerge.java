package 区间合并类;

import utils.matrixTrans;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Xursan on 2019/8/24.
 * <p>
 * 给出一个区间的集合，请合并所有重叠的区间。
 * 输入: [[1,3],[2,6],[8,10],[15,18]]
 * 输出: [[1,6],[8,10],[15,18]]
 * 解释: 区间 [1,3] 和 [2,6] 重叠, 将它们合并为 [1,6].
 * <p>
 * 输入: [[1,4],[4,5]]
 * 输出: [[1,5]]
 * 解释: 区间 [1,4] 和 [4,5] 可被视为重叠区间。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/merge-intervals
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class RangeMerge {
    /**
     * 区间合并。若无序，则需要判断这个条件，对于区间i和区间j，若 right_j<left_i || right_i<left_j则无法重叠
     * （画不重叠线段即可得出），对前面条件取反，right_j>=left_i && right_i >= left_j 则可以重叠。
     * 而这种情况下，我们对于每一个区间i要和剩下的所有区间进行比对，所以时间复杂度为O(n^2)
     * <p>
     * 但是如果，我们对区间按left从小到大排序，则只需要判断相邻的两个区间是否重叠即可（这时可以直接判断right_j >= left_i
     * 因为这里排序后已经包含了right_i >= left_j的条件），这时如果相邻区间都无法重叠，后面的更加不会重叠了。所以我们只需
     * 要在循环中记录当前待合并区间tmp，并将无法再合并的区间加入结果集res，不断更新待合并区间即可。
     *
     * @param intervals
     * @return
     */
    public static int[][] merge(int[][] intervals) {
        if (intervals.length == 0) {
            return new int[][]{};
        }
        Arrays.sort(intervals, (o1, o2) -> o1[0] - o2[0]);
        List<int[]> res = new ArrayList<>();
        int[] tmp = intervals[0];
        for (int i = 1; i < intervals.length; i++) {
            if (intervals[i][0] <= tmp[1]) {
                //更新待合并区间
                tmp[0] = intervals[i][0] > tmp[0] ? tmp[0] : intervals[i][0];
                tmp[1] = intervals[i][1] > tmp[1] ? intervals[i][1] : tmp[1];
            } else {
                //无法合并，加入待合并区间tmp到结果集
                res.add(tmp);
                //更新待合并区间
                tmp = intervals[i];
            }
        }
        res.add(tmp);
        int[][] ans = new int[res.size()][2];
        return res.toArray(ans);
    }

    public static void main(String[] args) {
        String a = "[1,18],[2,6],[8,10],[15,18]";
        int[][] nums = matrixTrans.strTrans2Array(a);
        for (int[] ints : merge(nums)) {
            for (int anInt : ints) {
                System.out.print(anInt + " ");
            }
            System.out.println();
        }
    }

}
