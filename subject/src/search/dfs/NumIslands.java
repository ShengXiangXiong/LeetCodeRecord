package search.dfs;

/**
 * Created By ShengXiang.Xiong on 2019/10/20
 * 给定一个由 '1'（陆地）和 '0'（水）组成的的二维网格，计算岛屿的数量。一个岛被水包围，
 * 并且它是通过水平方向或垂直方向上相邻的陆地连接而成的。你可以假设网格的四个边均被水包围。
 * 输入:
 * 11110
 * 11010
 * 11000
 * 00000
 * 输出: 1
 * <p>
 * 输入:
 * 11000
 * 11000
 * 00100
 * 00011
 * 输出: 3
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/number-of-islands
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class NumIslands {
    private static int[][] towards = {{1, 0}, {0, 1}, {-1, 0}, {0, -1}};
    private static boolean[][] mem;

    public static void dfs(int i, int j, char[][] grid) {
        mem[i][j] = true;
        for (int[] toward : towards) {
            int i1 = toward[0] + i;
            int j1 = toward[1] + j;
            if (i1 >= 0 && i1 < grid.length && j1 >= 0 && j1 < grid[0].length && grid[i1][j1] == '1' && !mem[i1][j1]) {
                dfs(i1, j1, grid);
            }
        }
    }

    /**
     * 直接dfs完成即可，由于不存在回溯的情况，不需要重设vis
     *
     * @param grid
     * @return
     */
    public static int numIslands(char[][] grid) {
        if (grid.length == 0 || grid[0].length == 0) {
            return 0;
        }
        int n = grid.length;
        int m = grid[0].length;
        mem = new boolean[n][m];
        int ans = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (grid[i][j] == '1' && !mem[i][j]) {
                    ans++;
                    dfs(i, j, grid);
                }
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        String a = "11110\n" +
                "11010\n" +
                "11000\n" +
                "00000";
        char[][] grid = utils.matrixTrans.str2ZOCharArray(a);
        System.out.println(numIslands(grid));
    }
}
