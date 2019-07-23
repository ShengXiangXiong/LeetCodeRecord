package array;

/**
 * PrevPermOpt1
 * 给你一个正整数的数组 A（其中的元素不一定完全不同），请你返回可在 一次交换（交换两数字 A[i] 和 A[j] 的位置）后得到的、
 * 按字典序排列小于 A 的最大可能排列。
 * <p>
 * 如果无法这么操作，就请返回原数组。
 * <p>
 * 示例 1：
 * 输入：[3,2,1]
 * 输出：[3,1,2]
 * 解释：
 * 交换 2 和 1
 * <p>
 * 示例 2：
 * 输入：[1,1,5]
 * 输出：[1,1,5]
 * 解释：
 * 这已经是最小排列
 */
public class PrevPermOpt1 {

    public static int[] prevPermOpt1(int[] A) {
        /**
         * 从后往前，找到不满足递减的那个数t，并与其前面递减序列中小于t的最大值（如果有多个选择离他最近的值）进行交换
         */
        int tmp = 0;
        int n = A.length - 1;
        for (int i = n; i > 0; i--) {
            if (A[i] < A[i - 1]) {
                tmp = A[i - 1];
                int j = n;
                while (j > 0 && (A[j] >= tmp || A[j] == A[j - 1])) {
                    //A[j]>=tmp 小于t的最大值
                    //A[j]==A[j-1] 离他最近
                    j--;
                }
                if (j >= 0) {
                    A[i - 1] = A[j];
                    A[j] = tmp;
                }
                break;
            }
        }
        return A;
    }

    public static void main(String[] args) {
        int[] a = {3, 2, 2, 2, 4};
        System.out.println(prevPermOpt1(a));
    }
}