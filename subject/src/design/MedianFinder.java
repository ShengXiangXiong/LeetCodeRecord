package design;

import java.util.PriorityQueue;

/**
 * Created By ShengXiang.Xiong on 2019/12/24
 * 中位数是有序列表中间的数。如果列表长度是偶数，中位数则是中间两个数的平均值。
 * 例如，
 * [2,3,4] 的中位数是 3
 * [2,3] 的中位数是 (2 + 3) / 2 = 2.5
 * <p>
 * 设计一个支持以下两种操作的数据结构：
 * void addNum(int num) - 从数据流中添加一个整数到数据结构中。
 * double findMedian() - 返回目前所有元素的中位数。
 * <p>
 * addNum(1)
 * addNum(2)
 * findMedian() -> 1.5
 * addNum(3)
 * findMedian() -> 2
 * <p>
 * 如果数据流中所有整数都在 0 到 100 范围内，你将如何优化你的算法？
 * 如果数据流中 99% 的整数都在 0 到 100 范围内，你将如何优化你的算法？
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/find-median-from-data-stream
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
class MedianFinder {
    /**
     * 理一下需求：快速查找数据流的中位数，那必然需要让数据有序，让数据有序的方法有多种：
     * 一是直接list然后再sort（O(nlogn)），然后直接索引中间元素（O（1））
     * 二是插入排序，往有序list中插入一个数的时间复杂度为O(logn)，但是移动元素可能需要O(n)的时间
     * 三是直接用treeset（显然排除，因为会存在重复值）
     * <p>
     * 我们再想一下，可能会想到使用双指针+有序容器，双指针维护中间两个元素的位置，然后有序容器保证在添加的过程中可以快速调整两个指针。
     * 即右指针指向紧邻下一个比他大的元素，左指针指向紧邻上一个比他小的元素。
     * 是不是有种单调队列的感觉，即快速定位最大值或最小值，且需要维护一个递增指针队列或递减指针队列来不断更新。
     * 但是这道题使用单调队列完全没必要，因为单调队列也会造成数据大量移动，不如直接使用插入排序。
     * 当我们想到这一点的时候其实已经很不错了，即需要将数据分成两部分来思考了。
     *
     * 我们再仔细想一下，要想实现有序容器，再插入的过程中不会造成大量元素移动，且能快速索引极值，那只能是堆了
     * <p>
     * 所以最佳办法是采取大小堆来实现，大顶堆维护较小一半的数据，小顶堆维护较大一半的数据
     */
    private int count;
    private PriorityQueue<Integer> maxHeap;
    private PriorityQueue<Integer> minHeap;

    /**
     * initialize your data structure here.
     */
    public MedianFinder() {
        maxHeap = new PriorityQueue<>((x, y) -> y - x);
        minHeap = new PriorityQueue<>();
        count = 0;
    }

    public void addNum(int num) {
        count++;
        if (maxHeap.isEmpty() || num <= maxHeap.peek()) {
            maxHeap.offer(num);
        } else {
            minHeap.offer(num);
        }
        //调整堆，防止数据倾斜，始终保证大顶堆>=小顶堆，且仅多1个
        if (maxHeap.size() < minHeap.size()) {
            maxHeap.offer(minHeap.poll());
        } else if (maxHeap.size() >= minHeap.size() + 2) {
            minHeap.offer(maxHeap.poll());
        }
    }

    public double findMedian() {
        //奇偶判断的优化，我们知道奇数的二进制必然在末尾是1，故奇数与0必然结果为1，而偶数必然结果为0
        if ((count & 1) == 0) {
            return (double) (minHeap.peek() + maxHeap.peek()) / 2;
        } else {
            return (double) maxHeap.peek();
        }
    }

    public static void main(String[] args) {
        MedianFinder mf = new MedianFinder();
        mf.addNum(1);
        mf.addNum(2);
        System.out.println(mf.findMedian());
        mf.addNum(3);
        System.out.println(mf.findMedian());
    }
}