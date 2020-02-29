package 完全组合类;

import java.util.ArrayList;
import java.util.List;

/**
 * Created By ShengXiang.Xiong on 2019/10/30
 */
public class Subsets {
    /**
     * 递归解决。
     * 关键点在于递归函数的设计：
     * cnt：表示还剩下几个数待加入，比如为3，则代表在当前位置下，还可以加入3个数
     * i：代表当前所处的nums位置
     * i+cnt<=nums.length：表示当前位置下是否还能向前扩展cnt个，如果可以则继续dfs，否则退出
     * cnt=0：递归出口，表示前面已经有cnt个数等待加入了，此时已经没有可以加入的数了，此时应该直接返回一个空List，让前面的值加入
     *
     * @param nums
     * @param i
     * @param cnt
     * @return
     */
    public static List<List<Integer>> dfs(int[] nums, int i, int cnt) {
        List<List<Integer>> res = new ArrayList<>();
        if (cnt == 0) {
            List<Integer> a = new ArrayList<>();
            res.add(a);
            return res;
        }
        while (i + cnt <= nums.length) {
            for (List<Integer> df : dfs(nums, i + 1, cnt - 1)) {
                df.add(nums[i]);
                res.add(df);
            }
            i++;
        }
        return res;
    }

    public static List<List<Integer>> subsets(int[] nums) {
        int i = 0;
        List<List<Integer>> res = new ArrayList<>();
        while (i <= nums.length) {
            res.addAll(dfs(nums, 0, i));
            i++;
        }
        return res;
    }

    /**
     * 更简单的一种思路：直接从空集开始构造，每遇到一个数，在保留原有子集的基础上，再往已存在的子集中添加当前数
     * 比如：123
     * 初始：[]
     * 遇到1====>[],[1]
     * 遇到2===》[],[1],[2],[1,2]
     * 遇到3===》[],[1],[2],[1,2],[3],[1,3],[2,3],[1,2,3]
     *
     * @param nums
     * @return
     */
    public static List<List<Integer>> subsets2(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        res.add(new ArrayList<>());
        for (int i = 0; i < nums.length; i++) {
            //注意List是引用类型，每次添加时都要new一个再添加，且循环时不要直接foreach循环res，因为res的size会不断变化
            int s = res.size();
            int j = 0;
            while (j < s) {
                List<Integer> tmp = new ArrayList<>(res.get(j));
                tmp.add(nums[i]);
                res.add(tmp);
                j++;
            }
        }
        return res;
    }

    public static void main(String[] args) {
        int[] nums = {1, 2, 3};
        for (List<Integer> subset : subsets2(nums)) {
            for (Integer i : subset) {
                System.out.print(i + " ");
            }
            System.out.println();
        }
    }
}
