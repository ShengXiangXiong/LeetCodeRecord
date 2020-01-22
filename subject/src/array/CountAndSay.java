package array;

/**
 * Created by Xursan on 2020/1/21.
 */
public class CountAndSay {
    public static String countAndSay(int n) {
        StringBuilder s = new StringBuilder("1");
        while (n-- > 1) {
            int p = 0, q = 1;
            StringBuilder s1 = new StringBuilder();
            while (q <= s.length()) {
                if (q == s.length() || s.charAt(p) != s.charAt(q)) {
                    s1.append(q - p);
                    s1.append(s.charAt(p));
                    p = q;
                    q++;
                } else {
                    q++;
                }
            }
            s = s1;
        }
        return String.valueOf(s);
    }

    public static void main(String[] args) {
        System.out.println(countAndSay(8));
    }
}
