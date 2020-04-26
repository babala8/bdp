package com.zjft.microservice.treasurybrain.linecenter.domain;

import lombok.Data;

@Data
public class NetworkLineRunOrgDetailKey {

    private String networkNo;

    private String networkLineRunNo;


    public void setNetworkNo(String networkNo) {
        this.networkNo = networkNo == null ? null : networkNo.trim();
    }

    public void setNetworkLineRunNo(String networkLineRunNo) {
        this.networkLineRunNo = networkLineRunNo == null ? null : networkLineRunNo.trim();
    }

}
