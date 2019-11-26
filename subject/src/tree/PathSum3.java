package tree;

import java.util.ArrayList;
import java.util.List;

/**
 * Created By ShengXiang.Xiong on 2019/11/26
 * 给定一个二叉树，它的每个结点都存放着一个整数值。
 * 找出路径和等于给定数值的路径总数。
 * 路径不需要从根节点开始，也不需要在叶子节点结束，但是路径方向必须是向下的（只能从父节点到子节点）。
 * 二叉树不超过1000个节点，且节点数值范围是 [-1000000,1000000] 的整数。
 * <p>
 * root = [10,5,-3,3,2,null,11,3,-2,null,1], sum = 8
 * <p>
 * 10
 * /  \
 * 5   -3
 * / \    \
 * 3   2   11
 * / \   \
 * 3  -2   1
 * <p>
 * 返回 3。和等于 8 的路径有:
 * 1.  5 -> 3
 * 2.  5 -> 2 -> 1
 * 3.  -3 -> 11
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/path-sum-iii
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class PathSum3 {
    /**
     * 最开始的想法是，在递归遍历子树时，就尝试以自身节点作为根节点，调整tar值作为最原始的oriTar，但是这会造成重复计算，
     * 上一层的tar值对应的路径，可能包含下一层调整tar值作为最原始的oriTar的路径，这肯定是错误的。
     * <p>
     * 所以最终直接改成，必须包含根节点在内的路径，然后在外层遍历整个树，以每个节点作为根节点，进行dfs寻找路径
     *
     * @param root
     * @param tar
     * @param oriTar
     * @param path
     * @return
     */
    public static int dfs(TreeNode root, int tar, int oriTar, List<Integer> path) {
        int cnt = 0;
        tar -= root.val;
        path.add(root.val);
        if (tar == 0) {
            cnt++;
            for (Integer integer : path) {
                System.out.print(integer + " ");
            }
            System.out.println();
        }
        if (root.left != null) {
            cnt += dfs(root.left, tar, oriTar, path);
            List<Integer> tmp = new ArrayList<>();
//            cnt+=dfs(root.left,oriTar,oriTar,tmp);
        }
        if (root.right != null) {
            cnt += dfs(root.right, tar, oriTar, path);
            List<Integer> tmp = new ArrayList<>();
//            cnt+=dfs(root.right,oriTar,oriTar,tmp);
        }
        path.remove((Integer) root.val);
        return cnt;
    }

    /**
     * 递归以每个节点作为根节点查找路径和
     *
     * @param root
     * @param sum
     * @return
     */
    public static int pathSum(TreeNode root, int sum) {
        int cnt = 0;
        if (root != null) {
            cnt += dfs(root, sum, sum, new ArrayList<>());
            cnt += pathSum(root.left, sum);
            cnt += pathSum(root.right, sum);
        }
        return cnt;
    }

    public static void main(String[] args) {
        int sum = 8;
        String s = "10,5,-3,3,2,null,11,3,-2,null,1";
        TreeNode root = new TreeNode(0);
        TreeNode.bstFromPreorder(root, s.split("[, ]"));
        System.out.println(pathSum(root, sum));
    }
}
