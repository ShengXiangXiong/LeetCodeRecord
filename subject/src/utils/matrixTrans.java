package utils;

import java.util.ArrayList;

public class matrixTrans {
    public static int[][] strTrans2Array(String s){
        ArrayList<ArrayList<Integer>> nums = new ArrayList<>();
        StringBuilder str = new StringBuilder(s);
        str.deleteCharAt(0);
        str.deleteCharAt(str.length()-1);
        String[] strs = str.toString().split("],");
        for (String str1 : strs) {
            String[] ss = str1.split("\\D");
            ArrayList<Integer> ls = new ArrayList<>();
            for (String s1 : ss) {
                if (s1.length() > 0) {
                    ls.add(Integer.valueOf(s1));
                }
            }
            nums.add(ls);
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
    }
}
