package com.zjft.microservice.treasurybrain.linecenter.domain;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class LineWorkDO {
    private String lineWorkId;

    private String lineNo;

    private String opNo1;

    private String opNo2;

    private String carNo;

    private BigDecimal planDistance;

    private BigDecimal planTimeCost;

    private String startLng;

    private String startLat;

    private String endLng;

    private String endLat;

    private String theYearMonth;

    private String theDay;

    private int customerCount;

    private String lineName;

	private List<LineScheduleDO> detailList;

    public String getLineWorkId() {
        return lineWorkId;
    }

    public void setLineWorkId(String lineWorkId) {
        this.lineWorkId = lineWorkId == null ? null : lineWorkId.trim();
    }

    public String getLineNo() {
        return lineNo;
    }

    public void setLineNo(String lineNo) {
        this.lineNo = lineNo == null ? null : lineNo.trim();
    }

    public String getOpNo1() {
        return opNo1;
    }

    public void setOpNo1(String opNo1) {
        this.opNo1 = opNo1 == null ? null : opNo1.trim();
    }

    public String getOpNo2() {
        return opNo2;
    }

    public void setOpNo2(String opNo2) {
        this.opNo2 = opNo2 == null ? null : opNo2.trim();
    }

    public String getCarNo() {
        return carNo;
    }

    public void setCarNo(String carNo) {
        this.carNo = carNo == null ? null : carNo.trim();
    }

    public BigDecimal getPlanDistance() {
        return planDistance;
    }

    public void setPlanDistance(BigDecimal planDistance) {
        this.planDistance = planDistance;
    }

    public BigDecimal getPlanTimeCost() {
        return planTimeCost;
    }

    public void setPlanTimeCost(BigDecimal planTimeCost) {
        this.planTimeCost = planTimeCost;
    }

    public String getStartLng() {
        return startLng;
    }

    public void setStartLng(String startLng) {
        this.startLng = startLng == null ? null : startLng.trim();
    }

    public String getStartLat() {
        return startLat;
    }

    public void setStartLat(String startLat) {
        this.startLat = startLat == null ? null : startLat.trim();
    }

    public String getEndLng() {
        return endLng;
    }

    public void setEndLng(String endLng) {
        this.endLng = endLng == null ? null : endLng.trim();
    }

    public String getEndLat() {
        return endLat;
    }

    public void setEndLat(String endLat) {
        this.endLat = endLat == null ? null : endLat.trim();
    }

    public String getTheYearMonth() {
        return theYearMonth;
    }

    public void setTheYearMonth(String theYearMonth) {
        this.theYearMonth = theYearMonth == null ? null : theYearMonth.trim();
    }

    public String getTheDay() {
        return theDay;
    }

    public void setTheDay(String theDay) {
        this.theDay = theDay == null ? null : theDay.trim();
    }
}
