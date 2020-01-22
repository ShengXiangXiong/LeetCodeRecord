package array;

/**
 * Created by Xursan on 2020/1/20.
 */
public class MissingNumber {
    /**
     * 我们在O(n)时间内可以求出数组和，并在O(n)时间内求出前 n+1自然数（包括 0）的和，将后者减去前者，就得到了缺失的数字。
     *
     * @param nums
     * @return
     */
    public int missingNumber(int[] nums) {
        int n = nums.length + 1;
        int sum = (n - 1) * n / 2;
        for (int num : nums) {
            sum -= num;
        }
        return sum;
    }
}
