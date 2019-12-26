package sort;

import java.util.ArrayList;
import java.util.List;

/**
 * Created By ShengXiang.Xiong on 2019/12/25
 * 给定一个整数数组 nums，按要求返回一个新数组 counts。数组 counts 有该性质： counts[i] 的值是  nums[i] 右侧小于 nums[i] 的元素的数量。
 * 输入: [5,2,6,1]
 * 输出: [2,1,1,0]
 * 解释:
 * 5 的右侧有 2 个更小的元素 (2 和 1).
 * 2 的右侧仅有 1 个更小的元素 (1).
 * 6 的右侧有 1 个更小的元素 (1).
 * 1 的右侧有 0 个更小的元素.
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/count-of-smaller-numbers-after-self
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class CountSmaller {

    /**
     * 逆序插入，并记录每个节点左子树的节点总数。因为正序插入，无法在插入时更新右子树节点的值。
     *
     * @return
     */
    class TreeNode {
        int val;
        int leftCount;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
            leftCount = 0;
        }
    }

    public TreeNode constructSortedTree(int pos, TreeNode root, int[] nums, int[] count) {
        if (root == null) {
            return new TreeNode(pos);
        }
        if (nums[root.val] >= nums[pos]) {
            root.leftCount++;
            root.left = constructSortedTree(pos, root.left, nums, count);
        } else {
            count[pos] += root.leftCount + 1;
            root.right = constructSortedTree(pos, root.right, nums, count);
        }
        return root;
    }

    public List<Integer> countSmaller(int[] nums) {
        TreeNode root = new TreeNode(nums.length - 1);
        int[] count = new int[nums.length];
        for (int i = nums.length - 2; i >= 0; i--) {
            constructSortedTree(i, root, nums, count);
        }
        List<Integer> res = new ArrayList<>();
        for (int i : count) {
            res.add(i);
        }
        return res;
    }

    /**
     * 实际上本题可以看做找逆序对。而对于逆序对，我们知道归并排序无疑是最佳方案。
     * 但是这里还不能直接使用归并排序，因为需要统计逆序，所以归并排序时的数组存的值应该为对应原数组的下标索引，这样才能
     * 达到统计逆序数的目的。
     * <p>
     * 记住归并排序的高层递归形式:
     * if(s<e){
     * split();split();merge()
     * }
     *
     * @param nums
     * @return
     */
    public List<Integer> countSmaller1(int[] nums) {
        int[] count = new int[nums.length];
        split(nums, 0, nums.length - 1, count);
        List<Integer> res = new ArrayList<>();
        for (int i : count) {
            res.add(i);
        }
        return res;
    }

    public int[] split(int[] nums, int s, int e, int[] count) {
        if (e - s >= 1) {
            int[] nums1;
            int[] nums2;
            int half = (s + e) / 2;
            nums1 = split(nums, s, half, count);
            nums2 = split(nums, half + 1, e, count);
            return merge(nums1, nums2, count, nums);
        } else {
            int[] a = new int[1];
            a[0] = s;
            return a;
        }
    }

    /**
     * 这里采取从后往前的合并方式，方便统计后一部分数组的逆序总数
     */
    public int[] merge(int[] nums1, int[] nums2, int[] count, int[] nums) {
        int p = nums1.length - 1, q = nums2.length - 1, cnt = nums1.length + nums2.length - 1;
        int[] res = new int[nums1.length + nums2.length];
        while (p >= 0 && q >= 0) {
            if (nums[nums1[p]] > nums[nums2[q]]) {
                count[nums1[p]] += q + 1;
                res[cnt--] = nums1[p--];
            } else {
                res[cnt--] = nums2[q--];
            }
        }
        while (p >= 0) {
            res[cnt--] = nums1[p--];
        }
        while (q >= 0) {
            res[cnt--] = nums2[q--];
        }
        return res;
    }

    public static void main(String[] args) {
        CountSmaller c = new CountSmaller();
        int[] nums = {-1, -1, 2, 1, 23, 124, 12, 413, 5, 2436, 57, 1};
        for (int i : c.countSmaller1(nums)) {
            System.out.print(i + " ");
        }
    }
}
