package com.zjft.microservice.treasurybrain.task.domain;

import lombok.Data;

import java.math.BigDecimal;


@Data
public class TaskInOut {
    private String id;

    private String taskNo;

    private String customerNo;

    private Integer direction;

    private Integer containerType;

    private BigDecimal amount;

    private Integer currencyType;

    private String currencyCode;

    private Integer denomination;

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

    public Integer getDirection() {
        return direction;
    }

    public void setDirection(Integer direction) {
        this.direction = direction;
    }

    public Integer getContainerType() {
        return containerType;
    }

    public void setContainerType(Integer containerType) {
        this.containerType = containerType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getCurrencyType() {
        return currencyType;
    }

    public void setCurrencyType(Integer currencyType) {
        this.currencyType = currencyType;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode == null ? null : currencyCode.trim();
    }

    public Integer getDenomination() {
        return denomination;
    }

    public void setDenomination(Integer denomination) {
        this.denomination = denomination;
    }
}
