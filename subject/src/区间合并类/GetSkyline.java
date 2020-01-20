package 区间合并类;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Created by Xursan on 2020/1/19.
 * 218. 天际线问题
 */
public class GetSkyline {
    /**
     * https://briangordon.github.io/2014/08/the-skyline-problem.html——参考题解
     * <p>
     * 从最原始的想法出发，一开始我想到的就是建立一个x轴对应高度的映射数组h[]，将x轴离散化，每一个坐标点对应一个高度，
     * 然后对于每一个矩形就更新一次h[]（选取最高点），直到遍历完毕，每次选取突变点即为结果。
     * <p>
     * 这里值得注意的一点是，矩形必须要视为左闭右开型，否则无法统一每次选取最高的形式。因为如果右边视为闭，会导致矩形
     * 交界处的值出错，因为这里选择的并不是最高而是第二高的；但是如果视为左闭右开型，可以每次都选取最高的，不会对最终
     * 结果产生影响。
     * <p>
     * 时间复杂度——O(n*m),n为building的数量，m为离散化后的坐标长度，m>>2n
     * <p>
     * 我们再仔细想一下，我们发现最终结果只会出现在突变点上，那不如直接将对应高度映射到突变点对应的坐标上就可以了，但是
     * 我们一开始并不能知晓突变点到底是哪一点，只能保证突变点一定存在于每一个矩形的左右两端，所以就选取左右两端作为其
     * 关键点（突变候选点），这样每次都只更新突变点的对应高度即可——时间复杂度O(n^2)
     * <p>
     * 我们再想一下，似乎也没必要，对于每一个矩形，去判断所有的关键点，只需要判断在该矩形范围内的所有关键点即可，自然
     * 而然想到应该采取某种方式快速索引出范围内的关键点，最直接的想法就是对关键点排序，然后二分查找。由于关键点取的是矩
     * 形的左右两端（矩形之间的右侧无序），所以有必要对关键点排序。
     * 虽然可以将查找关键点的时间复杂度降维log(n)，但是最坏情况下（比如金字塔形），每次仍要更新大量关键点，时间复杂度
     * 仍为O(n^2)
     * <p>
     * 那我们再反着思考一下，每次根据关键点去索引矩形，找出覆盖该关键点的所有矩形（不包括右边），然后选出最大高度的即可。
     * 所以现在的关键问题就在于如何迅速找到覆盖该关键点的所有矩形。
     * 从左到右遍历所有关键点，然后维护一个 矩形集s ，其目的是每次到一个关键点后，都可以迅速更新s，然后得到包含此关键点
     * 的所有矩形。那么问题就在于如何维护这个矩形集s，实际上我们在从左往右遍历关键点时，因为关键点也和矩形成对应关系。
     * 初始将第一个关键点对应的矩形高度入s，然后后面遍历时判断本次关键点所对应的矩形高度是否在以前出现过，如果出现过，
     * 则表明这一定是前面对应矩形的右端点，那就从s中弹出，即表明对于此关键点而言，该矩形一定不包含（右侧开，所以不包含）
     * 若没出现过，则加入s，表示此关键点被包含在此矩形中（左侧闭，包含）。
     * <p>
     * 上面解决了矩形集的维护问题，下面就考虑如何快速获得最大值，那必然是大顶堆，将矩形集维护成一个大顶堆即可。
     *
     * @param buildings
     * @return
     */
    public static List<List<Integer>> getSkyline(int[][] buildings) {
        PriorityQueue<Integer> q = new PriorityQueue<>((o1, o2) -> (o2 - o1));
        List<List<Integer>> res = new ArrayList<>();
        //用于判断此关键点是否和上一次的关键点高度一致，若一致则表示此关键点不是突变点，不会在最终结果中
        int last = -1;
        int[][] keyPoints = new int[buildings.length * 2][2];
        for (int i = 0; i < buildings.length; i++) {
            //left point
            keyPoints[i][0] = buildings[i][0];
            keyPoints[i][1] = -buildings[i][2];//左侧高取相反数，用于区分左侧还是右侧，以及x相同时按左右高低升序排序
            //right point
            keyPoints[i + buildings.length][0] = buildings[i][1];
            keyPoints[i + buildings.length][1] = buildings[i][2];
        }
        Arrays.sort(keyPoints, (int[] o1, int[] o2) -> (o1[0] - o2[0] == 0 ? o1[1] - o2[1] : o1[0] - o2[0]));
        for (int[] b : keyPoints) {
            //左侧
            if (b[1] < 0) {
                q.add(-b[1]);
            } else {
                //右侧
                q.remove(b[1]);
            }
            //在此关键点上不存在矩形，即为0点
            int maxH = 0;
            //若存在矩形则更新
            if (!q.isEmpty()) {
                maxH = q.peek();
            }
            if (last != maxH) {
                List<Integer> tmp = new ArrayList<>();
                tmp.add(b[0]);
                tmp.add(maxH);
                res.add(tmp);
                last = maxH;
            }
        }
        return res;
    }

    public static void main(String[] args) {
        String s = "[[1,2,1],[1,2,2],[1,2,3]]";
        int[][] nums = utils.matrixTrans.strTrans2Array(s);
        for (List<Integer> tmp : getSkyline(nums)) {
            for (Integer integer : tmp) {
                System.out.print(integer + " ");
            }
            System.out.println();
        }

    }
}
