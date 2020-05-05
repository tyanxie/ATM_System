package top.atm.message;

import java.util.HashMap;
import java.util.Map;

/**
 * @author taifu
 */

@SuppressWarnings ("unused")
public class DepositMessage extends AbstractMessage {
    private static final Map<Status, AbstractMessage> messageMap;

    private DepositMessage(Status status, String... messages) {
        super(status.getCode(), messages);
    }

    static {
            messageMap = new HashMap<>();

            messageMap.put(Status.OK,
                new DepositMessage(Status.OK));
            messageMap.put(Status.FORMAT_ERROR,
                new DepositMessage(Status.FORMAT_ERROR, "存款金额错误"));
            messageMap.put(Status.DATABASE_ERROR,
                new DepositMessage(Status.DATABASE_ERROR, "数据库访问异常", "请稍后重试或寻找管理员"));
            messageMap.put(Status.DIGITAL_ERROR,
                new DepositMessage(Status.DIGITAL_ERROR, "存款金额不得小于 0"));
            messageMap.put(Status.VERIFY_ERROR,
                new DepositMessage(Status.VERIFY_ERROR, "核对失败", "请将钞票取出", "确认无误后重新放入"));
            messageMap.put(Status.OVER_LIMIT_ERROR,
                new DepositMessage(Status.OVER_LIMIT_ERROR, "存款金额超出单张银行卡上限", "请更换银行卡或咨询管理员"));
            messageMap.put(Status.UNKNOWN,
                new DepositMessage(Status.UNKNOWN, "发生未知错误", "请联系管理员解决"));
    }

    public static AbstractMessage get(Status status) {
        return messageMap.get(status);
    }

    @Override
    public String debugStatus() {
        for (Status value : Status.values()) {
            if (value.code.equals(getStatus())) {
                return value.name();
            }
        }
        return "UNKNOWN";
    }

    public enum Status {
        OK(0),
        FORMAT_ERROR(1),
        DATABASE_ERROR(2),
        DIGITAL_ERROR(3),
        VERIFY_ERROR(4),
        OVER_LIMIT_ERROR(5),
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
