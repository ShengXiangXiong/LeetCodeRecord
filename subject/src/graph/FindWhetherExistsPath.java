package graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by Xursan on 2020/2/25.
 * 面试题 04.01. 节点间通路
 * 节点间通路。给定有向图，设计一个算法，找出两个节点之间是否存在一条路径。
 * 输入：n = 3, graph = [[0, 1], [0, 2], [1, 2], [1, 2]], start = 0, target = 2
 * 输出：true
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/inter-node-access-lcci
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class FindWhetherExistsPath {
    /**
     * 基于hashmap方式实现邻接表
     *
     * @param n
     * @param graph
     * @param start
     * @param target
     * @return
     */
    public static boolean findWhetherExistsPath(int n, int[][] graph, int start, int target) {
        HashMap<Integer, HashSet<Integer>> m = new HashMap<>();
        for (int[] ints : graph) {
            if (ints[0] != ints[1]) {
                HashSet<Integer> hs = m.getOrDefault(ints[0], new HashSet<>());
                hs.add(ints[1]);
                m.put(ints[0], hs);
            }
        }
        return dfs(m, start, target);
    }

    public static boolean dfs(HashMap<Integer, HashSet<Integer>> g, int s, int t) {
        HashSet<Integer> hs = g.get(s);
        if (hs != null) {
            if (hs.contains(t)) {
                return true;
            } else {
                for (Integer h : hs) {
                    if (dfs(g, h, t)) {
                        hs.add(t);
                        g.put(s, hs);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 基于ArrayList[]数组的方式实现邻接表，速度更快，空间更小
     *
     * @param n
     * @param graph
     * @param start
     * @param end
     * @return
     */
    public boolean findWhetherExistsPath1(int n, int[][] graph, int start, int end) {
        ArrayList<Integer>[] mtx = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            if (mtx[graph[i][0]] == null) {
                mtx[graph[i][0]] = new ArrayList<>();
            }
            //注意这里邻接表中存的是边，并不是终点
            mtx[graph[i][0]].add(i + 1);
        }
        return dfs(mtx, start, end, graph);
    }

    public boolean dfs(ArrayList<Integer>[] mtx, int start, int end, int[][] g) {
        if (mtx[start] != null) {
            for (int i = 0; i < mtx[start].size(); i++) {
                int[] p = g[mtx[start].get(i) - 1];
                if (p[1] == end) {
                    return true;
                }
                if (dfs(mtx, p[1], end, g)) {
                    return true;
                }
            }
        }
        return false;
    }


    public static void main(String[] args) {
        String graph = "[[0, 1], [0, 2], [0, 4], [0, 4], [0, 1], [1, 3], [1, 4], [1, 3], [2, 3], [3, 4]]";
        int[][] g = utils.matrixTrans.strTrans2Array(graph);
        int n = 5;
        int start = 0;
        int target = 4;
        System.out.println(findWhetherExistsPath(n, g, start, target));
    }
}
