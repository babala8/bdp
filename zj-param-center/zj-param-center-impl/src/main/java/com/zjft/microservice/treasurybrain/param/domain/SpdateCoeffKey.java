package com.zjft.microservice.treasurybrain.param.domain;

public class SpdateCoeffKey {

    private String startDate;

    private String endDate;

    private String clrCenterNo;

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate == null ? null : startDate.trim();
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate == null ? null : endDate.trim();
    }

    public String getClrCenterNo() {
        return clrCenterNo;
    }

    public void setClrCenterNo(String clrCenterNo) {
        this.clrCenterNo = clrCenterNo == null ? null : clrCenterNo.trim();
    }

}
