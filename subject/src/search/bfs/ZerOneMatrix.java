package search.bfs;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;

/**
 * 给定一个由 0 和 1 组成的矩阵，找出每个元素到最近的 0 的距离。
 *
 * 两个相邻元素间的距离为 1 。
 *
 * 示例 1:
 * 输入:
 *
 * 0 0 0
 * 0 1 0
 * 0 0 0
 * 输出:
 *
 * 0 0 0
 * 0 1 0
 * 0 0 0
 * 示例 2:
 * 输入:
 *
 * 0 0 0
 * 0 1 0
 * 1 1 1
 * 输出:
 *
 * 0 0 0
 * 0 1 0
 * 1 2 1
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/01-matrix
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */

public class ZerOneMatrix {
    public static int[][] towards={{0,1},{0,-1},{-1,0},{1,0}};
    public static int[][] mem;
    public static int n;
    public static int m;
    public static boolean[][] vis;
    public static int initValue = 10000001;

//    public static void pushNextLayer(int x,int y,Queue<int[]> q,boolean visParam){
//        for (int[] toward : towards) {
//            int _x = x+toward[0];
//            int _y = y+toward[1];
//            if(_x>=0&&_x<n&&_y>=0&&_y<m){
//                //visParam控制是否要加入已访问过的节点
//                if(visParam&&vis[_x][_y]){
//                    continue;
//                }
//                q.add(new int[] {_x,_y});
//            }
//        }
//    }

    /*public static int bfs(ArrayDeque<int[]> q){
        int s = q.size();//当前层元素的个数
        int tmp = initValue;//当前层离0的最短距离
        int[] p;
        int x;
        int y;
        ArrayDeque<int[]> memElem = q.clone();//记录当前层的所有元素，用于更新
        //下一层元素入队
        while (s>0){
            p = q.poll();
            x = p[0];
            y = p[1];
            vis[x][y] = true;//表示当前位置已被搜索过
            if(mem[x][y]>1){
                pushNextLayer(x,y,q,true);
            }else{
                tmp = Math.min(mem[x][y],tmp);
                if(tmp==0){
                    for (int[] pp : memElem) {
                        vis[pp[0]][pp[1]]=false;
                    }
                    return 0;
                }
            }
            s--;
        }
        if(!q.isEmpty()){
            tmp = Math.min(tmp,bfs(q));
        }
        for (int[] pp : memElem) {
            vis[pp[0]][pp[1]]=false;
        }
        return 1+tmp;
    }*/


    public static int bfs(int tmp, int cnt ,ArrayDeque<int[]> q){
        int res = cnt+tmp;
        if(cnt>=tmp){
            return tmp;
        }
        int s = q.size();
        while(s>0){
            int[] p = q.pop();
            int x = p[0];
            int y = p[1];
            vis[x][y]=true;
            for (int[] toward : towards) {
                int _x = x+toward[0];
                int _y = y+toward[1];
                if(_x>=0&&_x<n&&_y>=0&&_y<m){
                    if(mem[_x][_y]==initValue&&!vis[_x][_y]){
                        q.add(new int[] {_x,_y});
                    }else {
                        tmp = Math.min(tmp,mem[_x][_y]);
                        if(tmp==0){
                            return cnt;
                        }
                    }
                }
            }
            s--;
        }
        if(!q.isEmpty()){
            res = Math.min(res,bfs(cnt+tmp,cnt+1,q));
        }
        return res;
    }

    public static int dfs(int i, int j){
        vis[i][j]=true;
        if(mem[i][j]>1){
            for (int[] toward : towards) {
                int x=toward[0]+i;
                int y=toward[1]+j;
                if(x>=0&&x<n&&y>=0&&y<m){
                    if(mem[x][y]!=initValue){
                        mem[i][j]=Math.min(mem[i][j],mem[x][y]+1);
                    }else {
                        if(!vis[x][y]){
                            mem[i][j]=Math.min(mem[i][j],dfs(x,y)+1);
                        }
                    }
                }
                if(mem[i][j]==1){
                    return mem[i][j];
                }
            }
        }
        return mem[i][j];
    }

    public static int[][] updateMatrix(int[][] matrix) {
        mem = matrix;
        n = matrix.length;
        m = matrix[0].length;
//        mem = new int[n][m];
        vis = new boolean[n][m];
        //init the dis of 1 to 0
        for (int i = 0; i < n; i++) {
            for(int j = 0; j< m; j++){
                if(mem[i][j]==1){
                    mem[i][j]=initValue;
                }
            }
        }
        for (int i = 0; i < n; i++) {
            for(int j = 0; j< m; j++){
                if(mem[i][j]>1){
                    ArrayDeque<int[]> q = new ArrayDeque<>();
                    q.add(new int[]{i,j});
                    mem[i][j] = bfs(initValue,1,q);
                }
            }
        }
        return mem;
    }

    //dp解法
    public static int[][] updataMatrix1(int[][] matrix){
        int row = matrix.length;
        int col = matrix[0].length;
        // 第一次遍历，正向遍历，根据相邻左元素和上元素得出当前元素的对应结果
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (matrix[i][j] == 1) {
                    matrix[i][j] = row + col;
                }
                if (i > 0) {
                    matrix[i][j] = Math.min(matrix[i][j], matrix[i - 1][j] + 1);
                }
                if (j > 0) {
                    matrix[i][j] = Math.min(matrix[i][j], matrix[i][j - 1] + 1);
                }
            }
        }
        // 第二次遍历，反向遍历，根据相邻右元素和下元素及当前元素的结果得出最终结果
        for (int i = row - 1; i >= 0; i--) {
            for (int j = col - 1; j >= 0; j--) {
                if (i < row - 1) {
                    matrix[i][j] = Math.min(matrix[i][j], matrix[i + 1][j] + 1);
                }
                if (j < col - 1) {
                    matrix[i][j] = Math.min(matrix[i][j], matrix[i][j + 1] + 1);
                }
            }
        }
        return matrix;
    }

    public static void main(String[] args) {
//        String str = "[[1,1,0,0,1,0,0,1,1,0],[1,0,0,1,0,1,1,1,1,1],[1,1,1,0,0,1,1,1,1,0],[0,1,1,1,0,1,1,1,1,1],[0,0,1,1,1,1,1,1,1,0],[1,1,1,1,1,1,0,1,1,1],[0,1,1,1,1,1,1,0,0,1],[1,1,1,1,1,0,0,1,1,1],[0,1,0,1,1,0,1,1,1,1],[1,1,1,0,1,0,1,1,1,1]]";
        String str="[[0,1,1,1],[1,1,1,1],[1,1,1,1]]";
        int[][] nums = utils.matrixTrans.strTrans2Array(str);
        for (int[] num : nums) {
            for (int i : num) {
                System.out.print(i+" ");
            }
            System.out.println();
        }
        System.out.println("__________________________________________");
        nums = updateMatrix(nums);
        for (int[] num : nums) {
            for (int i : num) {
                System.out.print(i+" ");
            }
            System.out.println();
        }
    }
}
