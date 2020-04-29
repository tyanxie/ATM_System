package top.atm.message;

/**
 * @author taifu
 */

public class WithdrawMessage extends AbstractMessage {
    public WithdrawMessage(Status status, String... messages) {
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
