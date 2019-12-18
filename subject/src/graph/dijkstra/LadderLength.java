package graph.dijkstra;

import java.util.*;

/**
 * Created By ShengXiang.Xiong on 2019/12/17
 * 给定两个单词（beginWord 和 endWord）和一个字典，找到从 beginWord 到 endWord 的最短转换序列的长度。转换需遵循如下规则：
 * 每次转换只能改变一个字母。
 * 转换过程中的中间单词必须是字典中的单词。
 * 说明:
 * 如果不存在这样的转换序列，返回 0。
 * 所有单词具有相同的长度。
 * 所有单词只由小写字母组成。
 * 字典中不存在重复的单词。
 * 你可以假设 beginWord 和 endWord 是非空的，且二者不相同。
 * <p>
 * 输入:
 * beginWord = "hit",
 * endWord = "cog",
 * wordList = ["hot","dot","dog","lot","log","cog"]
 * 输出: 5
 * 解释: 一个最短转换序列是 "hit" -> "hot" -> "dot" -> "dog" -> "cog",
 * 返回它的长度 5。
 * <p>
 * 输入:
 * beginWord = "hit"
 * endWord = "cog"
 * wordList = ["hot","dot","dog","lot","log"]
 * 输出: 0
 * 解释: endWord "cog" 不在字典中，所以无法进行转换。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/word-ladder
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class LadderLength {
    /**
     * 其实一开始就想到的应该是和图有关的算法，而马上想到的就是寻找最短路径。而对于图的最短路径，自然而然想到的是dijkstra
     * 算法，但是由于这里所有边的权重都可看为1，那用bfs也可以完成（bfs求最短路径算是dijkstra算法的一种特例）
     * <p>
     * 这里采取 dijkstra
     *
     * @param beginWord
     * @param endWord
     * @param wordList
     * @return
     */
    public static int ladderLength(String beginWord, String endWord, List<String> wordList) {
        int s = wordList.size();
        int end = wordList.indexOf(endWord);
        int ori = 0;
        int maxVal = 1000000000;
        if (end == -1) {
            return 0;
        }
        end++;
        int[][] dp = new int[s + 1][s + 1];
        for (int i = 1; i < s; i++) {
            for (int j = i + 1; j < s + 1; j++) {
                String str1 = wordList.get(i - 1);
                String str2 = wordList.get(j - 1);
                int k = 0;
                int cnt = 0;
                while (k < str1.length()) {
                    if (str1.charAt(k) != str2.charAt(k)) {
                        cnt++;
                    }
                    if (cnt > 1) {
                        dp[i][j] = maxVal;
                        dp[j][i] = maxVal;
                        break;
                    }
                    k++;
                }
                if (cnt == 1) {
                    dp[i][j] = 1;
                    dp[j][i] = 1;
                }
            }
        }

        Queue<Integer> rest = new ArrayDeque<>();
        List<Integer> comp = new ArrayList<>();
        for (int i = 1; i < s + 1; i++) {
            String str1 = wordList.get(i - 1);
            int k = 0;
            int cnt = 0;
            while (k < str1.length()) {
                if (str1.charAt(k) != beginWord.charAt(k)) {
                    cnt++;
                }
                if (cnt > 1) {
                    dp[i][ori] = maxVal;
                    dp[ori][i] = maxVal;
                    break;
                }
                k++;
            }
            if (cnt == 1) {
                dp[ori][i] = 1;
                dp[i][ori] = 1;
                comp.add(i);
            } else {
                rest.add(i);
            }
        }

        while (rest.size() > 0 && comp.size() > 0) {
            int cnt1 = rest.size();
            List<Integer> com = new ArrayList<>(comp);
            comp.clear();
            while (cnt1-- > 0) {
                int i = rest.poll();
                for (Integer j : com) {
                    dp[ori][i] = Math.min(dp[ori][i], dp[ori][j] + dp[j][i]);
                }
                if (dp[ori][i] != maxVal) {
                    comp.add(i);
                } else {
                    rest.add(i);
                }
                if (dp[ori][end] != maxVal) {
                    return dp[ori][end] + 1;
                }
            }
        }
        return dp[ori][end] == maxVal ? 0 : dp[ori][end] + 1;
    }

    public static void main(String[] args) {
        String beginWord = "hog";
        String endWord = "cog";
        List<String> wordList = Arrays.asList("cog");
        System.out.println(ladderLength(beginWord, endWord, wordList));
    }
}
