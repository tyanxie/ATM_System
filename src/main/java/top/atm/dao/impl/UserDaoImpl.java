package top.atm.dao.impl;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import top.atm.bean.User;
import top.atm.dao.UserDao;
import top.atm.util.CloseUtils;
import top.atm.util.JdbcUtils;

import java.sql.Connection;

/**
 * @author taifu
 */

public class UserDaoImpl implements UserDao {
    @Override
    public User getUserById(Long id) {
        Connection connection = null;
        try {
            connection = JdbcUtils.getConnection();
            QueryRunner runner = new QueryRunner();
            return runner.query(
                connection,
                "select id, name from user where id = ?",
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
}
