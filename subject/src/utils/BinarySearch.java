package utils;

/**
 * Created by Xursan on 2019/8/8.
 *
 * 二分查找看上去很简单，但实则实现起来还是需要仔细思考的，尤其是边界情况。
 * 开闭区间的不同，造成的while循环的条件不同，若数组长度为n
 * 若为[s,e),其中e=n，则while(s<e)——这里无需s=e是因为其始终可以取到数组边界n-1
 * 若为[s,e],其中e=n-1，则为while(s<=e) ——这里s=e的目的就是使其能够取到边界e
 *
 * 根据上面二分查找的思想，实现 查找到最后一个小于等于某值的数findLastLE  以及  查找到最后一个小于某值的数findLastL
 * 和    查找到第一个大于等于某值的数findFirstGE   以及  查找到第一个大于某值的数findFirstG
 * 这几个函数实现起来实际山是比二分查找要难的，边界情况很复杂。
 */
public class BinarySearch<T> {

    public static int find(int s,int e, Object[] objs, Object tar, String ... args){
        e -= 1;
        while (s<=e){
            int mid = (s+e)/2;
            if((int)objs[mid]==(int)tar){
                return mid;
            }
            if((int)objs[mid]<(int)tar){
                s = mid+1;
            }else {
                e = mid-1;
            }
        }
        return -1;
    }

    /**
     * 思路：要找到小于等于的，必然若mid大于tar，则mid必然不可能是最终的结果，很放心地令e=mid-1
     * 但是若mid<=tar，有可能mid是最终的结果，此时若还是像二分查找那样 s = mid+1，会造成最终的结果取不到。
     * 那该怎么办呢？  显然不可能让s = mid 保持不动，因为这违背了二分查找的思路会造成死循环。
     * 这里我们可以采取返回 s-1的值来作为结果，因为一旦mid+1错过了最终结果，会造成e不断向s靠拢，直至e<s结束循环。
     * 所以这样保证了即使s错过了，但仍然可以使最终的结果正确。
     *
     * 若没有，则返回-1
     * @param s
     * @param e
     * @param objs
     * @param tar
     * @param args
     * @return
     */
    public static int findLastLE(int s,int e, Object[] objs, Object tar, String ... args){
        e -= 1;
        while(s<=e){
            int mid = (s+e)/2;
            if((int)objs[mid]<=(int)tar){
                s = mid+1;
            }else {
                e = mid-1;
            }
        }
        return s-1;
    }

    /**
     * 不包含等于，只需要将=tar的情况分在另一个分支中,若mid>=tar，必然e=mid-1没问题
     * @param s
     * @param e
     * @param objs
     * @param tar
     * @param args
     * @return
     */
    public static int findLastL(int s,int e, Object[] objs, Object tar, String ... args){
        e -= 1;
        while(s<=e){
            int mid = (s+e)/2;
            if((int)objs[mid]<(int)tar){
                s = mid+1;
            }else {
                e = mid-1;
            }
        }
        return s-1;
    }
    /**
     * 和上面相反,若mid<tar，必然s=mid+1没问题，若mid >= tar ，e = mid-1会造成最终的结果取不到，
     * 所以同上，令e始终指向满足条件的前一个数，因为如果e刚好略过了最终结果，后面循环时e始终不动，
     * 而s会不断靠近直至大于e，结束循环。此时我们返回e+1作为最终的结果即可。
     * 值得注意的一点是这里同上面逻辑不同的是将=情况归在了e的向左变化，而上面是将=的情况归在s的向右变化。
     * 因为存在值相同的情况，前者是找最后一个小于等于的，所以得让s不断逼近，后者是找第一个大于等于的，所以得让e不断逼近
     * @param s
     * @param end
     * @param objs
     * @param tar
     * @param args
     * @return
     */
    public static int findFirstGE(int s,int end, Object[] objs, Object tar, String ... args){
        int e = end-1;
        while(s<=e){
            int mid = (s+e)/2;
            if((int)objs[mid]<(int)tar){
                //此时说明[s,mid]区间一定不存在满足条件的数，舍弃，在[mid+1,e]的区间可能存在满足条件的数，故令s=mid+1
                s = mid+1;
            }else {
                //此时的[mid,e]区间的数满足条件了，故此舍弃此区间，即让e=mid-1，不断尝试向前缩小
                e = mid-1;
            }
        }
        //若不存在，会导致e+1=end,此时应该返回-1
        return e+1==end?-1:e+1;
    }

    /**
     * 不包含等于，只需要将=tar的情况分在另一个分支中
     * @param s
     * @param end
     * @param objs
     * @param tar
     * @param args
     * @return
     */
    public static int findFirstG(int s,int end, Object[] objs, Object tar, String ... args){
        int e = end-1;
        while(s<=e){
            int mid = (s+e)/2;
            if((int)objs[mid]<=(int)tar){
                //此时说明[s,mid]区间一定不存在满足条件的数，舍弃，在[mid+1,e]的区间可能存在满足条件的数，故令s=mid+1
                s = mid+1;
            }else {
                //此时的[mid,e]区间的数满足条件了，故此舍弃此区间，即让e=mid-1，不断尝试向前缩小
                e = mid-1;
            }
        }
        //若不存在，会导致e+1=end,此时应该返回-1
        return e+1==end?-1:e+1;
    }
    public static void main(String[] args) {
        Integer[] nums = {1,2,3,5,6,7,7,15,18,19};
        System.out.println(findLastL(0,10,nums,0));
    }

}
