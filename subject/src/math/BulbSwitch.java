package math;

/**
 * Created by Xursan on 2019/9/15.
 * <p>
 * 初始时有 n 个灯泡关闭。 第 1 轮，你打开所有的灯泡。 第 2 轮，每两个灯泡你关闭一次。
 * 第 3 轮，每三个灯泡切换一次开关（如果关闭则开启，如果开启则关闭）。
 * 第 i 轮，每 i 个灯泡切换一次开关。 对于第 n 轮，你只切换最后一个灯泡的开关。 找出 n 轮后有多少个亮着的灯泡。
 * <p>
 * 输入: 3
 * 输出: 1
 * 解释:
 * 初始时, 灯泡状态 [关闭, 关闭, 关闭].
 * 第一轮后, 灯泡状态 [开启, 开启, 开启].
 * 第二轮后, 灯泡状态 [开启, 关闭, 开启].
 * 第三轮后, 灯泡状态 [开启, 关闭, 关闭].
 * <p>
 * 你应该返回 1，因为只有一个灯泡还亮着。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/bulb-switcher
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class BulbSwitch {
    /**
     * 暴力模拟。每i*k个一翻转，用mem数组来模拟。时间复杂度接近O(n^2)
     *
     * @param n
     * @return
     */
    public static int bulbSwitch(int n) {
        int cnt = n;
        int[] mem = new int[n + 1];
        for (int i = 0; i <= n; i++) {
            mem[i] = 1;
        }
        if (n == 0) {
            return 0;
        }
        for (int i = 2; i <= n; i++) {
            int k = 1;
            while (k * i <= n) {
                int pos = k * i;
                mem[pos] = -mem[pos];
                cnt += mem[pos];
                k++;
            }
        }
        return cnt;
    }

    /**
     * 仔细思考一下，非平方数其相乘的因子一定不同，所以其因子一定是成对出现，即其前面一定出现过偶数次的翻转，
     * 外加上自己当前这一次以及第一次的初始翻转，故相当于操作了偶数次，即仍为原始关闭状态；
     * 而对于平方数来说，由于其因子中存在一模一样的两个数，所以因子一定是奇数，故其前面只出现过奇数次翻转，
     * 外加上自己当前这一次以及第一次的初始翻转，仍为奇数次，故为开启状态。
     * 所以题目就转变为了，球当前n范围内的完全平方数有多少个
     *
     * @param n
     * @return
     */
    public static int bulbSwitch2(int n) {
        return (int) Math.sqrt(n);
    }

    public static void main(String[] args) {
        System.out.println(bulbSwitch2(99999998));
    }

}
