package math;

/**
 * Created by Xursan on 2020/1/23.
 * 69. x 的平方根
 * 实现 int sqrt(int x) 函数。
 * 计算并返回 x 的平方根，其中 x 是非负整数。
 * 由于返回类型是整数，结果只保留整数的部分，小数部分将被舍去。
 * 输入: 8
 * 输出: 2
 * 说明: 8 的平方根是 2.82842...,
 *      由于返回类型是整数，小数部分将被舍去。
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/sqrtx
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class MySqrt {
    /**
     * 方法1：二分法，每次取一半（取整），然后求其平方根与x的大小关系
     * 若小于x则其整数解一定在其右侧，否则在其左侧，不断逼近，直至p小于q。
     *
     * @param x
     * @return
     */
    public static int mySqrt(int x) {
        if (x == 1)
            return 1;
        int min = 0;
        int max = x;
        while (max - min > 1) {
            int m = (max + min) / 2;
            //防止溢出
            if (x / m < m)
                max = m;
            else
                min = m;
        }
        return min;
    }

    /**
     * 采取牛顿迭代法
     *
     * @param a
     * @return
     */
    public static int mySqrt1(int a) {
        long x = a;
        while (x * x > a) {
            x = (x + a / x) / 2;
        }
        return (int) x;
    }

    public static void main(String[] args) {
        System.out.println(mySqrt(0));
    }
}
