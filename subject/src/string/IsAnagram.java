package string;

import java.util.Arrays;
import java.util.Objects;

/**
 * Created by Xursan on 2020/1/20.
 */
public class IsAnagram {
    public boolean isAnagram(String s, String t) {
        char[] s1 = s.toCharArray();
        Arrays.sort(s1);
        char[] t1 = t.toCharArray();
        Arrays.sort(t1);
        return Objects.equals(String.valueOf(s1), String.valueOf(t1));
    }
}
