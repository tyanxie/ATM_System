package top.atm.service.impl;

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
 */

public class AccountServiceImpl implements AccountService {
    private static final UserDao userDao = new UserDaoImpl();
    private static final AccountDao accountDao = new AccountDaoImpl();

    /**
     * 存款上限
     */
    private static final BigDecimal UPPER_LIMIT = new BigDecimal("99999999.99");

    @Override
    public User getAccountHostUser(String id, String password) {
        Account account = accountDao.getAccountByIdAndPassword(id, password);
        if (account == null) {
            return null;
        }
        User user = userDao.getUserById(account.getUserId());
        if (user == null) {
            logger.warn("获取 account 成功但获取 user 失败");
            return null;
        }
        return user;
    }

    @Override
    public AbstractMessage verifyDeposit(String accountId, String deposit) {
        BigDecimal amount;
        try {
            amount = new BigDecimal(deposit);
        } catch (NumberFormatException e) {
            return new DepositMessage(DepositMessage.Status.FORMAT_ERROR, "存款金额错误");
        }

        if (!RandomUtils.nextBoolean()) {
            // 使用随机数模仿验证真伪失败
            return new DepositMessage(DepositMessage.Status.VERIFY_ERROR, "核对失败", "请将钞票取出", "确认无误后重新放入");
        }

        BigDecimal balance = accountDao.getBalance(accountId);
        if (balance == null) {
            // 获取账户余额失败, 判定存款失败
            return new DepositMessage(DepositMessage.Status.DATABASE_ERROR, "数据库访问异常", "请稍后重试或寻找管理员");
        } else if (amount.add(balance).compareTo(UPPER_LIMIT) > 0) {
            // 存款后超出存储上限
            return new DepositMessage(DepositMessage.Status.OVER_LIMIT_ERROR, "存款金额超出单张银行卡上限", "请更换银行卡或咨询管理员");
        }
        return new DepositMessage(DepositMessage.Status.OK);
    }

    @Override
    public AbstractMessage deposit(String accountId, String deposit) {
        BigDecimal amount;
        try {
            amount = new BigDecimal(deposit);
            if (amount.compareTo(new BigDecimal("0")) <= 0) {
                // 存款金额 为/小于 0
                return new DepositMessage(DepositMessage.Status.DIGITAL_ERROR, "存款金额不得小于 0");
            }
        } catch (NumberFormatException e) {
            // 无法转换 BigDecimal, 存款失败
            return new DepositMessage(DepositMessage.Status.FORMAT_ERROR, "存款金额有误");
        }

        int result = accountDao.deposit(accountId, amount);
        if (result != 1) {
            // 若修改的行数不为 1, 则修改数据库失败, 亦即存款失败
            return new DepositMessage(DepositMessage.Status.DATABASE_ERROR, "数据库访问异常", "请稍后重试或寻找管理员");
        }
        return new DepositMessage(DepositMessage.Status.OK);
    }

    @Override
    public AbstractMessage withdraw(String accountId, String withdraw) {
        BigDecimal amount;
        BigDecimal balance = accountDao.getBalance(accountId);
        if (balance == null) {
            // 获取账户余额失败, 判定取款失败
            return new WithdrawMessage(WithdrawMessage.Status.DATABASE_ERROR, "数据库访问异常", "请稍后重试或寻找管理员");
        }
        try {
            amount = new BigDecimal(withdraw);
            if (balance.compareTo(amount) < 0) {
                // 账户的余额不足, 取款失败
                return new WithdrawMessage(WithdrawMessage.Status.INSUFFICIENT_BALANCE_ERROR, "账户余额不足", "取款失败");
            }
        } catch (NumberFormatException e) {
            // 无法转换为 BigDecimal 类型, 取款失败
            return new WithdrawMessage(WithdrawMessage.Status.FORMAT_ERROR, "存款金额有误");
        }

        int result = accountDao.withdraw(accountId, amount);
        // 修改的行数不为 1, 修改失败, 亦即取款失败
        if (result != 1) {
            return new WithdrawMessage(WithdrawMessage.Status.DATABASE_ERROR, "数据库访问异常", "请稍后重试或寻找管理员");
        }
        // 修改的行数为 1, 则取款成功
        return new WithdrawMessage(WithdrawMessage.Status.OK);
    }

    @Override
    public AbstractMessage verifyTransfer(String sourceAccountId, String targetAccountId, String transfer) {
        if (StringUtils.equals(sourceAccountId, targetAccountId)) {
            // 自己给自己转账, 这是不允许的
            return new TransferMessage(TransferMessage.Status.SELF_ERROR, "不能给自己转账");
        }

        Long userId = accountDao.getUserIdByAccountId(targetAccountId);
        if (userId == null) {
            return new TransferMessage(TransferMessage.Status.TARGET_NOT_EXIST_ERROR, "目标账户不存在");
        }

        BigDecimal amount;
        try {
            amount = new BigDecimal(transfer);
        } catch (NumberFormatException e) {
            return new TransferMessage(TransferMessage.Status.FORMAT_ERROR, "存款金额有误");
        }

        BigDecimal balance = accountDao.getBalance(sourceAccountId);
        if (balance == null) {
            // 获取账户余额失败, 判定无法转账
            return new TransferMessage(TransferMessage.Status.DATABASE_ERROR, "数据库访问异常", "请稍后重试或寻找管理员");
        } else if (amount.compareTo(balance) > 0) {
            // 转账金额大于源账户余额, 不可转账
            return new TransferMessage(TransferMessage.Status.INSUFFICIENT_BALANCE_ERROR, "账户余额不足", "转账失败");
        }
        // 返回转账的目标的姓名
        Long targetUserId = accountDao.getUserIdByAccountId(targetAccountId);
        User targetUser = userDao.getUserById(targetUserId);
        return new TransferMessage(TransferMessage.Status.OK, targetUser.getName());
    }

    @Override
    public AbstractMessage transfer(String sourceAccountId, String targetAccountId, String transfer) {
        if (StringUtils.equals(sourceAccountId, targetAccountId)) {
            // 自己给自己转账, 这是不允许的
            return new TransferMessage(TransferMessage.Status.SELF_ERROR, "不能给自己转账");
        }

        BigDecimal amount;
        BigDecimal sourceBalance = accountDao.getBalance(sourceAccountId);
        try {
            amount = new BigDecimal(transfer);
            if (amount.compareTo(sourceBalance) > 0) {
                // 转账金额大于源账户余额, 转账失败
                return new TransferMessage(TransferMessage.Status.INSUFFICIENT_BALANCE_ERROR, "账户余额不足", "转账失败");
            }
        } catch (NumberFormatException e) {
            // 无法转换为 BigDecimal 类型, 转账失败
            return new TransferMessage(TransferMessage.Status.FORMAT_ERROR, "存款金额有误");
        }

        int result = accountDao.transfer(sourceAccountId, targetAccountId, amount);
        if (result != 2) {
            // 转账失败, 数据库错误
            return new TransferMessage(TransferMessage.Status.DATABASE_ERROR, "数据库访问异常", "请稍后重试或寻找管理员");
        }

        // 返回转账的目标的姓名
        Long targetUserId = accountDao.getUserIdByAccountId(targetAccountId);
        User targetUser = userDao.getUserById(targetUserId);
        return new TransferMessage(TransferMessage.Status.OK, targetUser.getName());
    }
}
