package stack;

import java.util.Stack;

/**
 * Created By ShengXiang.Xiong on 2019/10/8
 * 给定一个仅包含 0 和 1 的二维二进制矩阵，找出只包含 1 的最大矩形，并返回其面积。
 * 输入:
 * [
 * ["1","0","1","0","0"],
 * ["1","0","1","1","1"],
 * ["1","1","1","1","1"],
 * ["1","0","0","1","0"]
 * ]
 * 输出: 6
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/maximal-rectangle
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class MaximalRectangle {
    /**
     * 思路：对于每一行的所有坐标点，视其为矩形的底部，其前面连续的1视为该点所对应的高度，这不就转换成了上一题的求柱子的
     * 最大矩形面积了，扫描一遍就可以得到所有列的柱子高度。同一纵列的数字则视为同一水平线的柱子，然后求出所有列中矩形面积
     * 最大的即可。
     * 如上：扫描一遍后的结果：
     * *   ["1","0","1","0","0"],
     * *   ["1","0","1","2","3"],
     * *   ["1","2","3","4","5"],
     * *   ["1","0","0","1","0"]
     *
     * @param matrix
     * @return
     */
    public static int maximalRectangle(char[][] matrix) {
        if (matrix.length == 0 || matrix[0].length == 0) {
            return 0;
        }
        int[][] polars = new int[matrix[0].length][matrix.length];
        int ans = 0;
        for (int i = 0; i < matrix.length; i++) {
            int j = 0;
            int cnt = 0;
            while (j < matrix[i].length) {
                if (matrix[i][j] == '0') {
                    cnt = 0;
                    polars[j][i] = 0;
                } else {
                    polars[j][i] = ++cnt;
                }
                j++;
            }
        }
        for (int i = 0; i < polars.length; i++) {
            ans = Math.max(ans, largestRectangleArea2(polars[i]));
        }
        return ans;
    }

    public static int largestRectangleArea2(int[] heights) {
        Stack<Integer> stack = new Stack<>();
        stack.push(-1);
        int maxarea = 0;
        for (int i = 0; i < heights.length; ++i) {
            while (stack.peek() != -1 && heights[stack.peek()] >= heights[i])
                maxarea = Math.max(maxarea, heights[stack.pop()] * (i - stack.peek() - 1));
            stack.push(i);
        }
        while (stack.peek() != -1)
            maxarea = Math.max(maxarea, heights[stack.pop()] * (heights.length - stack.peek() - 1));
        return maxarea;
    }

    public static void main(String[] args) {
        String s = "[\n" +
                "  [\"1\",\"0\",\"1\",\"0\",\"0\"],\n" +
                "  [\"1\",\"0\",\"1\",\"1\",\"1\"],\n" +
                "  [\"1\",\"1\",\"1\",\"1\",\"1\"],\n" +
                "  [\"1\",\"0\",\"0\",\"1\",\"0\"]\n" +
                "]\n";
        char[][] cs = utils.matrixTrans.str2CharArray(s);
        System.out.println(maximalRectangle(cs));
    }
}
