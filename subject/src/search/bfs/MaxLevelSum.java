package search.bfs;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * 给你一个二叉树的根节点 root。设根节点位于二叉树的第 1 层，而根节点的子节点位于第 2 层，依此类推。
 * 请你找出层内元素之和 最大 的那几层（可能只有一层）的层号，并返回其中 最小 的那个。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/maximum-level-sum-of-a-binary-tree
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode(int x) {
        val = x;
    }
}

public class MaxLevelSum {
    /**
     * 思路很简单，就是一个bfs，使用队列即可
     *
     * @param root
     * @return
     */
    public int maxLevelSum(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int layer = 0;
        int res = 1;
        int cnt = 0;
        Queue<TreeNode> q = new ArrayDeque<>();
        q.add(root);
        while (q.size() > 0) {
            layer++;
            int n = q.size();
            int sum = 0;
            while (n-- > 0) {
                TreeNode t = q.poll();
                if (t.left != null) {
                    q.add(t.left);
                }
                if (t.right != null) {
                    q.add(t.right);
                }
                sum += t.val;
            }
            if (sum > cnt) {
                cnt = sum;
                res = layer;
            }
        }
        return res;
    }
}
