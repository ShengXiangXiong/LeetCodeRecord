package DivideAndConquer;

import utils.matrixTrans;

/**
 * Created By ShengXiang.Xiong on 2020/1/10
 * <p>
 * 240. 搜索二维矩阵 II
 * <p>
 * 编写一个高效的算法来搜索 m x n 矩阵 matrix 中的一个目标值 target。该矩阵具有以下特性：
 * 每行的元素从左到右升序排列。
 * 每列的元素从上到下升序排列。
 * <p>
 * 现有矩阵 matrix 如下：
 * <p>
 * [
 * [1,   4,  7, 11, 15],
 * [2,   5,  8, 12, 19],
 * [3,   6,  9, 16, 22],
 * [10, 13, 14, 17, 24],
 * [18, 21, 23, 26, 30]
 * ]
 * 给定 target = 5，返回 true。
 * 给定 target = 20，返回 false。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/search-a-2d-matrix-ii
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class SearchMatrix2 {
    /**
     * 很容易想到要使用二分查找，只是这里需要的不只是一个二分查找。
     * 我们想一下如果我们对第一行进行二分查找，找到第一个小于等于tar的位置row1，则至少可以干掉row1后面的所有行。
     * 同理我们还需要对最右边一列二分，找到第一个大于等于tar的位置col1，则至少可以干掉col1前面的所有列。
     * <p>
     * 总结：维护2个指针，分别对应行的开始is，列的终点je（表示tar可能存在的范围），通过二分不断缩小指针范围
     * 直至行指针is超出ie，或者列指针je等于-1。
     * is——判断最右一列，寻找>=tar的第一个数，小于tar的行必然不满足，舍弃
     * je——判断最上一行，寻找<=tar的第一个数，大于tar的行必然不满足，舍弃
     * ie——指向最后一行，is不能超过，否则返回false
     * js——指向最左边一列，je不能超过，否则返回false
     * <p>
     * 最坏情况，每次干掉一行或者一列，每次寻找耗时O(log n)，从而可以知道时间复杂度为 O(log n+log (n-1)+...+log 1) = log(n !)
     *
     * @param matrix
     * @param target
     * @return
     */
    public boolean searchMatrix(int[][] matrix, int target) {
        if (matrix.length == 0 || matrix[0].length == 0) {
            return false;
        }
        int is = 0, ie = matrix.length - 1;
        int js = 0, je = matrix[0].length - 1;
        while (true) {
            is = binaryGECol(matrix, target, is, ie, je);
            if (is == ie + 1) {
                return false;
            }
            if (matrix[is][je] == target) {
                return true;
            }
            je = binaryLERow(matrix, target, js, je, is);
            if (je == -1) {
                return false;
            }
            if (matrix[is][je] == target) {
                return true;
            }
        }
    }

    public int binaryGECol(int[][] nums, int tar, int i, int j, int col) {
        while (i <= j) {
            int mid = (i + j) / 2;
            if (nums[mid][col] < tar) {
                i = mid + 1;
            } else {
                j = mid - 1;
            }
        }
        return j + 1;
    }

    public int binaryLECol(int[][] nums, int tar, int i, int j, int col) {
        while (i <= j) {
            int mid = (i + j) / 2;
            if (nums[mid][col] <= tar) {
                i = mid + 1;
            } else {
                j = mid - 1;
            }
        }
        return i - 1;
    }

    public int binaryGERow(int[][] nums, int tar, int i, int j, int row) {
        while (i <= j) {
            int mid = (i + j) / 2;
            if (nums[row][mid] < tar) {
                i = mid + 1;
            } else {
                j = mid - 1;
            }
        }
        return j + 1;
    }

    public int binaryLERow(int[][] nums, int tar, int i, int j, int row) {
        while (i <= j) {
            int mid = (i + j) / 2;
            if (nums[row][mid] <= tar) {
                i = mid + 1;
            } else {
                j = mid - 1;
            }
        }
        return i - 1;
    }

    /**
     * 从右上角开始，充分利用行列的递增性。设右上角为row，col
     * 如果tar<nums[row][col]，必然col对应的列全大于tar,所以col--
     * 如果tar>nums[row][col]，必然row对应的行全小于tar,所以row++
     * 若tar==nums[row][col]直接返回true
     * <p>
     * 直到，row或者col已经超出了边界都没有找到，则直接返回false
     * <p>
     * 时间复杂度：每次减少1行1列，比较耗时O(1)，所以时间复杂度为O(m+n)
     *
     * @param matrix
     * @param target
     * @return
     */
    public boolean searchMatrix1(int[][] matrix, int target) {
        if (matrix.length == 0 || matrix[0].length == 0) {
            return false;
        }
        int row = matrix.length - 1;
        int col = 0;
        while (row >= 0 && col < matrix[0].length) {
            if (matrix[row][col] > target) {
                row--;
            } else if (matrix[row][col] < target) {
                col++;
            } else {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        String s = "[\n" +
                "  [1,   4,  7, 11, 15],\n" +
                "  [2,   5,  8, 12, 19],\n" +
                "  [3,   6,  9, 16, 22],\n" +
                "  [10, 13, 14, 17, 24],\n" +
                "  [18, 21, 23, 26, 30]\n" +
                "]\n";
        int[][] mat = matrixTrans.strTrans2Array(s);
        SearchMatrix2 searchMatrix2 = new SearchMatrix2();
        int tar = 31;
        System.out.println(searchMatrix2.searchMatrix(mat, tar));

    }
}
