package tree;

/**
 * Created By ShengXiang.Xiong on 2019/12/20
 * <p>
 * 337. 打家劫舍 III——两个直接相连的节点不能相加，求所有可能相加节点的最大和
 */
public class Rob3 {
    /**
     * 和求树的最大路径和那道题类似，树形dp，按照套路，从最高层分析，即可知道，整个树的最大值来源于：
     * 1.不包含当前节点值：左子树的最大值+右子树的最大值
     * 2.包含当前节点值：不包含左子树根节点的最大值+不包含右子树根节点的最大值
     *
     * @param root
     * @return
     */
    public static int[] dfs(TreeNode root) {
        //res[0] 包含当前节点在内的最大值   res[1]不包含当前节点在内的最大值
        int[] res = new int[2];
        if (root != null) {
            int[] r = dfs(root.right);
            int[] l = dfs(root.left);
            res[0] = r[1] + l[1] + root.val;
            res[1] = Math.max(r[0], r[1]) + Math.max(l[0], l[1]);
        }
        return res;
    }

    public int rob(TreeNode root) {
        int[] res = dfs(root);
        return Math.max(res[0], res[1]);
    }
}
