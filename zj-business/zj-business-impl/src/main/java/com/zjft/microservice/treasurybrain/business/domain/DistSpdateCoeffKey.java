package com.zjft.microservice.treasurybrain.business.domain;

public class DistSpdateCoeffKey {
    private String startDate;

    private String endDate;

    private Integer districtNo;

    private String clrCenterNo;

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate == null ? null : startDate.trim();
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate == null ? null : endDate.trim();
    }

    public Integer getDistrictNo() {
        return districtNo;
    }

    public void setDistrictNo(Integer districtNo) {
        this.districtNo = districtNo;
    }

    public String getClrCenterNo() {
        return clrCenterNo;
    }

    public void setClrCenterNo(String clrCenterNo) {
        this.clrCenterNo = clrCenterNo == null ? null : clrCenterNo.trim();
    }
}
