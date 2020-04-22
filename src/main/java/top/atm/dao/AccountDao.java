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
     * 存款
     *
     * @param accountId 存款账户的 id
     * @param amount    存款金额
     * @return 修改的行数
     */
    int deposit(String accountId, BigDecimal amount);
}
