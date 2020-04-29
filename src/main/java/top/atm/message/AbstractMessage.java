package top.atm.message;

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
public abstract class AbstractMessage implements Serializable {
    private final String[] messages;

    /**
     * 状态码, 需要注意的是, 我们默认 0 为成功状态
     */
    private final Integer status;

    public AbstractMessage(Integer status, String... messages) {
        this.status = status;
        this.messages = messages;
    }

    /**
     * 返回状态码是否为成功
     *
     * @return 状态码为 0 则表示成功, 返回 true, 否则返回 false
     */
    public boolean isOK() {
        return status == 0;
    }

    /**
     * 返回状态码是否为错误
     *
     * @return 状态码为 0 则表示成功, 返回 false, 否则返回 true
     */
    public boolean isError() {
        return status != 0;
    }

    /**
     * 使用该方法返回对于状态码的描述, 可以方便调用者查看以及输出
     * 理论上应当包含一个 switch 语句
     */
    public abstract String debugStatus();

    public String[] getMessages() {
        return messages;
    }

    public Integer getStatus() {
        return status;
    }
}
