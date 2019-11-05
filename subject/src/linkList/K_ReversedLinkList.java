package linkList;

public class K_ReversedLinkList {
    public static ListNode reverseKGroup(ListNode head, int k) {
        if (k == 1) {
            return head;
        }
        ListNode head1 = new ListNode(-1);
        head1.next = head;
        ListNode start = head1;
        while (start != null) {
            ListNode p = start.next;
            ListNode before = p;
            ListNode end = p;
            int cnt = k;
            while (end != null && cnt-- > 1) {
                end = end.next;
            }
            if (end == null) {
                break;
            }
            ListNode q = p.next;
            ListNode after = q.next;
            //先换首尾
            p.next = end.next;
            cnt = k;
            while (cnt-- > 1) {
                q.next = before;
                before = q;
                q = after;
                if (q != null) {
                    after = after.next;
                }
            }
            start.next = end;
            start = p;
        }
        return head1.next;
    }

    public static void main(String[] args) {

        ListNode head = new ListNode(0);
        ListNode p = head;
        for (int i = 1; i < 8; i++) {
            p.next = new ListNode(i);
            p = p.next;
        }
        head = reverseKGroup(head, 6);
        while (head != null) {
            System.out.print(head.val + " ");
            head = head.next;
        }
    }
}
