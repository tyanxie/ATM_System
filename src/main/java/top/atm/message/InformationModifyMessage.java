package top.atm.message;

import java.util.Hashtable;
import java.util.Map;

/**
 * @author taifu
 */

public class InformationModifyMessage extends AbstractMessage {
    private static final Map<Status, AbstractMessage> messageMap;

    static {
        messageMap = new Hashtable<>();

        messageMap.put(Status.OK, new InformationModifyMessage(Status.OK));
        messageMap.put(Status.INPUT_ERROR,
            new InformationModifyMessage(Status.INPUT_ERROR, "输入有误", "请务必确保每个选项都有输入"));
        messageMap.put(Status.DATABASE_ERROR,
            new InformationModifyMessage(Status.DATABASE_ERROR, "数据库访问异常", "请稍后重试或寻找管理员"));
        messageMap.put(Status.UNKNOWN,
            new InformationModifyMessage(Status.UNKNOWN, "发生未知错误", "请联系管理员解决"));
    }

    public static AbstractMessage get(Status status) {
        return messageMap.get(status);
    }

    private InformationModifyMessage(Status status, String... messages) {
        super(status.getCode(), messages);
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
        INPUT_ERROR(1),
        DATABASE_ERROR(2),
        UNKNOWN(3);

        private final Integer code;

        Status(Integer code) {
            this.code = code;
        }

        public Integer getCode() {
            return code;
        }
    }
}
