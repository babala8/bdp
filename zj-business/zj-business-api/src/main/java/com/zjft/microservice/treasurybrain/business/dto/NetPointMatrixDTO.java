package com.zjft.microservice.treasurybrain.business.dto;

import lombok.Data;

@Data
public class NetPointMatrixDTO {
	private Integer distance;

	private Integer timeCost;

	private String note;

	private int count;

	private String clrCenterNo;

	private Integer dataType;

    private String startPointNo;

    private String endPointNo;

    private Integer type;

    private Integer tactic;

    public String getStartPointNo() {
        return startPointNo;
    }

    public void setStartPointNo(String startPointNo) {
        this.startPointNo = startPointNo == null ? null : startPointNo.trim();
    }

    public String getEndPointNo() {
        return endPointNo;
    }

    public void setEndPointNo(String endPointNo) {
        this.endPointNo = endPointNo == null ? null : endPointNo.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getTactic() {
        return tactic;
    }

    public void setTactic(Integer tactic) {
        this.tactic = tactic;
    }
}
