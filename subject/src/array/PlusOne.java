package array;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Xursan on 2019/9/22.
 */
public class PlusOne {
    public static int[] plusOne(int[] digits) {
        int plus = 0;
        List<Integer> ans = new ArrayList<>();
        int tmp = digits[digits.length - 1] + 1;
        if (tmp >= 10) {
            plus = 1;
            ans.add(tmp % 10);
        } else {
            ans.add(tmp);
        }
        for (int i = digits.length - 2; i >= 0; i--) {
            int t = digits[i] + plus;
            if (t >= 10) {
                plus = 1;
                ans.add((t) % 10);
            } else {
                plus = 0;
                ans.add(t);
            }
        }
        //循环完还剩下的进位
        if (plus == 1) {
            ans.add(1);
        }
        int[] res = new int[ans.size()];
        int cnt = res.length - 1;
        for (Integer an : ans) {
            res[cnt--] = an;
        }
        return res;
    }

    public static void main(String[] args) {
        int[] a = {9};
        for (int i : plusOne(a)) {
            System.out.print(i + " ");
        }
    }
}
