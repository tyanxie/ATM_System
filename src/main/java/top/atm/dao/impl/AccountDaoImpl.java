package top.atm.dao.impl;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import top.atm.bean.Account;
import top.atm.dao.AccountDao;
import top.atm.util.CloseUtils;
import top.atm.util.JdbcUtils;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * @author taifu
 */

public class AccountDaoImpl implements AccountDao {
    @Override
    public Account getAccountByIdAndPassword(String id, String password) {
        Connection connection = null;
        try {
            connection = JdbcUtils.getConnection();
            QueryRunner runner = new QueryRunner();
            return runner.query(
                connection,
                " select" +
                    "   user_id userId" +
                    " from account" +
                    " where " +
                    "   id = ? and " +
                    "   password = ?",
                new BeanHandler<>(Account.class),
                id, password
            );
        } catch (Exception e) {
            logger.error("获取用户失败 " + e.getMessage());
        } finally {
            CloseUtils.close(connection);
        }
        return null;
    }

    @Override
    public int deposit(String accountId, BigDecimal amount) {
        Connection connection = null;
        PreparedStatement ps = null;

        try {
            connection = JdbcUtils.getConnection();
            ps = connection.prepareStatement("update account set balance = balance + ? where id = ?");
            ps.setBigDecimal(1, amount);
            ps.setString(2, accountId);
            return ps.executeUpdate();
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            CloseUtils.close(connection, ps);
        }
        return 0;
    }
}
