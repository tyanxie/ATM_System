package top.atm.util;

import org.junit.Test;

/**
 * @author taifu
 */

public class JdbcUtilsTest {

    @Test
    public void getConnection() throws Exception {
        System.out.println(JdbcUtils.getConnection());
    }
}