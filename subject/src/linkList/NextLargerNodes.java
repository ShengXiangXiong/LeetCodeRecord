package linkList;

import java.util.Stack;

/**
 * Created by Xursan on 2020/2/24.
 * 1019. 链表中的下一个更大节点
 */
public class NextLargerNodes {
    /**
     * 转换为数组，然后使用单调栈即可
     *
     * @param head
     * @return
     */
    public static int[] nextLargerNodes(ListNode head) {
        Stack<Integer> s = new Stack<>();
        int[] res = new int[10001];
        int cnt = 0;
        ListNode p = head;
        while (p != null) {
            res[cnt++] = p.val;
            p = p.next;
        }
        int[] ans = new int[cnt];
        for (int i = 0; i < cnt; i++) {
            while (!s.isEmpty() && res[i] > res[s.peek()]) {
                ans[s.peek()] = res[i];
                s.pop();
            }
            s.add(i);
        }
        while (!s.isEmpty()) {
            ans[s.pop()] = 0;
        }
        return ans;
    }

    public static void main(String[] args) {
        ListNode head = new ListNode(1);
        ListNode p = head;
//        for (int i = 1; i <= 10; i++) {
//            p.next = new ListNode(i);
//            p = p.next;
//        }
        p = head;
        while (p != null) {
            System.out.print(p.val + " ");
            p = p.next;
        }
        System.out.println();
        for (int s : nextLargerNodes(head)) {
            System.out.print(s + " ");
        }
    }
}
