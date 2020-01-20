package string;

import java.util.HashSet;

/**
 * Created by Xursan on 2020/1/12.
 * 387. 字符串中的第一个唯一字符
 * 给定一个字符串，找到它的第一个不重复的字符，并返回它的索引。如果不存在，则返回 -1。
 * s = "leetcode"
 * 返回 0.
 * s = "loveleetcode",
 * 返回 2.
 * 注意事项：您可以假定该字符串只包含小写字母。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/first-unique-character-in-a-string
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class FirstUniqChar {
    public static int firstUniqChar(String s) {
        int[] c = new int[26];
        char[] cs = s.toCharArray();

        for (char c1 : cs) {
            c[c1 - 'a']++;
        }
        HashSet<Integer> hs = new HashSet<>();
        for (int i = 0; i < 26; i++) {
            if (c[i] == 1) {
                hs.add(i);
            }
        }
        for (int i = 0; i < cs.length; i++) {
            if (hs.contains(cs[i] - 'a')) {
                return i;
            }
        }
        return -1;
    }

    public int firstUniqChar1(String s) {
        int res = -1;
        for (char ch = 'a'; ch <= 'z'; ch++) {
            int index = s.indexOf(ch);
            if (index != -1 && index == s.lastIndexOf(ch)) {
                res = (res == -1 || res > index) ? index : res;
            }
        }
        return res;
    }

    public static void main(String[] args) {
        String s = "loveleetcode";
        System.out.println(firstUniqChar(s));
    }
}
