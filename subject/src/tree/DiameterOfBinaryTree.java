package tree;

/**
 * Created By ShengXiang.Xiong on 2019/12/5
 * 给定一棵二叉树，你需要计算它的直径长度。一棵二叉树的直径长度是任意两个结点路径长度中的最大值。这条路径可能穿过根结点。
 * 给定二叉树
 * <p>
 * 1
 * / \
 * 2   3
 * / \
 * 4   5
 * 返回 3, 它的长度是路径 [4,2,1,3] 或者 [5,2,1,3]。
 * 注意：两结点之间的路径长度是以它们之间边的数目表示。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/diameter-of-binary-tree
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class DiameterOfBinaryTree {
    private static int res = 0;

    /**
     * dfs递归，分析最长直径的来源：
     * 1.左子树的直径
     * 2.右子树的直径
     * 3.左右子树的最大高度之和
     * 因此递归返回时需要保存2个值，一是树的高度res[0]，二是树的直径res[1]
     * 这道题在形式上很像 二叉树的最大路径和问题，需要保存两个值
     *
     * @param root
     * @return
     */
    public static int[] height1(TreeNode root) {
        int[] res = new int[2];
        if (root != null) {
            int[] left = height1(root.left);
            int[] right = height1(root.right);
            res[0] = Math.max(left[0], right[0]) + 1;
            res[1] = Math.max(left[1], Math.max(left[0] + right[0], right[1]));
        }
        return res;
    }

    /**
     * dfs 递归，和二叉树的最大路径和问题一样，
     * 更简单的做法是直接定义一个全局变量res；
     * 然后在递归过程中不断更新每一个子树的最大直径（因为最终的res一定来源于某个树的左子树高度加右子树高度和）
     *
     * @param root
     * @return
     */
    public static int height(TreeNode root) {
        int h = 0;
        if (root != null) {
            int le = height(root.left);
            int rt = height(root.right);
            h = Math.max(le, rt) + 1;
            res = Math.max(le + rt, res);
        }
        return h;
    }

    public static int diameterOfBinaryTree(TreeNode root) {
        height(root);
        return res;
    }

    public static void main(String[] args) {
        String s = "4,-7,-3,null,null,-9,-3,9,-7,-4,null,6,null,-6,-6,null,null,0,6,5,null,9,null,null,-1,-4,null,null,null,-2";
        TreeNode root = new TreeNode(0);
        TreeNode.bstFromPreorder(root, s.split("[, ]"));
        System.out.println(diameterOfBinaryTree(root));
    }
}
