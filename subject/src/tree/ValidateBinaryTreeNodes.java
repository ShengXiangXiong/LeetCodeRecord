package tree;

/**
 * Created by Xursan on 2020/2/25.
 * 1361. 验证二叉树
 */
public class ValidateBinaryTreeNodes {
    /**
     * 由示例2可知，每个节点入度必须是1（不能大也不能小）；（除了根节点外，但是为了防止示例3的情况,所以提前将根节点的入度设为1）
     *
     * @param n
     * @param leftChild
     * @param rightChild
     * @return
     */
    public static boolean validateBinaryTreeNodes(int n, int[] leftChild, int[] rightChild) {
        if (n == 0) {
            return false;
        }
        int[] count = new int[n];
        count[0] = 1;
        for (int i = 0; i < n; i++) {
            if (count[i] != 1) {
                return false;
            }
            int l = 0, r = 0;
            if (leftChild[i] != -1) {
                l = ++count[leftChild[i]];
            }
            if (rightChild[i] != -1) {
                r = ++count[rightChild[i]];
            }
            if (l > 1 || r > 1) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        int n = 4;
        int[] leftChild = {1, -1, 3, -1};
        int[] rightChild = {2, 3, -1, -1};
        System.out.println(validateBinaryTreeNodes(n, leftChild, rightChild));
    }
}
