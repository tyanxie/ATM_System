package top.atm.bean;

import java.math.BigDecimal;

/**
 * 账户 JavaBean
 *
 * @author taifu
 */

@SuppressWarnings ("unused")
public class Account {
    private String id;
    private Long userId;
    private Integer bankId;
    private String password;
    private BigDecimal balance;
    private Boolean freeze;

    public Account() {}

    public Account(String id, Long userId, Integer bankId, String password, BigDecimal balance, Boolean freeze) {
        this.id = id;
        this.userId = userId;
        this.bankId = bankId;
        this.password = password;
        this.balance = balance;
        this.freeze = freeze;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getBankId() {
        return bankId;
    }

    public void setBankId(Integer bankId) {
        this.bankId = bankId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Boolean getFreeze() {
        return freeze;
    }

    public void setFreeze(Boolean freeze) {
        this.freeze = freeze;
    }

    @Override
    public String toString() {
        return "Account{" +
            "id='" + id + '\'' +
            ", userId=" + userId +
            ", bankId=" + bankId +
            ", password='" + password + '\'' +
            ", balance=" + balance +
            ", freeze=" + freeze +
            '}';
    }
}
