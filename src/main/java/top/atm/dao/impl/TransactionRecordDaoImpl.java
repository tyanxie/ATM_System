package top.atm.dao.impl;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import top.atm.bean.TransactionRecord;
import top.atm.dao.TransactionRecordDao;
import top.atm.util.CloseUtils;
import top.atm.util.JdbcUtils;

import java.sql.Connection;
import java.util.List;

/**
 * @author taifu
 */

public class TransactionRecordDaoImpl implements TransactionRecordDao {
    private static volatile TransactionRecordDao instance;

    public static TransactionRecordDao getInstance() {
        if (instance == null) {
            synchronized (TransactionRecordDao.class) {
                if (instance == null) {
                    instance = new TransactionRecordDaoImpl();
                }
            }
        }
        return instance;
    }

    private TransactionRecordDaoImpl() {}

    @Override
    public Long getTotalRecordByAccountId(String accountId) {
        Connection connection = null;

        try {
            connection = JdbcUtils.getConnection();
            // 设置为只读, 优化查询速度
            connection.setReadOnly(true);
            QueryRunner runner = new QueryRunner();
            return runner.query(
                connection,
                "select count(*) from transaction_record where source_account_id = ?",
                new ScalarHandler<>(),
                accountId
            );
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            CloseUtils.close(connection);
        }
        return 0L;
    }

    @Override
    public List<TransactionRecord> listRangeRecord(String accountId, Long startNumber, Integer itemPerPage) {
        Connection connection = null;

        try {
            connection = JdbcUtils.getConnection();
            // 设置为只读, 优化查询速度
            connection.setReadOnly(true);
            QueryRunner runner = new QueryRunner();
            return runner.query(
                connection,
                "select id," +
                    "       source_account_id sourceAccountId," +
                    "       target_account_id targetAccountId," +
                    "       type," +
                    "       amount," +
                    "       remarks," +
                    "       create_time       occurTime " +
                    "from transaction_record " +
                    "where source_account_id = ? " +
                    "limit ?, ?",
                new BeanListHandler<>(TransactionRecord.class),
                accountId, startNumber, itemPerPage
            );
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            CloseUtils.close(connection);
        }
        return null;
    }
}
