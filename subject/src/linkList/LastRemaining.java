package linkList;

/**
 * Created by Xursan on 2020/3/3.
 */
public class LastRemaining {
    public int lastRemaining(int n, int m) {
        return n == 1 ? 0 : (lastRemaining(n - 1, m) + m) % n;
    }

    public static void main(String[] args) {

    }
}
