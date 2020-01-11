package sort;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Created by Xursan on 2020/1/11.
 * 输入: nums = [1,1,1,2,2,3], k = 2
 * 输出: [1,2]
 * <p>
 * 输入: nums = [1], k = 1
 * 输出: [1]
 * 说明：
 * <p>
 * 你可以假设给定的 k 总是合理的，且 1 ≤ k ≤ 数组中不相同的元素的个数。
 * 你的算法的时间复杂度必须优于 O(n log n) , n 是数组的大小。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/top-k-frequent-elements
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class TopKFrequent {
    /**
     * 很简单统计一遍，然后使用小顶堆，排序方案为，按map中key所对应的val来排序——时间复杂度O(n log k)
     * 在k大于n/2的情况下，改用大顶堆，删除前n-k个元素即可。
     * <p>
     * 另外还可以选择快排，快排的划分可以快速定位到第k大（小）的位置——时间复杂度O(n)
     * 划分时根据其hash中对应key存储的个数val来划分，实际上数组中存的值仍然为key，相当于是快排根据key索引val，进行划分
     *
     * @param nums
     * @param k
     * @return
     */
    public static List<Integer> topKFrequent(int[] nums, int k) {
        HashMap<Integer, Integer> m = new HashMap<>();
        PriorityQueue<Integer> q = new PriorityQueue<>(((o1, o2) -> m.get(o1) - m.get(o2)));
        for (int num : nums) {
            m.put(num, m.getOrDefault(num, 0) + 1);
        }
        for (Integer key : m.keySet()) {
            if (q.size() < k) {
                q.add(key);
            } else {
                if (m.get(q.peek()) < m.get(key)) {
                    q.poll();
                    q.add(key);
                }
            }
        }
        List<Integer> res = new ArrayList<>();
        res.addAll(q);
        return res;
    }

    public static void main(String[] args) {
        int[] nums = {1, 1, 1, 2, 2, 3, 4, 5, 6, 1, 2, 4};
        int k = 3;
        for (int num : topKFrequent(nums, k)) {
            System.out.println(num);
        }
    }
}
