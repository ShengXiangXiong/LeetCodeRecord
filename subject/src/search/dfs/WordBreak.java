package search.dfs;

import java.util.HashSet;
import java.util.List;

/**
 * Created By ShengXiang.Xiong on 2019/10/25
 * 给定一个非空字符串 s 和一个包含非空单词列表的字典 wordDict，判定 s 是否可以被空格拆分为一个或多个在字典中出现的单词。
 * 拆分时可以重复使用字典中的单词。
 * 你可以假设字典中没有重复的单词。
 * <p>
 * 输入: s = "leetcode", wordDict = ["leet", "code"]
 * 输出: true
 * 解释: 返回 true 因为 "leetcode" 可以被拆分成 "leet code"。
 * <p>
 * 输入: s = "applepenapple", wordDict = ["apple", "pen"]
 * 输出: true
 * 解释: 返回 true 因为 "applepenapple" 可以被拆分成 "apple pen apple"。
 *      注意你可以重复使用字典中的单词。
 * <p>
 * 输入: s = "catsandog", wordDict = ["cats", "dog", "sand", "and", "cat"]
 * 输出: false
 */
public class WordBreak {
    /**
     * 回溯匹配+双指针
     * 首先将wordDict转换为Hash表，对于s让指针tail++，然后匹配Hash表，如果匹配；
     * head=tail+1，继续dfs查找，直到指针head指向s末尾
     * 注意剪枝
     *
     * @param s
     * @param wordDict
     * @return
     */
    public static boolean wordBreak(String s, List<String> wordDict) {
        HashSet<String> set = new HashSet<>(wordDict);
        //剪枝，记录返回false的head，如果后面的划分刚好又一此head进行查找，则可以避免tail++，直接返回false
        HashSet<Integer> mem = new HashSet<>();
        return dfs(s, 0, 1, set, mem);
    }

    public static boolean dfs(String s, int head, int tail, HashSet<String> dic, HashSet<Integer> mem) {
        if (head == s.length()) {
            return true;
        }
        if (mem.contains(head)) {
            return false;
        }
        while (tail <= s.length()) {
            if (dic.contains(s.substring(head, tail))) {
                if (dfs(s, tail, tail + 1, dic, mem)) {
                    return true;
                } else {
                    mem.add(tail);
                }
            }
            tail++;
        }
        mem.add(head);
        return false;
    }

    /**
     * 动态规划
     * 用dp[i]记录字符串s[0:i]是否可以由字典中的单词构成。
     * 两重for循环遍历s[i:j], 对于特定的i和j， 有三种情况:
     * 1.如果i == 0, 这种情况很简单，我们遍历一下，看看s[i:j]是否在字典中即可。
     * <p>
     * 2.对于i > 0的情况，我们要分两种情况讨论, 因为如果s[0:i]无法由字典中的单词构成，那么即使s[i:j]可以由字典中的单词构成，也毫无意义。
     * 2.1 如果s[0:i]无法由字典中的单词构成，那么这种case可以直接忽略。
     * 2.2. 如果s[0:i]可以由字典中的单词构成，同时s[i:j]是字典中的单词，那么我们可以认为s[0:j]可以由字典中的单词构成。将dp[j]置为1
     * 最后，检查dp[len(s)]即可.
     */
    public boolean wordBreakDp(String s, List<String> wordDict) {
        HashSet wordDictSet = new HashSet(wordDict);
        boolean[] dp = new boolean[s.length() + 1];
        dp[0] = true;
        for (int i = 1; i <= s.length(); i++) {
            for (int j = 0; j < i; j++) {
                if (dp[j] && wordDictSet.contains(s.substring(j, i))) {
                    dp[i] = true;
                    break;
                }
            }
        }
        return dp[s.length()];
    }

    public static void main(String[] args) {
        String s = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaab";
        String ss = "[\"a\",\"aa\",\"aaa\",\"aaaa\",\"aaaaa\",\"aaaaaa\",\"aaaaaaa\",\"aaaaaaaa\",\"aaaaaaaaa\",\"aaaaaaaaaa\"]";
        List<String> wordDict = utils.matrixTrans.str2List(ss);
        System.out.println(wordBreak(s, wordDict));
    }
}
