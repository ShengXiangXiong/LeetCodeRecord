package dp.字符串dp;

/**
 * Created by Xursan on 2019/9/22.
 * 给定两个单词 word1 和 word2，计算出将 word1 转换成 word2 所使用的最少操作数 。
 * 你可以对一个单词进行如下三种操作：
 * 插入一个字符
 * 删除一个字符
 * 替换一个字符
 * <p>
 * 输入: word1 = "horse", word2 = "ros"
 * 输出: 3
 * 解释:
 * horse -> rorse (将 'h' 替换为 'r')
 * rorse -> rose (删除 'r')
 * rose -> ros (删除 'e')
 * <p>
 * 输入: word1 = "intention", word2 = "execution"
 * 输出: 5
 * 解释:
 * intention -> inention (删除 't')
 * inention -> enention (将 'i' 替换为 'e')
 * enention -> exention (将 'n' 替换为 'x')
 * exention -> exection (将 'n' 替换为 'c')
 * exection -> execution (插入 'u')
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/edit-distance
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class MinEditDistance {
    /**
     * 解决两个字符串的动态规划问题，一般都是用两个指针 i,j 分别指向两个字符串a,b的最后，根据题目的要求然后一步步往前走，
     * 缩小问题的规模。因为dp问题是无后效性的，所以只要从后面的阶段逐次往前分析，则问题规模就会一步步缩小。
     * 对于字符串a和b（目标），具体分析，首先明确，如果a[i]=b[j]，说明
     * 此阶段的最优解只与前一阶段a[i-1]和b[j-1]有关系，即i与j均-1，因为这时两字符相等，肯定什么都不做距离最小；
     * 其次如果a[i]!=b[j]，则此阶段的最优解与前一阶段的状态有关，需要从增删改三个操作中选其中一个操作出来，使当前阶段的编辑距离最小。
     *
     * 首先从上面分析，通过ij指针来作为问题阶段，必然ij是作为阶段特征，则自然有dp[i][j]表示到目前阶段（即a[0...i]变为
     * b[0...j]的阶段的最小编辑距离）的最小编辑距离，那自然增删改和跳过为四个状态。
     *
     * 接下来具体分析，从后往前，自上而下，只要确定了当前状态，它就一定不会受上一阶段的影响。
     * 如果当前指针a[i]==b[j],则i--;j--即有dp[i-1][j-1]=dp[i][j]；相反，不等，则有3个状态，增删改。
     * 对于增而言，则表示在a的当前位置的后面添加一个字符b[j]，此时就与目标串的b[j]一致了，于是再向前一阶段进行匹配，
     * 而此时的前一阶段，由于a串是在i后面添加了一位使b[j]匹配了，但是自己a[i]并没有完成匹配，所以i不变，j--，即dp[i][j-1]+1=dp[i][j];
     * 同理对于删而言，b[j]并没有完成匹配，a[i]删掉了，所以i-1,j不变，即dp[i-1][j]+1 = dp[i][j];
     * 对于改而言，a[i],b[j]都匹配（i--，j-），即dp[i-1][j-1]+1 = dp[i][j];
     *
     * 那相当于我们逆向思考，最终的dp[i][j](i=a.length-1;j=b.length-1)，取决于：
     *  if(a[i]==b[j]){ dp[i][j]=dp[i-1][j-1]}
     *  else{dp[i][j]=min{dp[i-1][j](删)，dp[i][j-1](增)，dp[i-1][j-1]}+1}
     *
     *  分别解释一下：dp[i-1][j]表示a的前i-1个字符变为b的前j个字符的最小编辑距离；
     *                 dp[i][j-1]表示a的前i个字符变为b的前j-1个字符的最小边界距离；
     *                 dp[i-1][j-1]表示a的前i-1个字符变为b的前j-1个字符的最小编辑距离。
     *  这不就直接定义了一个自上而下的递归函数，那么问题就可以通过递归+记忆化数组来解决了。
     *
     * @param word1
     * @param word2
     * @return
     */
    /**
     * 这里直接采取递推的方式，自下而上，前面已经得到了状态转移方程，那么首先找出初始dp值，然后做一个二维的状态转移图，
     * 然后结合图就可以很直观的找出了递推方式的规律，即每个dp，如果当前值不等，都来自于其左上方，正上方，紧邻左边的三个
     * 中的最小值+1.
     *
     * @param word1
     * @param word2
     * @return
     */
    public static int minDistance(String word1, String word2) {
        int n = word1.length();
        int m = word2.length();
        int[][] dp = new int[n + 1][m + 1];
        int cnt = 0;
        while (cnt <= n) {
            dp[cnt][0] = cnt;
            cnt++;
        }
        cnt = 0;
        while (cnt <= m) {
            dp[0][cnt] = cnt;
            cnt++;
        }
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    dp[i][j] = dp[i - 1][j - 1] < dp[i][j - 1] ? Math.min(dp[i - 1][j - 1], dp[i - 1][j]) + 1 : Math.min(dp[i][j - 1], dp[i - 1][j]) + 1;
                }
            }
        }
        return dp[n][m];
    }

    public static int minDistance2(String word1, String word2) {
        int[][] dp = new int[word1.length() + 1][word2.length() + 1];
        for (int i = 1; i < word1.length() + 1; i++) {
            dp[i][0] = i;
        }
        for (int i = 1; i < word2.length() + 1; i++) {
            dp[0][i] = i;
        }
        for (int i = 1; i < word1.length() + 1; i++) {
            for (int j = 1; j < word2.length() + 1; j++) {
                if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    dp[i][j] = Math.min(dp[i - 1][j - 1], Math.min(dp[i - 1][j], dp[i][j - 1])) + 1;
                }
            }
        }
        return dp[word1.length()][word2.length()];
    }

    public static void main(String[] args) {
        String word1 = "sea";
        String word2 = "ate";
        System.out.println(minDistance2(word1, word2));
    }
}
