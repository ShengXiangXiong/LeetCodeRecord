package array;

/**
 * Created By ShengXiang.Xiong on 2019/9/18
 * <p>
 * 在仅包含 0 和 1 的数组 A 中，一次 K 位翻转包括选择一个长度为 K 的（连续）子数组，同时将子数组中的每个 0 更改为 1，而每个 1 更改为 0。
 * 返回所需的 K 位翻转的次数，以便数组没有值为 0 的元素。如果不可能，返回 -1。
 * 输入：A = [0,1,0], K = 1
 * 输出：2
 * 解释：先翻转 A[0]，然后翻转 A[2]。
 * <p>
 * 输入：A = [1,1,0], K = 2
 * 输出：-1
 * 解释：无论我们怎样翻转大小为 2 的子数组，我们都不能使数组变为 [1,1,1]。
 * <p>
 * 输入：A = [0,0,0,1,0,1,1,0], K = 3
 * 输出：3
 * 解释：
 * 翻转 A[0],A[1],A[2]: A变成 [1,1,1,1,0,1,1,0]
 * 翻转 A[4],A[5],A[6]: A变成 [1,1,1,1,1,0,0,0]
 * 翻转 A[5],A[6],A[7]: A变成 [1,1,1,1,1,1,1,1]
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/minimum-number-of-k-consecutive-bit-flips
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class MinKBitFlips {

    /**
     * 思路：设置双指针，前后指针分别指向第一个0的位置，不断翻转，直至pq距离小于k，此时就无法翻转了。
     * 如果p>q则表示已经翻转完了所有的0，能够满足要求，否则表示还剩下没有翻转的0
     *
     * @return
     */
    public int find0pos(int[] a, int p, int q, boolean pre) {
        if (pre) {
            while (p <= q && a[p] == 1) {
                p++;
            }
        } else {
            while (q >= p && a[q] == 1) {
                q--;
            }
        }
        return pre ? p : q;
    }

    public void flips(int[] a, int p, int q, boolean pre, int k) {
        while (k-- > 0) {
            if (pre) {
                a[p] = 1 - a[p];
                p++;
            } else {
                a[q] = 1 - a[q];
                q--;
            }
        }
    }

    public int minKBitFlips(int[] A, int K) {
        int p = 0;
        int q = A.length - 1;
        int res = 0;
        while (q - p + 1 >= K) {
            //找到p指针的0位置
            p = find0pos(A, p, q, true);
            //如果pq距离已经小于k了，直接返回，表示不能翻转了
            if (q - p + 1 < K) {
                break;
            }
            //翻转
            flips(A, p, q, true, K);
            res++;
            p = find0pos(A, p, q, true);
            //同p指针，只是向前而言
            q = find0pos(A, p, q, false);
            if (q - p + 1 < K) {
                break;
            }
            flips(A, p, q, false, K);
            res++;
            q = find0pos(A, p, q, false);
        }
        return q >= p ? -1 : res;
    }

    /**
     * 贪心。
     * 试想一下，如果最左边的元素是0，那么无论如何都必须要翻转这个0才行，所以得翻转这个以最左边的0开始的子数组；
     * 同理，如果最左边的元素是1，那么我们一定不能够翻转他，因为就算翻转了，后面还得翻转过来，所以没必要。
     * 这证明了我们可以贪心地执行这一过程：在明确是否要反转第一个子数组之后（位置 0 至 K-1），
     * 我们可以考虑将数组中第一个元素（值为 1）移除，然后重复这个过程。
     * 如果到最后剩余翻转的0小于k，则表示无法翻转，返回-1；否则返回ans
     *
     * @param A
     * @param K
     * @return
     */
    public int minKBitFlips1(int[] A, int K) {
        int N = A.length;
        int[] hint = new int[N];
        int ans = 0, flip = 0;
        // 当我们翻转子数组形如 A[i], A[i+1], ..., A[i+K-1]
        // 我们可以在此位置置反翻转状态，并且在位置 i+K 设置一个提醒，告诉我们在那里也要置反翻转状态
        for (int i = 0; i < N; ++i) {
            flip ^= hint[i];
            if (A[i] == flip) {  // 我们是否必须翻转从此开始的子数组
                ans++;  // 翻转子数组 A[i] 至 A[i+K-1]
                if (i + K > N) return -1;  // 如果没办法翻转整个子数组了，那么就不可行
                flip ^= 1;
                if (i + K < N) hint[i + K] ^= 1;
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        int[] A = {1, 1, 1, 1, 1, 1};
        int K = 3;
        MinKBitFlips a = new MinKBitFlips();
        System.out.println(a.minKBitFlips(A, K));
    }
}
