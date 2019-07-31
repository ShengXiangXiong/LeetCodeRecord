package search.dfs;

import java.util.*;

/**
 * Created by Xursan on 2019/7/28.
 */
public class FourNumSum {
    private static List<List<Integer>> hs = new ArrayList<>();
    private static HashMap<Integer,Boolean> no = new HashMap<>();
    public static void dfs(int[] nums, int tar, int pos, List<Integer> tmp){
        if(tmp.size()==4){
            if(tar==0){
//                Collections.sort(tmp);
                hs.add(new ArrayList<>(tmp));
            }
            return;
        }
        int t = tmp.size();
        for (int i = pos; i < nums.length-3+t; i++) {
            while (i<nums.length-4+t && i>pos && nums[i]==nums[i-1]){
                i++;
            }
            if(nums[i]>tar && nums[i]>=0){
                return;
            }
            tmp.add(nums[i]);
            dfs(nums,tar-nums[i],i+1,tmp);
            tmp.remove(t);
        }
//        for (Integer num : nums) {
//            ArrayList<Integer> nums1 = new ArrayList<>(nums);
//            nums1.remove(num);
//            tmp.add(num);
//            dfs(nums1,tar-num,tmp);
//            tmp.remove(num);
//        }
    }


    /**
     * 思路：1.sort 2.从大到小记录每个数的位置——pos[num] = {index1,index2,...}
     * 3.依次ijk循环，相当于固定前三个数，得到需要查找的最后一个数tmp（tmp = target-n1-n2-n3），然后通过hash表 pos
     * 查找tmp的位置，如果tmp所对应的的位置大于k，就表示在其后面存在这样一个数使等式成立。
     * 若未找到则表示不存在，若找到但是位置小于等于k，表示和前面数重复，不可取（因为数不能重复取）
     * <p>
     * 重复list过滤，只需要通过三个while循环，分别对ijk做判断，
     * 若其值大于初始位置（表示不是和上一层的num作比较） and 和此层的前一个值相等 and 小于nums.length 则++
     * <p>
     * 时间复杂度：O(n^3)
     *
     * @param nums
     * @param target
     * @return
     */
    public static List<List<Integer>> fourSum_n3(int[] nums, int target) {
        List<List<Integer>> res = new ArrayList<>();
        Arrays.sort(nums);
        HashMap<Integer, PriorityQueue<Integer>> pos = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            if (pos.containsKey(nums[i])) {
                pos.get(nums[i]).offer(i);
            } else {
                PriorityQueue<Integer> p = new PriorityQueue<>(Comparator.comparingInt((Integer o) -> o).reversed());
                p.offer(i);
                pos.put(nums[i], p);
            }
        }
        for (int i = 0; i < nums.length - 3; i++) {
            while (i < nums.length - 3 && i > 0 && nums[i] == nums[i - 1]) {
                i++;
            }
            for (int j = i + 1; j < nums.length - 2; j++) {
                while (j < nums.length - 2 && j > i + 1 && nums[j] == nums[j - 1]) {
                    j++;
                }
                for (int k = j + 1; k < nums.length - 1; k++) {
                    while (k < nums.length - 1 && k > j + 1 && nums[k] == nums[k - 1]) {
                        k++;
                    }
                    int tmp = target;
                    tmp -= nums[i] + nums[j] + nums[k];
                    if (pos.containsKey(tmp) && pos.get(tmp).peek() > k) {
                        List<Integer> c = new ArrayList<>();
                        c.add(nums[i]);
                        c.add(nums[j]);
                        c.add(nums[k]);
                        c.add(tmp);
                        res.add(c);
                    }
                }
            }
        }
        return res;
    }

    public static int lowerBound(int[] nums, int l, int r, int target) {
        while (l < r) {
            int m = (l + r) / 2;
            if (nums[m] == target) r = m;
            else l = m + 1;
        }
        return l;
    }

    /**
     * 特殊情况：目标值是由4个相等的值组成的，while的过滤就会导致这种情况漏掉，所以不能直接过滤重复
     * 两两组合构成val对
     *
     * @param nums
     * @param target
     * @return
     */
    public static List<List<Integer>> fourSum_n2(int[] nums, int target) {
        HashSet<TreeSet<Integer>> hres = new HashSet<>();
        Arrays.sort(nums);
        HashMap<Integer, ArrayList<int[]>> pos = new HashMap<>();
        HashSet<Integer> mem = new HashSet<>();
        for (int i = 0; i < nums.length - 1; i++) {
//            while (i<nums.length-1 && i>0 && nums[i]==nums[i-1]){
//                i++;
//            }
            for (int j = i + 1; j < nums.length; j++) {
//                while (j<nums.length && j>i+1 && nums[j]==nums[j-1]){
//                    j++;
//                }
//                if(j==nums.length){
//                    continue;
//                }
                int v = nums[i] + nums[j];
//                int[] indexs = {nums[i],nums[j]};
                int[] indexs = {i, j};
                if (pos.containsKey(v)) {
                    pos.get(v).add(indexs);
                } else {
                    ArrayList<int[]> t = new ArrayList<>();
                    t.add(indexs);
                    pos.put(v, t);
                }
            }
        }
        for (Integer v : pos.keySet()) {
            if (mem.contains(v)) {
                continue;
            }
            int s = target - v;
            mem.add(s);
            mem.add(v);
            if (pos.containsKey(s)) {
                for (int[] vs : pos.get(v)) {
                    for (int[] ss : pos.get(s)) {
                        boolean dup = false;
                        for (int s1 : ss) {
                            for (int v1 : vs) {
                                if (s1 == v1) {
                                    dup = true;
                                    break;
                                }
                            }
                        }
                        if (!dup) {
                            //去掉位置调换的重复
                            TreeSet<Integer> tmp = new TreeSet<>();
                            tmp.add(vs[0]);
                            tmp.add(vs[1]);
                            tmp.add(ss[0]);
                            tmp.add(ss[1]);
                            hres.add(tmp);
                        }
                    }
                }
            }
        }
        //去掉值重复
        HashSet<List<Integer>> hhres = new HashSet<>();
        for (TreeSet<Integer> re : hres) {
            List<Integer> t = new ArrayList<>();
            for (Integer index : re) {
                t.add(nums[index]);
            }
            hhres.add(t);
        }
        return new ArrayList<>(hhres);
    }


    public static List<List<Integer>> fourSum(int[] nums, int target) {
        Arrays.sort(nums);
//        dfs(nums,target,0,new ArrayList<>());
//        ArrayList<Integer> a = new ArrayList<>();
//        for (int num : nums) {
//            a.add(num);
//        }
//        bad_dfs(a,target,new ArrayList<>());
        return hs;
    }

    public static void main(String[] args) {
        int[] nums = {91277418, 66271374, 38763793, 4092006, 11415077, 60468277, 1122637, 72398035, -62267800, 22082642, 60359529, -16540633, 92671879, -64462734, -55855043, -40899846, 88007957, -57387813, -49552230, -96789394, 18318594, -3246760, -44346548, -21370279, 42493875, 25185969, 83216261, -70078020, -53687927, -76072023, -65863359, -61708176, -29175835, 85675811, -80575807, -92211746, 44755622, -23368379, 23619674, -749263, -40707953, -68966953, 72694581, -52328726, -78618474, 40958224, -2921736, -55902268, -74278762, 63342010, 29076029, 58781716, 56045007, -67966567, -79405127, -45778231, -47167435, 1586413, -58822903, -51277270, 87348634, -86955956, -47418266, 74884315, -36952674, -29067969, -98812826, -44893101, -22516153, -34522513, 34091871, -79583480, 47562301, 6154068, 87601405, -48859327, -2183204, 17736781, 31189878, -23814871, -35880166, 39204002, 93248899, -42067196, -49473145, -75235452, -61923200, 64824322, -88505198, 20903451, -80926102, 56089387, -58094433, 37743524, -71480010, -14975982, 19473982, 47085913, -90793462, -33520678, 70775566, -76347995, -16091435, 94700640, 17183454, 85735982, 90399615, -86251609, -68167910, -95327478, 90586275, -99524469, 16999817, 27815883, -88279865, 53092631, 75125438, 44270568, -23129316, -846252, -59608044, 90938699, 80923976, 3534451, 6218186, 41256179, -9165388, -11897463, 92423776, -38991231, -6082654, 92275443, 74040861, 77457712, -80549965, -42515693, 69918944, -95198414, 15677446, -52451179, -50111167, -23732840, 39520751, -90474508, -27860023, 65164540, 26582346, -20183515, 99018741, -2826130, -28461563, -24759460, -83828963, -1739800, 71207113, 26434787, 52931083, -33111208, 38314304, -29429107, -5567826, -5149750, 9582750, 85289753, 75490866, -93202942, -85974081, 7365682, -42953023, 21825824, 68329208, -87994788, 3460985, 18744871, -49724457, -12982362, -47800372, 39958829, -95981751, -71017359, -18397211, 27941418, -34699076, 74174334, 96928957, 44328607, 49293516, -39034828, 5945763, -47046163, 10986423, 63478877, 30677010, -21202664, -86235407, 3164123, 8956697, -9003909, -18929014, -73824245};
        int tar = -236727523;

//        int[] nums = {-1,-5,-5,-3,2,5,0,4};
//        int tar = -7;

//        int[] nums={0,0,0,0};
//        int tar = 0;

//        int[] nums = {0,1,5,0,1,5,5,-4};
//        int tar = 11;

        List<List<Integer>> res = fourSum_n2(nums,tar);
        for (List<Integer> re : res) {
            for (Integer integer : re) {
                System.out.print(integer+" ");
            }
            System.out.println();
        }
    }


}
