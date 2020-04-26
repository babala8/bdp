package com.zjft.microservice.treasurybrain.common.domain;

import lombok.Data;

@Data
public class AddnotesPlanDetailKey {
    private String addnotesPlanNo;

    private String devNo;

    public void setAddnotesPlanNo(String addnotesPlanNo) {
        this.addnotesPlanNo = addnotesPlanNo == null ? null : addnotesPlanNo.trim();
    }

    public void setDevNo(String devNo) {
        this.devNo = devNo == null ? null : devNo.trim();
    }
}
