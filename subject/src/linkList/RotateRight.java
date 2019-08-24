package linkList;

/**
 * Created by Xursan on 2019/8/24.
 * 给定一个链表，旋转链表，将链表每个节点向右移动 k 个位置，其中 k 是非负数。
 * 输入: 1->2->3->4->5->NULL, k = 2
 * 输出: 4->5->1->2->3->NULL
 * 解释:
 * 向右旋转 1 步: 5->1->2->3->4->NULL
 * 向右旋转 2 步: 4->5->1->2->3->NULL
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/rotate-list
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */

public class RotateRight {
    /**
     * 双指针，先求出链表长度，通过模运算找到有效右移位数，然后通过前后指针，前指针对应断开点，后指针指向链表末尾。
     *
     * @param head
     * @param k
     * @return
     */
    public static ListNode rotateRight(ListNode head, int k) {
        if (k == 0 || head == null || head.next == null) {
            return head;
        }
        ListNode h = new ListNode(0);
        h.next = head;
        ListNode p = head;
        ListNode q = head;
        int cnt = 0;
        while (q != null) {
            q = q.next;
            cnt++;
        }
        k %= cnt;
        if (k == 0) {
            return head;
        }
        cnt = 0;
        q = head;
        while (cnt < k) {
            q = q.next;
            cnt++;
        }
        while (q.next != null) {
            q = q.next;
            p = p.next;
        }
        q.next = head;
        h.next = p.next;
        p.next = null;
        return h.next;
    }

    /**
     * 仔细分析，发现我们并不需要双指针，我们始终会求链表长度count，找到有效右移tmp,将链表末尾连接成环。实际上我们唯一
     * 需要的是求出断开点的位置，实际上断开点的位置就是从头结点开始的第count-tmp个右移节点，这实际上通过分析循环数组右移，
     * 就可以很轻松的得出这个结论（循环数组右移，就是分成前后两段，前段的末尾就是我们要求的断开点，而前段的长度就等于数
     * 组的长度-有效右移位数）。
     *
     * @param head
     * @param k
     * @return
     */
    public static ListNode rotateRight2(ListNode head, int k) {
        if (head == null) return null;
        //链表长度
        int count = 1;
        ListNode p = head;
        while (p.next != null) {
            p = p.next;
            count++;
        }
        //p到达尾部  链接头节点变环
        p.next = head;
        int tmp = count - k % count;
        while (tmp > 0) {
            p = p.next;
            tmp--;
        }
        //将p.next==null  断开链表
        ListNode node = p.next;
        p.next = null;
        return node;
    }

    public static void main(String[] args) {
        ListNode head = new ListNode(0);
        ListNode p = head;
        for (int i = 1; i < 3; i++) {
            p.next = new ListNode(i);
            p = p.next;
        }
        head = rotateRight(head, 4);
        while (head != null) {
            System.out.print(head.val + " ");
            head = head.next;
        }
    }
}
