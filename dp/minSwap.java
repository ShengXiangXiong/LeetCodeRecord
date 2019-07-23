package dp;



class minSwap {
    /**
     * 我们有两个长度相等且不为空的整型数组 A 和 B 。

    我们可以交换 A[i] 和 B[i] 的元素。注意这两个元素在各自的序列中应该处于相同的位置。

    在交换过一些元素之后，数组 A 和 B 都应该是严格递增的（数组严格递增的条件仅为A[0] < A[1] < A[2] < ... < A[A.length - 1]）。

    给定数组 A 和 B ，请返回使得两个数组均保持严格递增状态的最小交换次数。假设给定的输入总是有效的。

    示例:
    输入: A = [1,3,5,4], B = [1,2,3,7]
    输出: 1
    解释: 
    交换 A[3] 和 B[3] 后，两个数组如下:
    A = [1, 3, 5, 7] ， B = [1, 2, 3, 4]
    两个数组均为严格递增的。
     */


    /**
     * 仔细分析我们可以发现，在某些条件下，前一阶段的状态（是否交换）可直接影响后一阶段的状态（是否交换）
     * 也就是说，后一阶段的结果仅仅依赖于前一阶段，很显然这就是一个dp问题。关键状态我们已经找到了即 是否交换，
     * 下面就是仔细分析，找到状态转移方程
     */
    public static int minSwap(int[] a, int[] b) {
        int cnt = 0;
        int n = a.length;
        int exc=1;//代表当前位置（阶段）交换（状态）的情况下的最优解
        int fix=0;//代表当前位置（阶段）不交换（状态）的情况下的最优解
        for (int i = 0; i < n-1;i++) {
            int exc_b=exc;//记录上一个阶段状态的临时变量
            int fix_b=fix;//记录上一个阶段状态的临时变量
            //不满足递增要求，则需要交换，交换时只能交换第i个或者第i+1个
            if(a[i]>=a[i+1]||b[i]>=b[i+1]){
                exc = fix_b+1;
                fix = exc_b;
            }else{
                //当满足递增要求时，也要分两种情况，一种情况是，当只交换其中一组时，会打乱原本递增的属性，这时需要两者都交换
                if(a[i]>=b[i+1]||b[i]>=a[i+1]){
                    exc = exc_b+1;
                    fix = fix_b;
                }else{
                    exc = Math.min(exc_b, fix_b)+1;
                    fix = Math.min(exc_b, fix_b);
                }
            }

        }
        return Math.min(exc, fix);
    }
    public static void main(String[] args) {
        // int[] a = {0,2,3,4,7,8,8,9,10,15,16,17,18,21,21,25};
        // int[] b = {0,1,2,3,4,6,10,11,12,13,14,15,16,17,26,28};
        int[] a={0,7,8,10,10,11,12};
        int[] b={4,4,5,7,11,14,15};
        System.out.println(minSwap(a, b));
    }
}