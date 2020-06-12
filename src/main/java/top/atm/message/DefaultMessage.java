package top.atm.message;

import top.atm.constant.ErrorCode;

import java.io.Serializable;

/**
 * 信息类的基类, 用于方便 MVC 三层之间方便传递信息, 所有信息类都应当继承该类
 * 将其设置为抽象类, 防止其被实例化, 实例化该类是无意义的
 * <p>
 * 推荐复杂的类型使用枚举完成
 *
 * @author taifu
 */

@SuppressWarnings ("unused")
public class DefaultMessage implements Serializable {
    private final ErrorCode code;
    private final String[] messages;

    public DefaultMessage(ErrorCode code, String... messages) {
        this.code = code;
        this.messages = new String[code.getMessages().length + messages.length];
        System.arraycopy(code.getMessages(), 0, this.messages, 0, code.getMessages().length);
        System.arraycopy(messages, 0, this.messages, code.getMessages().length, messages.length);
    }

    public ErrorCode getCode() {
        return code;
    }

    public String[] getMessages() {
        return messages;
    }
}
