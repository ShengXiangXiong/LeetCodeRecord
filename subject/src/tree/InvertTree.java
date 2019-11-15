package tree;

/**
 * Created By ShengXiang.Xiong on 2019/11/7
 * 翻转一棵二叉树。
 */
public class InvertTree {
    public static TreeNode invertTree(TreeNode root) {
        if (root != null) {
            TreeNode tmp = root.right;
            root.right = invertTree(root.left);
            root.left = invertTree(tmp);
        }
        return root;
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(0);
        String s = "-10,9,20,null,null,15,-7";
        TreeNode.bstFromPreorder(root, s.split("[, ]"));
        root = invertTree(root);
        while (root != null) {
            System.out.print(root.val + " ");
        }
    }
}
