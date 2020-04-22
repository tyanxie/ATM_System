package top.atm.util;

/**
 * 关闭工具类, 只有一个 close() 方法
 * 用于安静的关闭传入的可关闭对象
 *
 * @author taifu
 */

public class CloseUtils {
    /**
     * 私有构造函数防止构造
     */
    private CloseUtils(){}

    public static void close(AutoCloseable... closeables) {
        for (AutoCloseable closeable : closeables) {
            try {
                if (closeable != null) {
                    closeable.close();
                }
            } catch (Exception ignore) {
            }
        }
    }
}