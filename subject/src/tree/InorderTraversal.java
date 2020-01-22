package tree;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Created by Xursan on 2020/1/21.
 * 给定一个二叉树，返回它的中序 遍历。
 */
public class InorderTraversal {
    /**
     * 很简单，使用栈来存储每一个子树的根节点，这样就可以模拟递归访问
     * 栈S;
     * p= root;
     * while(p || S不空){
     * //将当前子树的所有左子树根节点入栈，直到p为空，那么栈顶必为左叶子节点
     * while(p){
     * p入S;
     * p = p的左子树;
     * }
     * p = S.top 出栈;      //左叶子节点
     * 访问p;               //根节点
     * p = p的右子树;        //右子树根节点，继续while循环右子树
     * }
     *
     * @param root
     * @return
     */
    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        Stack<TreeNode> s = new Stack<>();
        TreeNode p = root;
        while (p != null || !s.isEmpty()) {
            while (p != null) {
                s.add(p);
                p = p.left;
            }
            p = s.pop();
            res.add(p.val);
            p = p.right;
        }
        return res;
    }
}
