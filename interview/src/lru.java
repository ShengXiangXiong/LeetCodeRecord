import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Xursan on 2020/3/4.
 * 缓存中使用LRU内存淘汰旧数据的策略，会按照最近访问时间进行排序，
 * 如内存空间不足将最老的数据淘汰。
 * 假设内存空间为6，原本内存中没有数据，对内存中数据的访问顺序如下：
 * 1, 2, 5, 3, 4, 6,1, 3, 3, 6, 7, 6,9
 * 请打印出最后内存中的数据，给出最优时间复杂度的方案 并编码完成
 */
public class lru {
    public static List<Integer> getLru(int[] nums, int k) {
        LinkedList<Integer> q = new LinkedList();
        HashSet<Integer> s = new HashSet<>();
        List<Integer> res = new ArrayList<>();
        for (Integer num : nums) {
            if (s.contains(num)) {
                q.remove(num);
                q.addFirst(num);
            } else {
                if (q.size() >= k) {
                    q.pollLast();
                }
                q.addFirst(num);
                s.add(num);
            }
        }
        for (Integer o : q) {
            res.add((o));
        }
        return res;
    }

    public static void main(String[] args) {
        int[] nums = {1, 2, 5, 3, 4, 6, 1, 3, 3, 6, 7, 6, 9};
        int k = 6;
        for (Integer integer : getLru(nums, k)) {
            System.out.println(integer);
        }
    }
}
