package linkList;

/**
 * Created by Xursan on 2020/2/11.
 * 链表奇数位升序，偶数位降序，如何改为有序链表
 */
public class SortLinkList {
    /**
     * 采用双指针+尾插法+迭代合并两个有序链表
     * <p>
     * 首先将偶数位的节点全都按序转移到末尾，自然形成了前后两个部分均有序的链表，然后开始合并两个有序链表即可
     * <p>
     * 注意合并两个有序链表其实同合并两个有序数组类似，只是链表无需提前开辟空间。
     * 我们知道合并两个有序数组，需要指定当前cnt指针，表示合并到第几个位置了，就是每次比较两个数组在各个指针处的大小，
     * 然后将对应数字赋值给当前的nums[cnt]。
     * <p>
     * 同理链表也可以采取这个措施，我们也用一个prev指针表示当前合并链表后的指针所处的位置，然后不断给prev指针赋值，
     * 然后指向下一个，直到某个链表结束，让prev指向还没结束的链表节点即可。
     * <p>
     * 这种方式做到了 空间复杂度 O(1)
     *
     * @param root
     */
    public static ListNode sortList(ListNode root) {
        ListNode p = root;
        ListNode q = p;
        int cnt = 0;
        while (q.next != null) {
            q = q.next;
            cnt++;
        }
        //尾插，偶数位转移到末尾
        while (p != q && p.next != q) {
            ListNode tmp = q.next;
            q.next = p.next;
            p.next = p.next.next;
            q.next.next = tmp;
            p = p.next;
        }
        //合并
        if (cnt % 2 == 0) {
            //偶数时其下一位才是后半部分的首位
            q = q.next;
        }
        p.next = null;//截断
        p = root;
        //哨兵节点，方便返回
        ListNode head = new ListNode(-1);
        //prev指针，每次让其指向小的那个数对应的节点，然后继续prev = prev.next
        ListNode prev = head;
        while (p != null && q != null) {
            if (p.val < q.val) {
                prev.next = p;
                p = p.next;
            } else {
                prev.next = q;
                q = q.next;
            }
            prev = prev.next;
        }
        //剩余未合并完的
        prev.next = p == null ? q : p;
        return head.next;
    }

    public static void main(String[] args) {
        ListNode root = new ListNode(1);
        ListNode p = root;
        for (int i = 2; i < 6; i++) {
            p.next = new ListNode(-i);
            p.next.next = new ListNode(i);
            p = p.next.next;
        }
        ListNode q = root;
        while (q != null) {
            System.out.print(q.val + " ");
            q = q.next;
        }
        System.out.println();
        root = sortList(root);
        q = root;
        while (q != null) {
            System.out.print(q.val + " ");
            q = q.next;
        }

    }
}
