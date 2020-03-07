package random;

/**
 * Created by Xursan on 2020/3/2.
 * 相同概率问题，给你一个rand()函数，返回0的概率是a，返回1的概率是1-a
 * 要求在rand()函数的基础上，重新写一个rand2函数，保证返回0和1的概率相等。
 */
public class SameProbability {

    public int rand() {
        //// TODO: 指定函数
        return 0;
    }

    /**
     * 其实对于这种概率题，基本套路就是，new 2个rand函数，由这两个函数的返回值进行组合，保证其组合返回的值符合要求即可
     * 比如这道题，p = rand()     q=rand()                然后想办法怎么组合才能保证其概率相等
     * 我们知道p(0) = a     p(1)=1-a        q(0)=a      q(1) = 1-a
     * 那要保证概率相等自然有====》p(0)*q(1) = p(1)*q(0)
     * 所以只有当pq=01或者qp=01时才返回，这样其返回结果只有两种情况，而且保证了这两种情况的发生概率是相等的。
     *
     * @return
     */
    public int rand2() {
        int p = rand();
        int q = rand();
        while (true) {
            if (p == 0 && q == 1) {
                //01标志0
                return 0;
            }
            if (p == 1 && q == 0) {
                //10标志1
                return 1;
            }
        }
    }
}
