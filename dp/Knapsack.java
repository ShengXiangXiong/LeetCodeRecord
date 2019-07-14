package dp;

/**
 * Knapsack
 */
public class Knapsack {

    /**
     * 价值=容量（dp装箱）0-1背包
     */
    public static int OneZeroKnapsack(int[] nums,int cap) {
        int n = nums.length;
        /** 约束条件：容量cap       阶段状态：容量cap+物品规模i    转移条件：选/不选
        * f[i][j] 在容量为j装前i个物品时的最大价值
        *   填表格容易理解，纵i横j
        * 状态转移方程：if(nums[i]<j)   //即物品i重量小于容量j
        *                   f[i][j] = max(f[i-1][j](不选),f[i-1][j-nums[i]]+nums[i]（选）)    
        *              else:        //即物品i重量大于容量j，自然就跳过与前一阶段保持一致
        *                   f[i][j] = f[i-1][j]
        */

        int[][] f = new int[n+1][cap+1];
        //注意i的下标
        for (int i = 1; i < n+1; i++) {
            int item = nums[i-1];
            for (int j = 0; j < cap+1; j++) {
                if(item<=j){
                    f[i][j] = Math.max(f[i-1][j],f[i-1][j-item]+item);  
                }else{
                    f[i][j] = f[i-1][j];
                }
            }
        }
        return f[n][cap];


        /**
         * 空间优化，从上面状态转移可知，每次我们只需要保存上一阶段i-1的信息，不需要保存多个阶段的信息，所以优化成一维数组
         * f[j]表示当前阶段容量为j的最大价值
         *      那么状态转移方程则为：f[j]=Math.max(f[j-nums[i]]+nums[i],f[j]);
         *      括号里面的f表示上一阶段的状态，而左边的f表示待更新的现阶段值，那么问题来了。
         *      上面二维我们可以选择容量从0到cap，因为我们保存了所有信息，这一阶段的值不会相互之间影响。
         *      但如果这里仍为递增循环，则会导致，后面大的容量状态可能会用到前面已经更新的小容量的状态。
         *      比如f[25] 更新了，覆盖了上一阶段的值，表示这一阶段的值，
         *      但是后面f[38]可能会用到f[25]这个状态，而此时的状态并不是我们想要的上一个阶段的状态了。
         *      所以此时我们得保证此阶段更新的值不会影响后续状态，所以这里就必须cap递减来做，
         *      这样可以保证小容量状态一定不会用到更新后的大容量状态
         */

        // int[] f = new int[cap+1];
        // for(int i=0;i<n;i++){  
        //     for(int j=cap;j>=nums[i];j--){
        //         f[j]=Math.max(f[j-nums[i]]+nums[i],f[j]);
        //     }
        // }
        // return f[cap];
    }

    /**
     * 价值=容量（dp装箱）完全背包
     */
    public static int completeKnapsack(int[] nums,int cap) {
        /** 约束条件：容量cap       阶段状态：容量cap    转移条件：选/不选
        * 与上面不同的是，物品任意个，可以重复拿取。
        * 所以这里我们外层对cap循环，这样就可以保证每个阶段的值都是从所有物品得出的，进行阶段状态转移
        * 而上面的0-1背包外层是对物品规模进行循环
        * f[i] 在容量为i时的最大价值
        * 状态转移方程：if(nums[j]<i)   //即物品i重量小于容量j
        *                   f[i] = Math.max(f[i-nums[j]]+nums[j],f[i])
        *              else:        //即物品i重量大于容量j，自然就跳过与前一阶段保持一致
        *                   f[i] = f[i-1]
        */
        int[] f = new int[cap+1];
        for (int i = 0; i < cap+1; i++) {
            for (int j = 0; j < nums.length; j++) {
                if(nums[j]<=i){
                    f[i] = Math.max(f[i-nums[j]]+nums[j],f[i]);
                }
            }
        }
        return f[cap];
    }

    public static void main(String[] args) {
        int[] nums = {5,6};
        System.out.println(OneZeroKnapsack(nums, 13));
    }
}