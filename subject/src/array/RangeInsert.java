package array;

import utils.matrixTrans;

/**
 * Created by Xursan on 2019/8/24.
 * 给出一个无重叠的 ，按照区间起始端点排序的区间列表。
 * 在列表中插入一个新的区间，你需要确保列表中的区间仍然有序且不重叠（如果有必要的话，可以合并区间）。
 * 输入: intervals = [[1,3],[6,9]], newInterval = [2,5]
 * 输出: [[1,5],[6,9]]
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/insert-interval
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class RangeInsert {
    public static int find(int[][] nums, int tar, int pos) {
        int s = 0;
        int e = nums.length - 1;
        if (pos == 0) {
            while (s <= e) {
                int mid = (s + e) / 2;
                if (nums[mid][pos] > tar) {
                    e = mid - 1;
                } else {
                    s = mid + 1;
                }
            }
            return s - 1;
        } else {
            while (s <= e) {
                int mid = (s + e) / 2;
                if (nums[mid][pos] < tar) {
                    s = mid + 1;
                } else {
                    e = mid - 1;
                }
            }
            return e + 1;
        }
    }

    /**
     * 这种题，最好数形结合，画图更直观，更方便解。
     * 最简单的思路是找到插入区间的左边所在的位置，然后从该位置的前一个位置进行区间合并。时间复杂度为O(n)
     * 但是我们仔细分析发现，其实并不需要对中间所有被覆盖的区间进行区间合并，我们只需要找到该插入区间的横跨范围，即找到
     * 其左边所对应的位置i（在所有区间的left中找到第一个小于tarL的位置），找到右边所对应的位置j（第一个大于tarR的位置）
     * 判断区间i和区间j与该newInterval的重叠关系，从而进行范围修改。由于基本算法思想是二分查找，平均时间复杂度O（log n）
     * 最坏情况下变为O(n)
     *
     * @param intervals
     * @param newInterval
     * @return
     */
    public static int[][] insert(int[][] intervals, int[] newInterval) {
        int tarL = newInterval[0];
        int tarR = newInterval[1];
        int i = find(intervals, tarL, 0);
        int j = find(intervals, tarR, 1);
        if (i >= 0 && tarL <= intervals[i][1]) {
            tarL = intervals[i][0];
        } else {
            i++;
        }
        if (j < intervals.length && tarR >= intervals[j][0]) {
            tarR = intervals[j][1];
        } else {
            j--;
        }
        int[][] ans = new int[intervals.length - j + i][2];
        int k = 0;
        while (k < i) {
            ans[k] = intervals[k];
            k++;
        }
        ans[i++] = new int[]{tarL, tarR};
        while (++j < intervals.length) {
            ans[i++] = intervals[j];
        }
        return ans;
    }

    public static void main(String[] args) {
        String s = " [[1,3],[6,9]]";
        int[] newInterval = {0, 1};
        int[][] nums = matrixTrans.strTrans2Array(s);
        for (int[] i : insert(nums, newInterval)) {
            for (int i1 : i) {
                System.out.print(i1 + " ");
            }
            System.out.println();
        }
    }
}
