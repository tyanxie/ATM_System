package top.atm.service.impl;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomUtils;
import top.atm.bean.Account;
import top.atm.bean.User;
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
    public AbstractMessage verifyDeposit(String accountId, String deposit) {
        BigDecimal amount;
        try {
            amount = new BigDecimal(deposit);
        } catch (NumberFormatException e) {
            return DepositMessage.get(DepositMessage.Status.FORMAT_ERROR);
        }

        if (!RandomUtils.nextBoolean()) {
            // 使用随机数模仿验证真伪失败
            return DepositMessage.get(DepositMessage.Status.VERIFY_ERROR);
        }

        BigDecimal balance = accountDao.getBalance(accountId);
        if (balance == null) {
            // 获取账户余额失败, 判定存款失败
            return DepositMessage.get(DepositMessage.Status.DATABASE_ERROR);
        } else if (amount.add(balance).compareTo(UPPER_LIMIT) > 0) {
            // 存款后超出存储上限
            return DepositMessage.get(DepositMessage.Status.OVER_LIMIT_ERROR);
        }
        return DepositMessage.get(DepositMessage.Status.OK);
    }

    @Override
    public AbstractMessage deposit(String accountId, String deposit) {
        BigDecimal amount;
        try {
            amount = new BigDecimal(deposit);
            if (amount.compareTo(new BigDecimal("0")) <= 0) {
                // 存款金额 为/小于 0
                return DepositMessage.get(DepositMessage.Status.DIGITAL_ERROR);
            }
        } catch (NumberFormatException e) {
            // 无法转换 BigDecimal, 存款失败
            return DepositMessage.get(DepositMessage.Status.FORMAT_ERROR);
        }

        int result = accountDao.deposit(accountId, amount);
        if (result != 1) {
            // 若修改的行数不为 1, 则修改数据库失败, 亦即存款失败
            return DepositMessage.get(DepositMessage.Status.DATABASE_ERROR);
        }
        return DepositMessage.get(DepositMessage.Status.OK);
    }

    @Override
    public AbstractMessage withdraw(String accountId, String withdraw) {
        BigDecimal amount;
        BigDecimal balance = accountDao.getBalance(accountId);
        if (balance == null) {
            // 获取账户余额失败, 判定取款失败
            return WithdrawMessage.get(WithdrawMessage.Status.DATABASE_ERROR);
        }
        try {
            amount = new BigDecimal(withdraw);
            if (balance.compareTo(amount) < 0) {
                // 账户的余额不足, 取款失败
                return WithdrawMessage.get(WithdrawMessage.Status.INSUFFICIENT_BALANCE_ERROR);
            }
        } catch (NumberFormatException e) {
            // 无法转换为 BigDecimal 类型, 取款失败
            return WithdrawMessage.get(WithdrawMessage.Status.FORMAT_ERROR);
        }

        int result = accountDao.withdraw(accountId, amount);
        // 修改的行数不为 1, 修改失败, 亦即取款失败
        if (result != 1) {
            return WithdrawMessage.get(WithdrawMessage.Status.DATABASE_ERROR);
        }
        // 修改的行数为 1, 则取款成功
        return WithdrawMessage.get(WithdrawMessage.Status.OK);
    }

    @Override
    public AbstractMessage verifyTransfer(String sourceAccountId, String targetAccountId, String transfer) {
        if (StringUtils.equals(sourceAccountId, targetAccountId)) {
            // 自己给自己转账, 这是不允许的
            return TransferMessage.get(TransferMessage.Status.SELF_ERROR);
        }

        Long userId = accountDao.getUserIdByAccountId(targetAccountId);
        if (userId == null) {
            return TransferMessage.get(TransferMessage.Status.TARGET_NOT_EXIST_ERROR);
        }

        BigDecimal amount;
        try {
            amount = new BigDecimal(transfer);
        } catch (NumberFormatException e) {
            return TransferMessage.get(TransferMessage.Status.FORMAT_ERROR);
        }

        BigDecimal balance = accountDao.getBalance(sourceAccountId);
        if (balance == null) {
            // 获取账户余额失败, 判定无法转账
            return TransferMessage.get(TransferMessage.Status.DATABASE_ERROR);
        } else if (amount.compareTo(balance) > 0) {
            // 转账金额大于源账户余额, 不可转账
            return TransferMessage.get(TransferMessage.Status.INSUFFICIENT_BALANCE_ERROR);
        }
        // 返回转账的目标的姓名
        Long targetUserId = accountDao.getUserIdByAccountId(targetAccountId);
        User targetUser = userDao.getUserById(targetUserId);
        return TransferMessage.get(TransferMessage.Status.OK, targetUser.getName());
    }

    @Override
    public AbstractMessage transfer(String sourceAccountId, String targetAccountId, String transfer) {
        if (StringUtils.equals(sourceAccountId, targetAccountId)) {
            // 自己给自己转账, 这是不允许的
            return TransferMessage.get(TransferMessage.Status.SELF_ERROR);
        }

        BigDecimal amount;
        BigDecimal sourceBalance = accountDao.getBalance(sourceAccountId);
        try {
            amount = new BigDecimal(transfer);
            if (amount.compareTo(sourceBalance) > 0) {
                // 转账金额大于源账户余额, 转账失败
                return TransferMessage.get(TransferMessage.Status.INSUFFICIENT_BALANCE_ERROR);
            }
        } catch (NumberFormatException e) {
            // 无法转换为 BigDecimal 类型, 转账失败
            return TransferMessage.get(TransferMessage.Status.FORMAT_ERROR);
        }

        int result = accountDao.transfer(sourceAccountId, targetAccountId, amount);
        if (result != 2) {
            // 转账失败, 数据库错误
            return TransferMessage.get(TransferMessage.Status.DATABASE_ERROR);
        }

        // 返回转账的目标的姓名
        Long targetUserId = accountDao.getUserIdByAccountId(targetAccountId);
        User targetUser = userDao.getUserById(targetUserId);
        return TransferMessage.get(TransferMessage.Status.OK, targetUser.getName());
    }

    @Override
    public AbstractMessage balanceQuery(String accountId) {
        BigDecimal balance = accountDao.getBalance(accountId);
        return
            balance == null
                ? BalanceQueryMessage.get(BalanceQueryMessage.Status.DATABASE_ERROR)
                : BalanceQueryMessage.get(BalanceQueryMessage.Status.OK, balance.toString());
    }

    @Override
    public AbstractMessage changePassword(String accountId, String oldPassword, String newPassword) {
        // 获取当前加密后的 password
        String currentPassword = accountDao.getPassword(accountId);
        if (currentPassword == null) {
            // 发生了未知错误, 当前密码查询失败, 无法断定是数据库错误
            return ModifyMessage.get(ModifyMessage.Status.UNKNOWN);
        }

        // 比较用户输入的密码经过 sha256 加密后是否与 currentPassword 相同
        String encodeOldPassword = DigestUtils.sha256Hex(oldPassword);
        if (!StringUtils.equalsNonNull(currentPassword, encodeOldPassword)) {
            // 用户输入的旧密码与数据库中的密码不匹配
            return ModifyMessage.get(ModifyMessage.Status.PASSWORD_NOT_MATCH_ERROR);
        }

        // 旧密码正确, 加密并修改数据库密码
        String encodeNewPassword = DigestUtils.sha256Hex(newPassword);
        int result = accountDao.changePassword(accountId, encodeNewPassword);

        if (result != 1) {
            // 数据库发生错误
            return ModifyMessage.get(ModifyMessage.Status.DATABASE_ERROR);
        }
        return ModifyMessage.get(ModifyMessage.Status.OK);
    }
}
