package top.atm.message;

import java.util.HashMap;
import java.util.Map;

/**
 * @author BlessingChimes
 */
public class BalanceQueryMessage extends AbstractMessage {

    private static final Map<BalanceQueryMessage.Status, AbstractMessage> messageMap;

    private BalanceQueryMessage(Status status, String... messages) {
        super(status.getCode(), messages);
    }

    static {
        messageMap = new HashMap<>();

        messageMap.put(Status.OK,
                new BalanceQueryMessage(Status.OK));
        messageMap.put(Status.DATABASE_ERROR,
                new BalanceQueryMessage(Status.DATABASE_ERROR, "数据库访问异常", "请稍后重试或寻找管理员"));
        messageMap.put(Status.UNKNOWN,
                new BalanceQueryMessage(Status.UNKNOWN, "发生未知错误", "请联系管理员解决"));

    }

    public static AbstractMessage get(Status status) {
        return messageMap.get(status);
    }

    public static AbstractMessage get(Status status, String... message) {
        return new BalanceQueryMessage(status, message);
    }

    @Override
    public String debugStatus() {
        for (TransferMessage.Status status : TransferMessage.Status.values()) {
            if (status.getCode().equals(getStatus())) {
                return status.name();
            }
        }
        return "UNKNOWN";
    }

    public enum Status {
        OK(0),
        DATABASE_ERROR(1),
        UNKNOWN(2);

        private final Integer code;

        Status(Integer code) {
            this.code = code;
        }

        public Integer getCode() {
            return code;
        }
    }
}
