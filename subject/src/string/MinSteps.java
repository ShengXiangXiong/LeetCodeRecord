package string;

/**
 * Created by Xursan on 2020/2/12.
 */
public class MinSteps {
    public static int minSteps(String s, String t) {
        int[] c = new int[256];
        int cnt = s.length();
        for (int i = 0; i < s.length(); i++) {
            c[s.charAt(i)]++;
        }
        for (int i = 0; i < t.length(); i++) {
            if (c[t.charAt(i)] > 0) {
                cnt--;
                c[t.charAt(i)]--;
            }
        }
        return cnt;
    }

    public static void main(String[] args) {
        String s = "friend";
        String t = "family";
        System.out.println(minSteps(s, t));
    }
}
