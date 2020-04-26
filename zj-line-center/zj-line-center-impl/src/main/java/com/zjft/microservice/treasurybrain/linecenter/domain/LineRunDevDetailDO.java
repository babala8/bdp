package com.zjft.microservice.treasurybrain.linecenter.domain;

import lombok.Data;

@Data
public class LineRunDevDetailDO {

    private String devNo;

    private String lineRunNo;


    public void setDevNo(String devNo) {
        this.devNo = devNo == null ? null : devNo.trim();
    }

    public void setLineRunNo(String lineRunNo) {
        this.lineRunNo = lineRunNo == null ? null : lineRunNo.trim();
    }

}
