package com.zjft.microservice.treasurybrain.param.domain;

public class DevChsKeyEventKey {
    private String eventNo;

    private String clrCenterNo;

    public String getEventNo() {
        return eventNo;
    }

    public void setEventNo(String eventNo) {
        this.eventNo = eventNo == null ? null : eventNo.trim();
    }

    public String getClrCenterNo() {
        return clrCenterNo;
    }

    public void setClrCenterNo(String clrCenterNo) {
        this.clrCenterNo = clrCenterNo == null ? null : clrCenterNo.trim();
    }
}
