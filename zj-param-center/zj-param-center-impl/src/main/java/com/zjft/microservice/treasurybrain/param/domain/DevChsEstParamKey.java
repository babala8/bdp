package com.zjft.microservice.treasurybrain.param.domain;

public class DevChsEstParamKey {
    private String estParamNo;

    private String clrCenterNo;

    public String getEstParamNo() {
        return estParamNo;
    }

    public void setEstParamNo(String estParamNo) {
        this.estParamNo = estParamNo == null ? null : estParamNo.trim();
    }

    public String getClrCenterNo() {
        return clrCenterNo;
    }

    public void setClrCenterNo(String clrCenterNo) {
        this.clrCenterNo = clrCenterNo == null ? null : clrCenterNo.trim();
    }
}
