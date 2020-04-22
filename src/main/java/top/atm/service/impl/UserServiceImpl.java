package top.atm.service.impl;

import top.atm.bean.User;
import top.atm.dao.UserDao;
import top.atm.dao.impl.UserDaoImpl;
import top.atm.service.UserService;

/**
 * @author taifu
 */

public class UserServiceImpl implements UserService {
    UserDao userDao = new UserDaoImpl();

    @Override
    public User getUser(Long id) {
        return userDao.getUserById(id);
    }
}
