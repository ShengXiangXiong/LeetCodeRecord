package tree;

/**
 * Created by Xursan on 2020/1/24.
 * 108. 将有序数组转换为二叉搜索树
 * 将一个按照升序排列的有序数组，转换为一棵高度平衡二叉搜索树。
 * 本题中，一个高度平衡二叉树是指一个二叉树每个节点 的左右两个子树的高度差的绝对值不超过 1。
 * 给定有序数组: [-10,-3,0,5,9],
 * 一个可能的答案是：[0,-3,9,-10,null,5]，它可以表示下面这个高度平衡二叉搜索树：
 * 0
 * /       \
 * -3        9
 * /        /
 * -10       5
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/convert-sorted-array-to-binary-search-tree
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class SortedArrayToBST {
    public static TreeNode sortedArrayToBST(int[] nums) {
        return build(nums, 0, nums.length - 1);
    }

    public static TreeNode build(int[] nums, int p, int q) {
        TreeNode root = null;
        if (p <= q) {
            int mid = (p + q) / 2;
            root = new TreeNode(nums[mid]);
            root.left = build(nums, p, mid - 1);
            root.right = build(nums, mid + 1, q);
        }
        return root;
    }

    public static void main(String[] args) {
        int[] nums = {};
        TreeNode root = sortedArrayToBST(nums);
        System.out.println();
    }
}
