package top.atm.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.atm.bean.User;

/**
 * @author taifu
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
     * 存款
     *
     * @param accountId 存款的目标账户
     * @param deposit 存款的金额, 需要在进行校验并转换为 BigDecimal 类型
     * @return 存款的结果, 存款成功则为 true
     */
    boolean deposit(String accountId, String deposit);
}
