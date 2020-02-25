package math;

import java.util.HashSet;

/**
 * Created by Xursan on 2020/2/25.
 * 面试题 16.21. 交换和
 * 给定两个整数数组，请交换一对数值（每个数组中取一个数值），使得两个数组所有元素的和相等。
 * 返回一个数组，第一个元素是第一个数组中要交换的元素，第二个元素是第二个数组中要交换的元素。若有多个答案，返回任意一个均可。
 * 若无满足条件的数值，返回空数组。
 * <p>
 * 输入: array1 = [4, 1, 2, 1, 1, 2], array2 = [3, 6, 3, 3]
 * 输出: [1, 3]
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/sum-swap-lcci
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class FindSwapValues {
    /**
     * 最终结果两数组相等，则反推两数组和一定为偶数，否则，不满足条件，返回空
     * 其次，只需要求得两数组和之差就可以解题了。我们假设a1需要+x,a2需要-x，所以有 2x = 两数组和之差
     * 所以只要我们依次判断，两数组中任意两个数的差是否为x（即 两数组和之差/2 ）即可，若是，则返回这两个数
     *
     * @param array1
     * @param array2
     * @return
     */
    public static int[] findSwapValues(int[] array1, int[] array2) {
        HashSet<Integer> a1 = new HashSet<>();
        HashSet<Integer> a2 = new HashSet<>();
        int sum = 0;
        for (int i : array1) {
            a1.add(i);
            sum += i;
        }
        for (int i : array2) {
            a2.add(i);
            sum -= i;
        }
        sum = sum < 0 ? -sum : sum;
        if ((sum & 1) == 0) {
            sum /= 2;
            for (Integer i : a1) {
                for (Integer j : a2) {
                    if (Math.abs(i - j) == sum) {
                        return new int[]{i, j};
                    }
                }
            }
        }
        return new int[]{};
    }

    public static void main(String[] args) {
        int[] array1 = new int[]{1, 2, 3};
        int[] array2 = new int[]{4, 5, 6};
        for (int i : findSwapValues(array1, array2)) {
            System.out.println(i);
        }
    }
}
