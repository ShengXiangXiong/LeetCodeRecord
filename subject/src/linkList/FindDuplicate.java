package linkList;

/**
 * Created By ShengXiang.Xiong on 2019/11/18
 * 给定一个包含 n + 1 个整数的数组 nums，其数字都在 1 到 n 之间（包括 1 和 n），可知至少存在一个重复的整数。
 * 假设只有一个重复的整数，找出这个重复的数。
 * <p>
 * 输入: [1,3,4,2,2]
 * 输出: 2
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/find-the-duplicate-number
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class FindDuplicate {

    /**
     * 很简单，推数学公式即可，单个重复值=sum（nums）-（1到n之和），但是这道题并非如此，因为重复值可能出现多次。
     * <p>
     * 其实再想一下，这道题可以想象成一个链表，数组中存的值是链表的下一个节点的指针（数组的位置），
     * 如果存在重复值（指向了同一个节点两次），必然会成环。
     * <p>
     * 而链表成环的判断，就很简单了，快慢指针即可，如果存在环，快指针一定会和慢指针碰撞
     * 由于这里显然会成环，因此需要求成环的第一个入口点。
     *
     * @param nums
     * @return
     */
    public static int findDuplicate(int[] nums) {
        int p = 0;
        int q = 0;
        while (true) {
            p = nums[p];
            q = nums[nums[q]];
            //快慢指针重合
            if (nums[p] == nums[q]) {
                p = 0;//指向头部
                //同时按步长为1开始走，直到指针重合时,返回节点值
                while (nums[p] != nums[q]) {
                    p = nums[p];
                    q = nums[q];
                }
                return nums[p];
            }
        }
    }

    public static void main(String[] args) {
        int[] nums = {1, 5, 3, 4, 1, 1};
        System.out.println(findDuplicate(nums));
    }
}
