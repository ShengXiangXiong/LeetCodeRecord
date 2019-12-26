package sort;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Created By ShengXiang.Xiong on 2019/10/24
 * <p>
 * 给定一个未排序的整数数组，找出最长连续序列的长度。
 * 要求算法的时间复杂度为 O(n)。
 * <p>
 * 输入: [100, 4, 200, 1, 3, 2]
 * 输出: 4
 * 解释: 最长连续序列是 [1, 2, 3, 4]。它的长度为 4。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/longest-consecutive-sequence
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class LongestConsecutive {
    /**
     * 并查集。
     * 遍历数组，对于i，每次在root中按位置（i+1,i-1,i）索引，如果不存在则自身为root，即root[i]=i,并设定cnt[i]=1；
     * 如果root[i-1]存在，则cnt[root[i-1]]+=cnt[root[i]]，root[i]=root[i-1];
     * 如果root[i+1]存在（不等于0）,则cnt[root[i或i+1]]+=cnt[root[i]]，且root[i]=root[root[i]]
     * 在遍历时保持一个ans = math.max(ans,cnt[root[i]])
     *
     * 这道题用并查集可能会超时，因为并查集的root根会变化，必须要while循环修改，这会导致O(n^2)的复杂度
     *
     * @param nums
     * @return
     */
    public static int longestConsecutive(int[] nums) {
        //分别计数
        HashMap<Integer, Integer> cnt = new HashMap<>();
        //root记录
        HashMap<Integer, Integer> root = new HashMap<>();

        int ans = 0;
        for (int num : nums) {
            int pa;
            int tmp = 1;
            if (root.containsKey(num)) {
                continue;
            }
            root.put(num, num);
            cnt.put(num, 1);
            if (root.containsKey(num + 1) && root.containsKey(num - 1)) {
                pa = num;
                int l = root.get(num - 1);
                while (root.get(l) != l) {
                    l = root.get(l);
                }
                int r = root.get(num + 1);
                while (root.get(r) != r) {
                    r = root.get(r);
                }
                tmp = cnt.get(l) + cnt.get(r) + 1;
                cnt.put(num, tmp);
                root.put(root.get(num - 1), pa);
                root.put(root.get(num + 1), pa);
            } else if (root.containsKey(num + 1)) {
                pa = root.get(num + 1);
                while (root.get(pa) != pa) {
                    pa = root.get(pa);
                }
                tmp = cnt.get(pa) + 1;
                cnt.put(pa, tmp);
                root.put(num, pa);
            } else if (root.containsKey(num - 1)) {
                pa = root.get(num - 1);
                while (root.get(pa) != pa) {
                    pa = root.get(pa);
                }
                tmp = cnt.get(pa) + 1;
                cnt.put(pa, tmp);
                root.put(num, pa);
            }
            ans = Math.max(tmp, ans);
        }
        return ans;
    }

    /**
     * hash+构造线性空间
     * 其实这也是最简单的思路，我们循环将nums中的每个num作为头节点，一直向上构造递增序列，然后在构造的过程中，记录序列的
     * 最长值即可。时间优化上，我们可以直接通过hash查找是否存在其递增序列，每次查找其+1的数即可。
     *
     * @param nums
     * @return
     */
    public static int solution(int[] nums) {
        Set<Integer> num_set = new HashSet<>();
        for (int num : nums) {
            num_set.add(num);
        }
        int longestStreak = 0;
        //以每个num作为头结点，通过hash循环查找+1递增的最长序列
        for (int num : num_set) {
            if (!num_set.contains(num - 1)) {
                int currentNum = num;
                int currentStreak = 1;
                while (num_set.contains(currentNum + 1)) {
                    currentNum += 1;
                    currentStreak += 1;
                }
                longestStreak = Math.max(longestStreak, currentStreak);
            }
        }
        return longestStreak;

    }

    /**
     * hash+区间集合（用双指针head、tail记录）
     * 由于题目是保证连续，故对于重复的可以直接跳过，仔细思考，遍历时，我们只需记录每一个连续区间所对应的的极值即可。
     * 然后在遍历时不断更新head、tail记录以及他们所对应的cnt值即可，相当于就是记录每一个连续区间的长度。
     *
     * @param nums
     * @return
     */
    public static int sol(int[] nums) {
        HashMap<Integer, Integer> cnt = new HashMap<>();
        HashMap<Integer, Integer> head = new HashMap<>();
        HashMap<Integer, Integer> tail = new HashMap<>();
        int ans = 0;
        for (int num : nums) {
            if (cnt.containsKey(num)) {
                continue;
            }
            cnt.put(num, 1);
            head.put(num, num);
            tail.put(num, num);
            if (cnt.containsKey(num - 1) && cnt.containsKey(num + 1)) {
                cnt.put(head.get(num + 1), cnt.get(num + 1) + cnt.get(num - 1) + 1);
                cnt.put(tail.get(num - 1), cnt.get(head.get(num + 1)));
                cnt.put(num, cnt.get(head.get(num + 1)));
                head.put(tail.get(num - 1), head.get(num + 1));
                tail.put(head.get(num + 1), tail.get(num - 1));
            } else if (cnt.containsKey(num - 1)) {
                cnt.put(num, cnt.get(num - 1) + 1);
                cnt.put(tail.get(num - 1), cnt.get(num));
                head.put(tail.get(num - 1), num);
                tail.put(num, tail.get(num - 1));
            } else if (cnt.containsKey(num + 1)) {
                cnt.put(head.get(num + 1), cnt.get(num + 1) + 1);
                cnt.put(num, cnt.get(head.get(num + 1)));
                head.put(num, head.get(num + 1));
                tail.put(head.get(num + 1), num);
            }
            ans = Math.max(ans, cnt.get(head.get(num)));
        }
        return ans;
    }

    public static void main(String[] args) {
        int[] nums = {100, 4, 200, 1, 3, 2, 9, 7, 5, 3, 8, 12, 11, 10};
        System.out.println(sol(nums));
    }
}
