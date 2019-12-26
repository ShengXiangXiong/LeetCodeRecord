package tree;

import java.util.*;

/**
 * Created By ShengXiang.Xiong on 2019/10/26
 */
public class TreeNode {
    public int val;
    public TreeNode left;
    public TreeNode right;

    public TreeNode(int x) {
        val = x;
    }

    public static ArrayList<Integer> preOrder(TreeNode root) {
        ArrayList path = new ArrayList();
        if (root.left != null) {
            path.addAll(preOrder(root.left));
        }
        path.add(root.val);
        if (root.right != null) {
            path.addAll(preOrder(root.right));
        }
        return path;
    }

    public static ArrayList<Integer> inOrder(TreeNode root) {
        ArrayList path = new ArrayList();
        path.add(root.val);
        if (root.left != null) {
            path.addAll(inOrder(root.left));
        }
        if (root.right != null) {
            path.addAll(inOrder(root.right));
        }
        return path;
    }

    public static ArrayList<Integer> postOrder(TreeNode root) {
        ArrayList path = new ArrayList();
        if (root.right != null) {
            path.addAll(postOrder(root.right));
        }
        if (root.left != null) {
            path.addAll(postOrder(root.left));
        }
        path.add(root.val);
        return path;
    }

    public static void bstFromPreorder(TreeNode root, String[] nums) {
        LinkedList<String> lk = new LinkedList<>(Arrays.asList(nums));
        if (lk.size() > 0) {
            Queue<TreeNode> q = new ArrayDeque<>();
            if (!lk.peek().matches("null|#")) {
                root.val = Integer.valueOf(lk.poll());
                q.add(root);
                while (q.size() > 0 && lk.size() > 0) {
                    int s = q.size();
                    while (s-- > 0 && lk.size() > 0) {
                        TreeNode cr = q.poll();
                        String l = lk.poll();
                        String r = "#";
                        if (lk.size() > 0) {
                            r = lk.poll();
                        }
                        assert l != null;
                        if (!l.matches("null|#")) {
                            TreeNode lchild = new TreeNode(Integer.valueOf(l));
                            cr.left = lchild;
                            q.add(lchild);
                        }
                        if (!r.matches("null|#")) {
                            TreeNode rchild = new TreeNode(Integer.valueOf(r));
                            cr.right = rchild;
                            q.add(rchild);
                        }
                    }
                }
            }
        }
    }
}
