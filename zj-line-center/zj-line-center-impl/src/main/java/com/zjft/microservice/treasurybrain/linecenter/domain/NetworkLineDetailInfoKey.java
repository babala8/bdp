package com.zjft.microservice.treasurybrain.linecenter.domain;

import lombok.Data;

@Data
public class NetworkLineDetailInfoKey {
    private String networkLineNo;

    private Short theDay;

    public String getNetworkLineNo() {
        return networkLineNo;
    }

    public void setNetworkLineNo(String networkLineNo) {
        this.networkLineNo = networkLineNo == null ? null : networkLineNo.trim();
    }

    public Short getTheDay() {
        return theDay;
    }

    public void setTheDay(Short theDay) {
        this.theDay = theDay;
    }
}
