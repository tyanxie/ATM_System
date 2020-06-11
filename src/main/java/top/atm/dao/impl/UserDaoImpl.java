package top.atm.dao.impl;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import top.atm.bean.User;
import top.atm.dao.UserDao;
import top.atm.util.CloseUtils;
import top.atm.util.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * @author taifu
 */

public class UserDaoImpl implements UserDao {
    private static volatile UserDao instance;

    /**
     * 使用单例模式中的双加锁方法
     */
    public static UserDao getInstance() {
        if (instance == null) {
            synchronized (UserDao.class) {
                if (instance == null) {
                    instance = new UserDaoImpl();
                }
            }
        }
        return instance;
    }

    private UserDaoImpl() {}

    @Override
    public User getUserById(Long id) {
        Connection connection = null;
        try {
            connection = JdbcUtils.getConnection();
            QueryRunner runner = new QueryRunner();
            return runner.query(
                connection,
                "select id, name, address, phone_number phoneNumber from user where id = ?",
                new BeanHandler<>(User.class),
                id
            );
        } catch (Exception e) {
            logger.error("获取用户失败 " + e.getMessage());
        } finally {
            CloseUtils.close(connection);
        }
        return null;
    }

    @Override
    public int update(User user) {
        Connection connection = null;
        PreparedStatement ps = null;

        try {
            connection = JdbcUtils.getConnection();
            ps = connection.prepareStatement("update user set name = ?, address = ?, phone_number = ? where id = ?");
            ps.setString(1, user.getName());
            ps.setString(2, user.getAddress());
            ps.setString(3, user.getPhoneNumber());
            ps.setLong(4, user.getId());
            return ps.executeUpdate();
        } catch (Exception e) {
            logger.error("修改用户信息过程中发生错误 " + e.getMessage());
        } finally {
            CloseUtils.close(ps, connection);
        }

        return 0;
    }
}
