package top.atm.message;

import java.io.Serializable;

/**
 * 信息类的基类, 用于方便 MVC 三层之间方便传递信息, 所有信息类都应当继承该类
 * 将其设置为抽象类, 防止其被实例化, 实例化该类是无意义的
 *
 * @author taifu
 */

@SuppressWarnings ("unused")
public abstract class BaseMessage implements Serializable {
    private String message;

    public BaseMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
