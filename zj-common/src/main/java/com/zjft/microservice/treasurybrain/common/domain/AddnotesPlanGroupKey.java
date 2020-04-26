package com.zjft.microservice.treasurybrain.common.domain;

public class AddnotesPlanGroupKey {
    private String addnotesPlanNo;

    private String groupNo;

    private Integer clrTimeInterval;

    public String getAddnotesPlanNo() {
        return addnotesPlanNo;
    }

    public void setAddnotesPlanNo(String addnotesPlanNo) {
        this.addnotesPlanNo = addnotesPlanNo == null ? null : addnotesPlanNo.trim();
    }

    public String getGroupNo() {
        return groupNo;
    }

    public void setGroupNo(String groupNo) {
        this.groupNo = groupNo == null ? null : groupNo.trim();
    }

    public Integer getClrTimeInterval() {
        return clrTimeInterval;
    }

    public void setClrTimeInterval(Integer clrTimeInterval) {
        this.clrTimeInterval = clrTimeInterval;
    }
}
