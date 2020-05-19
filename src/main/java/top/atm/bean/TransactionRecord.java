package top.atm.bean;

import java.math.BigDecimal;

public class TransactionRecord {
    private Long id;
    private String sourceAccountId;
    private String targetAccountId;
    private Integer type;
    private BigDecimal amount;
    private String remarks;

    public TransactionRecord(Long id, String sourceAccountId,
                             String targetAccountId, Integer type,
                             BigDecimal amount, String remarks) {
        this.id = id;
        this.sourceAccountId = sourceAccountId;
        this.targetAccountId = targetAccountId;
        this.type = type;
        this.amount = amount;
        this.remarks = remarks;
    }

    public TransactionRecord() {
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
}
