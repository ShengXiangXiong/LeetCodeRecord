package dp;

/**
 * stockProfit
 */
public class stockProfit {

    public static int maxProfit(int[] a) {
        int i = 0;
        int res = 0;
        int n = a.length;
        while(i<n){
            int buy = a[i];
            i++;
            while(i<n&&a[i]<=buy){
                buy = a[i];
                i++;
            }
            while(i<n-1&&a[i+1]>=a[i]){
                i++;
            }
            if(i<n){
                res+=a[i]-buy;
            }
            i++;
        }
        return res>0?res:0;
    }
    
    public static int oneTransictionMaxProfit(int[] a) {
        /**
         * dp[i]:以i结尾的最大利润 buy:在i阶段之前买入的最低价格
         * 状态转移 buy = min(buy,a[i])  dp[i] = max(dp[i-1],a[i]-buy)
         * 进一步思考，dp[i]优化成profit：只需要记录其前一个阶段的最佳值即可
         */
        int buy = a[0];
        int profit = 0;
        for (int i = 1; i < a.length;i++) {
            profit = Math.max(profit, a[i]-buy);
            buy = Math.min(buy, a[i]);
        }
        return profit;
    }
    public static void main(String[] args) {
        int[] a={7,6,5,4,3,2,1};
        System.out.println(oneTransictionMaxProfit(a));
    }
}