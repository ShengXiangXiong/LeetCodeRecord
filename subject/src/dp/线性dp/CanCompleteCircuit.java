package dp.线性dp;

/**
 * Created by Xursan on 2020/1/24.
 * 134. 加油站
 */
public class CanCompleteCircuit {
    /**
     * 预处理
     * 基本思路：将循环路程分为3段：分别统计。
     * 1.从0到i的剩余油量，以及维护一个0到i的最小值数组，防止中间耗油过多而中断导致结果错误
     * 2.从i到n的剩余油量
     * 3.从n到0的剩余油量
     * 然后从后往前开始遍历，每次先判断从i到n是否连通，再判断剩余路段叠加后的油量是否满足
     *
     * @param gas
     * @param cost
     * @return
     */
    public static int canCompleteCircuit(int[] gas, int[] cost) {
        int n = gas.length;
        if (n == 0) {
            return -1;
        }
        if (n == 1) {
            return gas[0] >= cost[0] ? 0 : -1;
        }
        int[] s = new int[n];
        int[] minV = new int[n];
        s[0] = gas[0] - cost[0];
        minV[0] = Math.min(s[0], 0);
        for (int i = 1; i < n; i++) {
            s[i] = gas[i] - cost[i] + s[i - 1];
            minV[i] = Math.min(s[i], minV[i - 1]);
        }
        int fix = gas[n - 1] - cost[n - 1];
        int sum = 0;
        for (int i = n - 1; i > 0; i--) {
            int tmp = 0;
            if (i < n - 1) {
                tmp = gas[i] - cost[i];
                sum += tmp;
            }
            if (tmp >= 0 && sum >= 0 && sum + fix + minV[i - 1] >= 0) {
                return i;
            }
        }
        return minV[n - 1] < 0 ? -1 : 0;
    }

    /**
     * 贪心
     * 我们引入变量 curr_tank ，记录当前油箱里剩余的总油量，total_tank，记录已走过的加油站所消耗的总油量（负代表差的油量）
     * 如果在某一个加油站 curr_tank比 0 小，意味着我们无法到达这个加油站。下一步我们把这个加油站当做新的起点，
     * 并将 curr_tank 重置为 0 ，因为重新出发，油箱中的油为 0 。
     * （从上一次重置的加油站到当前加油站的任意一个加油站出发，到达当前加油站之前， curr_tank 也一定会比 0 小）
     * 算法：
     * 初始化 total_tank 和 curr_tank 为 0 ，并且选择 0 号加油站为起点。
     * 遍历所有的加油站：
     * 每一步中，都通过加上 gas[i] 和减去 cost[i] 来更新 total_tank 和 curr_tank 。
     * 如果在 i + 1 号加油站， curr_tank < 0 ，将 i + 1 号加油站作为新的起点，同时重置 curr_tank = 0 ，让油箱也清空。
     * 如果 total_tank < 0 ，返回 -1 ，否则返回 starting station。
     *
     * @param gas
     * @param cost
     * @return
     */
    public int canCompleteCircuit1(int[] gas, int[] cost) {
        int n = gas.length;
        int total_tank = 0;
        int curr_tank = 0;
        int starting_station = 0;
        for (int i = 0; i < n; ++i) {
            total_tank += gas[i] - cost[i];
            curr_tank += gas[i] - cost[i];
            // If one couldn't get here,
            if (curr_tank < 0) {
                // Pick up the next station as the starting one.
                starting_station = i + 1;
                // Start with an empty tank.
                curr_tank = 0;
            }
        }
        return total_tank >= 0 ? starting_station : -1;
    }

    public static void main(String[] args) {
        int[] gas = {2, 4};
        int[] cost = {3, 4};

        System.out.println(canCompleteCircuit(gas, cost));
    }
}
