package bitComputation;

/**
 * Created By ShengXiang.Xiong on 2019/11/22
 */
public class BitCount {
    public static int hammingWeight(int n) {
        int cnt = 0;
        while (n != 0) {
            cnt++;
            n &= n - 1;
        }
        return cnt;
    }

    /**
     * 受前面位1题的影响，对于任意数字 n，将 n和 n - 1 做与运算，会把最后一个 1 的位变成 0。
     * 自然我们可以得出，i的1个数肯定和i-1有关系，我们知道i&i-1会将i的最后一个1抹掉，相当于就是说，
     * i的1 来自于 （i&i-1）这个值1的个数+1（被抹掉的这个1）。
     * 而i&i-1这个值的1的个数肯定在前面已经被计算过了，所以即有res[i] = res[i&i-1]+1
     *
     * @param num
     * @return
     */
    public static int[] countBits(int num) {
        int[] res = new int[num + 1];
        res[0] = 0;
        for (int i = 1; i <= num; i++) {
            res[i] = res[i & i - 1] + 1;
        }
        return res;
    }

    public static void main(String[] args) {
        int num = 15;
        for (int i : countBits(num)) {
            System.out.print(i + " ");
        }
    }
}
