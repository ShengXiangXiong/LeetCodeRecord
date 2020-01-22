package 回文类;

/**
 * Created by Xursan on 2020/1/20.
 * 680. 验证回文字符串 Ⅱ
 * 给定一个非空字符串 s，最多删除一个字符。判断是否能成为回文字符串。
 * <p>
 * 输入: "aba"
 * 输出: True
 * <p>
 * 输入: "abca"
 * 输出: True
 * <p>
 * 字符串只包含从 a-z 的小写字母。字符串的最大长度是50000。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/valid-palindrome-ii
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class ValidPalindrome {

    /**
     * 如果字符串的起始字符和结束字符相同（即 s[0]==s[s.length-1]），则内部字符是否为回文（s[1], s[2], ..., s[s.length - 2]）
     * 将唯一地确定整个字符串是否为回文。
     * 假设我们想知道 s[i],s[i+1],...,s[j] 是否形成回文。如果 i >= j，就结束判断。如果 s[i]=s[j]，那么我们可以取 i++;j--。
     * 否则，回文必须是 s[i+1], s[i+2], ..., s[j] 或 s[i], s[i+1], ..., s[j-1] 这两种情况。
     * <p>
     * 最开始我们疑惑的地方在于，删除字符后左右字符的对应关系，但是我们仔细分析，考虑任意字符串
     * s[0]...s[i-1] s[i] ... s[j] s[j+1] ... s[n]
     * <p>
     * 若s[i]!=s[j]，则必须要删除<=i或者>=j的字符，因为删除s[i]...s[j]中间的并不会改变s[i],s[j]的对应关系，所以我们只需要
     * 判断删除<=i或者>=j的某个字符后的字符串是否回文即可。进一步分析，我们实际上只需要考虑删除s[i]或者s[j]即可，因为若ij
     * 之外的回文，删除外面的反而可能破坏外面的回文关系，而且无论如何都需要判断s[i+1], s[i+2], ..., s[j]
     * 或 s[i], s[i+1], ..., s[j-1] 这两种情况是否回文。
     *
     * @param s
     * @return
     */
    public static boolean validPalindrome(String s) {
        char[] cs = s.toCharArray();
        int p = 0, q = cs.length - 1;
        while (p < q) {
            if (cs[p] != cs[q]) {
                int p1 = p + 1;
                int q1 = q;
                while (p1 < q1 && cs[p1] == cs[q1]) {
                    p1++;
                    q1--;
                }
                if (p1 >= q1) {
                    return true;
                }
                p1 = p;
                q1 = q - 1;
                while (p1 < q1 && cs[p1] == cs[q1]) {
                    p1++;
                    q1--;
                }
                if (p1 >= q1) {
                    return true;
                }
                return false;
            }
            p++;
            q--;
        }
        return true;
    }

    public static void main(String[] args) {
        String s = "aabcacaa";
        System.out.println(validPalindrome(s));
    }
}
