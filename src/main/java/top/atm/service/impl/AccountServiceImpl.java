package top.atm.service.impl;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomUtils;
import top.atm.bean.Account;
import top.atm.bean.User;
import top.atm.constant.ErrorCode;
import top.atm.dao.AccountDao;
import top.atm.dao.UserDao;
import top.atm.dao.impl.AccountDaoImpl;
import top.atm.dao.impl.UserDaoImpl;
import top.atm.message.*;
import top.atm.service.AccountService;
import top.atm.util.StringUtils;

import java.math.BigDecimal;

/**
 * @author taifu
 * @author BlessingChimes
 */

public class AccountServiceImpl implements AccountService {
    private static final UserDao userDao = UserDaoImpl.getInstance();
    private static final AccountDao accountDao = AccountDaoImpl.getInstance();

    private static volatile AccountService instance;

    public static AccountService getInstance() {
        if (instance == null) {
            synchronized (AccountService.class) {
                if (instance == null) {
                    instance = new AccountServiceImpl();
                }
            }
        }
        return instance;
    }

    private AccountServiceImpl() {}

    /**
     * 存款上限
     */
    private static final BigDecimal UPPER_LIMIT = new BigDecimal("99999999.99");

    @Override
    public User getAccountHostUser(String id, String password) {
        // 获取加密后的密码
        String encodePassword = DigestUtils.sha256Hex(password);
        Account account = accountDao.getAccountByIdAndPassword(id, encodePassword);
        if (account == null) {
            return null;
        }
        User user = userDao.getUserById(account.getUserId());
        if (user == null) {
            logger.warn("获取 account 成功但获取 user 失败");
        }
        return user;
    }

    @Override
    public ErrorCode verifyDeposit(String accountId, String deposit) {
        BigDecimal amount;
        try {
            amount = new BigDecimal(deposit);
        } catch (NumberFormatException e) {
            return ErrorCode.DEPOSIT_FORMAT_ERROR;
        }

        if (!RandomUtils.nextBoolean()) {
            // 使用随机数模仿验证真伪失败
            return ErrorCode.DEPOSIT_VERIFY_ERROR;
        }

        BigDecimal balance = accountDao.getBalance(accountId);
        if (balance == null) {
            // 获取账户余额失败, 判定存款失败
            return ErrorCode.DATABASE_ERROR;
        } else if (amount.add(balance).compareTo(UPPER_LIMIT) > 0) {
            // 存款后超出存储上限
            return ErrorCode.DEPOSIT_OVER_LIMIT_ERROR;
        }
        return ErrorCode.OK;
    }

    @Override
    public ErrorCode deposit(String accountId, String deposit) {
        BigDecimal amount;
        try {
            amount = new BigDecimal(deposit);
            if (amount.compareTo(new BigDecimal("0")) <= 0) {
                // 存款金额 == 或 < 0
                return ErrorCode.DEPOSIT_DIGITAL_ERROR;
            }
        } catch (NumberFormatException e) {
            // 无法转换 BigDecimal, 存款失败
            return ErrorCode.DEPOSIT_FORMAT_ERROR;
        }

        int result = accountDao.deposit(accountId, amount);
        if (result != 1) {
            // 若修改的行数不为 1, 则修改数据库失败, 亦即存款失败
            return ErrorCode.DATABASE_ERROR;
        }
        return ErrorCode.OK;
    }

    @Override
    public ErrorCode withdraw(String accountId, String withdraw) {
        BigDecimal amount;
        BigDecimal balance = accountDao.getBalance(accountId);
        if (balance == null) {
            // 获取账户余额失败, 判定取款失败
            return ErrorCode.DATABASE_ERROR;
        }
        try {
            amount = new BigDecimal(withdraw);
            if (balance.compareTo(amount) < 0) {
                // 账户的余额不足, 取款失败
                return ErrorCode.WITHDRAW_INSUFFICIENT_BALANCE_ERROR;
            }
        } catch (NumberFormatException e) {
            // 无法转换为 BigDecimal 类型, 取款失败
            return ErrorCode.WITHDRAW_FORMAT_ERROR;
        }

        int result = accountDao.withdraw(accountId, amount);
        // 修改的行数不为 1, 修改失败, 亦即取款失败
        if (result != 1) {
            return ErrorCode.DATABASE_ERROR;
        }
        // 修改的行数为 1, 则取款成功
        return ErrorCode.OK;
    }

