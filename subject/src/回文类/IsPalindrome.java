package 回文类;

/**
 * Created by Xursan on 2020/1/20.
 * 125. 验证回文串
 * 给定一个字符串，验证它是否是回文串，只考虑字母和数字字符，可以忽略字母的大小写。
 * 说明：本题中，我们将空字符串定义为有效的回文串。
 * <p>
 * 输入: "A man, a plan, a canal: Panama"
 * 输出: true
 * <p>
 * 输入: "race a car"
 * 输出: false
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/valid-palindrome
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class IsPalindrome {
    public char change(char a) {
        if (a >= 'A' && a <= 'Z') {
            a = (char) ('a' + (a - 'A'));
        }
        return a;
    }

    public boolean checkNum(char a) {
        return a >= '0' && a <= '9';
    }

    public boolean checkAlpha(char a) {
        return a >= 'A' && a <= 'Z' || a >= 'a' && a <= 'z';
    }

    public boolean isPalindrome(String s) {
        int p = 0, q = s.length() - 1;
        char[] chars = s.toCharArray();
        while (p < q) {
            while (p < q && !checkAlpha(chars[p]) && !checkNum(chars[p])) {
                p++;
            }
            while (p < q && !checkAlpha(chars[q]) && !checkNum(chars[q])) {
                q--;
            }
            if (change(chars[p]) != change(chars[q])) {
                return false;
            }
            p++;
            q--;
        }
        return true;
    }

    public static void main(String[] args) {
        String s = "`l;`` 1o1 ??;l`";
        IsPalindrome a = new IsPalindrome();
        System.out.println(a.isPalindrome(s));
    }
}
