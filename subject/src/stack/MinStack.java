package stack;

/**
 * Created by Xursan on 2019/8/28.
 * 设计一个支持 push，pop，top 操作，并能在常数时间内检索到最小元素的栈。
 * push(x) -- 将元素 x 推入栈中。
 * pop() -- 删除栈顶的元素。
 * top() -- 获取栈顶元素。
 * getMin() -- 检索栈中的最小元素。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/min-stack
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
class MinStack {

    /**
     * 关键点在于，如何快速获得最小值，并且在不断remove和add中，相应的最小值也应该作出相应变化。
     * 最开始考虑使用小顶堆（优先队列）来作为最小值的容器minVals，每次加入或删除一个数到栈中，minVals也要加入或删除。
     * 这种方法的增删时间复杂度均为O(Log n)。
     * 再考虑一下，我们之所以要是用小顶堆，是因为若删除的数据为最小数时，我们要将最小值指向第二小的数，所以我们必须得保
     * 存以前的数，并且使之有序。这里使用小顶堆，就可以不断进行调整，保证顶端总是最小值。
     * 但是我们换种思路，我们能不能不用小顶堆就可以找到最小值，答案是肯定的。我们可以使用一个数组来保存最小值序列，
     * minVals[i] = j   其中下标i对应当前栈大小为i时的最小值为j，这样就可以保存每一个栈在不同大小时的最小值。这种方法的
     * 增删时间复杂度均为O(1)。
     */
    /**
     * initialize your data structure here.
     */
    private int[] s;
    private int[] min;
    private int top;

    public MinStack() {
        s = new int[10];
        min = new int[10];
        top = -1;
    }

    public void push(int x) {
        if (top == s.length - 1) {
            int[] temps = new int[s.length * 2];
            int[] tempm = new int[s.length * 2];
            for (int i = 0; i < s.length; i++) {
                temps[i] = s[i];
                tempm[i] = min[i];
            }
            s = temps;
            min = tempm;
        }
        s[top + 1] = x;
        if (top < 0) {
            min[top + 1] = x;
        } else {
            min[top + 1] = x < min[top] ? x : min[top];
        }
        top++;
    }

    public void pop() {
        if (top >= 0) {
            top--;
        }
    }

    public int top() {
        if (top >= 0) {
            return s[top];
        } else {
            return -1;
        }
    }

    public int getMin() {
        return min[top];
    }

    public static void main(String[] args) {
        MinStack minStack = new MinStack();
        minStack.push(-2);
        minStack.push(0);
        minStack.push(-3);
        System.out.println(minStack.getMin());
        minStack.pop();
        System.out.println(minStack.top());
        System.out.println(minStack.getMin());
    }
}
