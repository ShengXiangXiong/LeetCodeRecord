import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.concurrent.Semaphore;

/**
 * Created by Xursan on 2020/3/5.
 */
public class H2O {
    static Semaphore h = new Semaphore(2);
    static Semaphore o = new Semaphore(1);
    static int k = 5;

    public static void main(String[] args) throws FileNotFoundException {
        BufferedInputStream in = new BufferedInputStream(new FileInputStream("a.txt"));
        new Thread(() -> {
            int cnt = k;
            while (cnt-- > 0) {
                for (int i = 0; i < 2; i++) {
                    try {
                        h.acquire();
                        System.out.println("H");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                o.release();
            }
        }).start();
        new Thread(() -> {
            int cnt = k;
            while (cnt-- > 0) {
                try {
                    o.acquire();
                    System.out.println("O");
                    h.release(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
