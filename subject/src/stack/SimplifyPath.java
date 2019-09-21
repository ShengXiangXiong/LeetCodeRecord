package stack;

import java.util.Stack;

/**
 * Created by Xursan on 2019/9/21.
 * 以 Unix 风格给出一个文件的绝对路径，你需要简化它。或者换句话说，将其转换为规范路径。
 * 在 Unix 风格的文件系统中，一个点（.）表示当前目录本身；此外，两个点 （..） 表示将目录切换到上一级（指向父目录）；
 * 两者都可以是复杂相对路径的组成部分。更多信息请参阅：Linux / Unix中的绝对路径 vs 相对路径
 * 请注意，返回的规范路径必须始终以斜杠 / 开头，并且两个目录名之间必须只有一个斜杠 /。
 * 最后一个目录名（如果存在）不能以 / 结尾。此外，规范路径必须是表示绝对路径的最短字符串。
 * <p>
 * 输入："/home/"
 * 输出："/home"
 * 解释：注意，最后一个目录名后面没有斜杠。
 * <p>
 * 输入："/../"
 * 输出："/"
 * 解释：从根目录向上一级是不可行的，因为根是你可以到达的最高级。
 * <p>
 * 输入："/home//foo/"
 * 输出："/home/foo"
 * 解释：在规范路径中，多个连续斜杠需要用一个斜杠替换。
 * <p>
 * 输入："/a/./b/../../c/"
 * 输出："/c"
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/simplify-path
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class SimplifyPath {
    /**
     * 1.此题主要考察的是栈,所以定义一个辅助栈;
     * 2.先把字符串以"/"为分隔符分割成数组,此时数组有"路径"、""、"."、".."这四种情况;
     * 3.遍历数组,当s[i].equals("..")并且栈不空时pop,当!s[i].equals("") && !s[i].equals(".") && !s[i].equals(".."),即s[i]是路径入栈;
     * 4.栈空,返回"/",栈非空,用StringBuffer做一个连接返回即可;
     * 注意：最好不要直接对单个char做判断，因为特殊路径"/..."被认为是正确的，这样就会造成...无法判断
     *
     * @param path
     * @return
     */
    public static String simplifyPath(String path) {
        String[] s = path.split("/");
        Stack<String> stack = new Stack<>();
        for (String value : s) {
            if (!stack.isEmpty() && value.equals(".."))
                stack.pop();
            else if (!value.equals("") && !value.equals(".") && !value.equals(".."))
                stack.push(value);
        }
        if (stack.isEmpty())
            return "/";

        StringBuilder res = new StringBuilder();
        for (String aStack : stack) {
            res.append("/").append(aStack);
        }
        return res.toString();
    }

    public static void main(String[] args) {

        String str = "/...";
        System.out.println(simplifyPath(str));
    }
}
