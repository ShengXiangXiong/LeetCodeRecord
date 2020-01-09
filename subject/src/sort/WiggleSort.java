package sort;

/**
 * Created by Xursan on 2020/1/7.
 * 给定一个无序的数组 nums，将它重新排列成 nums[0] < nums[1] > nums[2] < nums[3]... 的顺序。
 * 输入: nums = [1, 5, 1, 1, 6, 4]
 * 输出: 一个可能的答案是 [1, 4, 1, 5, 1, 6]
 * <p>
 * 输入: nums = [1, 3, 2, 2, 3, 1]
 * 输出: 一个可能的答案是 [2, 3, 1, 3, 1, 2]
 * 说明:
 * 你可以假设所有输入都会得到有效的结果。
 * 进阶:
 * 你能用 O(n) 时间复杂度和 / 或原地 O(1) 额外空间来实现吗？
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/wiggle-sort-ii
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class WiggleSort {
    /**
     * 观察题目很容易得出一个结论，即最终结果一定是要保证大的数在偶数位。
     * 所以自然而然可以想到：先将原数组分为大小两部分，大的一部分分在偶数位，小的一部分放在奇数位
     * 即可保证这种摆动排序方式。起初的想法是直接sort，然后在放。但是仔细分析发现我们并不需要全部排序，
     * 只需要找到其中位数，这样能够将其分为大小两部分即可。所以使用快排分割，不断缩小空间。直到找到中位数的位置。
     * 但是这样还不能完成本题，因为很有可能存在大小两部分有相等的数，可能在奇偶位置穿插后，导致相等的数相邻，下面具体分析：
     * A代表小的一部分，B代表大的一部分，r代表A、B中可能相等的数。
     * 如果A和B都存在r，那么r一定是A的最大值，B的最小值，这意味着r一定出现在A的尾部，B的头部。其实，如果这一数字的个数较少，
     * 不会出现这一现象，只有当这一数字个数达到原数组元素总数的一半，才会在穿插后的出现在相邻位置。以下举几个例子进行形象地说明：
     * 例如，对于数组[1,1,2,2,3,3],分割为[1,1,2]和[2,3,3]，虽然A和B都出现了2，但穿插后为[1,2,1,3,2,3]，满足要求。
     * 而如果2的个数再多一些，即[1,1,2,2,2,3]，分割为[1,1,2]和[2,2,3]，最终结果为[1,2,1,2,2,3]，来自A的2和来自B的2出现在了相邻位置。
     *
     * 出现这一问题是因为重复数在A和B中的位置决定的，因为r在A尾部，B头部，所以如果r个数太多（大于等于(length(nums) + 1)/2），
     * 就可能在穿插后相邻。要解决这一问题，我们需要使A的r和B的r在穿插后尽可能分开。一种可行的办法是将A和B反序：
     *
     * 例如，对于数组[1,1,2,2,2,3]，分割为[1,1,2]和[2,2,3]，分别反序后得到[2, 1, 1]和[3, 2, 2]，此时2在A头部，B尾部，
     * 穿插后就不会发生相邻了。但是我们仅使用快排是不行的，因为就算反序后也不能保证大的在前面，因为在划分时AB中r的位置
     * 是不确定的，但是我们反序的目的是使r尽可能分开。
     * 所以我们的下一步就是如何将r集中在一起，然后小于r的集中在左侧，大于r的集中在右侧，自然而然的形成了反序，然后奇偶
     * 穿插即可。
     * 所以为了完成这个目的需要使用三分法，将原数组划分成三段。
     *
     * @param nums
     */
    public static void wiggleSort(int[] nums) {
        int s = 0, e = nums.length - 1;
        int mid = (s + e) / 2;
        int pos = segment(nums, s, e);
        while (pos != mid) {
            if (pos > mid) {
                e = pos - 1;
                pos = segment(nums, s, e);
            } else {
                s = pos + 1;
                pos = segment(nums, s, e);
            }
        }
        //这里已经使用三分法完成了重排，接下来可以直接实现奇偶穿插，但是要额外开辟数组
        partition3way(nums, nums[pos]);
        //另外一种思路就是使用虚拟地址思路，在三分数组时便可以实现奇偶穿插
        //我们三分时操作虚拟地址，实际上变动的却是真实地址，注意一点真实地址对三分法实际上没影响，因为我们只需要保证
        //三分法能够正常结束，能够遍历每一个数即可，而使用虚拟地址必然能够保证遍历每一个数。
        //从本质上理解，使用虚拟地址后，其三分法实际的k的下标取值过程为   1-3-5-7-..0-2-4-6-...
        //只是使用虚拟地址后，三分法将大的安排在左侧，小的安排在右侧，但实际下标却为奇偶交错了。

    }

    public static void swap(int[] nums, int i, int j) {
        int tmp = nums[j];
        nums[j] = nums[i];
        nums[i] = tmp;
    }

    public static int transfer(int n, int i) {
        //i是虚拟地址，(1+2*(i)) % (n|1)是实际地址
        //其中n为数组长度，‘|’为按位或，如果n为偶数，(n|1)为n+1，如果n为奇数，(n|1)仍为n
        //[0, mid) -> {1,3,5,7...}
        //[mid,n) -> {0,2,4,8...}
        return (2 * i + 1) % (n | 1);
    }

    /**
     * 三分法：不同于快排的二分法，快排只需要两个指针即可，即j始终指向小于等于基准值的位置，i始终指向大于等于基准值的位置
     * 而三分法需要使左侧严格小于基准值，右侧严格大于基准值，中间等于基准值。
     * 因此，从理论上讲，需要维持三个指针：
     * i始终指向当前大于基准值的最右侧
     * k始终指向当前等于基准值的最右侧
     * j始终指向当前小于基准值的最左侧
     * <p>
     * 然后，k从i始终向j移动，发现大于基准值的就让swap(i,k)，i++,k++（相当于将当前小于基准值的nums[k]放在位置i）
     * 同理发现小于基准值的就让swap(j,k)，j--（相当于将当前大于基准值的nums[k]放在位置j）
     * 直到j>=k，划分结束。
     * <p>
     * 可以看出，其本质思想与快排的思想一致，关键点就在于选定运动指针，维护大小指针。比如快排的快慢指针形式，
     * 它就是维持慢指针始终指向当前最后一个小于基准值的位置，快指针一直运动，始终指向当前最后一个大于等于基准值的位置
     *
     * @param nums
     * @param midVal
     */
    public static void partition3way(int[] nums, int midVal) {
        int i = 0, k = 0;
        int j = nums.length - 1;
        while (k <= j) {
            int indexK = transfer(nums.length, k);
            if (nums[indexK] > midVal) {
                swap(nums, transfer(nums.length, i), indexK);
                i++;
                k++;
            } else if (nums[indexK] < midVal) {
                swap(nums, indexK, transfer(nums.length, j));
                j--;
            } else {
                k++;
            }
        }
    }

    public static int segment(int[] nums, int s, int e) {
        int tmp = nums[s];
        while (s < e) {
            while (e > s && nums[e] >= tmp) {
                e--;
            }
            nums[s] = nums[e];
            while (s < e && nums[s] <= tmp) {
                s++;
            }
            nums[e] = nums[s];
        }
        nums[s] = tmp;
        return s;
    }

    public static void main(String[] args) {
        int[] nums = {1,5,1,1,6,4};
        wiggleSort(nums);
        for (int i : nums) {
            System.out.print(i + " ");
        }
    }
}
