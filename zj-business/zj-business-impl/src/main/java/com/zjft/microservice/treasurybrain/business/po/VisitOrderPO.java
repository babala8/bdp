package com.zjft.microservice.treasurybrain.business.po;

import lombok.Data;

import java.math.BigDecimal;
@Data
public class VisitOrderPO {
    private String customerNumber;

    private String orderDate;

    private BigDecimal orderTimePeriod;

    private String orderTime;

    private String note;

    public String getId() {
        return customerNumber;
    }

    public void setId(String id) {
        this.customerNumber = id == null ? null : id.trim();
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate == null ? null : orderDate.trim();
    }

    public BigDecimal getOrderTimePeriod() {
        return orderTimePeriod;
    }

    public void setOrderTimePeriod(BigDecimal orderTimePeriod) {
        this.orderTimePeriod = orderTimePeriod;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime == null ? null : orderTime.trim();
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note == null ? null : note.trim();
    }
}
