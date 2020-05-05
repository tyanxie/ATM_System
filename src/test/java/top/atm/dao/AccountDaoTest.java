package top.atm.dao;

import org.junit.Test;
import top.atm.dao.impl.AccountDaoImpl;

import java.math.BigDecimal;

/**
 * @author taifu
 */

public class AccountDaoTest {
    private static final AccountDao accountDao = AccountDaoImpl.getInstance();

    @Test
    public void getBalance() {
        BigDecimal admin = accountDao.getBalance("admin");
        System.out.println("admin = " + admin);
        System.out.println("admin.getClass() = " + admin.getClass());
        BigDecimal admin1 = accountDao.getBalance("admin1");
        System.out.println("admin1 = " + admin1);
    }
}