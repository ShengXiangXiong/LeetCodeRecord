package array;

/**
 * Created by Xursan on 2019/9/18.
 * 给定一个非空整数数组，找到使所有数组元素相等所需的最小移动数，其中每次移动可将选定的一个元素加1或减1。
 * 您可以假设数组的长度最多为10000。
 * 输入:
 * [1,2,3]
 * 输出:
 * 2
 * 说明：
 * 只有两个动作是必要的（记得每一步仅可使其中一个元素加1或减1）：
 * [1,2,3]  =>  [2,2,3]  =>  [2,2,2]
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/minimum-moves-to-equal-array-elements-ii
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class MinMoves2 {
    /**
     * 仔细找规律发现，当取其中位数时，其移动距离最小。而对于快速找中位数，可以使用分治思想。
     * 中位数证明：
     * 为了方便，我们先假设一共有2n+1个数，它们从小到大排序之后如下：
     * . . . a m b . . .
     * 其中m是中位数。此时，m左边有n个数，m右边也有n个数。我们假设把m左边所有数变成m需要的代价是x，
     * 把m右边所有数变成m的代价是y，此时的总代价就是t = x+y
     * 好，如果你觉得中位数不是最优解，我们来看看把所有数都变成a的总代价是多少。
     * 由于之前m右边n个数变成m的代价是y，现在让右边的数全变成a，此时右边的数的代价是y+(m-a)*n；m左边的n个数全变成a，
     * 它们的代价会减少到x-(m-a)*n。所以两边相加，结果还是 x-(m-a)*n + y+(m-a)*n == x+y。 但是，别忘了，m也要变成a，
     * 所以总代价是x+y+m-a，大于x+y。同理，如果让所有数都变成比m大的b，总代价则变为x+y+b-m（你可以自己算一下），
     * 依然比x+y大。并且越往左移或者往右移，这个值都会越来越大。 因此，在有2n+1个数的时候，选择中位数就是最优解。
     * <p>
     * 这个证明同样可以很简单地推广到2n个数。
     * . . . a b . . .
     * 假设a左边有n-1个数，b右边也有n-1个数。如果我们选择把所有数都变成a，设a左边所有数变成a的代价是x，
     * b右边所有数变成a的代价是y，因此总代价是x+y+b-a（b也要变成a）。 现在尝试下如果把所有数变成b，
     * 那么a左边的总代价变成了x+(b-a)*(n-1)，b右边总代价变成了y-(b-a)*(n-1)，同时还要把a变成b，
     * 因此总代价同样为x+(b-a)*(n-1)+y-(b-a)*(n-1) == x+y+b-a。也就是说当总个数为2n时，两个中位数选哪个最终结果都一样，
     * 但是继续左移和继续右移，都会使总代价增加（可以自己试试）。
     * 至此，证明了中位数是最优策略
     * <p>
     * 可以把数组中的数看做n个人站在数轴上，每次可以让一个人走一步，最后所有人在一个点相遇，问总共最少走几步？
     * 这要怎么走呢？当然是两边的人往中间走啊。
     *
     * @return
     */
    public int partition(int[] a, int s, int e) {
        int p = s;
        int q = e;
        int tmp = a[s];
        while (p < q) {
            while (p < q && a[q] >= tmp) {
                q--;
            }
            a[p] = a[q];
            while (p < q && a[p] <= tmp) {
                p++;
            }
            a[q] = a[p];
        }
        a[p] = tmp;
        return p;
    }

    public int minMoves2(int[] nums) {
        int tar = nums.length / 2;
        int s = 0;
        int e = nums.length - 1;
        int pos = partition(nums, s, e);
        while (pos != tar) {
            if (pos < tar) {
                s = pos + 1;
                pos = partition(nums, s, e);
            } else {
                e = pos - 1;
                pos = partition(nums, s, e);
            }
        }
        int ans = 0;
        s = 0;
        e = nums.length - 1;
        while (s < e) {
            ans += (nums[e--] - nums[s++]);
        }
        return ans;
    }

    public static void main(String[] args) {
        int[] nums = {13, 2, 5, 6, 7, 1, 0};
        MinMoves2 minMoves = new MinMoves2();
        System.out.println(minMoves.minMoves2(nums));
    }
}
