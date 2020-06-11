package top.atm.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.atm.bean.Account;

import java.math.BigDecimal;

/**
 * @author taifu
 */

public interface AccountDao {
    Logger logger = LoggerFactory.getLogger(AccountDao.class);

    /**
     * 获取帐号和密码对应的账户
     *
     * @param id       account 的帐号
     * @param password account 的 id
     * @return 注意该方法返回的 Account 中只包含 userId 这一个属性
     * 当帐号和密码不匹配的时候返回 null
     */
    Account getAccountByIdAndPassword(String id, String password);

    /**
     * 获取用户的 id
     *
     * @param accountId 用户的帐号 id
     * @return 用户的 id
     */
    Long getUserIdByAccountId(String accountId);

    /**
     * 获取余额
     *
     * @param accountId 需要查询余额的账户的 id
     * @return 查询到的余额, 如果没有查询到, 则返回 null
     */
    BigDecimal getBalance(String accountId);

    /**
     * 存款
     *
     * @param accountId 存款账户的 id
     * @param amount    存款金额
     * @return 数据库被修改的行数
     */
    int deposit(String accountId, BigDecimal amount);

    /**
     * 取款
     *
     * @param accountId 取款账户的 id
     * @param amount    取款金额
     * @return 数据库被修改的行数
     */
    int withdraw(String accountId, BigDecimal amount);

    /**
     * 转账
     *
     * @param sourceAccountId 转账方账户 id
     * @param targetAccountId 收款方账户 id
     * @param amount          转账金额
     * @return 数据库被修改的行数
     */
    int transfer(String sourceAccountId, String targetAccountId, BigDecimal amount);

    /**
     * 获取账户当前的密码
     *
     * @param accountId 账户 id
     * @return 账户当前密码
     */
    String getPassword(String accountId);

    /**
     * 修改账户的密码
     *
     * @param accountId 账户 id
     * @param newPassword 新密码
     * @return 修改的结果, 修改成功则为 1, 失败则为 0
     */
    int changePassword(String accountId, String newPassword);
}
