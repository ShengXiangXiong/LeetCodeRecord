package string;

/**
 * Created by Xursan on 2019/8/31.
 * <p>
 * 给定两个字符串 s 和 t，判断它们是否是同构的。如果 s 中的字符可以被替换得到 t ，那么这两个字符串是同构的。
 * 所有出现的字符都必须用另一个字符替换，同时保留字符的顺序。两个字符不能映射到同一个字符上，但字符可以映射自己本身。
 * <p>
 * 输入: s = "egg", t = "add"
 * 输出: true
 * <p>
 * 输入: s = "foo", t = "bar"
 * 输出: false
 * <p>
 * 输入: s = "paper", t = "title"
 * 输出: true
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/isomorphic-strings
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class IsIsomorphic {
    public static boolean isIsomorphic(String s, String t) {
        char[] s1 = s.toCharArray();
        char[] s2 = t.toCharArray();
        if (s1.length == 0 && s2.length == 0) {
            return true;
        }
        if (s1.length != s2.length) {
            return false;
        }
        char[] mem1 = new char[128];//保证s1唯一映射
        char[] mem2 = new char[128];//保证s2唯一映射
        mem1[s1[0]] = s2[0];
        mem2[s2[0]] = s1[0];
        for (int i = 1; i < s1.length; i++) {
            if (mem1[s1[i]] == '\0') {
                mem1[s1[i]] = s2[i];
            }
            if (mem2[s2[i]] == '\0') {
                mem2[s2[i]] = s1[i];
            }
            if (mem1[s1[i]] != '\0' && mem1[s1[i]] != s2[i]) {
                return false;
            }
            if (mem2[s2[i]] != '\0' && mem2[s2[i]] != s1[i]) {
                return false;
            }

        }
        return true;
    }

    /**
     * 引入中间变量ascll，将s与t都映射到ascll上，这样就可以判断是否一一映射了。不用像上一个一样，分开进行映射。
     *
     * @param s
     * @param t
     * @return
     */
    public static boolean isIsomorphic2(String s, String t) {
        char[] sc = s.toCharArray();
        char[] tc = t.toCharArray();
        int[] ascii = new int[512];
        for (int i = sc.length - 1; i >= 0; i--) {
            if (ascii[sc[i]] != ascii[tc[i] + 256]) {
                return false;
            }
            ascii[sc[i]] = ascii[tc[i] + 256] = i;
        }
        return true;
    }

    public static void main(String[] args) {
        String s = "ac";
        String t = "ce";
        System.out.println(isIsomorphic2(s, t));
    }
}
