package top.atm.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.atm.bean.User;
import top.atm.message.AbstractMessage;

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
     * @param deposit   存款的金额, 需要在进行校验并转换为 BigDecimal 类型
     * @return 存款的结果, 存款成功则为 true
     */
    AbstractMessage deposit(String accountId, String deposit);

    /**
     * 提前校验用户输入的金额是否是正确的格式
     * 此外加入随机 boolean 值模仿纸钞验真伪失败
     *
     * @param deposit 用户输入的待存储的金额
     * @return 校验结果, 成功为 true, 失败为 false
     */
    AbstractMessage verifyDeposit(String deposit);
}
