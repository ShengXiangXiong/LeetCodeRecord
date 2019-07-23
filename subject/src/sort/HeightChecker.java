package sort;

import java.util.Arrays;

/**
 * HeightChecker
 */
public class HeightChecker {

    public static int heightChecker(int[] heights) {
        int[] copy = heights.clone();
        Arrays.sort(copy);
        int cnt = 0;
        for (int i = 0; i < heights.length; i++) {
            if (copy[i] != heights[i]) {
                cnt++;
            }
        }
        return cnt;
    }

    public static void main(String[] args) {
        int[] a = {1, 1, 1, 1, 1, 1, 1, 1};
        System.out.println(heightChecker(a));
    }
}