package linkList;

/**
 * Created by Xursan on 2020/1/20.
 * 237. 删除链表中的节点
 */
public class DeleteNode {
    /**
     * 不并不是传统意义上的给定链表，然后给定值删除链表节点，而是只给定待删除节点，然后想办法删除它。
     * 一开始可能完全摸不着头脑，连链表头节点都没给，怎么知道其前一个节点。其实换种思路，只需要将待删除节点后面的
     * 所有节点的值往前覆盖，然后删除最后一个节点即可完成。
     *
     * @param node
     */
    public void deleteNode(ListNode node) {
        while (node.next.next != null) {
            node.val = node.next.val;
            node = node.next;
        }
        node.val = node.next.val;
        node.next = null;
    }
}
