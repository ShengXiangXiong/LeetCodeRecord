package dp.区间dp;

/**
 * Created By ShengXiang.Xiong on 2019/12/9
 * 给定一个字符串，你的任务是计算这个字符串中有多少个回文子串。
 * 具有不同开始位置或结束位置的子串，即使是由相同的字符组成，也会被计为是不同的子串。
 * <p>
 * 输入: "abc"
 * 输出: 3
 * 解释: 三个回文子串: "a", "b", "c".
 * <p>
 * 输入: "aaa"
 * 输出: 6
 * 说明: 6个回文子串: "a", "a", "a", "aa", "aa", "aaa".
 * 注意:
 * <p>
 * 输入的字符串长度不会超过1000。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/palindromic-substrings
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class CountCycleSubstrings {
    private int[][] mem;

    public boolean cycle(char[] s, int p, int q) {
        if (mem[p][q] == 1) {
            return true;
        }
        if (mem[p][q] == 2) {
            return false;
        }
        if (p < q) {
            if (s[p] == s[q] && cycle(s, p + 1, q - 1)) {
                mem[p][q] = 1;
                return true;
            } else {
                mem[p][q] = 2;
                return false;
            }
        }
        mem[p][q] = 1;
        return true;
    }

    /**
     * 记忆数组+递归（也可以转化为dp）
     *
     * @param s
     * @return
     */
    public int countSubstrings(String s) {
        int res = s.length();
        char[] ch = s.toCharArray();
        mem = new int[s.length()][s.length()];
        for (int i = 0; i < s.length(); i++) {
            for (int j = i + 1; j < s.length(); j++) {
                if (cycle(ch, i, j)) {
                    res++;
                }
            }
        }
        return res;
    }


    /**
     * 解法二：中心扩展==>时间复杂度O(n^2)
     * 从中心（区分1个点的中心还是两个点的中心）向两边同时扩散，如左右两边都相等则是回文，继续扩散
     * <p>
     * 执行用时 :2 ms , 在所有 Java 提交中击败了99.02%的用户
     * 内存消耗 :34 MB, 在所有 Java 提交中击败了92.79%的用户
     *
     * @param s
     * @return
     */
    public int countSubstrings1(String s) {
        int res = 0;
        for (int i = 0; i < s.length(); i++) {
            //分奇偶考虑
            res += countSegment(s, i, i);
            res += countSegment(s, i, i + 1);
        }
        return res;
    }

    //start往左边跑，end往右边跑, 判断s[start, end]是否为回文
    public static int countSegment(String s, int start, int end) {
        int count = 0;
        while (start >= 0 && end < s.length() && s.charAt(start--) == s.charAt(end++)) {
            count++;
        }
        return count;
    }

    public static void main(String[] args) {
        System.out.println(new CountCycleSubstrings().countSubstrings("aaba"));
    }
}
