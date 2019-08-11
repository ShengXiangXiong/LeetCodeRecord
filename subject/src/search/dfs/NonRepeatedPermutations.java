package search.dfs;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class NonRepeatedPermutations {

    public static List<List<Integer>> dfs(List<Integer> nums) {
        List<List<Integer>> res = new ArrayList<>();
        if (nums.size() == 1) {
            List<Integer> tmp = new ArrayList<>();
            tmp.add(nums.get(0));
            res.add(tmp);
            return res;
        }
        HashSet<Integer> mem = new HashSet<>();
        for (Integer l : nums) {
            List<Integer> tmp = new ArrayList<>(nums);
            if (mem.contains(l)) {
                continue;
            }
            mem.add(l);
            tmp.remove(l);
            for (List<Integer> ls : dfs(new ArrayList<>(tmp))) {
                ls.add(l);
                res.add(ls);
            }
        }
        return res;
    }

    public static List<List<Integer>> permuteUnique(int[] nums) {
        List<Integer> tmp = new ArrayList<>();
        for (int i : nums) {
            tmp.add(i);
        }
        return dfs(tmp);
    }

    public static void main(String[] args) {
        int[] nums = {1, 1, 1, 1, 1, 1, 2};
        for (List<Integer> ls : permuteUnique(nums)) {
            for (Integer l : ls) {
                System.out.print(l + " ");
            }
            System.out.println();
        }
    }
}
