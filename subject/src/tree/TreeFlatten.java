package tree;

/**
 * Created By ShengXiang.Xiong on 2019/10/29
 * 将树按先序遍历展开为链表，空间复杂度为O(1)
 */
public class TreeFlatten {
    /**
     * 因为要使用原地算法，故不能新建链表，于是直接考虑将树的right作为链接指针。
     * 实际上，本题考查的点是二叉树的线索化，只需要先序遍历此树，对于每个树而言，每次将其左子树的最后一个节点的right指针
     * 指向其下一个先序遍历的节点（实际上就是左子树的最后一个节点指向右子树的根节点）
     *
     * 完全按照先序遍历来，但是由于是需要找到左子树的最右节点，所以对于每个子树都必须得返回其最右节点
     * @param root
     */
    public void flatten(TreeNode root) {
        if (root == null) {
            return;
        }
        preOrder(root);
    }

    public TreeNode preOrder(TreeNode root) {
        TreeNode lastNode = root;
        if (root.left != null) {
            lastNode = preOrder(root.left);
            lastNode.right = root.right;
        }
        if (root.right != null) {
            lastNode = preOrder(root.right);
        }
        if (root.left != null) {
            root.right = root.left;
            root.left = null;
        }
        return lastNode;
    }


    /**
     * 换一种思路，我们知道题目给定的遍历顺序其实就是先序遍历的顺序，所以我们能不能利用先序遍历的代码，
     * 每遍历一个节点，就将上一个节点的右指针更新为当前节点。先序遍历的顺序是 1 2 3 4 5 6。
     * <p>
     * 遍历到 2，把 1 的右指针指向 2。1 -> 2 3 4 5 6。
     * 遍历到 3，把 2 的右指针指向 3。1 -> 2 -> 3 4 5 6。
     * ... ...
     * 一直进行下去似乎就解决了这个问题。但现实是残酷的，原因就是我们把 1 的右指针指向 2，那么 1 的原本的右孩子就丢失了，也就是 5 就找不到了。
     * <p>
     * 解决方法的话，我们可以逆过来进行。
     * 我们依次遍历 6 5 4 3 2 1，然后每遍历一个节点就将当前节点的右指针更新为上一个节点。
     * 遍历到 5，把 5 的右指针指向 6。6 <- 5 4 3 2 1。
     * 遍历到 4，把 4 的右指针指向 5。6 <- 5 <- 4 3 2 1。
     * <p>
     * 这样就不会有丢失孩子的问题了，因为更新当前的右指针的时候，当前节点的右孩子已经访问过了。
     * 而 6 5 4 3 2 1 的遍历顺序其实变形的后序遍历，遍历顺序是右子树->左子树->根节点。
     */
    TreeNode last = null;
    public void flatten1(TreeNode root) {
        if (root == null) return;
        flatten1(root.right);
        flatten1(root.left);
        // 递归是从右孩子开始遍历，然后左孩子，然后根节点
        // 保留上一次遍历的节点，作为下一个遍历节点的右孩子
        root.right = last;
        root.left = null;
        last = root;
    }

    public static void main(String[] args) {
        String s = "2,1,4,7,8,3,5,#,#,9,#,10";
        TreeNode root = new TreeNode(0);
        TreeNode.bstFromPreorder(root, s.split("[, ]"));
        TreeFlatten a = new TreeFlatten();
        a.flatten(root);
        while (root != null) {
            System.out.print(root.val + " ");
            root = root.right;
        }
    }
}
