package dp;
import java.util.*;

/**
 * Created by Xursan on 2019/8/8.
 *
 * 接雨水
 * 输入的为每个柱子的高度，每个柱子宽为1
 * input:[0,1,0,2,1,0,1,3,2,1,2,1]
 * output:6
 *
 * 思路：要想能够接受雨水，即要连续相邻的柱子能够形成凹型结构。故只需要以每一个柱子i，向前寻找高于i的第一个柱子j，向后寻找
 * 高于i的第一个柱子k。然后这个凹型结构的蓄水量便是v = (k-j)*min(nums[j],nums[k])
 * 可以定义一个结构体，或者类 pillar(pos,height) 然后先按height排序O(nlogn)。从高到低，依次取柱子，第一高和第二高的柱子必然不能够形成
 * 凹型结构，故从3开始，然后选择其前面的柱子中（因为排序后必然前面满足高度的要求）左右方向上位置（pos）离得最近的作为凹型结构。
 * 这里可以采取一个Treeset作为前面柱子pos的容器，这样可以快速二分查找，定位两边离得最近的柱子位置。O（nlogn）
 */

public class WaterCollection {


    public static int height(int[] nums){
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

    public static void main(String[] args) {
        int[] nums = {0,1,0,2,1,0,1,3,2,1,2,1};

        System.out.println(height(nums));
    }
}
