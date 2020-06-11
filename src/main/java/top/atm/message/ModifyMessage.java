package top.atm.message;

import java.util.Hashtable;
import java.util.Map;

/**
 * @author taifu
 */

public class ModifyMessage extends AbstractMessage {
    private static final Map<Status, AbstractMessage> messageMap;

    private ModifyMessage(Status status, String... messages) {
        super(status.getCode(), messages);
    }

    static {
        messageMap = new Hashtable<>();

        messageMap.put(Status.OK,
            new ModifyMessage(Status.OK));
        messageMap.put(Status.PASSWORD_NOT_MATCH_ERROR,
            new ModifyMessage(Status.PASSWORD_NOT_MATCH_ERROR, "修改失败", "原密码错误"));
        messageMap.put(Status.DATABASE_ERROR,
            new ModifyMessage(Status.DATABASE_ERROR, "数据库访问异常", "请稍后重试或寻找管理员"));
        messageMap.put(Status.UNKNOWN,
            new ModifyMessage(Status.UNKNOWN, "发生未知错误", "请联系管理员解决"));
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
        PASSWORD_NOT_MATCH_ERROR(1),
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
