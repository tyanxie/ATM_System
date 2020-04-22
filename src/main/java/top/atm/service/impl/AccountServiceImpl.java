package top.atm.service.impl;

import top.atm.bean.Account;
import top.atm.bean.User;
import top.atm.dao.AccountDao;
import top.atm.dao.UserDao;
import top.atm.dao.impl.AccountDaoImpl;
import top.atm.dao.impl.UserDaoImpl;
import top.atm.service.AccountService;

import java.math.BigDecimal;

/**
 * @author taifu
 */

public class AccountServiceImpl implements AccountService {
    private static final UserDao userDao = new UserDaoImpl();
    private static final AccountDao accountDao = new AccountDaoImpl();

    @Override
    public User getAccountHostUser(String id, String password) {
        Account account = accountDao.getAccountByIdAndPassword(id, password);
        if (account == null) {
            return null;
        }
        User user = userDao.getUserById(account.getUserId());
        if (user == null) {
            logger.warn("获取 account 成功但获取 user 失败");
            return null;
        }
        return user;
    }

    @Override
    public boolean deposit(String accountId, String deposit) {
        BigDecimal amount;
        try {
            amount = new BigDecimal(deposit);
            if (amount.compareTo(new BigDecimal("0")) <= 0) {
                // 存款金额 为/小于 0
                return false;
            }
        } catch (NumberFormatException e) {
            // 无法转换 BigDecimal, 存款失败
            logger.error(e.getMessage());
            return false;
        }

        int result = accountDao.deposit(accountId, amount);
        // 若修改的行数不为 1, 则修改数据库失败, 亦即存款失败
        return result == 1;
    }
}
