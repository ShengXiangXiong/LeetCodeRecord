package utils;

import java.util.ArrayList;

public class matrixTrans {
    public static int[][] strTrans2Array(String s){
        ArrayList<ArrayList<Integer>> nums = new ArrayList<>();
        StringBuilder str = new StringBuilder(s);
        str.deleteCharAt(0);
        str.deleteCharAt(str.length()-1);
        String[] strs = str.toString().split("]");
        for (String str1 : strs) {
            String[] ss = str1.split("\\D");
            ArrayList<Integer> ls = new ArrayList<>();
            for (String s1 : ss) {
                if (s1.length() > 0) {
                    ls.add(Integer.valueOf(s1));
                }
            }
            if (ls.size() > 0) {
                nums.add(ls);
            }
        }
        int n=nums.size();
        int m=nums.get(0).size();
        int[][] res = new int[n][m];
        int i=0;
        for (ArrayList<Integer> ls : nums) {
            int j=0;
            for (Integer l : ls) {
                res[i][j]=l;
                j++;
            }
            i++;
        }
        return res;
    }

    public static char[][] str2CharArray(String s) {
        ArrayList<ArrayList<Character>> res = new ArrayList<>();
        StringBuilder str = new StringBuilder(s);

//        str.deleteCharAt(0);
//        str.deleteCharAt(str.length() - 1);
//        String[] strs = str.toString().replaceAll("[^(A-Za-z0-9)]"," ").split(" ");
        String[] strs = str.toString().split("[]\n]");
        for (String str1 : strs) {
            String[] ss = str1.split("[^(A-Za-z0-9)]");
            ArrayList<Character> ls = new ArrayList<>();
            for (String s1 : ss) {
                if (s1.length() > 0) {
                    ls.add(s1.charAt(0));
                }
            }
            if (ls.size() > 0) {
                res.add(ls);
            }
        }
        char[][] cs = new char[res.size()][res.get(0).size()];
        for (int i = 0; i < res.size(); i++) {
            for (int j = 0; j < res.get(0).size(); j++) {
                cs[i][j] = res.get(i).get(j);
            }
        }
        return cs;
    }

    public static char[][] str2ZOCharArray(String s) {
        ArrayList<ArrayList<Character>> res = new ArrayList<>();
        StringBuilder str = new StringBuilder(s);

        String[] strs = str.toString().split("[^(A-Za-z0-9)]");
        for (String str1 : strs) {
            ArrayList<Character> ls = new ArrayList<>();
            for (Character s1 : str1.toCharArray()) {
                ls.add(s1);
            }
            if (ls.size() > 0) {
                res.add(ls);
            }
        }
        char[][] cs = new char[res.size()][res.get(0).size()];
        for (int i = 0; i < res.size(); i++) {
            for (int j = 0; j < res.get(0).size(); j++) {
                cs[i][j] = res.get(i).get(j);
            }
        }
        return cs;
    }

    public static ArrayList<String> str2List(String s) {
        ArrayList<String> res = new ArrayList<>();
        String[] strs = s.split("[^(A-Za-z0-9)]");
        for (String str : strs) {
            if (str.length() > 0) {
                res.add(str);
            }
        }
        return res;
    }
    public static void main(String[] args) {
        int[][] nums;
        String str = "[[1,0,1,1,0,0,1,0,0,1],[0,1,1,0,1,0,1,0,1,1],[0,0,1,0,1,0,0,1,0,0],[1,0,1,0,1,1,1,1,1,1],[0,1,0,1,1,0,0,0,0,1],[0,0,1,0,1,1,1,0,1,0],[0,1,0,1,0,1,0,0,1,1],[1,0,0,0,1,1,1,1,0,1],[1,1,1,1,1,1,1,0,1,0],[1,1,1,1,0,1,0,0,1,1]]";
        nums = strTrans2Array(str);
        for (int[] num : nums) {
            for (int i : num) {
                System.out.print(i+" ");
            }
            System.out.println();
        }
        String ss = "[[\"a\",\"a\",\"a\"],[\"a\",\"b\",\"b\"],[\"a\",\"b\",\"b\"],[\"b\",\"b\",\"b\"],[\"b\",\"b\",\"b\"],[\"a\",\"a\",\"a\"],[\"b\",\"b\",\"b\"],[\"a\",\"b\",\"b\"],[\"a\",\"a\",\"b\"],[\"a\",\"b\",\"a\"]]\n";
        for (char[] chars : str2CharArray(ss)) {
            for (char aChar : chars) {
                System.out.print(aChar + " ");
            }
            System.out.println();
        }

    }
}
