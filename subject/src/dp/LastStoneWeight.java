package dp;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * LastStoneWeight
 */
public class LastStoneWeight {

    private static int res = 3000001;
    private static int n;
    private static int diff = 3000001;
    // private static int half_res=0;

    public static int lastStoneWeight(int[] stones) {
        PriorityQueue<Integer> maxHeap = new PriorityQueue<Integer>(1000, new Comparator<Integer>() {
            //new 了一个匿名Comparator对象，Comparator是一个函数式接口，在匿名函数中使用较方便
            @Override
            public int compare(Integer o1, Integer o2) {
                return o2 - o1;
            }
        });
        for (int tmp : stones) {
            maxHeap.add(tmp);
        }
        int res = maxHeap.peek();
        while (maxHeap.size() > 1) {
            res = Math.abs(maxHeap.poll() - maxHeap.poll());
            maxHeap.add(res);
        }
        return res;
    }


    /**
     * dfs暴力穷举
     *
     * @param nums
     * @param i
     * @param tmp
     */
    public static void minWeight(int[] nums, int i, int tmp) {
        if (i < n) {
            minWeight(nums, i + 1, tmp + nums[i]);
            minWeight(nums, i + 1, tmp - nums[i]);
        } else {
            res = Math.min(res, Math.abs(tmp));
        }
    }

    /**
     * dfs已知目标剪枝
     *
     * @param
     * @return
     */
    public static void minDisTarget(int[] nums, int i, int tar1) {
        for (; i < n; i++) {
            int tar = tar1 - nums[i];
            if (tar < 0) {
                continue;
            }//剪枝
            diff = Math.abs(tar) >= Math.abs(diff) ? diff : tar;//更新diff
            minDisTarget(nums, i + 1, tar);
        }
    }


    public static int lastStoneWeightII(int[] stones) {

        /**
         *
         */
        // n = stones.length;
        // minWeight(stones, 1, stones[0]);
        // return res;

        int sum = 0;
        n = stones.length;
        for (int tmp : stones) {
            sum += tmp;
        }
        int cap = sum / 2;
        int[] f = new int[cap + 1];
        //0-1背包
        for (int i = 0; i < n; i++) {
            for (int j = cap; j >= stones[i]; j--) {
                f[j] = Math.max(f[j - stones[i]] + stones[i], f[j]);
            }
        }
        return sum - 2 * f[cap];
    }


    private static boolean helper(int[] nums, int idx, int sum, int target) {
        if (sum == target) {
            return true;
        }
        if (sum > target) {
            return false;
        }
        if (idx == nums.length) {
            return false;
        }
        return helper(nums, idx + 1, sum + nums[idx], target) || helper(nums, idx + 1, sum, target);
    }

    public static int betterLastStoneWeightII(int[] stones) {
        int sum = 0;
        for (int stone : stones) {
            sum += stone;
        }
        for (int i = (sum >> 1); ; i--) {
            if (helper(stones, 0, 0, i)) {
                return sum - 2 * i;
            }
        }
    }

    public static void main(String[] args) {
        int[] a = {21, 16, 23, 32, 25, 13, 20, 18, 22, 21, 84, 35, 33, 17, 27, 24, 10, 19, 31, 26, 94, 37, 31, 25, 24, 25, 15, 23, 17, 13};
        int[] b = {31, 26, 33, 21, 40};
        System.out.println(lastStoneWeightII(b));
    }
}