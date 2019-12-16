package array;

/**
 * Created by Xursan on 2019/12/16.
 * 合并两个有序数组
 */
public class Merge {
    public static void merge(int[] nums1, int m, int[] nums2, int n) {
        int[] tmp = nums1.clone();
        int p = 0, q = 0, cnt = 0;
        while (p < m && q < n) {
            if (tmp[p] < nums2[q]) {
                nums1[cnt++] = tmp[p++];
            } else {
                nums1[cnt++] = nums2[q++];
            }
        }
        while (p < m) {
            nums1[cnt++] = tmp[p++];
        }
        while (q < n) {
            nums1[cnt++] = nums2[q++];
        }
    }

    public static void main(String[] args) {
        int[] nums1 = {1};
        int m = 1;
        int[] nums2 = {};
        int n = 0;
        merge(nums1, m, nums2, n);
        for (int i : nums1) {
            System.out.println(i + " ");
        }
    }
}
