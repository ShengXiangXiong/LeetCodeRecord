package linkList;

/**
 * Created By ShengXiang.Xiong on 2019/11/5
 * 在 O(n log n) 时间复杂度和常数级空间复杂度下，对链表进行排序。
 */
public class QSortLinkList {
    /**
     * 前后指针（快慢指针）快排：
     * <p>
     * 基本思路，在没找到大于key值前，slow永远紧跟fast，遇到大于枢轴的值，两者之间就会拉开差距，中间差的肯定是连续的大于key的值，
     * 当遇到小于key的值时，交换两个下标对应的值（表示又恢复满足slow和fast的状态了），然后fast再向前走即可，直到fast指向末尾
     * <p>
     * 1.定义基准值，这里选取第一个
     * 2.定义快慢指针fast、slow指向第一个，fast表示大于基准值的最远位置，slow表示大于基准值的前一个位置
     * 3.如果fast大于基准值，则fast一直走，slow不变
     * 4.如果fast小于基准值，则交换slow与fast，然后slow++，fast++
     * 5.直到fast指向末尾，表示划分结束
     * <p>
     * O(nlogn)无疑要求必须得快排、归并
     * 我们知道归并的空间复杂度对于数组而言都是很高的，而对于链表而言，也需要执行链表合并等操作，常数空间必不满足
     * 于是考虑快排，快排的基本思路是基于双指针和分治思想实现的，然而快排在大多数算法的介绍中都是通过左右双指针往中间
     * 遍历的方式实现的，而这道题由于链表是单向链表，且只允许常数空间，肯定不能使用左右指针往中间遍历的方式，于是只能
     * 考虑使用其他的双指针形式，于是有了解题思路：前后指针快排法，prev始终指向小于目标值tmp的最后一个节点，
     * after始终指向大于目标值tmp的最远一个节点，每当after遇到比tmp小的值时，就将其赋值给prev，prev前进1，然后赋值给after，
     * 直到当after指向当前链表尾部时，即表示此轮划分结束，然后将prev赋值为tmp，返回prev。（即前面的算法过程）
     *
     * @param head
     * @return
     */
    public ListNode sortList(ListNode head) {
        qsort(head, null);
        return head;
    }

    public ListNode pivlot(ListNode head, ListNode last) {
        ListNode prev = head;
        ListNode after = head;
        int tmp = head.val;
        while (after != last) {
            while (after != last && after.val >= tmp) {
                after = after.next;
            }
            if (after != last) {
                prev.val = after.val;
                prev = prev.next;
                after.val = prev.val;
                after = after.next;
            }
        }
        prev.val = tmp;
        return prev;
    }

    public void qsort(ListNode head, ListNode last) {
        if (head != last) {
            ListNode tmp = pivlot(head, last);
            if (tmp.next != last) {
                qsort(tmp.next, last);
            }
            if (tmp != head) {
                qsort(head, tmp);
            }
        }
    }


    /**
     * 依旧采取快排的思想，不过这里是基于三指针，mid指针与左右指针法（大多数快排思路）
     *
     * @param head
     * @return
     */
    public ListNode sortList1(ListNode head) {
        if (head == null || head.next == null) return head;
        int val = head.val;
        ListNode left = new ListNode(-1);
        ListNode mid = new ListNode(-1);
        ListNode right = new ListNode(-1);
        ListNode l = left, m = mid, r = right;
        while (head != null) {
            if (head.val < val) {
                l.next = head;
                l = l.next;
            } else if (head.val > val) {
                r.next = head;
                r = r.next;
            } else {
                m.next = head;
                m = m.next;
            }
            head = head.next;
        }
        l.next = null;
        r.next = null;
        left.next = sortList1(left.next);
        right.next = sortList1(right.next);
        l = left;
        while (l.next != null) l = l.next;
        l.next = mid.next;
        m.next = right.next;
        return left.next;
    }


    public static void main(String[] args) {
        ListNode head = new ListNode(-1);
        ListNode p = head;
        int[] nums = {23, 124, 1, 41, 4, 15, 6, 8, 9, 9, 3, 0};
        for (int num : nums) {
            p.next = new ListNode(num);
            p = p.next;
        }
        QSortLinkList a = new QSortLinkList();
        a.sortList1(head);
        while (head != null) {
            System.out.print(head.val + " ");
            head = head.next;
        }
    }
}
