package DivideAndConquer;

import utils.matrixTrans;

import java.util.PriorityQueue;

/**
 * Created by Xursan on 2020/1/11.
 * 378. 有序矩阵中第K小的元素
 * 给定一个 n x n 矩阵，其中每行和每列元素均按升序排序，找到矩阵中第k小的元素。
 * 请注意，它是排序后的第k小元素，而不是第k个元素。
 * matrix = [
 * [ 1,  5,  9],
 * [10, 11, 13],
 * [12, 13, 15]
 * ],
 * k = 8,
 * <p>
 * 返回 13。
 * 说明:你可以假设 k 的值永远是有效的, 1 ≤ k ≤ n2 。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/kth-smallest-element-in-a-sorted-matrix
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class KthSmallest {
    /**
     * 这道题本质就是多路归并。
     * 最容易想到的就是，多路归并，然后选择第k小的，自然时间复杂度为归并n个数组的时间O(n*n)
     * 优化1：
     * 但是我们注意到，我们并不需要归并k之后的，而且数组是有序的，最小值必然每次只出现在每一个数组的首位，因此需要从
     * 每一个数组的首位中选择最小的一个数出来（O(n)），然后k--，此时对应的数组首位的i++。
     * 这样做的话，时间复杂度为 O（k*n），当k特别大(=n*n)时，时间复杂度为O(n^3)
     * 继续优化2：
     * 我们要从首位选择最小的一个数出来，遍历的时间复杂度为O(n)，但是如果使用小顶堆存储所有行首元素，每次便可以以O(1)
     * 的时间复杂度弹出最小值，然后紧接着加入nums[col++]元素，调整堆的时间复杂度为O(log n)
     * 所以最终的时间复杂度为O(k*log n),当k特别大(=n*n)时，时间复杂度为O(n^2 *log n)
     *
     * @param matrix
     * @param k
     * @return
     */
    public static int kthSmallest(int[][] matrix, int k) {
        //注意这里需要使用索引方式（位置）来存储到堆中，这样在弹出时能够知道对应着哪一行
        PriorityQueue<int[]> q = new PriorityQueue<>((o1, o2) -> (matrix[o1[0]][o1[1]] - matrix[o2[0]][o2[1]]));
        for (int i = 0; i < matrix.length; i++) {
            q.add(new int[]{i, 0});
        }
        int[] tmp = new int[2];
        while (k > 0) {
            tmp = q.poll();
            k--;
            if (tmp[1] + 1 < matrix.length) {
                q.add(new int[]{tmp[0], tmp[1] + 1});
            }
        }
        return matrix[tmp[0]][tmp[1]];
    }

    /**
     * 上面的解法，虽然也很巧妙，但是只利用了行的有序性，并没有利用列的有序性，所以思考是否能够利用，行列的有序性，结合
     * 二分法快速查找第k小的元素。
     *
     * @param matrix
     * @param k
     * @return
     */
    public int kthSmallest1(int[][] matrix, int k) {
        return 0;
    }

    public static void main(String[] args) {
        String s = "[\n" +
                " [ 1,  5,  9],\n" +
                " [10, 11, 13],\n" +
                " [12, 13, 15]\n" +
                " ]";
        int k = 8;
        int[][] mat = matrixTrans.strTrans2Array(s);
        System.out.println(kthSmallest(mat, k));
    }
}
