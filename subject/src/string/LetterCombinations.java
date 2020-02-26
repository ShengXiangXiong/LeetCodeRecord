package string;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/**
 * Created by Xursan on 2020/2/26.
 * 17. 电话号码的字母组合
 */
public class LetterCombinations {
    private static String letterMap[] = {
            "",     //0
            "",     //1
            "abc",  //2
            "def",  //3
            "ghi",  //4
            "jkl",  //5
            "mno",  //6
            "pqrs", //7
            "tuv",  //8
            "wxyz"  //9
    };

    /**
     * 最开始想到的可能都是基于dfs方式的解法，但实际上像这种 子集、组合题，都可以通过构造法去解，每次都循环构造一个解
     * 空间（可以用队列存储），然后在这个解空间之上继续构造，直到结束。
     *
     * @param digits
     * @return
     */
    public static List<String> letterCombinations(String digits) {
        List<String> res = new ArrayList<>();
        if (digits == null || digits.length() == 0) {
            return res;
        }
        Queue<String> q = new ArrayDeque<>();
        char[] c = digits.toCharArray();
        for (char c1 : letterMap[c[0] - '0'].toCharArray()) {
            q.add(String.valueOf(c1));
        }
        for (int i = 1; i < c.length; i++) {
            int s = q.size();
            while (s-- > 0) {
                String tmp = q.poll();
                for (char c1 : letterMap[c[i] - '0'].toCharArray()) {
                    q.add(tmp + String.valueOf(c1));
                }
            }
        }
        res.addAll(q);
        return res;
    }

    public static void main(String[] args) {
        String s = "354";
        letterCombinations(s).forEach(System.out::println);
    }
}