    @Override
    public DefaultMessage verifyTransfer(String sourceAccountId, String targetAccountId, String transfer) {
        if (StringUtils.equals(sourceAccountId, targetAccountId)) {
            // 自己给自己转账, 这是不允许的
            return ErrorCode.TRANSFER_SELF_ERROR.buildMessage();
        }

        Long userId = accountDao.getUserIdByAccountId(targetAccountId);
        if (userId == null) {
            return ErrorCode.TRANSFER_TARGET_NOT_EXIST_ERROR.buildMessage();
        }

        BigDecimal amount;
        try {
            amount = new BigDecimal(transfer);
        } catch (NumberFormatException e) {
            return ErrorCode.TRANSFER_FORMAT_ERROR.buildMessage();
        }

        BigDecimal balance = accountDao.getBalance(sourceAccountId);
        if (balance == null) {
            // 获取账户余额失败, 判定无法转账
            return ErrorCode.DATABASE_ERROR.buildMessage();
        } else if (amount.compareTo(balance) > 0) {
            // 转账金额大于源账户余额, 不可转账
            return ErrorCode.TRANSFER_INSUFFICIENT_BALANCE_ERROR.buildMessage();
        }
        // 返回转账的目标的姓名
        Long targetUserId = accountDao.getUserIdByAccountId(targetAccountId);
        User targetUser = userDao.getUserById(targetUserId);
        return ErrorCode.OK.buildMessage(targetUser.getName());
    }

    @Override
    public DefaultMessage transfer(String sourceAccountId, String targetAccountId, String transfer) {
        if (StringUtils.equals(sourceAccountId, targetAccountId)) {
            // 自己给自己转账, 这是不允许的
            return ErrorCode.TRANSFER_SELF_ERROR.buildMessage();
        }

        BigDecimal amount;
        BigDecimal sourceBalance = accountDao.getBalance(sourceAccountId);
        try {
            amount = new BigDecimal(transfer);
            if (amount.compareTo(sourceBalance) > 0) {
                // 转账金额大于源账户余额, 转账失败
                return ErrorCode.TRANSFER_INSUFFICIENT_BALANCE_ERROR.buildMessage();
            }
        } catch (NumberFormatException e) {
            // 无法转换为 BigDecimal 类型, 转账失败
            return ErrorCode.TRANSFER_FORMAT_ERROR.buildMessage();
        }

        int result = accountDao.transfer(sourceAccountId, targetAccountId, amount);
        if (result != 2) {
            // 转账失败, 数据库错误
            return ErrorCode.DATABASE_ERROR.buildMessage();
        }

        // 返回转账的目标的姓名
        Long targetUserId = accountDao.getUserIdByAccountId(targetAccountId);
        User targetUser = userDao.getUserById(targetUserId);
        return ErrorCode.OK.buildMessage(targetUser.getName());
    }

    @Override
    public DefaultMessage balanceQuery(String accountId) {
        BigDecimal balance = accountDao.getBalance(accountId);
        return
            balance == null
                ? ErrorCode.DATABASE_ERROR.buildMessage()
                : ErrorCode.OK.buildMessage(balance.toString());
    }

    @Override
    public ErrorCode changePassword(String accountId, String oldPassword, String newPassword) {
        // 获取当前加密后的 password
        String currentPassword = accountDao.getPassword(accountId);
        if (currentPassword == null) {
            // 发生了未知错误, 当前密码查询失败, 无法断定是数据库错误
            return ErrorCode.UNKNOWN;
        }

        // 比较用户输入的密码经过 sha256 加密后是否与 currentPassword 相同
        String encodeOldPassword = DigestUtils.sha256Hex(oldPassword);
        if (!StringUtils.equalsNonNull(currentPassword, encodeOldPassword)) {
            // 用户输入的旧密码与数据库中的密码不匹配
            return ErrorCode.MODIFY_PASSWORD_NOT_MATCH_ERROR;
        }

        // 旧密码正确, 加密并修改数据库密码
        String encodeNewPassword = DigestUtils.sha256Hex(newPassword);
        int result = accountDao.changePassword(accountId, encodeNewPassword);

        if (result != 1) {
            // 数据库发生错误
            return ErrorCode.DATABASE_ERROR;
        }
        return ErrorCode.OK;
    }
}
