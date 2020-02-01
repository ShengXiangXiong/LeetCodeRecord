package 回文类;

/**
 * Created by Xursan on 2020/2/1.
 * 5. 最长回文子串
 */
public class LongestPalindrome {
    /**
     * 中心扩展分奇偶，由于每一次选择中心，左右指针最长遍历和为O(n)，故时间复杂度为O(n^2)
     *
     * @param s
     * @return
     */
    public static String longestPalindrome(String s) {
        if (s.length() == 0) {
            return s;
        }
        int n = s.length();
        char[] cs = s.toCharArray();
        int[] res = new int[2];
        //奇
        for (int i = 1; i < n - 1; i++) {
            int p = i - 1;
            int q = i + 1;
            int cnt = 1;
            while (p >= 0 && q < n && cs[p] == cs[q]) {
                cnt += 2;
                p--;
                q++;
            }
            if (res[1] - res[0] + 1 < cnt) {
                res = new int[]{p + 1, q - 1};
            }
        }
        //偶
        for (int i = 0; i < n - 1; i++) {
            int p = i;
            int q = i + 1;
            int cnt = 0;
            while (p >= 0 && q < n && cs[p] == cs[q]) {
                cnt += 2;
                p--;
                q++;
            }
            if (res[1] - res[0] + 1 < cnt) {
                res = new int[]{p + 1, q - 1};
            }
        }
        return s.substring(res[0], res[1] + 1);
    }

    public static void main(String[] args) {
        String s = "";
        System.out.println(longestPalindrome(s));
    }
}
