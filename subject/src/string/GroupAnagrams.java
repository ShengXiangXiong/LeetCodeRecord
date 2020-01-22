package string;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Xursan on 2019/8/7.
 *
 * input ["tea","ate","git","atb","tig"]
 * output [["tea","ate"],["git",""tig],["atb]]
 * 字母相同的放在一组
 *
 * 毫无疑问，应该使用hash表，关键点在于key的构造
 * 方案一：对字符串中的每个字符进行统计构造hashmap => tmp 作为key
 * 方案二：对字符串排序，这样就避免了统计，直接将char[]转换为的String作为key（注意点：不要用数组作为key，因为数组没有
 * 重写HashCode和Equals方法）
 */
public class GroupAnagrams {
    /**
     * 思路很简单：构造hash字典，统计每一个字符串中字符出现的次数，tmp为该字符串的字符统计结果，
     * res为最终的结果，我们知道只要字符出现次数一样即可分在同一组，所以res的key则为tmp，value则为list<String>
     * @param strs
     * @return
     */
    public static List<List<String>> groupAnagrams(String[] strs){
        HashMap<HashMap<Character,Integer>,List<String>> group = new HashMap<>();
        for (String str : strs) {
            char[] charArray = str.toCharArray();
            HashMap<Character,Integer> tmp = new HashMap<>();
            for (char c : charArray) {
                if(tmp.containsKey(c)){
                    tmp.put(c,tmp.get(c)+1);
                }else {
                    tmp.put(c,1);
                }
            }
            if(group.containsKey(tmp)){
                group.get(tmp).add(str);
            }else {
                List<String> s1 = new ArrayList<>();
                s1.add(str);
                group.put(tmp,s1);
            }
        }
        List<List<String>> res = new ArrayList<>();
        res.addAll(group.values());
        return res;

    }

    /**
     * 其实这道题的关键点就在于相同组的字符串中的各字符的位置不相同，我们知道
     * @param strs
     * @return
     */
    public static List<List<String>> Soulution2 (String[] strs){
        HashMap<String,List<String>> res = new HashMap<>();
        for (String str : strs) {
            char[] s = str.toCharArray();
            Arrays.sort(s);
            String tmp = String.valueOf(s);
            if(res.containsKey(tmp)){
                res.get(tmp).add(str);
            }else {
                List<String> ls = new ArrayList<>();
                ls.add(str);
                res.put(tmp,ls);
            }
        }
        return new ArrayList<>(res.values());
    }

    public static void main(String[] args) {
        String[] strs = {"eta","ate","it","att","tta","x","nat","tan"};
        for (List<String> sts : Soulution2(strs)) {
            for (String s : sts) {
                System.out.print(s+"    ");
            }
            System.out.println();
        }
    }

}
