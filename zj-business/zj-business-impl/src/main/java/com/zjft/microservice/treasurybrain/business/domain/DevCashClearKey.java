package com.zjft.microservice.treasurybrain.business.domain;

public class DevCashClearKey {
    private String devNo;

    private String addcashId;

    public String getDevNo() {
        return devNo;
    }

    public void setDevNo(String devNo) {
        this.devNo = devNo == null ? null : devNo.trim();
    }

    public String getAddcashId() {
        return addcashId;
    }

    public void setAddcashId(String addcashId) {
        this.addcashId = addcashId == null ? null : addcashId.trim();
    }
}
