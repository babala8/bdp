package com.zjft.microservice.treasurybrain.linecenter.domain;

import lombok.Data;

@Data
public class AddnotesPlanDetailDO {
    private String addnotesPlanNo;

    private String devNo;

    public void setAddnotesPlanNo(String addnotesPlanNo) {
        this.addnotesPlanNo = addnotesPlanNo == null ? null : addnotesPlanNo.trim();
    }

    public void setDevNo(String devNo) {
        this.devNo = devNo == null ? null : devNo.trim();
    }
}
