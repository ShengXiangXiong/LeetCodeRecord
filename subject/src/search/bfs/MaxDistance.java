package search.bfs;

import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by Xursan on 2020/3/5.
 * 1162. 地图分析
 */
public class MaxDistance {
    int[][] to = new int[][]{{1, 0}, {0, 1}, {-1, 0}, {0, -1}};

    //一开始以某一个海洋作为出发点，然后循环求出所有海洋到陆地的最短距离后，最后再求最远距离，但始终超时
    public int bfs(int[][] grid, int[][] mem, int x, int y) {
        int n = grid.length;
        int m = grid[0].length;
        Queue<int[]> q = new LinkedList<>();
        boolean[][] vis = new boolean[n][m];
        q.add(new int[]{x, y});
        int cnt = 1;
        int minRes = 1000000;
        while (!q.isEmpty()) {
            int k = q.size();
            while (k-- > 0) {
                int[] tmp = q.poll();
                vis[tmp[0]][tmp[1]] = true;
                for (int[] ints : to) {
                    int ix = tmp[0] + ints[0];
                    int jx = tmp[1] + ints[1];
                    if (ix >= 0 && ix < n && jx >= 0 && jx < m && !vis[ix][jx]) {
                        if (mem[ix][jx] > 0) {
                            minRes = Math.min(minRes, cnt + mem[ix][jx]);
                        } else if (grid[ix][jx] == 1) {
                            minRes = Math.min(minRes, cnt);
                        } else {
                            q.add(new int[]{ix, jx});
                        }
                    }
                }
            }
            cnt++;
            if (cnt > minRes) {
                break;
            }
        }
        mem[x][y] = minRes;
        return minRes;
    }

    public int maxDistance(int[][] grid) {
        if (grid.length == 0 || grid[0].length == 0) {
            return -1;
        }
        int n = grid.length;
        int m = grid[0].length;
        int[][] mem = new int[n][m];
        int ans = -1;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (grid[i][j] == 0 && mem[i][j] == 0) {
                    if (mem[i][j] > 0) {
                        ans = Math.max(ans, mem[i][j]);
                    } else {
                        int dis = bfs(grid, mem, i, j);
                        if (dis == 1000000) {
                            return -1;
                        }
                        ans = Math.max(ans, dis);
                    }
                }
            }
        }
        return ans;
    }


    /**
     * 换一种思路，以陆地作为出发点，则每到达一个海洋，一定是该海洋的最短距离。
     * 这里值得学习的一点是，以前做得最多的bfs都是单源bfs，即从一个点出发去bfs，而这里如果再使用这种单源bfs的话，必定
     * 需要循环求最短路径，显然是不可取的，所以可以采取多源bfs的策略，从多个出发点同时bfs，到达每个海洋（0）时，必定是
     * 该海洋到达陆地的最短距离，相当于一个包围圈，每次都是逐步缩小的，所以被访问的海洋一定是其对应的最短距离，所以可以
     * 得出结论，最后一个被访问的海洋就是离陆地最远的海洋。
     */
    public int maxDistance1(int[][] grid) {
        if (grid.length == 0 || grid[0].length == 0) {
            return -1;
        }
        int n = grid.length;
        int m = grid[0].length;
        Queue<int[]> q = new ArrayDeque<>();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (grid[i][j] == 1) {
                    q.add(new int[]{i, j});
                }
            }
        }
        if (q.size() == 0 || q.size() == m * n) {
            return -1;
        }
        int ans = -1;
        while (!q.isEmpty()) {
            int k = q.size();
            while (k-- > 0) {
                int[] tmp = q.poll();
                ans = grid[tmp[0]][tmp[1]];
                for (int[] ints : to) {
                    int ix = tmp[0] + ints[0];
                    int jx = tmp[1] + ints[1];
                    if (ix >= 0 && ix < n && jx >= 0 && jx < m && grid[ix][jx] == 0) {
                        grid[ix][jx] = grid[tmp[0]][tmp[1]] + 1;
                        q.add(new int[]{ix, jx});
                    }
                }
            }
        }
        return ans - 1;
    }

    public static void main(String[] args) {
        MaxDistance md = new MaxDistance();
        String str = "[[1,1,1],[1,1,1],[1,1,1]]";
        int[][] grid = utils.matrixTrans.strTrans2Array(str);
        System.out.println(md.maxDistance1(grid));
    }
}
