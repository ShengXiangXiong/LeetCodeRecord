package dp.线性dp.jump_interval;

/**
 * Created by Xursan on 2019/8/23.
 *
 * 给定一个非负整数数组，你最初位于数组的第一个位置。
 数组中的每个元素代表你在该位置可以跳跃的最大长度。判断你是否能够到达最后一个位置。

 示例 1:
 输入: [2,3,1,1,4]
 输出: true
 解释: 从位置 0 到 1 跳 1 步, 然后跳 3 步到达最后一个位置。

 输入: [3,2,1,0,4]
 输出: false
 解释: 无论怎样，你总会到达索引为 3 的位置。但该位置的最大跳跃长度是 0 ， 所以你永远不可能到达最后一个位置。

 来源：力扣（LeetCode）
 链接：https://leetcode-cn.com/problems/jump-game
 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class CanJump {
    /**
     * 思路一：注意到，只有当数组中调到jump值为0的位置处时才可能跳不到末尾
     * 所以我们只需要判断为0的点处i，其前面所有的点j距i的距离是否小于其位置j对应的jump距离，如果是，则可以跳过该0点，
     * 否则无论如何都会调到0点，导致跳不到末尾。
     * 时间复杂度——O(n^2)
     * @param nums
     * @return
     */
    public static boolean canJump(int[] nums) {
        if(nums.length<=1){
            return true;
        }
        int[] pos2jump = new int[nums.length];
        for (int i = 0; i < nums.length-1; i++) {
            if(nums[i]==0){
                int j = 0;
                boolean flag = false;
                while (j<i){
                    if(pos2jump[j]>i-j){
                        flag = true;
                        break;
                    }
                    j++;
                }
                if (!flag){
                    return false;
                }
            }
            pos2jump[i]=nums[i];
        }
        return true;
    }

    /**
     * 思路二：仔细研究发现，要想跳到某个位置pos，需要其前面的位置下标加上其对应的jump距离>pos才行
     * 比如说位置2要跳到5，必须要nums[2]+2>=5才行。所以根据这个思路，我们从末尾出发，分析其是否能跳到其前面的位置，若能
     * 则不断更新leftDis（距左边位置0的距离）
     * 动态规划——贪心，时间复杂度O(n)
     * @param nums
     * @return
     */
    public boolean canJump2(int[] nums) {
        int leftDis = nums.length - 1;
        for (int i = nums.length - 2; i >= 0; i--) {
            if (i + nums[i] >= leftDis) {
                leftDis = i;
            }
        }
        return leftDis == 0;
    }

    /**
     * 其实这道题，最开始很容易想到是一个dp问题，而对于dp问题，可以采取递归+记忆表；或者采用从大到小（规模上），
     * 自底向上的递推方式，这样就节约了递归栈的开销。
     *
     * 如果我们可以从数组中的某个位置跳到最后的位置，就称这个位置是“好坐标”，否则称为“坏坐标”。
     * 问题可以简化为第0个位置是不是“好坐标”
     * @param
     */
    enum Index {
        GOOD, BAD, UNKNOWN
    }
    public boolean canJump3(int[] nums) {
        Index[] memo = new Index[nums.length];
        for (int i = 0; i < memo.length; i++) {
            memo[i] = Index.UNKNOWN;
        }
        memo[memo.length - 1] = Index.GOOD;

        for (int i = nums.length - 2; i >= 0; i--) {
            int furthestJump = Math.min(i + nums[i], nums.length - 1);
            for (int j = i + 1; j <= furthestJump; j++) {
                if (memo[j] == Index.GOOD) {
                    memo[i] = Index.GOOD;
                    break;
                }
            }
        }
        return memo[0] == Index.GOOD;
    }

    public static void main(String[] args) {
        int[] nums = {2,0,0};
        System.out.println(canJump(nums));
    }
}
