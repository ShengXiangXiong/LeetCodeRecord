package linkList;

/**
 * Created By ShengXiang.Xiong on 2019/11/15
 * 请判断一个链表是否为回文链表。
 * 输入: 1->2
 * 输出: false
 * <p>
 * 输入: 1->2->2->1
 * 输出: true
 * 进阶：
 * 你能否用 O(n) 时间复杂度和 O(1) 空间复杂度解决此题？
 */
public class IsPalindrome {
    /**
     * 基本思路，就是找到链表中点，翻转后半段或者前半段链表，然后再一一比对即可
     *
     * @param head
     * @return
     */
    public static ListNode reverse(ListNode head) {
        if (head == null) {
            return null;
        }
        ListNode a = new ListNode(-1);
        a.next = head;
        ListNode p = a;
        ListNode q = p.next;
        while (q != null) {
            ListNode tmp = q.next;
            q.next = p;
            p = q;
            q = tmp;
        }
        //头节点的下一个置为空，否则头节点处将无限循环
        head.next = null;
        return p;
    }

    public static boolean isPalindrome(ListNode head) {
        int cnt = 0;
        boolean even = true;
        ListNode p = head;
        while (p != null) {
            cnt++;
            p = p.next;
        }
        p = head;
        if (cnt % 2 != 0) {
            even = false;
        }
        ListNode q = head;
        cnt /= 2;
        while (cnt-- > 0) {
            q = q.next;
        }
        if (even) {
            q = reverse(q);
        } else {
            q = reverse(q.next);
        }
        while (q != null && p != null) {
            if (p.val != q.val) {
                return false;
            }
            p = p.next;
            q = q.next;
        }
        return true;
    }

    public static void main(String[] args) {
        ListNode head = new ListNode(0);
        ListNode p = head;
        for (int i = 1; i < 5; i++) {
            p.next = new ListNode(i);
            p = p.next;
        }
        for (int i = 4; i >= 0; i--) {
            p.next = new ListNode(i);
            p = p.next;
        }

        System.out.println(isPalindrome(null));
    }
}
