package ruleSimulation;

/**
 * lastWordLen
 */
public class lastWordLen {

    public static int lengthOfLastWord(String s) {
        char[] c = s.toCharArray();
        int res = 0;
        int i = c.length - 1;
        while (i > 0 && c[i] == ' ') {
            i--;
        }
        while (i >= 0 && c[i] != ' ') {
            i--;
            res++;
        }
        return res;
    }

    public static void main(String[] args) {
        String s = "123 456 789 1";
        System.out.println(lengthOfLastWord(s));
    }
}