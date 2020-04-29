package top.atm.message;

/**
 * 转账所使用的消息类
 *
 * @author taifu
 */

public class TransferMessage extends AbstractMessage {
    public TransferMessage(Status status, String... messages) {
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
