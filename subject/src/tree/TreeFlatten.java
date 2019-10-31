package tree;

/**
 * Created By ShengXiang.Xiong on 2019/10/29
 * 将树按先序遍历展开为链表，空间复杂度为O(1)
 */
public class TreeFlatten {
    /**
     * 因为要使用原地算法，故不能新建链表，于是直接考虑将树的right作为链接指针。
     * 实际上，本题考查的点是二叉树的线索化，只需要先序遍历此树，对于每个树而言，每次将其左子树的最后一个节点的right指针
     * 指向其下一个先序遍历的节点（实际上就是左子树的最后一个节点指向右子树的最左节点
     * 遍历完后，再对整体将所有left变为right指针即可。
     *
     * @param root
     */
    public void flatten(TreeNode root) {
        if (root == null) {
            return;
        }
        preOrder(root);
        while (root.left != null || root.right != null) {
            if (root.left != null) {
                root.right = root.left;
                root.left = null;
            }
            root = root.right;
        }
    }

    public void preOrder(TreeNode root) {
        if (root.left != null) {
            findLastNode(root.left).right = root.right;
            preOrder(root.left);
        }
        if (root.right != null) {
            preOrder(root.right);
        }
    }

    public TreeNode findLastNode(TreeNode root) {
        while (root.right != null) {
            root = root.right;
        }
        while (root.left != null) {
            root = root.left;
        }
        return root;
    }

    TreeNode last = null;

    public void flatten1(TreeNode root) {
        if (root == null) return;
        flatten(root.right);
        flatten(root.left);
        root.right = last;
        root.left = null;
        last = root;
    }

    public static void main(String[] args) {
        String s = "2,1,4,null,null,3,5";
        TreeNode root = new TreeNode(0);
        TreeNode.bstFromPreorder(root, s.split("[, ]"));
        TreeFlatten a = new TreeFlatten();
        a.flatten1(root);
        while (root != null) {
            System.out.print(root.val + " ");
            root = root.right;
        }
    }
}
