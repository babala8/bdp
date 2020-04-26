package com.zjft.microservice.treasurybrain.task.po;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TaskCheckPO {
    private String id;

    private String taskNo;

    private String customerNo;

    private String containerNo;

    private BigDecimal amount;

    private String currencyCode;

    private Integer currencyType;

    private Integer denomination;

    private String opNo;

    private String opName;

    private String opTime;

    private String clearMachineNo;

    private BigDecimal cashShortOver;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getTaskNo() {
        return taskNo;
    }

    public void setTaskNo(String taskNo) {
        this.taskNo = taskNo == null ? null : taskNo.trim();
    }

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo == null ? null : customerNo.trim();
    }

    public String getContainerNo() {
        return containerNo;
    }

    public void setContainerNo(String containerNo) {
        this.containerNo = containerNo == null ? null : containerNo.trim();
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode == null ? null : currencyCode.trim();
    }

    public Integer getCurrencyType() {
        return currencyType;
    }

    public void setCurrencyType(Integer currencyType) {
        this.currencyType = currencyType;
    }

    public Integer getDenomination() {
        return denomination;
    }

    public void setDenomination(Integer denomination) {
        this.denomination = denomination;
    }

    public String getOpNo() {
        return opNo;
    }

    public void setOpNo(String opNo) {
        this.opNo = opNo == null ? null : opNo.trim();
    }

    public String getOpTime() {
        return opTime;
    }

    public void setOpTime(String opTime) {
        this.opTime = opTime == null ? null : opTime.trim();
    }

    public String getClearMachineNo() {
        return clearMachineNo;
    }

    public void setClearMachineNo(String clearMachineNo) {
        this.clearMachineNo = clearMachineNo == null ? null : clearMachineNo.trim();
    }

    public BigDecimal getCashShortOver() {
        return cashShortOver;
    }

    public void setCashShortOver(BigDecimal cashShortOver) {
        this.cashShortOver = cashShortOver;
    }
}
