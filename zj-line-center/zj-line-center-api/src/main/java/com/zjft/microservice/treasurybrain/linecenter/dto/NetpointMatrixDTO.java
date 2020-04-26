package com.zjft.microservice.treasurybrain.linecenter.dto;

import com.zjft.microservice.treasurybrain.common.dto.DTO;

public class NetpointMatrixDTO extends DTO {
	private static final long serialVersionUID = 1L;
	
	private String startPointNo;
	
	private String startPointName;
	
	private String endPointNo;
	
	private String endPointName;
	
	private int type;
	
	private int tactic;
	
	private int distance;
	
	private int timeCost;
	
	private String note;

	public String getStartPointNo() {
		return startPointNo;
	}

	public void setStartPointNo(String startPointNo) {
		this.startPointNo = startPointNo;
	}

	public String getStartPointName() {
		return startPointName;
	}

	public void setStartPointName(String startPointName) {
		this.startPointName = startPointName;
	}

	public String getEndPointNo() {
		return endPointNo;
	}

	public void setEndPointNo(String endPointNo) {
		this.endPointNo = endPointNo;
	}

	public String getEndPointName() {
		return endPointName;
	}

	public void setEndPointName(String endPointName) {
		this.endPointName = endPointName;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getTactic() {
		return tactic;
	}

	public void setTactic(int tactic) {
		this.tactic = tactic;
	}

	public int getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}

	public int getTimeCost() {
		return timeCost;
	}

	public void setTimeCost(int timeCost) {
		this.timeCost = timeCost;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
	
}
