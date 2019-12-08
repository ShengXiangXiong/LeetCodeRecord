package sort;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * Created By ShengXiang.Xiong on 2019/12/8
 * 给定一个用字符数组表示的 CPU 需要执行的任务列表。其中包含使用大写的 A - Z 字母表示的26 种不同种类的任务。
 * 任务可以以任意顺序执行，并且每个任务都可以在 1 个单位时间内执行完。CPU 在任何一个单位时间内都可以执行一个任务，或者在待命状态。
 * 然而，两个相同种类的任务之间必须有长度为 n 的冷却时间，因此至少有连续 n 个单位时间内 CPU 在执行不同的任务，或者在待命状态。
 * <p>
 * 你需要计算完成所有任务所需要的最短时间。
 * <p>
 * 输入: tasks = ["A","A","A","B","B","B"], n = 2
 * 输出: 8
 * 执行顺序: A -> B -> (待命) -> A -> B -> (待命) -> A -> B.
 * <p>
 * 任务的总个数为 [1, 10000]。
 * n 的取值范围为 [0, 100]。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/task-scheduler
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class LeastInterval {
    /**
     * 根据桶的思想，每一个桶的大小即为n+1，里面只能放不同的任务，因此最直观的想法就是每次取前n+1数量的任务
     * 在选择每一轮中的任务时，我们可以用优先队列（堆）来代替排序。在一开始，我们把所有的任务加入到优先队列中。
     * 在每一轮，我们从优先队列中选择最多 n + 1 个任务，把它们的数量减去 1，再放回堆中（如果数量不为 0），直到堆为空。
     *
     * @param tasks
     * @param n
     * @return
     */
    public static int leastInterval(char[] tasks, int n) {
        int res = 0;
        PriorityQueue<Integer> q = new PriorityQueue<>(Comparator.comparingInt(o -> -o));
        int[] dic = new int[256];
        for (char task : tasks) {
            dic[task]++;
        }
        for (int i : dic) {
            if (i > 0) {
                q.add(i);
            }
        }
        while (q.size() > 0) {
            int[] tmp = new int[n + 1];
            int cnt = 0;
            for (int i = 0; i < n + 1 && q.size() > 0; i++) {
                tmp[i] = q.poll() - 1;
                cnt++;
            }
            for (int k : tmp) {
                if (k > 0) {
                    q.add(k);
                }
            }
            if (q.size() > 0) {
                res += n + 1;
            } else {
                res += cnt;
            }
        }
        return res;
    }

    /**
     * 更进一步，可以推出公式 (count[25] - 1) * (n + 1) + maxCount
     * 解释一下这个公式怎么来的
     * <p>
     * 1. 假设数组 ["A","A","A","B","B","C"]，n = 2，A的频率最高，记为count = 3，所以两个A之间必须间隔2个任务，
     * 才能满足题意并且是最短时间（两个A的间隔大于2的总时间必然不是最短），因此执行顺序为： A->X->X->A->X->X->A，
     * 这里的X表示除了A以外其他字母，或者是待命，不用关心具体是什么，反正用来填充两个A的间隔的。
     * 上面执行顺序的规律是： 有count - 1个A，其中每个A需要搭配n个X，再加上最后一个A，所以总时间为 (count - 1) * (n + 1) + 1
     * <p>
     * 2. 要注意可能会出现多个频率相同且都是最高的任务，比如 ["A","A","A","B","B","B","C","C"]，所以最后会剩下一个A和一个B，
     * 因此最后要加上频率最高的不同任务的个数 maxCount
     * <p>
     * 3. 公式算出的值可能会比数组的长度小，如["A","A","B","B"]，n = 0，此时要取数组的长度
     *
     * @param tasks
     * @param n
     * @return
     */
    public static int leastInterval2(char[] tasks, int n) {
        int[] count = new int[26];
        for (int i = 0; i < tasks.length; i++) {
            count[tasks[i] - 'A']++;
        }//统计词频
        Arrays.sort(count);//词频排序，升序排序，count[25]是频率最高的
        int maxCount = 0;
        //统计有多少个频率最高的字母
        for (int i = 25; i >= 0; i--) {
            if (count[i] != count[25]) {
                break;
            }
            maxCount++;
        }
        //公式算出的值可能会比数组的长度小，取两者中最大的那个
        return Math.max((count[25] - 1) * (n + 1) + maxCount, tasks.length);
    }

    public static void main(String[] args) {
        char[] tasks = {'A', 'A', 'A', 'B', 'B', 'C', 'A', 'D'};
        int n = 1;
        System.out.println(leastInterval(tasks, n));
    }
}
