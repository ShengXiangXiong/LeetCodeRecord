package dp.matrix;

/**
 * longestUpPath
 */
public class longestUpPath {
    static int[][] offset = {{-1, 0}, {1, 0}, {0, 1}, {0, -1}};
    static int[][] mem;
    static int[][] mat;
    static int col;
    static int row;

    public static int longestIncreasingPath(int[][] matrix) {
        int res = 0;
        row = matrix.length;
        col = matrix[0].length;
        mem = new int[row][col];
        mat = matrix;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (mem[i][j] != 0) {
                    res = Math.max(res, mem[i][j]);
                } else {
                    res = Math.max(res, dfs(i, j));
                }
            }
        }
        return res;
    }

    public static int dfs(int x, int y) {
        if (mem[x][y] != 0) {
            return mem[x][y];
        } else {
            int res = 1;
            for (int[] off : offset) {
                int nx = x + off[0];
                int ny = y + off[1];
                if (nx < 0 || nx > row - 1 || ny < 0 || ny > col - 1 || mat[x][y] >= mat[nx][ny]) {
                    continue;
                }
                int tmp = 1 + dfs(x + off[0], y + off[1]);
                res = Math.max(tmp, res);
            }
            mem[x][y] = res;
            return res;
        }
    }

    /*
    更好的解法
    dp[i][j]代表以i,j为起点的最长的递增长度，防止重复计算（因为他的子路线肯定被算过了 当我们遍历到子路线的点是就不用计算了） 
    dfs 深度搜索 遍历四个方向 只要不出界下个值比当前值大 则dfs子路线的最大长度数加一。
    */
    private int[][] dir = {{0, -1}, {-1, 0}, {0, 1}, {1, 0}};
    private int[][] dp;

    public int dfs(int[][] matrix, int[][] dp, int i, int j) {
        if (dp[i][j] > 0)  //已经计算过的直接返回即可
            return dp[i][j];
        int n = matrix.length, m = matrix[0].length;
        int max = 1;
        for (int k = 0; k < 4; k++) {
            int x = dir[k][0] + i;
            int y = dir[k][1] + j;
            if (x < 0 || y < 0 || x >= n || y >= m || matrix[x][y] <= matrix[i][j])
                continue;
            int len = 1 + dfs(matrix, dp, x, y);
            max = Math.max(len, max);
        }
        dp[i][j] = max;
        return max;
    }

    public int longestIncreasingPathII(int[][] matrix) {
        if (matrix == null || matrix.length == 0) {
            return 0;
        }
        int n = matrix.length, m = matrix[0].length;
        dp = new int[n][m];
        int max = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                max = Math.max(dfs(matrix, dp, i, j), max);
            }
        }
        return max;
    }


    public static void main(String[] args) {
        int[][] matrix = {{0}, {1}, {5}, {5}};
        System.out.println(longestIncreasingPath(matrix));
    }

}