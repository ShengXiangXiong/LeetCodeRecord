package twoPointer;

import java.util.LinkedList;

/**
 * Created by Xursan on 2019/9/7.
 * 编写一个程序，找出第 n 个丑数。丑数就是只包含质因数 2, 3, 5 的正整数。
 * <p>
 * 输入: n = 10
 * 输出: 12
 * 解释: 1, 2, 3, 4, 5, 6, 8, 9, 10, 12 是前 10 个丑数。
 * 说明:  
 * 1 是丑数。n 不超过1690。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/ugly-number-ii
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class NthUglyNum {
    /**
     * 最简单的想法是直接以+1的方式进行循环，然后判断其是否满足丑数的要求。但这样做显然会超时，因为无法预料到第n个丑数
     * 到底是多大。
     * 我们再换一种想法，我们知道，丑数只能是在以前更小的丑数的基础上通过*2、*3、*5得来，那我们就可以每次在更小的丑数上
     * 乘以相应的2、3、5，选择其中最小的即可。关键点就在于只能选择尽可能小的且未乘过2或3或5的数（因为就算它是最小的但是
     * 它已经用2、3、5乘过了，就不能用了），所以我们得维护3个序列，L2,L3,L5，分别代表可以乘的数，里面的元素就代表当前已
     * 计算过的丑数。那么当前最小值res = Min(L2[0]*2,L3[0]*3,L5[0]*5)，如果res = Li*i，那么就从相应的Li中移除首元素，并
     * 加入到下一阶段的序列中（L5除外，它只需要移除，因为已经将2、3、5乘完了）,这样就每次从这3个序列中选出首元素并乘以
     * 相应的数的最小值，并维护相应的序列即可。
     *
     * @param n
     * @return
     */
    public static int nthUglyNumber(int n) {
        if (n == 1) {
            return 1;
        }
        LinkedList<Integer> two = new LinkedList<>();
        LinkedList<Integer> three = new LinkedList<>();
        LinkedList<Integer> five = new LinkedList<>();
        two.add(1);
        int res = 0;
        for (int i = 1; i < n; i++) {
            boolean id[] = new boolean[3];
            if (two.peek() != null) {
                res = two.peek() * 2;
                id[0] = true;
            }
            if (three.peek() != null) {
                Integer tmp = three.peek() * 3;
                if (res == tmp) {
                    id[1] = true;
                }
                if (res > tmp) {
                    res = tmp;
                    id[1] = true;
                    id[0] = false;
                }
            }
            if (five.peek() != null) {
                Integer tmp = five.peek() * 5;
                if (res == tmp) {
                    id[2] = true;
                }
                if (res > tmp) {
                    res = tmp;
                    id[2] = true;
                    id[1] = false;
                    id[0] = false;
                }
            }

            if (id[0]) {
                three.add(two.poll());
            }
            if (id[1]) {
                five.add(three.poll());
            }
            if (id[2]) {
                five.removeFirst();
            }
            two.addLast(res);
        }
        return res;
    }

    /**
     * 三指针
     * 在上面的基础上，我们再仔细思考，我们实际上每一个阶段要做的就是用L2、L3、L5序列中的最小值分别乘以2、3、5，然后再
     * 维护序列，对于我们而言，每个序列的后面的数据我们并不关心，那我们采用序列完全就是多余，不如直接用3个指针
     * （i2、i3、i5），分别指向已加入丑数数组dp的对应可用2、3、5乘的最小数即可，每次找到最小的数时，如果它等于i2*2，就
     * 直接让i2的指针在dp数组上后移一位即可，因为其后一位一定满足可以乘2且最小的要求，同理等于i3*3，就让i3指针后移一位。
     * 注意这里不能用if else，因为2*3等于3*2，这种情况下i2和i3指针都得向后移动。
     *
     * @param n
     * @return
     */
    public static int nthUglyNumber2(int n) {
        int[] dp = new int[n];
        dp[0] = 1;
        int i2 = 0, i3 = 0, i5 = 0;
        for (int i = 1; i < n; i++) {
            int min = Math.min(Math.min(dp[i2] * 2, dp[i3] * 3), dp[i5] * 5);
            if (min == dp[i2] * 2) i2++;
            if (min == dp[i3] * 3) i3++;
            if (min == dp[i5] * 5) i5++;
            dp[i] = min;
        }
        return dp[n - 1];
    }

    public static void main(String[] args) {
        System.out.println(nthUglyNumber(8));
    }
}
