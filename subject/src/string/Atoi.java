package string;


public class Atoi {
    /**
     * 思路：注意点，一是要提前判断，通过设置Integer.MAX_VALUE/10变量，防止中间乘法溢出
     * 二是要考虑到刚好前一个res值为Integer.MAX_VALUE/10的情况，这时的输入值与极值只是个位数的差距
     * 这种情况可以通过对溢出的判断来解决，我们知道在个位数的加减范围内，正数溢出会变为负数，负数溢出会变为正数
     * 所以通过res的正负变化即可知晓输入值是否超出了范围
     *
     * @param str
     * @return
     */
    public static int atoi(String str) {
        str = str.trim();
        if (str.length() == 0) {
            return 0;
        }
        int res = 0;
        int start = 0;
        int maxv = Integer.MAX_VALUE / 10;
        char[] cs = str.toCharArray();
        if (cs[0] != '-' && cs[0] != '+' && (cs[0] - '0' > 9 || cs[0] - '0' < 0)) {
            return 0;
        }
        boolean minus = false;
        if (cs[0] == '-') {
            minus = true;
            start++;
        }
        if (cs[1] == '+') {
            start++;
        }
        for (int i = start; i < cs.length; i++) {
            int num = cs[i] - '0';
            if (num < 0 || num > 9) {
                return res;
            }
            if (Math.abs(res) > maxv) {
                return minus ? Integer.MIN_VALUE : Integer.MAX_VALUE;
            } else {
                res *= 10;
                if (minus) {
                    res = res - num > 0 ? Integer.MIN_VALUE : res - num;
                } else {
                    res = res + num < 0 ? Integer.MAX_VALUE : res + num;
                }
            }
        }
        return res;
    }

    public static int atoi2(String str) {
        str = str.trim();
        if (str.length() == 0) return 0;

        // + - 号
        char firstChar = str.charAt(0);
        int sign = 1;
        int start = 0;
        long res = 0;
        if (firstChar == '+') {
            sign = 1;
            start++;
        } else if (firstChar == '-') {
            sign = -1;
            start++;
        }

        for (int i = start; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i))) {
                return (int) res * sign;
            }
            res = res * 10 + str.charAt(i) - '0';
            if (sign == 1 && res > Integer.MAX_VALUE) return Integer.MAX_VALUE;
            if (sign == -1 && res > Integer.MAX_VALUE) return Integer.MIN_VALUE;
        }
        return (int) res * sign;
    }

    public static void main(String[] args) {
        String str = "2147483649";
        System.out.println(atoi2(str));
    }
}
