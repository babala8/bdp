package com.zjft.microservice.treasurybrain.linecenter.domain;

import com.zjft.microservice.treasurybrain.common.domain.AddnotesPlanGroupKey;

public class AddnotesPlanGroupDO extends AddnotesPlanGroupKey {

	private String lineName;
	public String getLineName(){return this.lineName;}
	public void setLineName(String lineName){ this.lineName = lineName;}

    private Integer planDevCnt;

    private Integer planNetpntCnt;

    private Integer planDistance;

    private Integer planTimeCost;

    public Integer getPlanDevCnt() {
        return planDevCnt;
    }

    public void setPlanDevCnt(Integer planDevCnt) {
        this.planDevCnt = planDevCnt;
    }

    public Integer getPlanNetpntCnt() {
        return planNetpntCnt;
    }

    public void setPlanNetpntCnt(Integer planNetpntCnt) {
        this.planNetpntCnt = planNetpntCnt;
    }

    public Integer getPlanDistance() {
        return planDistance;
    }

    public void setPlanDistance(Integer planDistance) {
        this.planDistance = planDistance;
    }

    public Integer getPlanTimeCost() {
        return planTimeCost;
    }

    public void setPlanTimeCost(Integer planTimeCost) {
        this.planTimeCost = planTimeCost;
    }
}
