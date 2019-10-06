package stack;

import java.util.Stack;

/**
 * Created by Xursan on 2019/10/6.
 * <p>
 * 给定 n 个非负整数，用来表示柱状图中各个柱子的高度。每个柱子彼此相邻，且宽度为 1 。
 * 求在该柱状图中，能够勾勒出来的矩形的最大面积。
 * <p>
 * 输入: [2,1,5,6,2,3]
 * 输出: 10
 */
public class LargestRectangleArea {
    /**
     * 单调栈——单调递增，每当遇到一个比当前栈顶大的元素则加入，遇到一个比栈顶小的，则连续弹出，并计算top与top-1所在
     * heights位置所对应高度形成的面积，分3块，取最大：
     * 1. 自己柱子形成的矩形面积
     * 2. 左右两边的柱子与自己共同形成的矩形面积
     * 3. 左右两边中更高一边柱子与自己形成的面积
     * <p>
     * 直至遇到比栈顶大的，然后将当前元素入栈；以此重复，直至遍历完heights。
     *
     * @param heights
     * @return
     */
    public static int largestRectangleArea(int[] heights) {
        if (heights.length == 0) {
            return 0;
        }
        int ans = heights[0];
        Stack<Integer> s = new Stack<>();
        s.add(0);
        for (int i = 1; i < heights.length; i++) {
            //自己柱子形成的矩形面积
            ans = Math.max(ans, heights[i]);
            while (!s.empty() && heights[i] <= heights[s.peek()]) {
                int pos = s.pop();
                //w1,h1,w2,h2分别代表2、3种矩形的长宽
                int w1, h1, w2, h2;
                if (s.empty()) {
                    w1 = i + 1;
                    h1 = heights[i];
                    ans = Math.max(ans, w1 * h1);
                } else {
                    if (heights[s.peek()] < heights[i]) {
                        h2 = heights[i];
                        w2 = i - pos + 1;
                    } else {
                        h2 = heights[s.peek()];
                        w2 = pos - s.peek() + 1;
                    }
                    w1 = i - s.peek() + 1;
                    h1 = Math.min(heights[s.peek()], heights[i]);
                    ans = Math.max(ans, Math.max(w1 * h1, w2 * h2));
                }
            }
            s.add(i);
        }
        while (!s.empty()) {
            int w, h;
            if (s.size() == 1) {
                w = heights.length;
                h = heights[s.pop()];
            } else {
                s.pop();
                w = heights.length - s.peek() + 1;
                h = heights[s.peek()];
            }
            ans = Math.max(ans, w * h);
        }
        return ans;
    }

    /**
     * 向左向右各扫描一次，记录每一个位置其左右方向各自第一个小于它的位置
     *
     * @param heights
     * @return
     */
    public static int largestRectangleArea3(int[] heights) {
        int[] left = new int[heights.length];
        int[] right = new int[heights.length];
        int ans = 0;
        int lm = 0;
        for (int i = 0; i < heights.length; i++) {
            if (heights[i] < heights[lm]) {
                left[i] = lm;
            } else {
                left[i] = i;
                lm = i;
            }
        }
        int rm = heights.length - 1;
        for (int i = heights.length - 1; i >= 0; i--) {
            if (heights[i] < rm) {
                right[i] = lm;
            } else {
                right[i] = i;
                rm = i;
            }
        }
        for (int i = 0; i < heights.length; i++) {
            ans = Math.max(ans, (right[i] - left[i] + 1) * heights[i]);
        }
        return ans;
    }

    public static void main(String[] args) {
        int[] nums = {2, 1, 2};
        System.out.println(largestRectangleArea(nums));
    }
}
