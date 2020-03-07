package array;

/**
 * Created by Xursan on 2020/3/5.
 */
public class candies {
    public static int[] distributeCandies(int candies, int num_people) {
        int[] res = new int[num_people];
        int n = (-1 + (int) Math.floor(Math.sqrt(1 + 8 * candies))) / 2;
        int minus = candies - (n * (n + 1) / 2);
        int cnt = n / num_people;
        int mod = n % num_people;
        if (n < num_people) {
            for (int i = 0; i < n; i++) {
                res[i] = i + 1;
            }
            res[n] = minus;
        } else {
            for (int i = 0; i < num_people; i++) {
                if (i < mod) {
                    res[i] = 1 + (cnt * (cnt + 1) / 2) * num_people + cnt * (i + 1);
                } else {
                    res[i] = i + 1 + (cnt * (cnt - 1) / 2) * num_people + (cnt - 1) * (i + 1);
                }
            }
            if (mod > 0) {
                res[mod] += minus;
            }
        }
        return res;
    }

    public double sum(double a, double b) {
        return a + b;
    }

    public static void main(String[] args) {
        int candies = 15, num_people = 3;
        for (int arg : distributeCandies(candies, num_people)) {
            System.out.println(arg);
        }
    }
}
