package Geometry;

/**
 * LineJudge
 */
public class LineJudge {

    public static boolean bad_isLine(int[][] points) {
        /**
         * 笨办法，虽然考虑到了斜率，但是因为x坐标相减有可能为0的情况，所以把相减为0的情况
         * 单独列了出来，这样无疑增加了复杂性
         */
        double x1 = points[0][0];
        double y1 = points[0][1];
        double x2 = points[1][0];
        double y2 = points[1][1];
        double x3 = points[2][0];
        double y3 = points[2][1];
        
        if(x1==x2&&x2==x3){return true;}
        if((x1==x2&&y1==y2)||(x2==x3&&y2==y3)||(x1==x3&&y1==y3)){
            return true;
        }
        if(x1==x2||x2==x3||x1==x3){return false;}
        else{
            if((y2-y1)/(x2-x1)==(y3-y2)/(x3-x2)){
                return true;
            }else{
                return false;
            }
        }
        
    }

    public static boolean isLine(int[][] points) {
        /**
         * 其实可以这样考虑，不计算斜率，而是对斜率等式做一个变形。
         * 要判断是否是直线，若不考虑除数为0的情况，起初为  (y1-y3)/(x1-x3) == (y1-y2)/(x1-x2)
         * 但是若考虑做一个变形，就可以不用考虑除数为0了，(y1-y3)*(x1-x2) == (y1-y2)*(x1-x2)
         * 这样就将等式除法判断转换为等式乘法判断乐
         */
        int x1 = points[0][0];
        int y1 = points[0][1];
        int x2 = points[1][0];
        int y2 = points[1][1];
        int x3 = points[2][0];
        int y3 = points[2][1];
        return (y1-y3)*(x1-x2) == (x1-x3)*(y1-y2);

    }
    public static void main(String[] args) {
        int[][] a= {{0,0},{1,1},{2,2}};
        System.out.println(isLine(a));
    }
}