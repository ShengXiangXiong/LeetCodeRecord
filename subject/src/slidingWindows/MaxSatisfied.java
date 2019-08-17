package slidingWindows;

/**
 * MaxSatisfied
 *
 *
 * 今天，书店老板有一家店打算试营业 customers.length 分钟。每分钟都有一些顾客（customers[i]）会进入书店，所有这些顾客都会在那一分钟结束后离开。

 在某些时候，书店老板会生气。 如果书店老板在第 i 分钟生气，那么 grumpy[i] = 1，否则 grumpy[i] = 0。 当书店老板生气时，那一分钟的顾客就会不满意，不生气则他们是满意的。

 书店老板知道一个秘密技巧，能抑制自己的情绪，可以让自己连续 X 分钟不生气，但却只能使用一次。

 请你返回这一天营业下来，最多有多少客户能够感到满意的数量。
  

 示例：

 输入：customers = [1,0,1,2,1,1,7,5], grumpy = [0,1,0,1,0,1,0,1], X = 3
 输出：16
 解释：
 书店老板在最后 3 分钟保持冷静。
 感到满意的最大客户数量 = 1 + 1 + 1 + 1 + 7 + 5 = 16.

 来源：力扣（LeetCode）
 链接：https://leetcode-cn.com/problems/grumpy-bookstore-owner
 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class MaxSatisfied {

    /**
     * 很简单，首先计算出原始的满意度cus_sum - gru_sum，然后再在原数组上滑动X大小的窗口，找出最大的补偿满意度cnt相加即可
     * 这里注意到，滑动窗口计算cnt时，有很大一部分是重叠了的，可以保留重叠前缀来优化时间。
     * @param customers
     * @param grumpy
     * @param X
     * @return
     */
    public static int maxSatisfied(int[] customers, int[] grumpy, int X) {
        int cnt = 0;
        int cus_sum = 0;
        int gru_sum = 0;
        for (int i = 0; i < customers.length; i++) {
            if (grumpy[i] == 1) {
                grumpy[i] = customers[i];
            }
            cus_sum += customers[i];
            gru_sum += grumpy[i];
        }
        if (X >= customers.length) {
            return cus_sum;
        }
        //注意技巧，滑动窗口，可以通过保存下一窗口的前缀来优化时间
        int pre = 0;
        for (int i = 0; i < X - 1; i++) {
            pre += grumpy[i];
        }
        for (int i = X - 1; i < customers.length; i++) {
            int tmp = pre + grumpy[i];
            cnt = Math.max(cnt, tmp);
            pre = tmp - grumpy[i - X + 1];
        }

        return cus_sum - gru_sum + cnt;
    }

    public static void main(String[] args) {
        int[] customers = {1, 0, 1, 2, 1, 1, 7, 5};
        int[] grumpy = {0, 1, 0, 1, 0, 1, 0, 1};
        int X = 5;
        System.out.println(maxSatisfied(customers, grumpy, X));
    }
}