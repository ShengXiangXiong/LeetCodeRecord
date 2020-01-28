package 回文类;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Xursan on 2020/1/23.
 * 131. 分割回文串
 * 给定一个字符串 s，将 s 分割成一些子串，使每个子串都是回文串。
 * 返回 s 所有可能的分割方案。
 * <p>
 * 输入: "aab"
 * 输出:
 * [
 * ["aa","b"],
 * ["a","a","b"]
 * ]
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/palindrome-partitioning
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class Partition {
    public List<List<String>> partition(String s) {
        List<List<String>> res = new ArrayList<>();
        boolean[][] mem = new boolean[s.length()][s.length()];
        return find(0, mem, s, new HashMap<>());
    }

    public boolean valid(int p, int q, String cs, boolean[][] mem) {
        if (q <= p + 1 && cs.charAt(p) == cs.charAt(q)) {
            return true;
        }
        if (cs.charAt(p) == cs.charAt(q) && mem[p + 1][q - 1]) {
            return true;
        }
        return false;
    }

    public List<List<String>> find(int p, boolean[][] mem, String cs, HashMap<String, List<List<String>>> m) {
        int q = p;
        List<List<String>> res = new ArrayList<>();
        while (q < cs.length()) {
            if (valid(p, q, cs, mem)) {
                List<List<String>> tmp;
                String str = cs.substring(p, q);
                String key = q + 1 + "_" + cs.length();
                if (m.containsKey(key)) {
                    tmp = m.get(key);
                } else {
                    tmp = find(q + 1, mem, cs, m);
                }

                for (List<String> tp : tmp) {
                    tp.add(str);
                    res.add(tp);
                }
                mem[p][q] = true;
            }
            q++;
        }
        String key = p + "_" + cs.length();
        m.put(key, res);
        return res;
    }
}
