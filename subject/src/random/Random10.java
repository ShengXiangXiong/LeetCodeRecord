package random;

import java.util.Random;

/**
 * Created by Xursan on 2020/2/11.
 * Leetcode 470. 用 Rand7() 实现 Rand10()
 * <p>
 * 使用两个rand7，画矩阵，只要保证每一个出现的数都是等概率的即可
 */
public class Random10 {
    public static int rand7() {
        Random rand = new Random(7);
        return rand.nextInt();
    }

    public static int random10() {
        int row, col, idx;
        do {
            row = rand7();
            col = rand7();
            idx = col + (row - 1) * 7;
        } while (idx > 40);
        return 1 + (idx - 1) % 10;
    }
}
