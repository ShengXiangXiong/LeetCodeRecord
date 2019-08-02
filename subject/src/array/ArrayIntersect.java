package array;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ArrayIntersect {
    public static int[] intersect(int[] nums1, int[] nums2) {
        HashMap<Integer, Integer> map = new HashMap<>();
        List<Integer> res = new ArrayList<>();
        int[] minAr;
        int[] maxAr;
        if (nums1.length < nums2.length) {
            minAr = nums1;
            maxAr = nums2;
        } else {
            minAr = nums2;
            maxAr = nums1;
        }
        for (int i : minAr) {
            if (map.containsKey(i)) {
                map.put(i, map.get(i) + 1);
            } else {
                map.put(i, 1);
            }
        }
        for (int i : maxAr) {
            if (map.containsKey(i)) {
                int tmp = map.get(i);
                if (tmp > 0) {
                    res.add(i);
                    map.put(i, tmp - 1);
                }
            }
        }
        int cnt = 0;
        int[] ress = new int[res.size()];
        for (int i : res) {
            ress[cnt] = i;
            cnt++;
        }
        return ress;
    }

    public static void main(String[] args) {
        int[] nums1 = {3, 2, 2, 1};
        int[] nums2 = {1, 2, 2, 1};
        for (int i : intersect(nums1, nums2)) {
            System.out.print(i + " ");
        }
    }
}
