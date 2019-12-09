package dp.jump_interval;

import java.util.Stack;

/**
 * Created By ShengXiang.Xiong on 2019/12/9
 * 根据每日 气温 列表，请重新生成一个列表，对应位置的输入是你需要再等待多久温度才会升高超过该日的天数。如果之后都不会升高，请在该位置用 0 来代替。
 * 例如，给定一个列表 temperatures = [73, 74, 75, 71, 69, 72, 76, 73]，你的输出应该是 [1, 1, 4, 2, 1, 1, 0, 0]。
 * 提示：气温 列表长度的范围是 [1, 30000]。每个气温的值的均为华氏度，都是在 [30, 100] 范围内的整数。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/daily-temperatures
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class DailyTemperatures {
    public static int[] dailyTemperatures(int[] T) {
        int[] res = new int[T.length];
        Stack<Integer> s = new Stack<>();
        s.add(-1);
        for (int i = 0; i < T.length; i++) {
            while (s.peek() != -1 && T[s.peek()] < T[i]) {
                res[s.peek()] = i - s.pop();
            }
            s.add(i);
        }
        while (s.peek() != -1) {
            res[s.pop()] = 0;
        }
        return res;
    }

    /**
     * 跳跃区间类DP。尾部DP
     * 根据题意，从最后一天推到第一天，这样会简单很多。因为最后一天显然不会再有升高的可能，结果直接为0。
     * 再看倒数第二天的温度，如果比倒数第一天低，那么答案显然为1，如果比倒数第一天高，又因为倒数第一天
     * 对应的结果为0，即表示之后不会再升高，所以倒数第二天的结果也应该为0。
     * 自此我们容易观察出规律，要求出第i天对应的结果，只需要知道第i+1天对应的结果就可以：
     * - 若T[i] < T[i+1]，那么res[i]=1；
     * - 若T[i] > T[i+1]
     * - res[i+1]=0，那么res[i]=0;
     * - res[i+1]!=0，那就比较T[i]和T[i+1+res[i+1]]（即将第i天的温度与比第i+1天大的那天的温度进行比较）
     */
    public static int[] dailyTemperatures1(int[] T) {
        int[] res = new int[T.length];
        res[T.length - 1] = 0;
        for (int i = T.length - 2; i >= 0; i--) {
            for (int j = i + 1; j < T.length; j += res[j]) {
                if (T[i] < T[j]) {
                    res[i] = j - i;
                    break;
                } else if (res[j] == 0) {
                    res[i] = 0;
                    break;
                }
            }
        }
        return res;
    }

    public static void main(String[] args) {
        int[] temperatures = {2, 1, 2};
        for (int i : dailyTemperatures(temperatures)) {
            System.out.print(i + " ");
        }
    }
}
