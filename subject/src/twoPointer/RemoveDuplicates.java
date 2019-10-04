package twoPointer;

/**
 * Created by Xursan on 2019/10/4.
 * 给定一个排序数组，你需要在原地删除重复出现的元素，使得每个元素最多出现两次，返回移除后数组的新长度。
 * 不要使用额外的数组空间，你必须在原地修改输入数组并在使用 O(1) 额外空间的条件下完成。
 * <p>
 * 给定 nums = [1,1,1,2,2,3],
 * 函数应返回新长度 length = 5, 并且原数组的前五个元素被修改为 1, 1, 2, 2, 3 。
 * 你不需要考虑数组中超出新长度后面的元素。
 */
public class RemoveDuplicates {
    /**
     * i始终指向已去重的数组最末端，所以我们只需要判断当前的num和i-2位置所在的nums[i-2]即可，如果相等，则表示中间至少
     * 存在了2个一样的，所以可以直接跳过，如果不等则表示中间最多只有一个，可以加入数组，于是nums[i++]=num。
     * <p>
     * 所以像这种方式可以推广到k个重复的，只要判断nums[i-k]和num的关系即可
     *
     * @param nums
     * @return
     */
    public static int removeDuplicates(int[] nums) {
        int i = 0;
        for (int n : nums)
            if (i < 2 || n > nums[i - 2])
                nums[i++] = n;
        return i;
    }

    public static void main(String[] args) {
        int[] nums = {1, 1, 1, 2, 2, 2, 2, 3};
        int n = removeDuplicates(nums);
        while (n-- > 0) {
            System.out.print(nums[n]);
        }
    }
}
