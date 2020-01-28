package stack;

import java.util.Stack;

/**
 * Created by Xursan on 2020/1/22.
 * 逆波兰表达式求值
 */
public class EvalRPN {
    /**
     * 技巧点：对于存在多种选择的方案时，采取switch case更方便，而不是用if
     *
     * @param tokens
     * @return
     */
    public static int evalRPN(String[] tokens) {
        Stack<Integer> s = new Stack<>();
        for (String token : tokens) {
            switch (token) {
                case "+":
                    s.add(s.pop() + s.pop());
                    break;
                case "-":
                    s.add(-s.pop() + s.pop());
                    break;
                case "*":
                    s.add(s.pop() * s.pop());
                    break;
                case "/":
                    int a = s.pop();
                    int b = s.pop();
                    s.add(b / a);
                    break;
                default:
                    s.add(Integer.valueOf(token));
            }
        }
        return s.pop();
    }

    public static void main(String[] args) {
        String[] s = {"10", "6", "9", "3", "+", "-11", "*", "/", "*", "17", "+", "5", "+"};
        System.out.println(evalRPN(s));
    }
}
