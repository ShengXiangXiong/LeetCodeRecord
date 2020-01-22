package tree;

/**
 * Created by Xursan on 2020/1/21.
 * 98. 验证二叉搜索树
 * 给定一个二叉树，判断其是否是一个有效的二叉搜索树。
 * 假设一个二叉搜索树具有如下特征：
 * 节点的左子树只包含小于当前节点的数。
 * 节点的右子树只包含大于当前节点的数。
 * 所有左子树和右子树自身必须也是二叉搜索树。
 * <p>
 * 输入:
 * 2
 * / \
 * 1   3
 * 输出: true
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/validate-binary-search-tree
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class IsValidBST {
    /**
     * 递归，并维护maxVal和minVal，分别指的是左右子树的最大值和最小值。
     * 首先判断，左右子树是否满足BST，再判断当前根节点和左右子树的最大值与最小值的关系。
     * 关键点在于如何维护maxVal和minVal，自下而上分析，必然是在递归返回时更新maxVal和minVal。
     * 其次注意当切换到右子树时，重置minVal，否则左子树的最小值会对右子树产生影响。
     */
    private int minVal = Integer.MAX_VALUE;
    private int maxVal = Integer.MIN_VALUE;

    public boolean isValidBST(TreeNode root) {
        if (root == null) {
            return true;
        }
        if (isValidBST(root.left)) {
            if (root.val > maxVal) {
                maxVal = Math.max(maxVal, root.val);
                minVal = Integer.MAX_VALUE;
                if (isValidBST(root.right)) {
                    if (root.val < minVal) {
                        minVal = Math.min(minVal, root.val);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 我们仔细分析发现实际上如果采用中序遍历，其必然是严格递增的序列，因此只需要维护上一个节点的值，采用中序遍历的递归
     * 形式即可。
     */
    private long tmp = Long.MIN_VALUE;

    public boolean isValidBST1(TreeNode root) {
        if (root == null) {
            return true;
        }
        if (isValidBST1(root.left)) {
            if (tmp < root.val) {
                tmp = root.val;//update
                if (isValidBST1(root.right)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void main(String[] args) {
        String s = "5,1,7,null,null,6,9";
        TreeNode root = new TreeNode(0);
        TreeNode.bstFromPreorder(root, s.split("[, ]"));
        IsValidBST isValidBST = new IsValidBST();
        System.out.println(isValidBST.isValidBST(root));
    }
}
