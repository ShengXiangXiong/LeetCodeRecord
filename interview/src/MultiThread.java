import java.util.concurrent.Semaphore;

/**
 * Created by Xursan on 2020/3/5.
 * 多线程轮流打印ABC
 */
public class MultiThread {
    static Semaphore a = new Semaphore(1);
    static Semaphore b = new Semaphore(0);
    static Semaphore c = new Semaphore(0);
    static int k = 3;

    /**
     * 信号量实现,初始打印A，所以初始A信号量为1，其余为0
     * 然后A负责释放B，B负责释放C，C负责释放A即可
     *
     * @param args
     */
    public static void main(String[] args) {
        new Thread(() -> {
            int cnt = k;
            while (cnt-- > 0) {
                try {
                    a.acquire();
                    System.out.println("A");
                    b.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        new Thread(() -> {
            int cnt = k;
            while (cnt-- > 0) {
                try {
                    b.acquire();
                    System.out.println("B");
                    c.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        new Thread(() -> {
            int cnt = k;
            while (cnt-- > 0) {
                try {
                    c.acquire();
                    System.out.println("C");
                    a.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }
}
