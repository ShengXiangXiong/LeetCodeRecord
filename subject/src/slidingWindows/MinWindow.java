package slidingWindows;

import javafx.util.Pair;

import java.util.*;

/**
 * Created by Xursan on 2019/8/18.
 * 最小覆盖子串
 * 给你一个字符串 S、一个字符串 T，请在字符串 S 里面找出：包含 T 所有字母的最小子串。
 * 示例：
 * 输入: S = "ADOBECODEBANC", T = "ABC"
 * 输出: "BANC"
 * 说明：
 * 如果 S 中不存这样的子串，则返回空字符串 ""。
 * 如果 S 中存在这样的子串，我们保证它是唯一的答案。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/minimum-window-substring
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class MinWindow {
    /**
     * 双指针法
     * 首先构造一个hash表match记录子串T的信息（字符：个数）,同时构造一个tmp表记录已匹配到的字符个数，
     * 再构造一个队列posTable，记录匹配的字符位置，方便移动head指针。
     * 然后遍历S串，head指向第一个满足hash表match的字符位置，tail指向最后一个满足match的位置。每找到一个满足match的，
     * 就相应的从match[word]-1。
     * 每次找到最后一个match的位置后，更新最小窗口长度res = Min(tail-head+1,res).
     * 然后posTable弹出队列的头元素，head指向posTable的首位置，并恢复match的信息即match[word]+1，tail指针继续向下寻找满足
     * match的最后一个字符。
     * <p>
     * 时间复杂度：O(S+T)，最坏的情况下，可能会对S中的每个元素遍历两遍，左指针和右指针各一遍（即s中的所有元素都满足match表）
     *
     * @param s
     * @param t
     * @return
     */
    public static String minWindow(String s, String t) {
        if (s.length() == 0 || t.length() == 0) {
            return "";
        }
        HashMap<Character, Integer> match = new HashMap<>();
        char[] cs = s.toCharArray();
        for (char c : t.toCharArray()) {
            match.put(c, match.getOrDefault(c, 0) + 1);
        }
        ArrayDeque<Integer> posTable = new ArrayDeque<>();
        int head = 0;
        int sn = cs.length;
        String res = "";
        int resn = s.length() + 1;
        int l = 0;
        int r = 0;
        HashMap<Character, Integer> tmp = new HashMap<>();
        while (head < sn) {
            if (match.getOrDefault(cs[head], 0) > 0) {
                //子串长度为1时的特殊情况
                if (t.length() == 1) {
                    return String.valueOf(cs[head]);
                }
                tmp.put(cs[head], 1);
                posTable.add(head);
                break;
            }
            head++;
        }
        int cnt = t.length() - 1;
        for (int tail = head + 1; tail < s.length(); tail++) {
            if (match.getOrDefault(cs[tail], 0) > 0) {
                tmp.put(cs[tail], tmp.getOrDefault(cs[tail], 0) + 1);
                posTable.add(tail);
                if (tmp.get(cs[tail]) <= match.get(cs[tail])) {
                    cnt--;
                }
            }
            while (cnt == 0) {
                if (resn > tail - head + 1) {
                    resn = tail - head + 1;
//                    res = s.substring(head,tail+1);
                    //优化
                    l = head;
                    r = tail + 1;
                }
//                res = res.length()>tail-head+1?s.substring(head,tail+1):res;
                tmp.put(cs[head], tmp.get(cs[head]) - 1);
                posTable.removeFirst();
                //判断当前head元素移除后，是否仍满足条件，因为存在多的情况
                if (tmp.get(cs[head]) < match.get(cs[head])) {
                    cnt += 1;
                }
                head = posTable.getFirst();
            }
        }
        return l == r ? "" : s.substring(l, r);
    }

    /**
     * 优化
     * 1、我们没必要每次找到一个匹配的窗口时，就进行subString，我们只需要记录最小窗口长度的起始位置即可
     * 2、数组索引比map方式更快，所以更好的方式是，将每一个字符的ascll码当做字符，这样速度将大大提升
     *
     * @param s
     * @param t
     * @return
     */
    public static String minWindow3(String s, String t) {
        char[] sChars = s.toCharArray();
        char[] pChars = t.toCharArray();
        int[] pMap = new int[128];
        int i = 0, j = 0; // 考察窗口[i,j-1]
        int count = pChars.length;
        int minLen = s.length() + 1, l = 0, r = 0;
        for (char pChar : pChars)
            pMap[pChar]++;
        while (j < sChars.length) {
            //减小计数
            if (pMap[sChars[j]] > 0)
                count--;
            pMap[sChars[j]]--;
            j++;
            //计数为 0说明区间[i,j-1] 包含 p
            while (count == 0) {
                //求得一个解
                if (j - i < minLen) {
                    minLen = j - i;
                    l = i;
                    r = j;
                }
                pMap[sChars[i]]++;
                // 增加计数
                if (pMap[sChars[i]] > 0)
                    count++;
                i++;
            }
        }
        return minLen == s.length() + 1 ? "" : s.substring(l, r);
    }

    /**
     * 针对上面的优化。
     * 我们建立一个 filtered\_Sfiltered_S列表，其中包括 SS 中的全部字符以及它们在SS的下标，但这些字符必须在 TT中出现。
     * S = "ABCDDDDDDEEAFFBC" T = "ABC"
     * filtered_S = [(0, 'A'), (1, 'B'), (2, 'C'), (11, 'A'), (14, 'B'), (15, 'C')]
     * 此处的(0, 'A')表示字符'A' 在字符串SS的下表为0。
     * 现在我们可以在更短的字符串filtered_S中使用滑动窗口法。
     *
     * @param s
     * @param t
     * @return
     */
    public static String minWindow2(String s, String t) {
        if (s.length() == 0 || t.length() == 0) {
            return "";
        }

        Map<Character, Integer> dictT = new HashMap<Character, Integer>();

        for (int i = 0; i < t.length(); i++) {
            int count = dictT.getOrDefault(t.charAt(i), 0);
            dictT.put(t.charAt(i), count + 1);
        }

        int required = dictT.size();

        // Filter all the characters from s into a new list along with their index.
        // The filtering criteria is that the character should be present in t.
        List<Pair<Integer, Character>> filteredS = new ArrayList<Pair<Integer, Character>>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (dictT.containsKey(c)) {
                filteredS.add(new Pair<Integer, Character>(i, c));
            }
        }

        int l = 0, r = 0, formed = 0;
        Map<Character, Integer> windowCounts = new HashMap<Character, Integer>();
        int[] ans = {-1, 0, 0};

        // Look for the characters only in the filtered list instead of entire s.
        // This helps to reduce our search.
        // Hence, we follow the sliding window approach on as small list.
        while (r < filteredS.size()) {
            char c = filteredS.get(r).getValue();
            int count = windowCounts.getOrDefault(c, 0);
            windowCounts.put(c, count + 1);

            if (dictT.containsKey(c) && windowCounts.get(c).intValue() == dictT.get(c).intValue()) {
                formed++;
            }

            // Try and co***act the window till the point where it ceases to be 'desirable'.
            while (l <= r && formed == required) {
                c = filteredS.get(l).getValue();

                // Save the smallest window until now.
                int end = filteredS.get(r).getKey();
                int start = filteredS.get(l).getKey();
                if (ans[0] == -1 || end - start + 1 < ans[0]) {
                    ans[0] = end - start + 1;
                    ans[1] = start;
                    ans[2] = end;
                }

                windowCounts.put(c, windowCounts.get(c) - 1);
                if (dictT.containsKey(c) && windowCounts.get(c).intValue() < dictT.get(c).intValue()) {
                    formed--;
                }
                l++;
            }
            r++;
        }
        return ans[0] == -1 ? "" : s.substring(ans[1], ans[2] + 1);
    }

    public static void main(String[] args) {
        String s = "ADOBECODEBANC";
        String t = "ADOBECODEBANC";
        System.out.println(minWindow(s, t));
    }
}
