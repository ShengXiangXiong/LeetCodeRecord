package 完全组合类;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Xursan on 2020/2/28.
 * 47. 全排列 II
 */
public class PermuteUnique {
    private List<List<Integer>> ans;

    public List<List<Integer>> permuteUnique(int[] nums) {
        Arrays.sort(nums);
        ans = new ArrayList<>();
        boolean[] mem = new boolean[100000];
        dfs(new ArrayList<>(), nums, mem);
        return ans;
    }

    /**
     * 关键点：
     * 1. 在于记录使用过的位置，然后直接跳过
     * 2. 回溯时一定记得重置
     *
     * @param res
     * @param nums
     * @param pos
     */
    public void dfs(List<Integer> res, int[] nums, boolean[] pos) {
        if (res.size() == nums.length) {
            ans.add(new ArrayList<>(res));
            return;
        }
        for (int i = 0; i < nums.length; i++) {
            //首先是没有被用过，其次重复则过滤，注意i-1有可能是已经被使用过的，所以使用过的i-1不算重复，不能跳过
            if (pos[i] || (i > 0 && !pos[i - 1] && nums[i] == nums[i - 1])) {
                continue;
            }
            res.add(nums[i]);
            pos[i] = true;
            dfs(res, nums, pos);
            res.remove(res.size() - 1);
            pos[i] = false;
        }
    }

    public static void main(String[] args) {
        PermuteUnique p = new PermuteUnique();
        int[] nums = new int[]{1, 1, 1, 1, 1, 2};
        for (List<Integer> integers : p.permuteUnique(nums)) {
            for (Integer integer : integers) {
                System.out.print(integer + " ");
            }
            System.out.println();
        }
    }
}
