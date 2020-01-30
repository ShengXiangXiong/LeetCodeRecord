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

    /**
     * 基本思路：循环递归+记忆数组
     * 假设字符串长n，由于切分方案有 2^n 种，所以想 暴力做，基本是不可能的。
     * 我们再仔细思考一下，其实我们可以考虑减治的思想，每次都从当前字符串首开始截取回文串，
     * 然后对后面的字符串继续递归调用找到所有的分割方案，如此一来即可完成。
     * <p>
     * 从上而下思考可能更容易一点：举个例子，当前已经找到了（2...n）字符串的所有划分方案，那必然对于以1为首的回文串的
     * 所有划分方案，就是将（2...n-1）字符串的所有划分方案的返回结果加上本次截取的回文串即可；
     * 同理，对于截取的（1..i）的回文串而言，他只需要考虑（i+1...n）的递归返回结果，并加入该结果集即可。
     * 从上面分析自然考虑到可以使用记忆数组，存储任意位置的划分方案。
     *
     * @param s
     * @return
     */
    public List<List<String>> partition(String s) {
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
        if (q == cs.length()) {
            res.add(new ArrayList<>());
            return res;
        }
        while (q < cs.length()) {
            if (valid(p, q, cs, mem)) {
                List<List<String>> tmp;
                String str = cs.substring(p, q+1);
                String key = q + 1 + "_" + cs.length();
                if (m.containsKey(key)) {
                    tmp = m.get(key);
                } else {
                    tmp = find(q + 1, mem, cs, m);
                }
                for (List<String> tp : tmp) {
                    //注意点，不能直接对tp操作，因为tp是取自hashmap，会对原来的记忆数组造成影响
                    List<String> ss = new ArrayList<>();
                    ss.add(str);
                    ss.addAll(tp);
                    res.add(ss);
                }
                mem[p][q] = true;
            }
            q++;
        }
        String key = p + "_" + cs.length();
        m.put(key, res);
        return res;
    }

    public static void main(String[] args) {
        String str = "aabaaba";
        Partition p = new Partition();
        for (List<String> strs : p.partition(str)) {
            for (String s : strs) {
                System.out.print(s + " ");
            }
            System.out.println();
        }
    }
}
