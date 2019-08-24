package array;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Xursan on 2019/8/23.
 * 给定一个包含 m x n 个元素的矩阵（m 行, n 列），请按照顺时针螺旋顺序，返回矩阵中的所有元素。
 示例 1:
 输入:
 [
 [ 1, 2, 3 ],
 [ 4, 5, 6 ],
 [ 7, 8, 9 ]
 ]
 输出: [1,2,3,6,9,8,7,4,5]

 来源：力扣（LeetCode）
 链接：https://leetcode-cn.com/problems/spiral-matrix
 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class SpiralOrder {
    /**
     * 思路很明确，就是从外层不断向里层扩展，像这种矩阵类型规则模拟题，可以考虑设置几个范围指针，限制其运动范围。
     * 这里我就设了lx，ly，rx，ry四个范围边界指针，每遍历一层，各指针向里缩拢，直至交错结束。
     * @param matrix
     * @return
     */
    public static List<Integer> spiralOrder(int[][] matrix) {
        List<Integer> res = new ArrayList<>();
        if(matrix.length==0){
            return res;
        }
        int lx = 0;
        int ly = 0;
        int ry = matrix[0].length-1;
        int rx = matrix.length-1;
        int sum = matrix[0].length*matrix.length;
        while (lx<=rx&&ly<=ry){
            for (int j = ly;j<=ry;j++){
                res.add(matrix[lx][j]);
            }
            lx++;
            for (int i = lx; i <= rx; i++) {
                res.add(matrix[i][ry]);
            }
            ry--;
            for (int j = ry; j >= ly && rx>=lx; j--) {
                res.add(matrix[rx][j]);
            }
            rx--;
            for (int i = rx; i >=lx && ry>=ly; i--) {
                res.add(matrix[i][ly]);
            }
            ly++;
        }
        return res;
    }

    public static void main(String[] args) {
        int[][] nums = {{1, 2, 3,4},{4,5, 6,7},{7,8,9,10},{10,11,12,13},{13,14,15,16}};
        for (Integer integer : spiralOrder(nums)) {
            System.out.println(integer);
        }
    }
}
