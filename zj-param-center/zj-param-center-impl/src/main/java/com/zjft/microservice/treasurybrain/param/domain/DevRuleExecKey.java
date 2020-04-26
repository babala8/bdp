package com.zjft.microservice.treasurybrain.param.domain;

public class DevRuleExecKey {

    private String devNo;

    private String startDate;

    private String endDate;

    public String getDevNo() {
        return devNo;
    }

    public void setDevNo(String devNo) {
        this.devNo = devNo == null ? null : devNo.trim();
    }

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

}
