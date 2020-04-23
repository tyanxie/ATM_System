package top.atm.service.impl;

import top.atm.bean.Account;
import top.atm.bean.User;
import top.atm.dao.AccountDao;
import top.atm.dao.UserDao;
import top.atm.dao.impl.AccountDaoImpl;
import top.atm.dao.impl.UserDaoImpl;
import top.atm.message.AbstractMessage;
import top.atm.message.DepositMessage;
import top.atm.service.AccountService;

import java.math.BigDecimal;
import java.util.Random;
import java.util.regex.Pattern;

/**
 * @author taifu
 */

public class AccountServiceImpl implements AccountService {
    private static final UserDao userDao = new UserDaoImpl();
    private static final AccountDao accountDao = new AccountDaoImpl();

    private static final Pattern PATTERN = Pattern.compile("^[1-9]\\d*(\\.(\\d{0,2}))?$");
    private static final Random RANDOM = new Random();

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
    public AbstractMessage deposit(String accountId, String deposit) {
        BigDecimal amount;
        DepositMessage message;
        try {
            amount = new BigDecimal(deposit);
            if (amount.compareTo(new BigDecimal("0")) <= 0) {
                // 存款金额 为/小于 0
                message = new DepositMessage(DepositMessage.Status.DIGITAL_ERROR, "存款金额不得小于 0");
                return message;
            }
        } catch (NumberFormatException e) {
            // 无法转换 BigDecimal, 存款失败
            message = new DepositMessage(DepositMessage.Status.FORMAT_ERROR, "存款金额有误");
            return message;
        }

        int result = accountDao.deposit(accountId, amount);
        if (result != 1) {
            // 若修改的行数不为 1, 则修改数据库失败, 亦即存款失败
            message = new DepositMessage(DepositMessage.Status.DATABASE_ERROR, "数据库访问异常", "请稍后重试或寻找管理员");
            return message;
        }
        message = new DepositMessage(DepositMessage.Status.OK);
        return message;
    }

    @Override
    public AbstractMessage verifyDeposit(String deposit) {
        DepositMessage message;

        // 使用正则表达式检验传入参数是否符合预期
        boolean result = PATTERN.matcher(deposit).matches();
        if (!result) {
            message = new DepositMessage(DepositMessage.Status.FORMAT_ERROR, "存款金额错误");
        } else if (!RANDOM.nextBoolean()) {
            // 使用随机数模仿验证真伪失败
            message = new DepositMessage(DepositMessage.Status.VERIFY_ERROR, "核对失败", "请将钞票取出", "确认无误后重新放入");
        } else {
            message = new DepositMessage(DepositMessage.Status.OK);
        }
        return message;
    }
}
