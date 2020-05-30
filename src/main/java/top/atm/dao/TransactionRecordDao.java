package top.atm.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.atm.bean.TransactionRecord;

import java.sql.*;
import java.util.List;

/**
 * @author taifu
 */

public interface TransactionRecordDao {
    Logger logger = LoggerFactory.getLogger(TransactionRecordDao.class);

    /**
     * 向数据库中插入交易记录地模板 SQL
     * 其中 create_time 直接调用了 SQL 中的 now() 函数获取当前时间
     */
    String TRANSACTION_RECORD_INSERT_SQL_TEMPLATE =
        "insert into transaction_record" +
            "(" +
            "   source_account_id," +
            "   target_account_id," +
            "   type," +
            "   amount," +
            "   remarks," +
            "   create_time," +
            "   delete_time" +
            ") " +
            " values(?, ?, ?, ?, ?, now(), null)";

    /**
     * 构造一个用于插入交易记录的 PreparedStatement
     *
     * @param connection SQL 连接
     * @param record     TransactionRecord 对象, 存储了当前交易的信息
     * @return 可用于插入交易记录的 PreparedStatement 对象
     */
    static PreparedStatement createTransactionRecodeInsertStatement(
        Connection connection, TransactionRecord record) throws SQLException {

        PreparedStatement ps = connection.prepareStatement(TransactionRecordDao.TRANSACTION_RECORD_INSERT_SQL_TEMPLATE);
        ps.setString(1, record.getSourceAccountId());
        ps.setString(2, record.getTargetAccountId());
        ps.setInt(3, record.getType());
        ps.setBigDecimal(4, record.getAmount());
        ps.setString(5, record.getRemarks());
        return ps;
    }

    /**
     * 查询交易记录总数
     *
     * @param accountId 账户 id
     * @return 对应账户 id 的交易记录的总数
     */
    Long getTotalRecordByAccountId(String accountId);

    /**
     * 查询交易记录
     *
     * @param accountId   账户 id
     * @param startNumber 当前要查询的页面
     * @param itemPerPage 每一页的记录数
     * @return 当前页面中账户 id 对应的 itemPerPage 个交易记录
     */
    List<TransactionRecord> listRangeRecord(String accountId, Long startNumber, Integer itemPerPage);
}
