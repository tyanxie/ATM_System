package top.atm.dao.impl;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import top.atm.bean.TransactionRecord;
import top.atm.dao.TransactionRecordDao;
import top.atm.util.CloseUtils;
import top.atm.util.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
    public String getUserNameByAccountId(String accountId) {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            connection = JdbcUtils.getConnection();
            // 设置为只读, 优化查询速度
            connection.setReadOnly(true);

            ps = connection.prepareStatement("select user_id from account where id = ?");
            ps.setString(1, accountId);
            rs = ps.executeQuery();
            rs.next();  // 在从 ResultSet 中提取数据之前一定要先调用 next() 方法将其移动到结果集的第一行
            int userId = rs.getInt(1);

            ps = connection.prepareStatement("select name from user where id = ?");
            ps.setInt(1, userId);
            rs = ps.executeQuery();
            rs.next();
            return rs.getString(1);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("dao exception");
            logger.error(e.getMessage());
        } finally {
            System.out.println("closeConnection");
            CloseUtils.close(connection, ps, rs);
        }
        return null;
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
