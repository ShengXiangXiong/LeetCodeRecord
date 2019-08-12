package stack;

import java.util.Arrays;
import java.util.Stack;
import java.util.TreeSet;

/**
 * Created by Xursan on 2019/8/8.
 *
 * 接雨水
 * 输入的为每个柱子的高度，每个柱子宽为1
 * input:[0,1,0,2,1,0,1,3,2,1,2,1]
 * output:6
 *
 * 思路1：要想能够接受雨水，即要连续相邻的柱子能够形成凹型结构。故只需要以每一个柱子i，向前寻找高于i的第一个柱子j，向后寻找
 * 高于i的第一个柱子k。然后这个凹型结构的蓄水量便是v = (k-j)*min(nums[j],nums[k])——算是按行算蓄水量
 * 可以定义一个二维数组，或者类 pillar(pos,height) 然后先按height排序O(nlogn)。从高到低，依次取柱子，第一高和第二高的柱子必然不能够形成
 * 凹型结构，故从3开始，然后选择其前面的柱子中（因为排序后必然前面满足高度的要求）左右方向上位置（pos）离得最近的作为凹型结构。
 * 这里可以采取一个Treeset作为前面柱子pos的容器，这样可以快速二分查找，定位两边离得最近的柱子位置。O（nlogn）
 *
 * 思路2：Hash表_动态编程。仔细分析，实际上我们并不需要找柱子i左右两边离得最近且高于i的柱子，我们只需要在i附近找左右两边的最大高度的柱子。
 * 求得每一个柱子离水平面的高度h_w，就知道每个柱子的蓄水量，然后求和。相当于是按列来求蓄水量，相当于对于每一个柱子i，先算出其对应水平面的高度h_w，
 * 再将左右两边低的一边作为h_w=min(left,max)，只是这里的柱子有可能存在高于左右两边的情况，此时h_w=h_i。
 * 然后h_w-h_i即是该柱子处的蓄水高度，循环完所有柱子，蓄水量便确定了。基于上面的思路，我们每次都得向前和向后寻找最大高度，我们这里
 * 可以采取hash表来存取每一个位置对应的最大高度，然后即可在O(1)的时间复杂度内找到前后的最大高度，O(n)的时间内就可以求得蓄水量。
 *
 * 思路三：单调栈。其实顺着思路一的想法，对于每个柱子，我每次找的是一个凹型结构，思路一是排序后从高到低按行计算(O(logn))，
 * 将中间的柱子作为起始考量点，向左右延伸寻找。
 * 其实我们用单调栈做题时都有一种从当前位置像两边扩展的感觉（但这仅仅是感觉，实际上并不是这么干的，如果要真这么干，时间复杂度时 O(n2) 的会超时）
 * 实际上找凹型结构，并不需要向前向后两个方向找，我们将最右边的柱子作为考量点i，只需判断以i结尾的柱子能否形成凹型结构。
 * 即这里考虑将向前的部分从高到低依次入栈，我只需要判断当前柱子i以及栈顶柱子stack.top的高度
 * 因为在栈中存的始终是单调向下的柱子序列,已形成了凹型结构的左边，所以我们只需考量当前柱子高度h_i与栈顶柱子stack.top的高度。
 * while h_i>stack.top 则表示此柱子形成了凹型结构的右边，然后弹出栈顶，计算凹型蓄水量，不断的和栈顶比较直到小于栈顶元素或者栈为空时
 * 将此柱子入栈（保持栈的单调性）。
 */

public class WaterCollection {


    public static int trap(int[] nums){
        if(nums.length<2){
            return 0;
        }
        int[][] pillars = new int[nums.length][2];
        for (int i = 0; i < nums.length; i++) {
            pillars[i][0]=i;
            pillars[i][1]=nums[i];
        }
        Arrays.sort(pillars, (o1, o2) -> o2[1]-o1[1]);
        int res = 0;
        TreeSet<Integer> hs = new TreeSet<>();
        hs.add(pillars[0][0]);
        for (int i = 1; i < nums.length; i++) {
            int h = pillars[i][1];
            int pos = pillars[i][0];
            hs.add(pos);
            Integer right = hs.higher(pos);
            Integer left = hs.lower(pos);
            if(right!=null && left!=null){
                res+=(right-left-1)*(Math.min(nums[right],nums[left])-h);
            }
        }
        return res;
    }

    public static int trap_hash(int[] height){
        int n = height.length;
        if(n<2){
            return 0;
        }
        int res = 0;
        int[] left = new int[n];
        int[] right = new int[n];
        left[0] = height[0];
        right[n-1] = height[n-1];
        for (int i = 1; i < n; i++) {
            left[i] = Math.max(left[i-1],height[i]);
            right[n-i-1] = Math.max(right[n-i],height[n-i-1]);
        }
        for (int i = 1; i < height.length; i++) {
            res+= Math.min(left[i],right[i])-height[i];
        }
        return res;
    }

    public static int trap_stack(int[] height){
        int n = height.length;
        if(n<2){
            return 0;
        }
        int res = 0;
        Stack<Integer> s = new Stack<>();
        s.add(0);
        for (int i = 1; i < n; i++) {
            while (!s.empty()&&height[s.peek()]<height[i]){
                int mid_pos = s.pop();
                if(s.empty()){
                    break;
                }
                int left_pos = s.peek();
                res+=(Math.min(height[left_pos],height[i])-height[mid_pos])*(i-left_pos-1);
            }
            s.add(i);
        }
        return res;
    }

    public static void main(String[] args) {
        int[] nums = {0,1,0,2,1,0,1,3,2,1,2,1};

        System.out.println(trap_stack(nums));
    }
}
