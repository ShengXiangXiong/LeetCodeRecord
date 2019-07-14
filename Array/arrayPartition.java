package Array;

/**
 * arrayPartition
 */
public class arrayPartition {
    class Solution {
        public int partitionDisjoint(int[] a) {
            /**
             * 前后指针。i始终指向当前状态（a[0~j]）满足条件的left的最远的位置,
             * left_max表示当前状态左边的最大值,tmp_max表示到当前j位置的数组最大值
             * if(left_max<a[j])
             *      j++
             *      update(tmp_max)
             * else:
             *      i=j;
             *      j++;
             *      update(left_max)
             */
            int left_max = a[0];
            int tmp_max = a[1];
            int i = 0;
            for (int j = 0; j < a.length; j++) {
                if(left_max<=a[j]){
                    tmp_max = a[j]>tmp_max?a[j]:tmp_max;
                }else{
                    i = j;
                    left_max = tmp_max>left_max?tmp_max:left_max;
                }
            }
            return i+1;
        }
    }
    
}