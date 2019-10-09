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
     * 首先思考一下，对于每一个柱子他有可能形成的最大矩形面积来自于：
     *  1. 左右两边第一个低于自己高度柱子共同形成的矩形（高度为左右两边中更低的，宽度即为right-left+1）
     *  2. 左右两边中更高一边柱子与另一边最后一个不低于自己的柱子（其实就是另一边第一个低于自己柱子的位置加一）
     *  形成的矩形面积（高度为左右两边中更高的，宽度为right-left）
     *  3. 左右两边中最后一个不低于自己的柱子共同形成的面积（高度为自己，宽度为right-left-1,有可能恰好是自己）
     *
     *  对于1很好解决，遍历一遍即可得出，但是对于2、3就需要一些思考了，而对于像这种向两边扩展的题，一般想到的就是单调栈，
     *  核心思想就是能够轻松找出左右两边第一个低于自己的柱子，那么自然而然就想到了单调递增的栈，栈顶始终指向的是已遍历过
     *  的最高柱子的位置，那么左边低于他的第一个柱子就是栈顶的下一个位置，如果当前位置的柱子低于栈顶柱子，那么自然当前
     *  位置的柱子就是栈顶柱子右边的第一个低于它的柱子，那么对于栈顶柱子其可能形成的所有最大矩形面积即可确定下来了。
     * 单调栈——单调递增，每当遇到一个比当前栈顶大的元素则加入，遇到一个比栈顶小的i，则连续弹出，并计算i与top-1所在
     * heights位置所对应高度形成的面积，分3块，取最大。直至遇到比栈顶大的，然后将当前元素入栈；以此重复，直至遍历完heights。
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
//            ans = Math.max(ans, heights[i]);
            while (!s.empty() && heights[i] <= heights[s.peek()]) {
                int pos = s.pop();
                if (s.empty()) {
                    int area1 = (i + 1) * heights[i];
                    int area2 = (i) * heights[pos];
                    ans = Math.max(ans, Math.max(area1, area2));
                } else {
                    int area1 = (i - s.peek() + 1) * Math.min(heights[i], heights[s.peek()]);
                    int area2 = (i - s.peek()) * Math.max(heights[i], heights[s.peek()]);
                    int area3 = (i - s.peek() - 1) * heights[pos];
                    ans = Math.max(ans, Math.max(area1, Math.max(area2, area3)));
                }
            }
            s.add(i);
        }
        while (s.size() > 1) {
            int r = heights.length - 1;
            int pos = s.pop();
            int area1 = (r - s.peek() + 1) * heights[s.peek()];
            int area2 = (r - s.peek()) * heights[pos];
            ans = Math.max(ans, Math.max(area1, area2));
        }
        //最低（最后一个）柱子
        ans = Math.max(ans, heights[s.peek()] * heights.length);
        return ans;
    }

    /**
     * 实际上仔细分析，上面对于每个柱子而言我们考虑太多了，我们目的是求出最大面积，按照柱子高度的水平线进行扫描，
     * 去寻找每一个柱子能够形成的矩形面积，就相当于遍历了所有可能形成的矩形了。
     * 所以我们只需要考虑第3种情况，即以柱子自身高度作为高，对于每一个柱子而言，它自身高度水平下能够形成的最大矩形面积
     * 取决于左右两边最后一个不低于自己的柱子所在的位置（即宽）。所以关键点还是如何迅速找出左右两边第一个低于自己的柱子。
     * 因此还是使用单调栈。
     * @param heights
     * @return
     */
    public static int largestRectangleArea2(int[] heights) {
        Stack<Integer> stack = new Stack<>();
        stack.push(-1);
        int maxarea = 0;
        for (int i = 0; i < heights.length; ++i) {
            while (stack.peek() != -1 && heights[stack.peek()] >= heights[i])
                maxarea = Math.max(maxarea, heights[stack.pop()] * (i - stack.peek() - 1));
            stack.push(i);
        }
        while (stack.peek() != -1)
            maxarea = Math.max(maxarea, heights[stack.pop()] * (heights.length - stack.peek() - 1));
        return maxarea;
    }

    public static void main(String[] args) {
        int[] nums = {2, 1, 2};
        System.out.println(largestRectangleArea(nums));
    }
}
