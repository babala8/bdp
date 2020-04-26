package com.zjft.microservice.treasurybrain.business.domain;

import lombok.Data;

@Data
public class AddnoteLineDetailInfoKey {
    private String routeNo;

    private Short theDay;

    public String getRouteNo() {
        return routeNo;
    }

    public void setRouteNo(String routeNo) {
        this.routeNo = routeNo == null ? null : routeNo.trim();
    }

    public Short getTheDay() {
        return theDay;
    }

    public void setTheDay(Short theDay) {
        this.theDay = theDay;
    }
}
