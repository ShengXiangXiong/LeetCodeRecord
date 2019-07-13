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
            char[] c = str1.toCharArray();
            ArrayList<Integer> ls = new ArrayList<>();
            for (char c1 : c) {
                int num = c1-'0';
                if(num>=0&&num<=9){
                    ls.add(num);
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
