package top.atm.message;

import java.util.HashMap;
import java.util.Map;

/**
 * 转账所使用的消息类
 *
 * @author taifu
 */

public class TransferMessage extends AbstractMessage {
    private static final Map<Status, AbstractMessage> messageMap;

    private TransferMessage(Status status, String... messages) {
        super(status.getCode(), messages);
    }

    static {
        messageMap = new HashMap<>();

        messageMap.put(Status.OK,
            new TransferMessage(Status.OK));
        messageMap.put(Status.FORMAT_ERROR,
            new TransferMessage(TransferMessage.Status.FORMAT_ERROR, "转账金额有误"));
        messageMap.put(Status.DATABASE_ERROR,
            new TransferMessage(TransferMessage.Status.DATABASE_ERROR, "数据库访问异常", "请稍后重试或寻找管理员"));
        messageMap.put(Status.INSUFFICIENT_BALANCE_ERROR,
            new TransferMessage(TransferMessage.Status.INSUFFICIENT_BALANCE_ERROR, "账户余额不足", "转账失败"));
        messageMap.put(Status.TARGET_NOT_EXIST_ERROR,
            new TransferMessage(TransferMessage.Status.TARGET_NOT_EXIST_ERROR, "目标账户不存在"));
        messageMap.put(Status.SELF_ERROR,
            new TransferMessage(TransferMessage.Status.SELF_ERROR, "不能给自己转账"));
        messageMap.put(Status.UNKNOWN,
            new TransferMessage(Status.UNKNOWN, "发生未知错误", "请联系管理员解决"));
    }

    public static AbstractMessage get(Status status) {
        return messageMap.get(status);
    }

    public static AbstractMessage get(Status status, String... messages) {
        return new TransferMessage(status, messages);
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
        TARGET_NOT_EXIST_ERROR(4),  // 目标账户不存在
        SELF_ERROR(5),  // 自己给自己转账
        UNKNOWN(6);

        private final Integer code;

        Status(Integer code) {
            this.code = code;
        }

        public Integer getCode() {
            return code;
        }
    }
}
