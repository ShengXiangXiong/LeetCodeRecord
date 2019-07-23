package array;

/**
 * MaxSatisfied
 */
public class MaxSatisfied {

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