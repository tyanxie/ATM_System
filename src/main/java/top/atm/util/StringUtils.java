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
    private StringUtils() {}

    /**
     * 判断 source 的值是否和 target 中的一个 String 相等
     *
     * @param source 源字符串
     * @param target 目标字符串集合
     * @return 如果 source 在 target 中出现, 那么返回值为 true, 否则返回为 false
     * 如果 source 和 target 有任意一个为 null, 那么返回 false
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

    /**
     * 判断两个字符串是否相等
     * 虽然实际运用上区分源与目标并无意义
     * 但是对于使用者和阅读者或许可以更加容易理清思路
     *
     * @param source 源字符串, 作为判断标准
     * @param target 目标字符串
     * @return 如果字符串均为 null 或内容相同, 则返回 true, 否则返回 false
     */
    public static boolean equals(String source, String target) {
        if (source == null) {
            return target == null;
        }
        return source.equals(target);
    }

    /**
     * 以忽略大小写的形式判断两个字符串是否相等
     *
     * @param source 源字符串
     * @param target 目标字符串
     * @return 如果字符串均为 null 或内容在全部转为小写后相同, 则返回 true, 否则返回 false
     */
    public static boolean equalsIgnoreCase(String source, String target) {
        if (source == null) {
            // true -> source == null && target == null
            // false -> source == null && target != null
            return target == null;
        } else if (target == null) {
            // source != null && target == null
            return false;
        }
        return equals(source.toLowerCase(), target.toLowerCase());
    }
}
