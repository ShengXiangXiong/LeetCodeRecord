package search.bfs;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created By ShengXiang.Xiong on 2019/9/4
 * <p>
 * 在一个 10^6 x 10^6 的网格中，每个网格块的坐标为 (x, y)，其中 0 <= x, y < 10^6。
 * 我们从源方格 source 开始出发，意图赶往目标方格 target。每次移动，我们都可以走到网格中在四个方向上相邻的方格，只要该方格不在给出的封锁列表 blocked 上。
 * 只有在可以通过一系列的移动到达目标方格时才返回 true。否则，返回 false。
 * <p>
 * 输入：blocked = [[0,1],[1,0]], source = [0,0], target = [0,2]
 * 输出：false
 * 从源方格无法到达目标方格，因为我们无法在网格中移动。
 * <p>
 * 输入：blocked = [], source = [0,0], target = [999999,999999]
 * 输出：true
 * 因为没有方格被封锁，所以一定可以到达目标方格。
 *  
 * 提示：
 * 0 <= blocked.length <= 200
 * blocked[i].length == 2
 * 0 <= blocked[i][j] < 10^6
 * source.length == target.length == 2
 * 0 <= source[i][j], target[i][j] < 10^6
 * source != target
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/escape-a-large-maze
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class EscapeMaze {
    static int[][] move = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
    static double mx = 1e6, my = 1e6;
    static int maxSquare = 0;

    public static boolean bfs(HashSet blo, int[] s, int[] tar, int cnt) {
        Queue<int[]> q = new LinkedList<>();
        HashSet<String> mem = new HashSet<>();
        mem.add(String.valueOf(s[0]) + s[1]);
        q.add(s);
        while (q.size() > 0) {
            int n = q.size();
            while (n > 0) {
                int[] tmp = q.poll();
                for (int[] ints : move) {
                    int dx = tmp[0] + ints[0];
                    int dy = tmp[1] + ints[1];
                    if (tar[0] == dx && tar[1] == dy || cnt > maxSquare) {
                        return true;
                    }
                    String pos = String.valueOf(dx) + dy;
                    if (dx >= 0 && dx <= mx && dy >= 0 && dy <= my && !blo.contains(pos) && !mem.contains(pos)) {
                        cnt++;
                        q.add(new int[]{dx, dy});
                        mem.add(pos);
                    }
                }
                n--;
            }
        }
        return false;
    }

    /**
     * 首先我们思考一下，要想判断是否能够到达，直接从原点进行bfs，直到遇到tar处，返回true，若遍历完整个网格都没发现，则
     * 返回false，但这样做，无疑时间复杂度将会是整个网格的面积，即O(n^2)，肯定会超时。
     * 那我们换一种思路，我们从障碍物的角度出发，判断其是否能够将source包围住，或者将targe包围住即可。那么问题就被转换
     * 成了如何判断点是否被包围。
     * 而对于判断点是否被包围，这是一个图形学计算的概念，首先我们得求出所有联通块形成的环状结构（可以通过并查集，当然也
     * 可以直接dfs+hash记忆来实现），但是还有可能出现点在边上的情况，如果存在两个点在边上，则也能够形成闭合环；接下来则
     * 对每个环与点进行判断，而在具体实现的时候发现还是很难做，因为在图形学中是根据多边形的所有边和点的位置求解得到交
     * 点的奇偶个数，根据奇偶个数来判断是否相交。到此为止，明显不应该这样继续做下去，因为还得根据所有点建模做多边形的边，
     * 所以我们不妨换个思路。
     * 从障碍物形成的最大面积maxSquare出发。
     * 如果说从原点s出发，进行bfs，如果他能够到达的所有点的个数之和cnt大于maxSquare，显然s不可能被障碍物包围，因为如果被
     * 包围，其能够到达的所有点的个数之和必定小于等于maxSquare。所以通过这种方式即可判断一个点是否被障碍物包围。
     * 那么问题就是如何求出障碍物能够形成的最大面积。
     * 假设障碍物的集合长度为len，则他最多能够形成的最大面积是多少呢？对于连续平面而言，这个问题相当简单，就是已知周长为
     * len，求len所能围成闭合形状的面积是多大，那么必然是围成1/4个圆的面积最大；但是对于离散网格平面而言，还是如此吗？
     * 显然不是，因为对于每个小方块，能够贡献的长度只有自己的边长和内部的斜对角线长度，没有圆的弧线。这样斜对角线就是获
     * 取最长长度的最优解。所以需要让所有小方格的斜对角线连成边这样才能使面积最大，如果让小方格模拟圆弧，必然会造成构成
     * 弧边的长度中包含了非斜边的小方格。那么即可得出和边界围成等腰三角形的面积最大。所以最大面积为   len*（len-1）/2。
     * <p>
     * 这样做的话，并不需要求环状结构了，因为直接通过和其所能够形成的最大面积（大环）进行了检测，如果围成的面积小于最大
     * 面积即小环，如果原点不在小环里面则其一定也不在大环里面，因为他实际没有被包围住；如果在小环里面，他一定也在大环里
     * 面，所以和最大面积进行的位置判断实际也等同于和实际所形成环状结构的判断。（即使有可能不能形成环状，但是这样的话，
     * 从原点s进行的bfs得到的cnt一定仍然满足大于等于maxSquare的条件）。
     * <p>
     * 整体算法设计，就是从原点s出发，如果提前遇到了tar，则返回true；如果其累计cnt>maxSquare，则表明s未被包围，返回true,
     * 否则返回false；接下来从tar点出发，同上面逻辑一样。
     * 只有两个都不被包围，或者两个都被包围，但是他们在包围圈中提前相遇了，则返回true，否则返回false。
     *
     * @param blocked
     * @param source
     * @param target
     * @return
     */
    public static boolean isEscapePossible(int[][] blocked, int[] source, int[] target) {
        int length = blocked.length;
        maxSquare = length * (length - 1) / 2;
        HashSet<String> blo = new HashSet<>();
        for (int[] ints : blocked) {
            blo.add(String.valueOf(ints[0]) + ints[1]);
        }
        return bfs(blo, source, target, 0) && bfs(blo, target, source, 0);
    }

    public static void main(String[] args) {
        int[][] blocked = {{0, 1}, {1, 0}};
        int[] source = {0, 0};
        int[] target = {999999, 999999};
        System.out.println(isEscapePossible(blocked, source, target));
    }
}
