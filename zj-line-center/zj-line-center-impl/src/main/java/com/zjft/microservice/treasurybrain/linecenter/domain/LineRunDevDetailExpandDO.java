package com.zjft.microservice.treasurybrain.linecenter.domain;

import lombok.Data;

@Data
public class LineRunDevDetailExpandDO extends LineRunDevDetailDO {
    private String orgNo;

    private String orgName;

    private String clrTimeInterval;

    private String arrivalTime;


    public String getOrgNo() {
        return orgNo;
    }

    public void setOrgNo(String orgNo) {
        this.orgNo = orgNo == null ? null : orgNo.trim();
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName == null ? null : orgName.trim();
    }

    public String getClrTimeInterval() {
        return clrTimeInterval;
    }

    public void setClrTimeInterval(String clrTimeInterval) {
        this.clrTimeInterval = clrTimeInterval == null ? null : clrTimeInterval.trim();
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime == null ? null : arrivalTime.trim();
    }
}
