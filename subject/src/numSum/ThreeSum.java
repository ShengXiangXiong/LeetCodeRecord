package numSum;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/**
 * Created By ShengXiang.Xiong on 2020/1/13
 * 15. 三数之和
 * 给定一个包含 n 个整数的数组 nums，判断 nums 中是否存在三个元素 a，b，c ，使得 a + b + c = 0 ？找出所有满足条件且不重复的三元组。
 * 注意：答案中不可以包含重复的三元组。
 * <p>
 * 例如, 给定数组 nums = [-1, 0, 1, 2, -1, -4]，
 * 满足要求的三元组集合为：
 * [
 * [-1, 0, 1],
 * [-1, -1, 2]
 * ]
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/3sum
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class ThreeSum {
    /**
     * 排序+hash+剪枝优化
     * 始终保持第3个最大，然后通过hash进行查找，结合实际情况进行剪枝优化
     *
     * @param nums
     * @return
     */
    public static List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        HashSet<Integer> h = new HashSet<>();
        Arrays.sort(nums);
        for (int num : nums) {
            h.add(num);
        }
        for (int i = 0; i < nums.length - 2; i++) {
            //剪枝1
            if (nums[i] > 0) {
                break;
            }
            //和前一个相同则跳过
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            for (int j = i + 1; j < nums.length - 1; j++) {
                //和前一个j相同则跳过(注意j必须大于i+1，否则可能存在nums[j]==nums[i]的情况被跳过)
                if (j > i + 1 && nums[j] == nums[j - 1]) {
                    continue;
                }
                int tmp = -nums[i] - nums[j];
                //由于排序，如果第三个数小于0，则一定后面（j++）所有对应的tmp都小于 0 ,且如果存在和为0的数，
                //一定在前面nums[j]为负数的时候已经算过了，无须再算，因此剪枝2
                if (tmp < 0) {
                    break;
                }
                //tmp>=nums[j] 避免双正数时的情况，始终保持第3个数最大
                if (h.contains(tmp) && tmp >= nums[j]) {
                    //当第三个数等于第二个数j时，判断第三个数和第二个数是否在同一个位置，若是，则丢弃
                    if (nums[j] == tmp && nums[j + 1] > nums[j]) {
                        continue;
                    }
                    List<Integer> ls = new ArrayList<>();
                    ls.add(nums[i]);
                    ls.add(nums[j]);
                    ls.add(tmp);
                    res.add(ls);
                }
            }
        }
        return res;
    }

    /**
     * 排序 + 双指针
     * 本题的难点在于如何去除重复解。要求的是a+b+c=0 其实就是要求a+b=-c，那么问题可以转化为依次遍历数组元素c，
     * 然后在剩下的数中做两数之和为-c的问题。问题在于如何简化算法以及优化复杂度。
     * <p>
     * 1.首先可以先排序（O(nlogn)），这样保证数组有序之后可以利用大小关系判断。
     * <p>
     * 2.设置两个指针left、right，分别从左边以及右边向中间遍历，如果找到a+b+c==0，那么可以将这个答案加入到答案集里 如果a+b+c<0，
     * 此时固定的是c，说明a+b太小了，因此left+=1；如果a+b+c>0，此时a+b过大，因此right-=1
     * <p>
     * 3.** 去重，这一步则是利用了有序性 **，如果两个数相同，那他们在数组的位置一定是相邻的（连着几个数相同也是可能的），
     * 因此去重的操作就能简单遍历一下相邻的是否相同即可。由于数组有序性使得去重这一步很简单，因此也可以看出第一步的作用。
     * <p>
     * 此外还有一些小细节的地方，比如说当遍历到c>0的时候，由于之后的数都是正数，那三数之和一定大于0，就没必要继续遍历c了
     * (因为 继续向后遍历c只会更大，那之后的数加起来一定大于0)； 或者固定c，如果c及其后面连着两个数a,b，他们的和已经大于0了，
     * 就没必要进行下一步的操作，此时遍历下一个c；同理，如果c和数组最后两个数的和仍然小于0，也没必要进行下一步操作。
     *
     * @param nums
     * @return
     */
    public List<List<Integer>> threeSum1(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        if (nums == null || nums.length < 3) {
            return res;
        }
        Arrays.sort(nums);
        int len = nums.length;
        for (int i = 0; i < len - 2; i++) {
            if (nums[i] > 0) break;
            if (i > 0 && nums[i] == nums[i - 1]) continue;
            int L = i + 1;
            int R = len - 1;
            while (L < R) {
                int sum = nums[i] + nums[L] + nums[R];
                if (sum == 0) {
                    res.add(Arrays.asList(nums[i], nums[L], nums[R]));
                    while (L < R && nums[L] == nums[L + 1]) L++;
                    while (L < R && nums[R] == nums[R - 1]) R--;
                    L++;
                    R--;
                } else if (sum < 0) L++;
                else R--;
            }
        }
        return res;
    }

    public static void main(String[] args) {
        int[] nums = {-1, 0, 1, 2, -1, -4, 2, 3};
        for (List<Integer> integers : threeSum(nums)) {
            for (Integer integer : integers) {
                System.out.print(integer + " ");
            }
            System.out.println();
        }
    }
}
