package top.atm.dao.impl;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
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
            // 设置只读, 提高效率
            connection.setReadOnly(true);
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
    public BigDecimal getBalance(String accountId) {
        Connection connection = null;
        try {
            connection = JdbcUtils.getConnection();
            QueryRunner runner = new QueryRunner();
            return runner.query(
                connection,
                "select balance from account where id = ?",
                new ScalarHandler<>(),
                accountId);
        } catch (Exception e) {
            // 获取失败
            logger.error(e.getMessage());
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
            connection.setAutoCommit(false);

            ps = connection.prepareStatement("update account set balance = balance + ? where id = ?");
            ps.setBigDecimal(1, amount);
            ps.setString(2, accountId);
            int result = ps.executeUpdate();
            if (result != 1) {
                // 存储出现意外, 需要回滚
                connection.rollback();
            } else {
                connection.commit();
            }
            return result;
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            CloseUtils.close(connection, ps);
        }
        return 0;
    }

    @Override
    public int withdraw(String accountId, BigDecimal amount) {
        Connection connection = null;
        PreparedStatement ps = null;

        try {
            connection = JdbcUtils.getConnection();
            connection.setAutoCommit(false);

            ps = connection.prepareStatement("update account set balance = balance - ? where id = ?");
            ps.setBigDecimal(1, amount);
            ps.setString(2, accountId);
            int result = ps.executeUpdate();
            if (result != 1) {
                // 存储出现意外, 需要回滚
                connection.rollback();
            } else {
                connection.commit();
            }
            return result;
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            CloseUtils.close(connection, ps);
        }
        return 0;
    }
}
