package top.atm.util;

/**
 * String 类对象的工具类
 *
 * @author taifu
 */

public class StringUtils {
    /**
     * 私有构造函数防止构造
     */
    private StringUtils(){}

    /**
     * 判断 source 的值是否和 target 中的一个 String 相等
     * @return 判断后的结果
     */
    public static boolean join(String source, String... target) {
        if (source == null || target == null) {
            return false;
        }
        for (String t : target) {
            if (source.equals(t)) {
                return true;
            }
        }
        return false;
    }
}
