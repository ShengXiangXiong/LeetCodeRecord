package dp.字符串dp;

import java.util.Stack;

/**
 * Created by Xursan on 2020/2/14.
 * Leetcode 32
 * 给定一个只包含 '(' 和 ')' 的字符串，找出最长的包含有效括号的子串的长度。
 * 输入: "(()"
 * 输出: 2
 * 解释: 最长有效括号子串为 "()"
 * <p>
 * 输入: ")()())"
 * 输出: 4
 * 解释: 最长有效括号子串为 "()()"
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/longest-valid-parentheses
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class LongestValidParentheses {
    /**
     * 栈+替换
     * 我们可以找出所有匹配的括号，然后将其对应位置置为True，这样只需要判断最终连续的最长true即可
     *
     * @param s
     * @return
     */
    public static int longestValidParentheses(String s) {
        char[] cs = s.toCharArray();
        boolean[] a = new boolean[cs.length];
        Stack<Integer> sta = new Stack<>();
        for (int i = 0; i < cs.length; i++) {
            if (cs[i] == ')' && !sta.empty() && cs[sta.peek()] == '(') {
                a[i] = true;
                a[sta.pop()] = true;
            } else {
                sta.add(i);
            }
        }
        int res = 0, cnt = 0;
        for (boolean b : a) {
            if (b) {
                cnt++;
            } else {
                res = Math.max(cnt, res);
                cnt = 0;
            }
        }
        return Math.max(cnt, res);
    }


    /**
     * dp
     * 在这种方法中，我们利用两个计数器 left和 right 。首先，我们从左到右遍历字符串，
     * 对于遇到的每个 ‘(’，我们增加 left 计算器，对于遇到的每个 ‘)’ ，
     * 我们增加 right计数器。每当 left计数器与 right计数器相等时，我们计算当前有效字符串的长度，
     * 并且记录目前为止找到的最长子字符串。如果 right计数器比 left计数器大时，我们将 left和 right
     * 计数器同时变回 0 。
     * 接下来，我们从右到左做一遍类似的工作。
     * 这样相当于：**将整个字符串，按照“多余的右括号”和“多余的左括号”之间的分界线，分为左右两个子串，
     * 然后从左到右和从右到左，分别统计两个子串内每个匹配子串的长度。**
     *
     * @param
     */
    public int longestValidParentheses1(String s) {
        int left = 0, right = 0, maxlength = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                left++;
            } else {
                right++;
            }
            if (left == right) {
                maxlength = Math.max(maxlength, 2 * right);
            } else if (right >= left) {
                left = right = 0;
            }
        }
        left = right = 0;
        for (int i = s.length() - 1; i >= 0; i--) {
            if (s.charAt(i) == '(') {
                left++;
            } else {
                right++;
            }
            if (left == right) {
                maxlength = Math.max(maxlength, 2 * left);
            } else if (left >= right) {
                left = right = 0;
            }
        }
        return maxlength;
    }

    public static void main(String[] args) {
        String s = "()(())";
        System.out.println(longestValidParentheses(s));
    }
}
