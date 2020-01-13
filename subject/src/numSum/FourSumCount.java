package numSum;

import java.util.HashMap;
import java.util.HashSet;

/**
 * Created By ShengXiang.Xiong on 2020/1/13
 * 454. 四数相加 II
 * 给定四个包含整数的数组列表 A , B , C , D ,计算有多少个元组 (i, j, k, l) ，使得 A[i] + B[j] + C[k] + D[l] = 0。
 * 为了使问题简单化，所有的 A, B, C, D 具有相同的长度 N，且 0 ≤ N ≤ 500 。所有整数的范围在 -2^28 到 2^28 - 1 之间，
 * 最终结果不会超过 2^31 - 1 。
 * 输入:
 * A = [ 1, 2]
 * B = [-2,-1]
 * C = [-1, 2]
 * D = [ 0, 2]
 * 输出:
 * 2
 * <p>
 * 解释:
 * 两个元组如下:
 * 1. (0, 0, 0, 1) -> A[0] + B[0] + C[0] + D[1] = 1 + (-2) + (-1) + 2 = 0
 * 2. (1, 1, 0, 0) -> A[1] + B[1] + C[0] + D[0] = 2 + (-1) + (-1) + 0 = 0
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/4sum-ii
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class FourSumCount {
    /**
     * 最容易想到的便是O(n^3)的算法，循环前3个，然后将D记录到hash进行查找
     * <p>
     * 再仔细思考一下，我们可以通过拆分方式，前后分别两两组合，然后转换为两数组和的问题了——时间复杂度变为O(n^2)
     *
     * @return
     */
    public int fourSumCount(int[] A, int[] B, int[] C, int[] D) {
        HashMap<Integer, Integer> countAB = new HashMap<>();
        HashSet<Integer> s = new HashSet<>();
        int cnt = 0;
        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < A.length; j++) {
                int tmp1 = A[i] + B[j];
                countAB.put(tmp1, countAB.getOrDefault(tmp1, 0) + 1);
                s.add(tmp1);
            }
        }
        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < A.length; j++) {
                cnt += countAB.getOrDefault(C[i] + D[j], 0);
            }
        }
        return cnt;
    }

    public static void main(String[] args) {

    }

}
