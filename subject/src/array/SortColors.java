package array;

/**
 * Created by Xursan on 2019/10/1.
 */
public class SortColors {
    public static void sortColors(int[] nums) {
        int red = 0;
        int white = 0;
        int blue = 0;
        for (int num : nums) {
            if (num == 0) {
                red++;
                white++;
                blue++;
            }
            if (num == 1) {
                white++;
                blue++;
            }
            if (num == 2) {
                blue++;
            }
        }
        int i = 0;
        while (i < red) {
            nums[i] = 0;
            i++;
        }
        while (i < white) {
            nums[i] = 1;
            i++;
        }
        while (i < blue) {
            nums[i] = 2;
            i++;
        }
    }

    public static void main(String[] args) {
        int[] nums = {2, 0, 1};
        sortColors(nums);
        for (int i : nums) {
            System.out.print(i + " ");
        }
    }
}
