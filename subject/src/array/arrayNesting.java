package array;

class arrayNesting {
    public static int arrayNesting(int[] nums) {
        int res = 0;
        int n = nums.length;

        for (int i = 0; i < n; i++) {
            if (nums[i] == -1) {
                continue;
            }
            int cnt = 1;
            int j = nums[i];
            nums[i] = -1;
            while (j != i) {
                cnt++;
                int pos = j;
                j = nums[j];
                nums[pos] = -1;
            }
            res = Math.max(cnt, res);
        }
        //直接对数组操作更好，下面是通过list链表进行操作，时间复杂度反而更高
        // ArrayList<Integer> pos_remain = new ArrayList<>();
        // for (int i = 0; i < n; i++) {
        //     pos_remain.add(i);
        // }
        // while(pos_remain.size()>0){
        //     int i = pos_remain.get(0);
        //     int cnt = 1;
        //     pos_remain.remove(Integer.valueOf(i));
        //     int s = i;
        //     i = nums[i];
        //     while(i!=s){
        //         pos_remain.remove(Integer.valueOf(i));
        //         i=nums[i];
        //         cnt++;
        //     }
        //     res = Math.max(cnt,res);
        // }
        return res;
    }

    public static int better(int[] nums) {
        int res = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == -1) continue;
            int j = i, count = 0;
            while (nums[j] != -1) {
                count++;
                int t = nums[j];
                nums[j] = -1;
                j = t;
            }
            res = res > count ? res : count;
        }
        return res;
    }

    public static void main(String[] args) {
        int[] a = {5, 4, 0, 3, 1, 6, 2};
        System.out.println(arrayNesting(a));
    }

}