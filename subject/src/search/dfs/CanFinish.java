package search.dfs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * Created By ShengXiang.Xiong on 2019/11/5
 * <p>
 * 现在你总共有 n 门课需要选，记为 0 到 n-1。
 * 在选修某些课程之前需要一些先修课程。 例如，想要学习课程 0 ，你需要先完成课程 1 ，我们用一个匹配来表示他们: [0,1]
 * 给定课程总量以及它们的先决条件，判断是否可能完成所有课程的学习？
 * <p>
 * 输入: 2, [[1,0],[0,1]]
 * 输出: false
 * 解释: 总共有 2 门课程。学习课程 1 之前，你需要先完成​课程 0；并且学习课程 0 之前，你还应先完成课程 1。这是不可能的。
 */
public class CanFinish {
    /**
     * 明显这个可以通过DFS来做，这里其输入是邻接表矩阵，可以通过map结构存储此图结构  即map<key-int,value-List<int>>
     * 每当遍历一个dfs路径，就存储其所经历过的点vis[key]，如果在遍历dfs路径中发现vis[key]=true，表示成环
     * 若遍历完整个map，都没有一条dfs路径成环的话，则表示不存在成环的结构
     *
     * @param numCourses
     * @param prerequisites
     * @return
     */
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        HashSet<Integer> mem = new HashSet<>();
        HashMap<Integer, List<Integer>> map = new HashMap<>();
        for (int[] nums : prerequisites) {
            map.put(nums[0], map.getOrDefault(nums[0], new ArrayList<>()));
            map.get(nums[0]).add(nums[1]);
        }
        for (Integer key : map.keySet()) {
            if (!dfs(map, key, new HashSet<>(), mem)) {
                return false;
            }
        }
        return true;
    }

    public boolean dfs(HashMap<Integer, List<Integer>> map, int key, HashSet<Integer> vis, HashSet<Integer> mem) {
        if (vis.contains(key)) {
            return false;
        }
        vis.add(key);
        if (!mem.contains(key) && map.get(key) != null) {
            for (Integer cnt : map.get(key)) {
                if (dfs(map, cnt, vis, mem)) {
                    mem.add(cnt);
                } else {
                    return false;
                }
            }
        }
        vis.remove(key);
        mem.add(key);
        return true;
    }

    public static void main(String[] args) {
        String s = "[[1,0],[2,6],[1,7],[6,4],[7,0],[0,5]]";
        CanFinish c = new CanFinish();
        System.out.println(c.canFinish(3, utils.matrixTrans.strTrans2Array(s)));
    }
}
