package array;

import utils.matrixTrans;

import java.util.*;

/**
 * Created by Xursan on 2019/8/31.
 * 假设有打乱顺序的一群人站成一个队列。 每个人由一个整数对(h, k)表示，其中h是这个人的身高，k是排在这个人前面且身高大于或等于h的人数。 编写一个算法来重建这个队列。
 * 注意：
 * 总人数少于1100人。
 * 输入:
 * [[7,0], [4,4], [7,1], [5,0], [6,1], [5,2]]
 * 输出:
 * [[5,0], [7,0], [5,2], [6,1], [4,4], [7,1]]
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/queue-reconstruction-by-height
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class ReconstructQueue {
    public static int[][] reconstructQueue(int[][] people) {
        int n = people.length;
        int[][] res = new int[n][2];
        TreeMap<Integer, Integer> mem = new TreeMap<>((o1, o2) -> o2 - o1);
        HashSet<String> ms = new HashSet<>();
        Arrays.sort(people, (o1, o2) -> o1[0] - o2[0]);
        for (int i = 0; i < n; i++) {
            for (int[] aPeople : people) {
                if (ms.contains(Arrays.toString(aPeople))) {
                    continue;
                }
                int cnt = 0;
                for (Integer integer : mem.keySet()) {
                    if (integer >= aPeople[0]) {
                        cnt += mem.get(integer);
                    } else {
                        break;
                    }
                }
                if (aPeople[1] == cnt) {
                    res[i] = aPeople;
                    mem.put(aPeople[0], mem.getOrDefault(aPeople[0], 0) + 1);
                    ms.add(Arrays.toString(aPeople));
                    break;
                }
            }
        }
        return res;
    }

    /**
     * 仔细分析发现，最矮的人people的最终位置pos和其前面的人数num(people[1])是一一对应的，所以可以每次循环找出
     * 最矮的人，并确定其最终位置即可,注意这里的index2pos，表示的是当前序列的位置对应关系，因为每次找到一个之后，其对应
     * 的序列都会少一个
     *
     * @param people
     * @return
     */
    public static int[][] reconstructQueue2(int[][] people) {
        int n = people.length;
        int[][] res = new int[n][2];
        Arrays.sort(people, (a, b) -> a[0] == b[0] ? b[1] - a[1] : a[0] - b[0]);
        List<Integer> index2pos = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            index2pos.add(i);
        }
        for (int[] ints : people) {
            int pos = index2pos.get(ints[1]);
            res[pos] = ints;
            index2pos.remove(ints[1]);
        }
        return res;
    }

    /**
     * 换另一种思路，要是我们已经确定好了一个有序队列B，每次都从A中选择最高的人往里面插入，则A里面的人都高于或等于B
     * 里面的人，所以从B中选出一个人p，则其位置只会和B有关，不会受其后面人（A）的影响，也不会影响已插入到B里面的人，
     * 所以其位于A中的位置便可以确定下来，即p[1]代表的就是插入B中的位置（表示其前面有多少个人存在），但这里容易忽视的
     * 一点是，若B中存在于当前p一样高的情况下，可能会影响前面已加入的那个人的位置，所以为了避免这种情况，
     * 可以在排序时将p[1]小的排在前面，这样就不会受后面同样高的人的影响了（因为后来的的p始终插在其后面）
     * <p>
     * 假设候选队列为 A，已经站好队的队列为 B.
     * 从 A 里挑身高最高的人 x 出来，插入到 B. 因为 B 中每个人的身高都比 x 要高，因此 x 插入的位置，就是看 x 前面应该有多
     * 少人就行了。比如 x 前面有 5 个人，那 x 就插入到队列 B 的第 5 个位置。
     * // 先排序
     * // [7,0], [7,1], [6,1], [5,0], [5,2], [4,4]
     * // 再一个一个插入。
     * // [7,0]
     * // [7,0], [7,1]
     * // [7,0], [6,1], [7,1]
     * // [5,0], [7,0], [6,1], [7,1]
     * // [5,0], [7,0], [5,2], [6,1], [7,1]
     * // [5,0], [7,0], [5,2], [6,1], [4,4], [7,1]
     *
     * @param people
     * @return
     */
    public static int[][] reconstructQueue3(int[][] people) {
        if (people == null || people.length == 0 || people[0].length == 0) return new int[0][0];
        Arrays.sort(people, (a, b) -> a[0] == b[0] ? a[1] - b[1] : b[0] - a[0]);
        int n = people.length;
        List<int[]> temp = new ArrayList<>();
        for (int[] p : people)
            temp.add(p[1], p);
        return temp.toArray(new int[n][2]);
    }

    public static void main(String[] args) {
        String str = "[[7,0], [4,4], [7,1], [5,0], [6,1], [5,2],[3,3],[6,3]]";
        int[][] nums = matrixTrans.strTrans2Array(str);
        for (int[] ints : reconstructQueue2(nums)) {
            System.out.println(ints[0] + " " + ints[1]);
        }
    }
}
