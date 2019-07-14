package Array;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.TreeMap;


/**
 * RearrangeBarcodes
 * 
 * 在一个仓库里，有一排条形码，其中第 i 个条形码为 barcodes[i]。

    请你重新排列这些条形码，使其中两个相邻的条形码 不能 相等。 你可以返回任何满足该要求的答案，此题保证存在答案。
    示例 1：
    输入：[1,1,1,2,2,2]
    输出：[2,1,2,1,2,1]
    示例 2：
    输入：[1,1,1,1,2,2,3,3]
    输出：[1,3,1,3,2,1,2,1]
 */
public class RearrangeBarcodes {

    /**
     * 最直接的想法便是给这些数字分位置，让相同的数字隔1个分开。
     * 根据结论推过程，由于必然存在，所以当数组变为有序之后
     * 即同一数字紧邻在一起，且按同一数字个数的大小从前往后排列（TreeMap实现）
     * 这样将前半部分依次分配在奇数位，后半部分依次分配在偶数位，
     * 那么一定可以保证同一数字之间是相互隔离开的。
     * @param barcodes
     * @return
     */
    public class pair {
        int key;
        int value;
    }


    public static int[] rearrangeBarcodes(int[] barcodes) {
        int n = barcodes.length;

        TreeMap<Integer,Integer> h = new TreeMap<>();

        for (int t : barcodes) {
            if (h.containsKey(t)) {
                h.put(t, h.get(t)+1);
            }else{
                h.put(t, 1);
            }
        }

        int cnt = 0;
        for (int key : h.keySet()) {
            for (int i = 0; i < h.get(key); i++) {
                barcodes[cnt]=key;
                cnt++;
            }
        }
        int[] res = new int[n];
        int tmp = 0;
        for (int i = 0; i < n; i+=2) {
            res[i]=barcodes[tmp];
            res[i+1]=barcodes[(n+1)/2+tmp];
            tmp++;
        }
        return res;
        
    }
}