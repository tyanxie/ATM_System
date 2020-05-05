package top.atm.message;

import java.util.HashMap;
import java.util.Map;

/**
 * @author taifu
 */

public class WithdrawMessage extends AbstractMessage {
    private static final Map<Status, AbstractMessage> messageMap;

    private WithdrawMessage(Status status, String... messages) {
        super(status.getCode(), messages);
    }

    static {
        messageMap = new HashMap<>();

        messageMap.put(Status.OK,
            new WithdrawMessage(Status.OK));
        messageMap.put(Status.FORMAT_ERROR,
            new WithdrawMessage(WithdrawMessage.Status.FORMAT_ERROR, "取款金额有误"));
        messageMap.put(Status.DATABASE_ERROR,
            new WithdrawMessage(WithdrawMessage.Status.DATABASE_ERROR, "数据库访问异常", "请稍后重试或寻找管理员"));
        messageMap.put(Status.INSUFFICIENT_BALANCE_ERROR,
            new WithdrawMessage(WithdrawMessage.Status.INSUFFICIENT_BALANCE_ERROR, "账户余额不足", "取款失败"));
        messageMap.put(Status.UNKNOWN,
            new WithdrawMessage(Status.UNKNOWN, "发生未知错误", "请联系管理员解决"));
    }

    public static AbstractMessage get(Status status) {
        return messageMap.get(status);
    }

    @Override
    public String debugStatus() {
        for (Status status : Status.values()) {
            if (status.getCode().equals(getStatus())) {
                return status.name();
            }
        }
        return "UNKNOWN";
    }

    public enum Status {
        OK(0),
        FORMAT_ERROR(1),
        DATABASE_ERROR(2),
        INSUFFICIENT_BALANCE_ERROR(3),  // 余额不足
        UNKNOWN(4);

        private final Integer code;

        Status(Integer code) {
            this.code = code;
        }

        public Integer getCode() {
            return code;
        }
    }
}
