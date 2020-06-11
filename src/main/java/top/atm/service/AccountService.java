package top.atm.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.atm.bean.User;
import top.atm.message.AbstractMessage;

/**
 * @author taifu
 * @author BlessingChimes
 */

public interface AccountService {
    Logger logger = LoggerFactory.getLogger(AccountService.class);

    /**
     * 获取一个账户对应的 user
     *
     * @param id       账户的 id
     * @param password 对应账户的密码
     * @return 如果成功则会返回对应账户, 否则返回 null
     */
    User getAccountHostUser(String id, String password);

    /**
     * 提前校验用户输入的金额是否是正确的格式
     * 此外加入随机 boolean 值模仿纸钞验真伪失败
     *
     * @param accountId 期望存款的账户的 id
     * @param deposit   用户输入的待存储的金额
     * @return 校验结果
     */
    AbstractMessage verifyDeposit(String accountId, String deposit);

    /**
     * 存款
     *
     * @param accountId 存款的目标账户
     * @param deposit   存款的金额, 需要进行校验并转换为 BigDecimal 类型
     * @return 存款的结果
     */
    AbstractMessage deposit(String accountId, String deposit);

    /**
     * 取款
     *
     * @param accountId 取款的目标账户
     * @param withdraw  取款的金额, 需要进行校验并转换为 BigDecimal 类型
     * @return 取款的结果
     */
    AbstractMessage withdraw(String accountId, String withdraw);

    /**
     * 提前校验用户输入的金额和目标账户是否存在
     *
     * @param sourceAccountId 源账户的 id
     * @param targetAccountId 目标账户的 id
     * @param transfer        转账金额
     * @return 判断的结果
     */
    AbstractMessage verifyTransfer(String sourceAccountId, String targetAccountId, String transfer);

    /**
     * 转账
     *
     * @param sourceAccountId 源账户的 id
     * @param targetAccountId 目标账户的 id
     * @param transfer        转账金额
     * @return 转账的结果
     */
    AbstractMessage transfer(String sourceAccountId, String targetAccountId, String transfer);

    /**
     * 余额查询
     *
     * @param accountId 查询的账户id
     * @return 查询余额的结果
     */
    AbstractMessage balanceQuery(String accountId);

    /**
     * 修改密码
     *
     * @param accountId 需要修改密码的账户的 id
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     * @return 修改密码的结果, 1 代表成功, 0 代表失败
     */
    AbstractMessage changePassword(String accountId, String oldPassword, String newPassword);
}
