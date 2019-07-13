package search.dfs;

import java.util.ArrayList;

/**
 * 你有 4 张写有 1 到 9 数字的牌。你需要判断是否能通过 *，/，+，-，(，) 的运算得到 24。
 *
 * 示例 1:
 *
 * 输入: [4, 1, 8, 7]
 * 输出: True
 * 解释: (8-4) * (7-1) = 24
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/24-game
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 *
 *
 * 总结：搜索问题，思路简单，就是递归求解
 * 这里第一层的思考就是每次从数组中选2个数，选用不同的符号进行计算，将计算的结果再存回数组中，
 * 直到数组大小为1，再与结果进行比对。
 * 但是这里值得学习的是：
 * 1.对于实数计算，我们是无法模拟的，比如2/3这种，显然我们不能算出精确结果，只能算出一个无限接近
 * 正确答案的数，所以最终我们的想法就是，只要最后结果与目标的误差error小于足够小的数时，我们就认为是正确答案。
 *
 * 2.容易忽略的点：减法和除法的运算不满足交换律，而我们在两两选择时，是按照顺序以for循环的方式实现的，这样没有考虑到
 * 位置的影响，会造成有两种情况丢失，所以减法和除法需要交换位置计算出另外两个结果。
 *
 * 3.在编程实现的过程中，发现并不是想象中这么简单。我们要总结一些技巧，规避容易出错的coding方式。
 * 比如，非基本类型在函数参数传递时，是以引用方式传递的，所以当我们在进行dfs时，或者for循环时，前面对其进行的操作会
 * 对后面对其操作产生依赖影响。所以在编程过程中，如果在循环或者函数调用中要对非基本类型进行增删操作，一律copy一个新对象，
 * 这样对新对象的操作，不会对原对象产生影响。
 *
 * 再比如，这里我们的每一阶段的目标是，计算出来一个新数，并加入到数组中。所以我们没有必要对 +-*\/ 进行for循环，我们只需要
 * 计算出每个符号所得出的结果，然后加入数组，dfs调用即可。
 */
public class Point24 {
    public static double error = 0.00000000001;
    public static double tar = 24;

    public static ArrayList<Double> addData(ArrayList<Double> nums,double d){
        ArrayList<Double> a = new ArrayList<>(nums);
        a.add(d);
        return a;
    }

    public static boolean dfs(double tmp,ArrayList<Double> nums){
        if(nums.size()==1){
            return Math.abs(Math.abs(tmp)-tar)<=error;
        }
        for (int i = 0;i<nums.size();i++) {
            for (int j = i+1;j<nums.size();j++) {
                double n1 = nums.get(i);
                double n2 = nums.get(j);
                ArrayList<Double> cp = new ArrayList<>(nums);
                cp.remove(n1);
                cp.remove(n2);
                double tmp1 = n1+n2;
                double tmp2 = n1*n2;
                double tmp3 = n1-n2;
                double tmp4 = n2-n1;
                double tmp5 = n1/n2;
                double tmp6 = n2/n1;
                boolean res = dfs(tmp1,addData(cp,tmp1))
                        ||dfs(tmp2,addData(cp,tmp2))
                        ||dfs(tmp3,addData(cp,tmp3))
                        ||dfs(tmp4,addData(cp,tmp4))
                        ||dfs(tmp5,addData(cp,tmp5))
                        ||dfs(tmp6,addData(cp,tmp6));
                if(res){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 出错原因，在于非基本类型都是传递的引用类型，在外层循环时，虽然remove后接了add操作，恢复原有数据，
     * 但是循环位置对应的数据已经发生了变化，所以造成了错误
     * @param tmp
     * @param nums
     * @return
     */
    /*public static boolean search.dfs(double tmp,ArrayList<Double> nums){
        if(nums.size()==1){
            if(Math.abs(tmp-tar)<=error){
                return true;
            }else{
                return false;
            }
        }
        boolean res = false;
        for (int i = 0;i<nums.size();i++) {
            for (int j = i+1;j<nums.size();j++) {
                double n1 = nums.get(i);
                double n2 = nums.get(j);
                nums.remove(n1);
                nums.remove(n2);
                for (int k = 0; k < 4; k++) {
                    //compute
                    if(k==0){
                        tmp =n1+n2;
                        nums.add(tmp);
                        res = search.dfs(tmp,nums);
                        nums.remove(tmp);
                    }
                    if(k==1){
                        tmp = n1-n2;
                        nums.add(tmp);
                        res = search.dfs(tmp,nums);
                        nums.remove(tmp);
                    }
                    if(k==2){
                        tmp = n1*n2;
                        nums.add(tmp);
                        res = search.dfs(tmp,nums);
                        nums.remove(tmp);
                    }
                    if(k==3){
                        tmp = n1/n2;
                        nums.add(tmp);
                        res = search.dfs(tmp,nums);
                        nums.remove(tmp);
                    }
                    if(res){
                        return true;
                    }
                }
                nums.add(n1);
                nums.add(n2);
            }
        }
        return false;
    }*/

    public static boolean judgePoint24(int[] nums) {
        ArrayList<Double> a = new ArrayList<>();
        for (int i:nums) {
            a.add((double)i);
        }
        return dfs(0,a);
    }

    public static void main(String[] args) {
        int[] nums = {8,1,6,6};
        System.out.println(judgePoint24(nums));
    }

}


