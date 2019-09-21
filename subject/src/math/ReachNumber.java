package math;

/**
 * Created by Xursan on 2019/9/21.
 * 在一根无限长的数轴上，你站在0的位置。终点在target的位置。
 * 每次你可以选择向左或向右移动。第 n 次移动（从 1 开始），可以走 n 步。
 * 返回到达终点需要的最小移动次数。
 * 输入: target = 3
 * 输出: 2
 * 解释:
 * 第一次移动，从 0 到 1 。
 * 第二次移动，从 1 到 3 。
 * 输入: target = 2
 * 输出: 3
 * 解释:
 * 第一次移动，从 0 到 1 。
 * 第二次移动，从 1 到 -1 。
 * 第三次移动，从 -1 到 2 。
 * 注意:
 * target是在[-10^9, 10^9]范围中的非零整数。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/reach-a-number
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class ReachNumber {
    /**
     * 记录数组添加正号数字之和为p, 添加负号数字之和为n，数组之和为s
     * p + n = s
     * p - n = target
     * 两式相减有：
     * s - target = 2 * n
     * 因此s - target一定为偶数，且s - target = 2 * n >= 0
     * 而s = i *(i + 1) /2
     * 因此就是求最小的i， 使得 i *(i + 1) /2 - target为偶数且大于等于0
     *
     * @param target
     * @return
     */
    public int reachNumber(int target) {
        int ans = 0;
        target = Math.abs(target);
        while (true) {
            int dif = ans * (ans + 1) / 2 - target;
            if (dif >= 0 && dif % 2 == 0) {
                return ans;
            }
            ans++;
        }
    }

    public static void main(String[] args) {
        ReachNumber a = new ReachNumber();
        System.out.println(a.reachNumber(1));
    }
}
