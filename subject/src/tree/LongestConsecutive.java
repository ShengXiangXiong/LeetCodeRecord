package tree;

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

    public static int solution(int[] nums) {
        Set<Integer> num_set = new HashSet<>();
        for (int num : nums) {
            num_set.add(num);
        }
        int longestStreak = 0;
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

    public static void main(String[] args) {
        int[] nums = {100, 4, 200, 1, 3, 2, 6, 5, 8, 7, 0};
        System.out.println(solution(nums));
    }
}
