package top.atm.bean;

import java.io.Serializable;

/**
 * 银行 JavaBean
 *
 * @author taifu
 */

@SuppressWarnings ("unused")
public class Bank implements Serializable {
    private Integer id;
    private String name;

    public Bank() {}

    public Bank(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Bank{" +
            "id=" + id +
            ", name='" + name + '\'' +
            '}';
    }
}
