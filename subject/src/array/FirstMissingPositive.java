package array;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;

/**
 * Created by Xursan on 2019/8/18.
 *
 * 给定一个未排序的整数数组，找出其中没有出现的最小的正整数。
 输入: [1,2,0]
 输出: 3

 输入: [3,4,-1,1]
 输出: 2

 输入: [7,8,9,11,12]
 输出: 1
 说明:
 你的算法的时间复杂度应为O(n)，并且只能使用常数级别的空间。

 来源：力扣（LeetCode）
 链接：https://leetcode-cn.com/problems/first-missing-positive
 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class FirstMissingPositive {
    /**
     * 初始想法，初始化一个hash表，将nums加入到表中去，从1开始递增判断表中是否含有该数据，若不含则就是最小正整数
     * @param nums
     * @return
     */
    public static int firstMissingPositive(int[] nums) {
        HashSet<Integer> hs = new HashSet<>();
        for (int num : nums) {
            hs.add(num);
        }
        int i = 1;
        while (true){
            if(!hs.contains(i)){
                return i;
            }
            i++;
        }
    }

    /**
     * 给数组排位置。
     * copy一个一样长的数组a，下标就对应数值减一（比如小标0对应的就是1），凡是在这个范围内(0,n)的数值都这样排。
     * 然后对a从0开始循环，如果a[i]=0,则表示该位置没在nums中出现，所以次位置所对应的数即为最小正数。
     * @param nums
     * @return
     */
    public static int firstMissingPositive2(int[] nums) {
        int n = nums.length;
        if(n==0){
            return 1;
        }
        int[] a= new int[n];
        for (int i = 0; i < n; i++) {
            if(nums[i]>0&&nums[i]<=n){
                a[nums[i]-1]=nums[i];
            }
        }
        for (int i = 0; i < n; i++) {
            if(a[i]==0){
                return i+1;
            }
        }
        return n+1;
    }

    public static void main(String[] args) {
        int[] nums={1,2,3,4,6};
        System.out.println(firstMissingPositive2(nums));
    }
}
