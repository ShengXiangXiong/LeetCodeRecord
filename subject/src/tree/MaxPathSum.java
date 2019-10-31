package tree;

/**
 * Created By ShengXiang.Xiong on 2019/10/26
 */
public class MaxPathSum {
    static int ans;

    /**
     * DFS递归遍历所有子树。
     * 我们从最高层树着手，其最大值来源于(必须要包括经过其本身根节点，否则线路就断了)：
     * 1.Max(左子树的整体最大路径和+root.val,右子树的整体最大路径和+root.val)
     * 2.左子树边路最大路径和+右子树边路最大路径和+root.val
     * 3.root.val
     * 解释一下：为什么要有边路（单回溯路径），因为只有边路才可以连接左右子树
     * 所以另外我们还得分析每一个子树的边路递归形式，其最大边路和来自于
     * 1. Max(左子树边路和+root.val,右子树边路和+root.val）
     * 2. root.val
     * <p>
     * 那我们设计递归函数就可以知道，需要保存两个值，一是当前子树的最大路径和sum[0]，二是当前子树的最大边路和sum[1]
     * 另外在遍历所有树时，需要时时更新ans=Max(ans,sum[0])
     *
     * @param root
     * @return
     */
    public static int[] dfs(TreeNode root) {
        //sum[0]表示当前子树的最大和，sum[1]表示当前子树的边路最大和（上层树可能会用到）
        int[] sum = new int[2];
        if (root.right == null && root.left == null) {
            sum[0] = root.val;
            sum[1] = root.val;
        } else {
            int[] l = new int[2];
            int[] r = new int[2];
            l[0] = -1000000000;
            l[1] = l[0];
            r[0] = l[0];
            r[1] = l[0];
            if (root.right != null) {
                r = dfs(root.right);
            }
            if (root.left != null) {
                l = dfs(root.left);
            }
            sum[0] = Math.max(root.val, Math.max(Math.max(l[0] + root.val, r[0] + root.val), l[1] + r[1] + root.val));
            sum[1] = Math.max(root.val, Math.max(l[1] + root.val, r[1] + root.val));
        }
        ans = Math.max(ans, sum[0]);
        return sum;
    }
    public static int maxPathSum(TreeNode root) {
        String s = "-10,9,20,null,null,15,-7";
        TreeNode.bstFromPreorder(root, s.split("[, ]"));
        ans = root.val;
        dfs(root);
        return ans;
    }

    public static void main(String[] args) {
        System.out.println(maxPathSum(new TreeNode(0)));
    }
}
