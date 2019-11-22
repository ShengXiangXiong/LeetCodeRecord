package bitComputation;

/**
 * Created By ShengXiang.Xiong on 2019/11/22
 */
public class BitOne {
    /**
     * 对于任意数字 n，将 n和 n - 1 做与运算，会把最后一个 1 的位变成 0
     *
     * @param n
     * @return
     */
    public static int hammingWeight(int n) {
        int cnt = 0;
        while (n != 0) {
            cnt++;
            n &= n - 1;
        }
        return cnt;
    }

    public static void main(String[] args) {
        int n = 0b0;
        System.out.println(hammingWeight(n));
    }
}
