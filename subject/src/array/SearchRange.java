package array;

/**
 * Created by Xursan on 2019/8/17.
 *
 * 给定一个按照升序排列的整数数组 nums，和一个目标值 target。找出给定目标值在数组中的开始位置和结束位置。
 你的算法时间复杂度必须是 O(log n) 级别。如果数组中不存在目标值，返回 [-1, -1]。

 示例 1:
 输入: nums = [5,7,7,8,8,10], target = 8
 输出: [3,4]

 示例 2:
 输入: nums = [5,7,7,8,8,10], target = 6
 输出: [-1,-1]

 来源：力扣（LeetCode）
 链接：https://leetcode-cn.com/problems/find-first-and-last-position-of-element-in-sorted-array
 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class SearchRange {
    /**
     * 二分查找找到最后一个等于的
     * @param nums
     * @param target
     * @return
     */
    public static int findLastE(int[] nums, int target){
        int e = nums.length-1;
        int s = 0;
        while (s<=e){
            int mid = (s+e)/2;
            if(nums[mid]<=target){
                s = mid+1;
            }else {
                e = mid-1;
            }
        }
        if(s==0||nums[s-1]!=target){
            return -1;
        } else {
            return s-1;
        }
    }

    /**
     * 找到第一个相等的
     * @param nums
     * @param target
     * @return
     */
    public static int findFirstE(int[] nums, int target){
        int e = nums.length-1;
        int s = 0;
        while (s<=e){
            int mid = (s+e)/2;
            if(nums[mid]<target){
                s = mid+1;
            }else {
                e = mid-1;
            }
        }
        if(e==nums.length-1||nums[e+1]!=target){
            return -1;
        } else {
            return e+1;
        }
    }

    public static int[] searchRange(int[] nums, int target) {
        int s = findFirstE(nums,target);
        int e = findLastE(nums,target);
        if(s==-1){
            return new int[]{-1,-1};
        }else {
            return new int[]{s,e};
        }
    }

    public static void main(String[] args) {
        int[] nums = {5,7,7,8,8,10};
        int target = 6;
        for (int i : searchRange(nums,target)) {
            System.out.println(i);
        }
    }
}
