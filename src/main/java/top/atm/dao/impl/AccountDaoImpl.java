package top.atm.dao.impl;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import top.atm.bean.Account;
import top.atm.bean.TransactionRecord;
import top.atm.dao.AccountDao;
import top.atm.dao.TransactionRecordDao;
import top.atm.util.CloseUtils;
import top.atm.util.JdbcUtils;

import java.math.BigDecimal;
import java.sql.*;

/**
 * @author taifu
 */

public class AccountDaoImpl implements AccountDao {
    private static volatile AccountDao instance;

    /**
     * 使用单例模式中的双加锁方法
     */
    public static AccountDao getInstance() {
        if (instance == null) {
            synchronized (AccountDao.class) {
                if (instance == null) {
                    instance = new AccountDaoImpl();
                }
            }
        }
        return instance;
    }

    private AccountDaoImpl() {}

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
    public Long getUserIdByAccountId(String accountId) {
        Connection connection = null;
        try {
            connection = JdbcUtils.getConnection();
            // 设置只读, 提高效率
            connection.setReadOnly(true);

            QueryRunner runner = new QueryRunner();
            return runner.query(
                connection,
                "select user_id from account where id = ?",
                new ScalarHandler<>(),
                accountId
            );
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            CloseUtils.close(connection);
        }
        return -1L;
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
                return result;
            }

            // 存款成功, 记录存款记录
            TransactionRecord record = new TransactionRecord(null, accountId,
                null, TransactionRecord.DEPOSIT, amount, null);
            ps = TransactionRecordDao.createTransactionRecodeInsertStatement(connection, record);
            result = ps.executeUpdate();
            if (result != 1) {
                // 插入存款记录失败, 需要回滚
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
                return result;
            }

            // 取款成功, 存储交易记录
            TransactionRecord record = new TransactionRecord(null, accountId,
                null, TransactionRecord.WITHDRAW, amount, null);
            ps = TransactionRecordDao.createTransactionRecodeInsertStatement(connection, record);
            result = ps.executeUpdate();
            if (result != 1) {
                // 插入取款记录失败, 需要回滚
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
    public int transfer(String sourceAccountId, String targetAccountId, BigDecimal amount) {
        Connection connection = null;
        PreparedStatement ps = null;

        try {
            connection = JdbcUtils.getConnection();
            connection.setAutoCommit(false);

            ps = connection.prepareStatement("update account set balance = balance + ? where id = ?");
            ps.setBigDecimal(1, amount);
            ps.setString(2, targetAccountId);
            int targetResult = ps.executeUpdate();
            if (targetResult != 1) {
                // 目标账户存储失败, 转账失败并回滚
                connection.rollback();
                return 0;
            }

            ps = connection.prepareStatement("update account set balance = balance - ? where id = ?");
            ps.setBigDecimal(1, amount);
            ps.setString(2, sourceAccountId);
            int sourceResult = ps.executeUpdate();
            if (sourceResult != 1) {
                // 源账户存储失败, 转账失败并回滚
                connection.rollback();
                return 0;
            }

            // 转账成功, 记录转账记录
            TransactionRecord record = new TransactionRecord(null, sourceAccountId,
                targetAccountId, TransactionRecord.TRANSFER, amount, null);
            ps = TransactionRecordDao.createTransactionRecodeInsertStatement(connection, record);
            sourceResult = ps.executeUpdate();
            if (sourceResult != 1) {
                // 源账户存储转账记录失败, 回滚
                connection.rollback();
                return 0;
            }

            record.setType(TransactionRecord.CREDIT);
            record.swapSourceAndTarget();
            ps = TransactionRecordDao.createTransactionRecodeInsertStatement(connection, record);
            targetResult = ps.executeUpdate();
            if (targetResult != 1) {
                // 目标账户存储转账记录失败, 回滚
                connection.rollback();
                return 0;
            }
            connection.commit();
            return targetResult + sourceResult;
        } catch (Exception e) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    logger.error(ex.getMessage());
                }
            }
        } finally {
            CloseUtils.close(connection, ps);
        }
        return 0;
    }
}
