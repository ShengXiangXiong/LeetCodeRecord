package dp;

/**
 * Created By ShengXiang.Xiong on 2019/12/30
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
    public static int longestSubstring(String s, int k) {
        int[] c = new int[26];
        int res = 0, cnt = 0;
        char[] chars = s.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            c[chars[i] - 'a']++;
        }
        for (int i = 0; i < chars.length; i++) {
            if (c[chars[i] - 'a'] >= k) {
                cnt++;
            } else {
                res = Math.max(cnt, res);
                cnt = 0;
            }
        }
        return res;
    }

    public static void main(String[] args) {
        String s = "ababbc";
        int k = 3;
        System.out.println(longestSubstring(s, k));
    }
}
