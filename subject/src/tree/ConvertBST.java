package tree;

/**
 * Created By ShengXiang.Xiong on 2019/12/4
 * （Greater Tree)，使得每个节点的值是原来的节点值加上所有大于它的节点值之和。
 * 输入: 二叉搜索树:
 * 5
 * /   \
 * 2     13
 * <p>
 * 输出: 转换为累加树:
 * 18
 * /   \
 * 20     13
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/convert-bst-to-greater-tree
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class ConvertBST {
    private int sum = 0;

    /**
     * 反后序遍历的变形。dfs的返回值、base均代表后序遍历时的右邻节点的值，只是base需要传递
     * 1.传递右邻节点base值给右子树，计算右子树的累加值rightBase
     * 2.然后再将右子树的累加值rightBase加到根节点root.val上
     * 3.最后再遍历左子树，传递当前节点的值（root.val）给左子树（左子树的右邻节点是root）。
     *
     * @param root
     * @param base
     * @return
     */
    public static int dfs(TreeNode root, int base) {
        if (root == null) return base;
        int rightBase = dfs(root.right, base);
        root.val += rightBase;
        return dfs(root.left, root.val);
    }

    public static TreeNode convertBST1(TreeNode root) {
        if (root == null) {
            return root;
        }
        dfs(root, 0);
        return root;
    }

    /**
     * 反后序遍历的变形。
     * 更简单的做法是完全贴合反后序遍历的形式，然后维护一个全局变量sum，
     * 表示其右领节点的值,这样就不需要像前面一样传递右邻节点的值。
     * <p>
     * 无论怎样，关键点在于如何维护传递右邻节点的值。
     *
     * @param root
     * @return
     */
    public TreeNode convertBST(TreeNode root) {
        if (root != null) {
            convertBST(root.right);
            sum += root.val;
            root.val = sum;
            convertBST(root.left);
        }
        return root;
    }

    public static void main(String[] args) {
        String s = "2,1,3";
        TreeNode root = new TreeNode(-1);
        TreeNode.bstFromPreorder(root, s.split("[, ]"));
        convertBST1(root);
        System.out.println();
    }
}
