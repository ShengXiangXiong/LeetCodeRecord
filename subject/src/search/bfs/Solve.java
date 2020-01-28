package search.bfs;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * Created by Xursan on 2020/1/22.
 * 130. 被围绕的区域
 */
public class Solve {
    private int[][] to = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};

    public void solve(char[][] board) {
        if (board.length == 0 || board[0].length == 0) {
            return;
        }
        int m = board.length;
        int n = board[0].length;
        boolean[][] vis = new boolean[m][n];
        for (int i = 0; i < m; i++) {
            if (board[i][0] == 'O') {
                vis[i][0] = true;
                bfs(board, vis, i, 0);
            }
            if (!vis[i][n - 1] && board[i][n - 1] == 'O') {
                vis[i][n - 1] = true;
                bfs(board, vis, i, n - 1);
            }
        }
        for (int i = 0; i < n; i++) {
            if (!vis[0][i] && board[0][i] == 'O') {
                vis[0][i] = true;
                bfs(board, vis, 0, i);
            }
            if (!vis[m - 1][i] && board[m - 1][i] == 'O') {
                vis[m - 1][i] = true;
                bfs(board, vis, m - 1, i);
            }
        }
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] == 'O' && !vis[i][j]) {
                    board[i][j] = 'X';
                }
            }
        }
    }

    public void bfs(char[][] board, boolean[][] vis, int i, int j) {
        Queue<int[]> q = new ArrayDeque<>();
        q.add(new int[]{i, j});
        while (!q.isEmpty()) {
            int s = q.size();
            while (s-- > 0) {
                int[] tmp = q.poll();
                for (int[] ints : to) {
                    int x = tmp[0] + ints[0];
                    int y = tmp[1] + ints[1];
                    if (x < board.length && x >= 0 && y < board[0].length && y >= 0 && !vis[x][y] && board[x][y] == 'O') {
                        q.add(new int[]{x, y});
                        vis[x][y] = true;
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        String s = "X X X X\n" +
                "X O O X\n" +
                "X O X X\n" +
                "X X X X";
        char[][] cs = utils.matrixTrans.str2CharArray(s);
        Solve solve = new Solve();
        solve.solve(cs);
        for (char[] c : cs) {
            for (char c1 : c) {
                System.out.print(c1 + " ");
            }
            System.out.println();
        }
    }
}
