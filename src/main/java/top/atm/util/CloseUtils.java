package top.atm.util;

/**
 * @author taifu
 */

public class CloseUtils {
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