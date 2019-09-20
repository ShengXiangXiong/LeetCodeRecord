package twoPointer;

/**
 * Created by Xursan on 2019/9/16.
 * 给定一个字符串 S 和一个字符 C。返回一个代表字符串 S 中每个字符到字符串 S 中的字符 C 的最短距离的数组。
 * <p>
 * 输入: S = "loveleetcode", C = 'e'
 * 输出: [3, 2, 1, 0, 1, 0, 0, 1, 2, 2, 1, 0]
 * 说明:
 * 字符串 S 的长度范围为 [1, 10000]。
 * C 是一个单字符，且保证是字符串 S 里的字符。
 * S 和 C 中的所有字母均为小写字母。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/shortest-distance-to-a-character
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class ShortestToChar {
    /**
     * 规模10000必然要O(n)的解法，这道题其实和前面做过的雨水收集很类似。
     * 首先能够确定的是，对于每一个字符我需要向两边展开，分别寻找到第一个等于给定字符C的位置，
     * 然后比较距左右两边的位置大小即可。
     * 所以很直观的想法就是，首先从左往右遍历，定义一个前向指针pos，始终指向其左边的第一个给定字符C的位置，
     * 然后res[i] = i-pos，如果cs[i]==C，则更新pos=i；
     * 然后从右往左遍历，同样定义一个右边的前向指针，始终指向其右边的第一个给定字符C的位置，
     * 然后res[i] = Min(res[i],pos-i)，如果cs[i]==C，则更新pos=i。
     *
     * @param S
     * @param C
     * @return
     */
    public static int[] shortestToChar(String S, char C) {
        char[] cs = S.toCharArray();
        int n = cs.length;
        int[] res = new int[n];
        int pos = -n << 1;
        for (int i = 0; i < n; i++) {
            if (cs[i] == C) {
                pos = i;
            }
            res[i] = i - pos;
        }
        pos = n << 1;
        for (int i = n - 1; i >= 0; i--) {
            if (cs[i] == C) {
                pos = i;
            }
            res[i] = Math.min(pos - i, res[i]);
        }
        return res;
    }

    /**
     * 从前面的总结，我们知道，这种需要往两边扩展的大多都可以使用双指针解法。
     *
     * @param S
     * @param C
     * @return
     */
    public static int[] shortestToChar1(String S, char C) {
        char[] cs = S.toCharArray();
        int n = cs.length;
        int[] res = new int[n];
        int pos = -n << 1;
        for (int i = 0; i < n; i++) {
            if (cs[i] == C) {
                pos = i;
            }
            res[i] = i - pos;
        }
        pos = n << 1;
        for (int i = n - 1; i >= 0; i--) {
            if (cs[i] == C) {
                pos = i;
            }
            res[i] = Math.min(pos - i, res[i]);
        }
        return res;
    }

    public static void main(String[] args) {
        String S = "abaa";
        char C = 'b';
        for (int i : shortestToChar(S, C)) {
            System.out.print(i + " ");
        }
    }
}
