package top.atm.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author BlessingChimes
 */

@SuppressWarnings ("unused")
public class TransactionRecord implements Serializable {
    private Long id;
    private String sourceAccountId;
    private String targetAccountId;
    private Integer type;
    private BigDecimal amount;
    private String remarks;
    private Date occurTime;

    /**
     * 存款, 取款, 转账, 收款所对应的 type 的值
     */
    public static final Integer DEPOSIT = 0;
    public static final Integer WITHDRAW = 1;
    public static final Integer TRANSFER = 2;
    public static final Integer CREDIT = 3;

    public String getTypeInString() {
        if (TransactionRecord.DEPOSIT.equals(type)) {
            return "存款";
        } else if (TransactionRecord.WITHDRAW.equals(type)) {
            return "取款";
        } else if (TransactionRecord.TRANSFER.equals(type)) {
            return "转账";
        } else if (TransactionRecord.CREDIT.equals(type)) {
            return "收款";
        }
        return "未知";
    }

    public void swapSourceAndTarget() {
        String temp = this.sourceAccountId;
        this.sourceAccountId = this.targetAccountId;
        this.targetAccountId = temp;
    }

    public TransactionRecord() {}

    public TransactionRecord(Long id, String sourceAccountId, String targetAccountId, Integer type, BigDecimal amount, String remarks) {
        this(id, sourceAccountId, targetAccountId, type, amount, remarks, null);
    }

    public TransactionRecord(Long id, String sourceAccountId, String targetAccountId, Integer type, BigDecimal amount, String remarks, Date occurTime) {
        this.id = id;
        this.sourceAccountId = sourceAccountId;
        this.targetAccountId = targetAccountId;
        this.type = type;
        this.amount = amount;
        this.remarks = remarks;
        this.occurTime = occurTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSourceAccountId() {
        return sourceAccountId;
    }

    public void setSourceAccountId(String sourceAccountId) {
        this.sourceAccountId = sourceAccountId;
    }

    public String getTargetAccountId() {
        return targetAccountId;
    }

    public void setTargetAccountId(String targetAccountId) {
        this.targetAccountId = targetAccountId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Date getOccurTime() {
        return occurTime;
    }

    public void setOccurTime(Date occurTime) {
        this.occurTime = occurTime;
    }

    @Override
    public String toString() {
        return "TransactionRecord{" +
            "id=" + id +
            ", sourceAccountId='" + sourceAccountId + '\'' +
            ", targetAccountId='" + targetAccountId + '\'' +
            ", type=" + type +
            ", amount=" + amount +
            ", remarks='" + remarks + '\'' +
            ", occurTime=" + occurTime +
            '}';
    }
}
