package dp;

/**
 * Created By ShengXiang.Xiong on 2019/12/30
 * LeetCode 395. 至少有K个重复字符的最长子串
 * 找到给定字符串（由小写字符组成）中的最长子串 T ， 要求 T 中的每一字符出现次数都不少于 k 。输出 T 的长度。
 * <p>
 * 输入:
 * s = "aaabb", k = 3
 * 输出:
 * 3
 * 最长子串为 "aaa" ，其中 'a' 重复了 3 次。
 * <p>
 * 输入:
 * s = "ababbc", k = 2
 * 输出:
 * 5
 * 最长子串为 "ababb" ，其中 'a' 重复了 2 次， 'b' 重复了 3 次
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/longest-substring-with-at-least-k-repeating-characters
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class LongestSubstring {
    /**
     * 方法一：枚举所有区间，然后判断是否满足题意。时间复杂度O(n^2)——不用想，肯定无法通过
     * <p>
     * 方法二：思路很简单，一开始就知道需要寻找到不满足要求的字符所在的位置。然后就可以将原始字符串分为多个子字符串，
     * 再对子字符串进行递归判断，直到字符串的所有字符满足要求才退出，然后取最大即可。
     * 最简单的思路就是统计出所有小于k的字符，然后直接调用split函数进行分割。然后再递归即可。
     *
     * @param s
     * @param k
     * @return
     */
    public static int longestSubstring(String s, int k) {
        int[] c = new int[26];
        int res = 0;
        char[] chars = s.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            c[chars[i] - 'a']++;
        }
        StringBuilder reg = new StringBuilder();
        for (int i = 0; i < 26; i++) {
            if (c[i] < k && c[i] != 0) {
                char tmp = (char) ('a' + i);
                reg.append(tmp);
                reg.append('|');
            }
        }
        if (reg.length() == 0) {
            return s.length();
        } else {
            reg.deleteCharAt(reg.length() - 1);
            String[] strs = s.split(reg.toString());
            for (String str : strs) {
                if (res < str.length()) {
                    res = Math.max(res, longestSubstring(str, k));
                }
            }
            return res;
        }
    }

    /**
     * 优化：依然采取分治递归的思想，但是，我们仔细分析，是否真的需要调用split函数吗？
     * 实际上我们可以直接通过两个指针+一个for循环搞定。
     * ij分别指向当前字符串满足要求的左右两端，然后中间通过一个指针k不断枚举可能的分割点，直到遇到一个不满足题意的位置
     * （即该位置处的字符出现次数小于k），则说明 i到(k-1)是一个可能的子串，(k+1)到j是一个可能的子串，然后分别递归即可。
     *
     * @param s
     * @param k
     * @return
     */
    public int longestSubstring1(String s, int k) {
        return getLong(s.toCharArray(), k, 0, s.length() - 1);
    }

    public int getLong(char[] ch, int k, int s, int e) {
        if (e - s + 1 < k) return 0;
        int[] times = new int[26];
        for (int i = s; i <= e; i++) times[ch[i] - 'a']++;
        while (e - s + 1 >= k && times[ch[s] - 'a'] < k) s++;
        while (e - s + 1 >= k && times[ch[e] - 'a'] < k) e--;
        for (int i = s; i <= e; i++) {
            if (times[ch[i] - 'a'] < k)
                return Math.max(getLong(ch, k, s, i - 1), getLong(ch, k, i + 1, e));
        }
        return e - s + 1;
    }
    public static void main(String[] args) {
        String s = "bbaaacbd";
        int k = 3;

        System.out.println(longestSubstring(s, k));
    }
}
