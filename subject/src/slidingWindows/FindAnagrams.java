package slidingWindows;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created By ShengXiang.Xiong on 2019/11/28
 * 给定一个字符串 s 和一个非空字符串 p，找到 s 中所有是 p 的字母异位词的子串，返回这些子串的起始索引。
 * 字符串只包含小写英文字母，并且字符串 s 和 p 的长度都不超过 20100。
 * 字母异位词指字母相同，但排列不同的字符串。
 * 不考虑答案输出的顺序。
 * <p>
 * 输入:
 * s: "cbaebabacd" p: "abc"
 * <p>
 * 输出:
 * [0, 6]
 * <p>
 * 解释:
 * 起始索引等于 0 的子串是 "cba", 它是 "abc" 的字母异位词。
 * 起始索引等于 6 的子串是 "bac", 它是 "abc" 的字母异位词。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/find-all-anagrams-in-a-string
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class FindAnagrams {
    /**
     * 很简单，hash统计+双指针
     * 值得注意的一点是，左指针的移动有两种情况，一种是如果右指针所在的字符不在子串中，则左指针直接移动到右指针位置+1，
     * 然后右指针移动到距左指针的最大窗口处
     * 另一种是，右指针所在的字符在子串中，只是多了，这种情况就不能跳过了，左指针只能向右不断扩展，并更新hash记录，直至
     * 右指针所指的字符在hash记录中正常。
     *
     * @param s1
     * @param s2
     * @return
     */
    public static List<Integer> findAnagrams(String s1, String s2) {
        List<Integer> res = new ArrayList<>();
        if (s1.length() == 0 || s2.length() == 0) {
            return res;
        }
        char[] ch1 = s1.toCharArray();
        char[] ch2 = s2.toCharArray();
        int n = s1.length();
        HashMap<Character, Integer> wc = new HashMap<>();
        for (char c : ch2) {
            wc.put(c, wc.getOrDefault(c, 0) + 1);
        }
        HashMap<Character, Integer> count = new HashMap<>(wc);
        int p = 0;
        int s = p;
        int q = p + s2.length() - 1;
        while (q < ch1.length) {
            while (p <= q && count.containsKey(ch1[p]) && count.get(ch1[p]) > 0) {
                count.put(ch1[p], count.get(ch1[p]) - 1);
                p++;
            }
            while (p <= ch1.length && p == q + 1) {
                res.add(s);
                q = p;
                if (p < ch1.length && ch1[s] == ch1[p]) {
                    s++;
                    p++;
                }
            }
            if (p < ch1.length && count.containsKey(ch1[p])) {
                while (count.get(ch1[p]) == 0) {
                    count.put(ch1[s], count.get(ch1[s]) + 1);
                    s++;
                }
            } else {
                s = ++p;
                count = new HashMap<>(wc);
            }
            q = s + ch2.length - 1;
        }
        return res;
    }

    /**
     * 优化，通过数组来作为hash表，将char的ascll码作为key
     *
     * @param s1
     * @param s2
     * @return
     */
    public static List<Integer> findAnagrams2(String s1, String s2) {
        List<Integer> res = new ArrayList<>();
        if (s1.length() == 0 || s2.length() == 0) {
            return res;
        }
        char[] ch1 = s1.toCharArray();
        char[] ch2 = s2.toCharArray();
        int[] count = new int[128];
        for (char c : ch2) {
            count[c]++;
        }
        int[] wc = count.clone();
        int p = 0;
        int s = p;
        int q = p + s2.length() - 1;
        while (q < ch1.length) {
            while (p <= q && wc[ch1[p]] > 0) {
                wc[ch1[p]]--;
                p++;
            }
            while (p <= ch1.length && p == q + 1) {
                res.add(s);
                q = p;
                if (p < ch1.length && ch1[s] == ch1[p]) {
                    s++;
                    p++;
                }
            }
            if (p < ch1.length && count[ch1[p]] > 0) {
                while (wc[ch1[p]] == 0) {
                    wc[ch1[s]]++;
                    s++;
                }
            } else {
                s = ++p;
                wc = count.clone();
            }
            q = s + ch2.length - 1;
        }
        return res;
    }

    public static void main(String[] args) {
        String s = "cbaebabacd";
        String p = "abc";
        for (Integer anagram : findAnagrams2(s, p)) {
            System.out.print(anagram + " ");
        }
    }
}
