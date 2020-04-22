package top.atm.message;

/**
 * @author taifu
 */

@SuppressWarnings ("unused")
public class DepositMessage extends BaseMessage {
    private Status status;

    public DepositMessage(String message, Status status) {
        super(message);
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    /**
     * 存款结果的枚举类型
     */
    public enum Status {
        OK
    }
}
