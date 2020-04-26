package com.zjft.microservice.treasurybrain.business.domain;

import lombok.Data;

@Data
public class LineRunDevDetailKey {

    private String devNo;

    private String lineRunNo;


    public void setDevNo(String devNo) {
        this.devNo = devNo == null ? null : devNo.trim();
    }

    public void setLineRunNo(String lineRunNo) {
        this.lineRunNo = lineRunNo == null ? null : lineRunNo.trim();
    }

}
