package top.atm.service.impl;

import top.atm.dao.AccountDao;
import top.atm.dao.UserDao;
import top.atm.dao.impl.AccountDaoImpl;
import top.atm.dao.impl.UserDaoImpl;
import top.atm.service.UserService;

/**
 * @author taifu
 */

public class UserServiceImpl implements UserService {
    private final UserDao userDao = UserDaoImpl.getInstance();
    private final AccountDao accountDao = AccountDaoImpl.getInstance();

    private static volatile UserService instance;

    private UserServiceImpl() {}

    public static UserService getInstance() {
        if (instance == null) {
            synchronized (UserService.class) {
                if (instance == null) {
                    instance = new UserServiceImpl();
                }
            }
        }
        return instance;
    }

    @Override
    public String getUsernameByAccountId(String accountId) {
        return userDao.getUserById(accountDao.getUserIdByAccountId(accountId)).getName();
    }
}
