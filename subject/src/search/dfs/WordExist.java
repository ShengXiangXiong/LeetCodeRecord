package search.dfs;

import java.util.*;

/**
 * Created by Xursan on 2019/10/3.
 * 给定一个二维网格和一个单词，找出该单词是否存在于网格中。单词必须按照字母顺序，通过相邻的单元格内的字母构成，
 * 其中“相邻”单元格是那些水平相邻或垂直相邻的单元格。同一个单元格内的字母不允许被重复使用。
 * board =
 * [
 * ['A','B','C','E'],
 * ['S','F','C','S'],
 * ['A','D','E','E']
 * ]
 * 给定 word = "ABCCED", 返回 true.
 * 给定 word = "SEE", 返回 true.
 * 给定 word = "ABCB", 返回 false.
 */
public class WordExist {
    static int[][] toward = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
    static boolean vis[][];

    public static boolean dfs(char[][] board, String word, int pos, int x, int y, List<String> path) {
        String index = String.format("(%d,%d)", x, y);
        vis[x][y] = true;
        if (board[x][y] == word.charAt(pos)) {
            if (pos == word.length() - 1 && board[x][y] == word.charAt(pos)) {
                path.add(index);
                for (String s : path) {
                    System.out.print(s + " ");
                }
                return true;
            }
            path.add(index);
            for (int[] to : toward) {
                //边界可以循环的情况
//                int nx = (x+to[0]+board.length)%board.length;
//                int ny = (y+to[1]+board[0].length)%board[0].length;
                int nx = x + to[0];
                int ny = y + to[1];
                if (0 <= nx && nx < board.length && 0 <= ny && ny < board[0].length && !vis[nx][ny]) {
                    if (dfs(board, word, pos + 1, nx, ny, path)) {
                        return true;
                    }
                }
            }
            path.remove(index);
        }
        //记得恢复vis——表示此条路径此位置不行，但是下一条路径可能就正确
        vis[x][y] = false;
        return false;
    }

    public static boolean exist(char[][] board, String word) {
        if (word.length() == 0 || board.length == 0 || board[0].length == 0) {
            return false;
        }
        vis = new boolean[board.length][board[0].length];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (dfs(board, word, 0, i, j, new ArrayList<>())) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean exist2(char[][] board, String word) {
        if (word.length() == 0 || board.length == 0 || board[0].length == 0) {
            return false;
        }
        HashMap<Character, Integer> map = new HashMap<>();
        for (Character c : word.toCharArray()) {
            if (map.containsKey(c)) {
                map.put(c, map.get(c) + 1);
            } else {
                map.put(c, 1);
            }
        }
        int[][] toward = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
        boolean vis[][] = new boolean[board.length][board[0].length];
        Queue<int[]> q = new ArrayDeque<>();

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                int cnt = word.length();
                if (!vis[i][j] && map.containsKey(board[i][j])) {
                    q.add(new int[]{i, j});
                    vis[i][j] = true;
                    while (q.size() > 0) {
                        int s = q.size();
                        while (s-- > 0) {
                            int[] pos = q.poll();
                            Character c = board[pos[0]][pos[1]];

                            if (map.containsKey(c)) {
                                if (map.get(c) > 0) {
                                    cnt--;
                                    if (cnt == 0) {
                                        return true;
                                    }
                                    map.put(c, map.get(c) - 1);
                                }
                                for (int[] ints : toward) {
                                    int nx = (pos[0] + ints[0] + board.length) % board.length;
                                    int ny = (pos[1] + ints[1] + board[0].length) % board[0].length;
                                    if (!vis[nx][ny] && map.containsKey(board[nx][ny])) {
                                        q.add(new int[]{nx, ny});
                                    }
                                    vis[nx][ny] = true;
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    public static void main(String[] args) {
        char[][] board = utils.matrixTrans.str2CharArray("[[\"a\",\"a\",\"a\"],[\"a\",\"b\",\"b\"],[\"a\",\"b\",\"b\"],[\"b\",\"b\",\"b\"],[\"b\",\"b\",\"b\"],[\"a\",\"a\",\"a\"],[\"b\",\"b\",\"b\"],[\"a\",\"b\",\"b\"],[\"a\",\"a\",\"b\"],[\"a\",\"b\",\"a\"]]\n");
        System.out.println(exist(board, "aabaaaabbb"));
    }
}
