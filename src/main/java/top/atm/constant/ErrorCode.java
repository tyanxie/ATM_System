package top.atm.constant;

import top.atm.message.DefaultMessage;

/**
 * @author taifu
 */

public enum ErrorCode {
    OK("00000"),
    DATABASE_ERROR("A0100", "数据库访问异常", "请稍后重试或寻找管理员"),

    DEPOSIT_FORMAT_ERROR("B0101", "存款金额错误"),
    DEPOSIT_DIGITAL_ERROR("B0102", "存款金额不得小于 0"),
    DEPOSIT_VERIFY_ERROR("B0103", "核对失败", "请将钞票取出", "确认无误后重新放入"),
    DEPOSIT_OVER_LIMIT_ERROR("B0104", "存款金额超出单张银行卡上限", "请更换银行卡或咨询管理员"),

    WITHDRAW_FORMAT_ERROR("C0101", "取款金额有误"),
    WITHDRAW_INSUFFICIENT_BALANCE_ERROR("C0102", "账户余额不足", "取款失败"),


    TRANSFER_FORMAT_ERROR("D0101", "转账金额有误"),
    TRANSFER_INSUFFICIENT_BALANCE_ERROR("D0102", "账户余额不足", "转账失败"),
    TRANSFER_TARGET_NOT_EXIST_ERROR("D0103", "目标账户不存在"),
    TRANSFER_SELF_ERROR("D0104", "不能给自己转账"),

    MODIFY_PASSWORD_NOT_MATCH_ERROR("E0101", "修改失败", "原密码错误"),
    MODIFY_INPUT_ERROR("E0102", "输入有误", "请务必确保每个选项都有输入"),

    UNKNOWN("FFFFF", "发生未知错误", "请联系管理员解决");

    private final String code;
    private final String[] messages;

    ErrorCode(String code, String... messages) {
        this.code = code;
        this.messages = messages;
    }

    public String getCode() {
        return code;
    }

    public String[] getMessages() {
        return messages;
    }

    public DefaultMessage buildMessage(String... messages) {
        return new DefaultMessage(this, messages);
    }

    public boolean isError() {
        return this != OK;
    }
}
