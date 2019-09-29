package array;

/**
 * Created by Xursan on 2019/9/29.
 * 编写一个高效的算法来判断 m x n 矩阵中，是否存在一个目标值。该矩阵具有如下特性：
 * 每行中的整数从左到右按升序排列。
 * 每行的第一个整数大于前一行的最后一个整数。
 * 输入:
 * matrix = [
 * [1,   3,  5,  7],
 * [10, 11, 16, 20],
 * [23, 30, 34, 50]
 * ]
 * target = 3
 * 输出: true
 */
public class SearchMatrix {
    public static int findFirstGE(int[][] matrix, int target) {
        int s = 0;
        int e = matrix.length - 1;
        int col = matrix[0].length - 1;
        while (s <= e) {
            int mid = (s + e) / 2;
            if (matrix[mid][col] < target) {
                s = mid + 1;
            } else {
                e = mid - 1;
            }
        }
        return e + 1 == matrix.length ? -1 : e + 1;
    }

    /**
     * 这里采取的是先按行进行二分查找，找到满足条件的行，在这行当中按列二分即可。时间复杂度是O(m+n)
     * <p>
     * 另外一个方法是将二维与一维坐标进行映射，这样就将原矩阵看做一维数组，这样就可以直接使用二分查找。
     * 二维数组行 = 一维坐标 / matrixColSize     二维数组列 = 一维坐标 % matrixColSize
     *
     * @param matrix
     * @param target
     * @return
     */
    public static boolean searchMatrix(int[][] matrix, int target) {
        if (matrix.length == 0 || matrix[0].length == 0) {
            return false;
        }
        int pos = findFirstGE(matrix, target);
        if (pos != -1) {
            int s = 0;
            int e = matrix[pos].length - 1;
            while (s <= e) {
                int mid = (s + e) / 2;
                if (matrix[pos][mid] < target) {
                    s = mid + 1;
                } else if (matrix[pos][mid] > target) {
                    e = mid - 1;
                } else {
                    return true;
                }
            }
        }
        return false;
    }

    public static void main(String[] args) {
        String matrix = "[\n" +
                " [1,   3,  5,  7],\n" +
                " [10, 11, 16, 20],\n" +
                " [23, 30, 34, 50]\n" +
                " ]";
        int tar = 11;
        System.out.println(searchMatrix(utils.matrixTrans.strTrans2Array(matrix), tar));
    }
}
