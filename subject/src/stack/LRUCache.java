package stack;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * Created By ShengXiang.Xiong on 2019/12/13
 * 关键点就在于如何快速将中间的key迅速删除，并插入到队尾
 * 我们知道，单是队列，是不支持O(1)时间内中间值的快速查找，所以不行，要想O(1)，那必须的得用Hash表记录，所以需要
 * 使用一个Hash表记录某个key所处于队列中的位置，然后将其删除，并且保证删除操作不会带来O(n)的级联影响（自然不能使用Array）
 * 那需要用什么来模拟这个队列呢？我们理一下需求：
 * 1.快速定位--Hash
 * 2.删除key--不能级联影响，只能是链表，而且单向链表肯定不行，因为删除该节点时，必须让其前一个节点指向后一个节点
 * 所以只能使用双向链表来模拟该队列
 * <p>
 * 既然知道用什么数据结构了，自然就简单了:
 * 一是队列中的节点Node{val,pre(前向指针),pos(后向指针)}
 * 二是Hash记录为<key,Node>
 * 三是使用双向链表模拟队列
 * <p>
 * LinkedList就是双向链表，所以实际上上面的所有功能（双向链表+Hash）实际上就是LinkHashMap这个数据结构类
 */
class LRUCache {
    /**
     * 这里不使用LinkHashMap，但是直接使用LinkList这个双向链表，由于已有LinkList，所以不必重新定义Node节点
     */
    private LinkedList<Integer> queue;
    private HashMap<Integer, Integer> hm;
    private int cap;

    public LRUCache(int capacity, int val) {
        cap = capacity;
        queue = new LinkedList<>();
        hm = new HashMap<>();
    }

    public int get(int key) {
        if (hm.containsKey(key)) {
            if (queue.peekLast() != key) {
                queue.remove((Integer) key);
                queue.addLast(key);
            }
            return hm.get(key);
        }
        return -1;
    }

    public void put(int key, int value) {
        if (cap == 0) {
            if (!hm.containsKey(key)) {
                hm.remove(queue.pollFirst());
                queue.addLast(key);
            } else if (queue.peekLast() != key) {
                queue.remove((Integer) key);
                queue.addLast(key);
            }
        }
        if (cap > 0) {
            if (!hm.containsKey(key)) {
                queue.addLast(key);
                cap--;
            } else if (queue.peekLast() != key) {
                queue.remove((Integer) key);
                queue.addLast(key);
            }
        }
        hm.put(key, value);
    }


    /**
     * 使用LinkHashMap,其本身的淘汰机制就是按照key的加入时间来判断的，get，put会自动更新key的时间
     *
     * @param args
     */
    private LinkedHashMap<Integer, Integer> cache;

    public LRUCache(int capacity) {
        this.cache = new LinkedHashMap<Integer, Integer>(capacity, 0.75f, true) {
            public boolean removeEldestEntry(Map.Entry eldest) {
                return size() > capacity;
            }
        };
    }

    public int get1(int key) {
        Integer val = cache.get(key);
        return val == null ? -1 : val;
    }

    public void put1(int key, int value) {
        cache.put(key, value);
    }

    public static void main(String[] args) {
        LRUCache cache = new LRUCache(2 /* 缓存容量 */);

        cache.put(2, 6);
        cache.put(1, 5);
        cache.put(1, 2);    // 该操作会使得密钥 2 作废
        System.out.println(cache.get(1));       // 返回 -1 (未找到)
        System.out.println(cache.get(2));       // 返回 -1 (未找到)

    }
}
