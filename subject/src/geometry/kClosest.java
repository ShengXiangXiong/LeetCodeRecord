package geometry;

import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Created by Xursan on 2019/9/15.
 * <p>
 * 我们有一个由平面上的点组成的列表 points。需要从中找出 K 个距离原点 (0, 0) 最近的点。
 * （这里，平面上两点之间的距离是欧几里德距离。）
 * 你可以按任何顺序返回答案。除了点坐标的顺序之外，答案确保是唯一的。
 * <p>
 * 输入：points = [[1,3],[-2,2]], K = 1
 * 输出：[[-2,2]]
 * 解释：
 * (1, 3) 和原点之间的距离为 sqrt(10)，
 * (-2, 2) 和原点之间的距离为 sqrt(8)，
 * 由于 sqrt(8) < sqrt(10)，(-2, 2) 离原点更近。
 * 我们只需要距离原点最近的 K = 1 个点，所以答案就是 [[-2,2]]。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/k-closest-points-to-origin
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class kClosest {
    /**
     * 大顶堆即可
     *
     * @param points
     * @param K
     * @return
     */
    public static int[][] kClosest(int[][] points, int K) {
        Queue<int[]> q = new PriorityQueue<>(((o1, o2) ->
                (int) (-Math.pow(o1[0], 2) - Math.pow(o1[1], 2) + Math.pow(o2[0], 2) + Math.pow(o2[1], 2))
        ));
        for (int[] point : points) {
            if (q.size() == K) {
                int dis = point[0] * point[0] + point[1] * point[1];
                int[] tmp = q.peek();
                int tmpDis = tmp[0] * tmp[0] + tmp[1] * tmp[1];
                if (tmpDis > dis) {
                    q.poll();
                    q.add(point);
                }
            } else {
                q.add(point);
            }
        }
        return q.toArray(new int[][]{});
    }

    public static void main(String[] args) {
        int[][] points = {{3, 3}, {5, -1}, {-2, 4}};
        int K = 2;
        for (int[] ints : kClosest(points, K)) {
            System.out.println(ints[0] + " " + ints[1]);
        }
    }
}
