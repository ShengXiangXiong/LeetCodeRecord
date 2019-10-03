package array;

/**
 * Created By ShengXiang.Xiong on 2019/9/28
 * 给定一个 m x n 的矩阵，如果一个元素为 0，则将其所在行和列的所有元素都设为 0。请使用原地算法。
 * 输入:
 * [
 *   [1,1,1],
 *   [1,0,1],
 *   [1,1,1]
 * ]
 * 输出:
 * [
 *   [1,0,1],
 *   [0,0,0],
 *   [1,0,1]
 * ]
 */
public class SetZeros {
    /**
     * 修改原矩阵中的数作为标记位
     * <p>
     * 最初的想法是，遍历一遍数组，然后找出所有0所在的行列，分别记录到cols和rows中，然后，分别按行按列赋0
     * 空间复杂度为O（m+n）
     * <p>
     * 题目要求是原地算法，则空间复杂度要求为常数级。开始的想法是遍历矩阵，每遇到一个0就对其所在的行赋0，然后对其列赋0，
     * 但这会出现一个问题后面遍历时遇到的0可能是前面赋值的0，并不是初始值就为0。那怎么办呢，后面于是想到，不直接赋0，
     * 而是将其修改为另一个范围外的值num，这样在初次遍历时所有被修改的值不为0，故不会对结果造成影响。然后下一次遍历时，
     * 每遇到num说明，它就是应该被置0的数，这样空间复杂度就变为O(1)了（可以说是不需要额外的空间了）。但是有一个问题，
     * 如果矩阵全都是0，那岂不是要对矩阵中的所有值全都赋值成num，然后每遇到一个num(共有n*m个)都有访问其行列所在的
     * 所有元素（n+m个），那时间复杂度就变为了O(m*n*(m+n))。
     * <p>
     * 其实更好的做法是，不用修改为范围外的num，而是每遇到一个0就将其所在行列的首位元素置0。
     * <p>
     * 1.矩阵中某个数为零，则将该数所在行的第一个数置零，所在列的第一个数置零，即matrix[0][j] = matrix[i][0] = 0;
     * 这样并不会影响该列该行首个数的取值，因为他们最后都会被置零。即让首行首列记录哪一列有零，哪一行有零
     * <p>
     * 2. 遍历矩阵中非首行首列的每个元素，如果所在行首或者列首元素为零，则说明该行该列应该都为零，将该元素置零，最后达到目的
     * <p>
     * 3. 第一步操作可能会让首行首列是否有零这个信息损失掉，因为首行首列被用来存储其他信息了，会改变他们的取值，
     * 所以再定义两个变量row0,col0记录首行首列是否有零，并且这一步需要放在前面
     *
     * @param matrix
     */
    public static void setZeroes(int[][] matrix) {
        Boolean isCol = false;
        int R = matrix.length;
        int C = matrix[0].length;

        for (int i = 0; i < R; i++) {

            // Since first cell for both first row and first column is the same i.e. matrix[0][0]
            // We can use an additional variable for either the first row/column.
            // For this solution we are using an additional variable for the first column
            // and using matrix[0][0] for the first row.
            if (matrix[i][0] == 0) {
                isCol = true;
            }

            for (int j = 1; j < C; j++) {
                // If an element is zero, we set the first element of the corresponding row and column to 0
                if (matrix[i][j] == 0) {
                    matrix[0][j] = 0;
                    matrix[i][0] = 0;
                }
            }
        }

        // Iterate over the array once again and using the first row and first column, update the elements.
        for (int i = 1; i < R; i++) {
            for (int j = 1; j < C; j++) {
                if (matrix[i][0] == 0 || matrix[0][j] == 0) {
                    matrix[i][j] = 0;
                }
            }
        }

        // See if the first row needs to be set to zero as well
        if (matrix[0][0] == 0) {
            for (int j = 0; j < C; j++) {
                matrix[0][j] = 0;
            }
        }

        // See if the first column needs to be set to zero as well
        if (isCol) {
            for (int i = 0; i < R; i++) {
                matrix[i][0] = 0;
            }
        }
    }
}
