package com.zjft.microservice.treasurybrain.linecenter.domain;

import lombok.Data;

import java.math.BigDecimal;
@Data
public class LineScheduleDO {
    private String lineNo;

    private Integer sortNo;

    private String customerNo;

    private Integer customerType;

    private String address;

    private String lng;

    private String lat;

    private String arrivalTime;

    private String lineWorkId;

    private String theYearMonth;

    private String theDay;

    private String clrTimeInterval;

    private String customerName;

    private String orgNo;

    private String orgName;

    public String getLineNo() {
        return lineNo;
    }

    public void setLineNo(String lineNo) {
        this.lineNo = lineNo == null ? null : lineNo.trim();
    }

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo == null ? null : customerNo.trim();
    }


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng == null ? null : lng.trim();
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat == null ? null : lat.trim();
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime == null ? null : arrivalTime.trim();
    }

    public String getLineWorkId() {
        return lineWorkId;
    }

    public void setLineWorkId(String lineWorkId) {
        this.lineWorkId = lineWorkId == null ? null : lineWorkId.trim();
    }

    public String getTheYearMonth() {
        return theYearMonth;
    }

    public void setTheYearMonth(String theYearMonth) {
        this.theYearMonth = theYearMonth == null ? null : theYearMonth.trim();
    }

    public String getTheDay() {
        return theDay;
    }

    public void setTheDay(String theDay) {
        this.theDay = theDay == null ? null : theDay.trim();
    }

    public String getClrTimeInterval() {
        return clrTimeInterval;
    }

    public void setClrTimeInterval(String clrTimeInterval) {
        this.clrTimeInterval = clrTimeInterval == null ? null : clrTimeInterval.trim();
    }
}
