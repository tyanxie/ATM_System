package top.atm.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.atm.bean.Page;
import top.atm.bean.TransactionRecord;

/**
 * @author taifu
 */

public interface TransactionRecordService {
    Logger logger = LoggerFactory.getLogger(TransactionRecordService.class);
    Integer ITEM_PER_PAGE_DEFAULT = 10;

    /**
     * 查询一个页面的所有交易记录
     *
     * @param accountId   查询交易记录的账户 id
     * @param currentPage 当前希望查询的页面
     * @param itemPerPage 一个页面的元组数量
     * @return 一个页面对象, 包含了页面所需的所有元组和属性
     */
    Page<TransactionRecord> getRecord(String accountId, String currentPage, String itemPerPage);

    /**
     * 获取转账目标账户或收款源账户的用户姓名
     * @param accountId 转账目标账户或收款源账户 id
     * @return 返回转账目标账户或收款源账户的用户姓名
     */
    String getUserName(String accountId);
}
