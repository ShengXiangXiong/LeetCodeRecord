package stack;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

/**
 * Created By ShengXiang.Xiong on 2019/11/25
 * 给定一个经过编码的字符串，返回它解码后的字符串。
 * 编码规则为: k[encoded_string]，表示其中方括号内部的 encoded_string 正好重复 k 次。注意 k 保证为正整数。
 * 你可以认为输入字符串总是有效的；输入字符串中没有额外的空格，且输入的方括号总是符合格式要求的。
 * 此外，你可以认为原始数据不包含数字，所有的数字只表示重复的次数 k ，例如不会出现像 3a 或 2[4] 的输入。
 * <p>
 * s = "3[a]2[bc]", 返回 "aaabcbc".
 * s = "3[a2[c]]", 返回 "accaccacc".
 * s = "2[abc]3[cd]ef", 返回 "abcabccdcdcdef".
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/decode-string
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class DecodeString {
    /**
     * 很简单，用一个栈处理即可，但是值得注意的两点是：1.数字超过1位数    2.[[]]多重括号形式
     *
     * @param s
     * @return
     */
    public static String decodeString(String s) {
        Stack<Character> sta = new Stack<>();
        for (char c : s.toCharArray()) {
            if (c == ']') {
                List<Character> st = new LinkedList<>();
                while (!sta.empty() && sta.peek() != '[') {
                    st.add(0, sta.pop());
                }
                sta.pop();
                int sum = 0;
                int cnt = 1;
                while (!sta.empty() && sta.peek() - '0' >= 0 && sta.peek() - '0' <= 9) {
                    sum += (sta.pop() - '0') * cnt;
                    cnt *= 10;
                }
                while (sum-- > 0) {
                    sta.addAll(st);
                }
            } else {
                sta.add(c);
            }
        }
        StringBuilder res = new StringBuilder("");
        for (Character c : sta) {
            res.append(c);
        }
        return res.toString();
    }

    public static void main(String[] args) {
        String s = "3[a2[c]]";
        System.out.println(decodeString(s));
    }
}
