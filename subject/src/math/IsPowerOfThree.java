package math;

/**
 * Created By ShengXiang.Xiong on 2019/12/27
 */
public class IsPowerOfThree {
    /**
     * 3的幂次的质因子只有3，1162261467是3的19次幂，是整数范围内最大的3的幂次。
     * 给出的n如果是3的幂，那一定可以被1162261467整除；
     * 相反，如果不是3的幂，它一定包含其他质因子，自然不会被1162261467整除。
     *
     * @param n
     * @return
     */
    public boolean isPowerOfThree(int n) {
        return n > 0 && 1162261467 % n == 0;
    }
}
