package dp;

/**
 * stock_2_trans
 */
public class stock_2_trans {

    public static int[] get_max_diff(int a1, int b1, int a2, int b2) {
        int[] res = {a1, b1};
        int tmp = b1 - a1;
        int[][] comb = {{a1, b1, b1 - a1}, {a1, b2, b2 - a1}, {a2, b2, b2 - a2}};
        for (int i = 0; i < comb.length; i++) {
            if (comb[i][2] > tmp) {
                tmp = comb[i][2];
                res[0] = comb[i][0];
                res[1] = comb[i][1];
            }
        }
        return res;
    }

    public static int max_res(int a, int b, int c) {
        int[] t = {a, b, c};
        int res = a;
        for (int i = 1; i < t.length; i++) {
            if (t[i] > res) {
                res = t[i];
            }
        }
        return res;
    }

    public static int maxProfit(int[] a) {
        int n = a.length - 1;
        int trans2 = 0;
        int i = 0;
        int buy = 0;

        while (i < n && a[i] >= a[i + 1]) {
            i++;
        }
        if (i >= n) {
            return 0;
        }
        int buy_one = a[i];

        while (i < n && a[i] <= a[i + 1]) {
            i++;
        }
        int sell_one = a[i];
        int[] t1 = {buy_one, sell_one};
        int btm = 0;
        int res = t1[1] - t1[0];
        int lastRes = res;
        i++;
        while (i < n) {
            while (i < n && a[i] >= a[i + 1]) {
                i++;
            }
            if (i >= n) {
                return res;
            }
            buy = a[i];
            while (i < n && a[i] <= a[i + 1]) {
                i++;
            }
            trans2 = a[i] - buy;

            int[] maxt2 = get_max_diff(t1[0], t1[1], buy, a[i]);
            //状态转移
            if (maxt2[0] == buy && maxt2[1] == a[i]) {
                btm = t1[1] - t1[0];
            }
            int maxTrans2 = maxt2[1] - maxt2[0];
            res = max_res(t1[1] - t1[0] + trans2, maxTrans2 + btm, lastRes);
            // res = Math.max(t1[1]-t1[0]+trans2, maxTrans2+btm);
            t1 = maxt2;
            lastRes = res;

            i++;
        }
        return res;

    }

    public static void main(String[] args) {
        // int[] a={8,6,4,3,3,2,3,5,8,3,8,2,6};
        int[] a = {6, 5, 4, 8, 6, 8, 7, 8, 9, 4, 5};
        System.out.println(maxProfit(a));
    }
}