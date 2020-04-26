package com.zjft.microservice.treasurybrain.param.dto;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import lombok.Data;

@Data
public class KeyDTO extends DTO {
private static final long serialVersionUID = 1L;
	private String clrCenterNo;
	private String eventNo;
	private String eventName;
	private String eventDesp;
	private Integer isValid;
	@Override
	public String toString() {
		return "KeyDTO [clrCenterNo=" + clrCenterNo + ", eventNo=" + eventNo
				+ ", eventName=" + eventName + ", eventDesp=" + eventDesp
				+ ", isValid=" + isValid +  "]";
	}

	public String getClrCenterNo() {
		return clrCenterNo;
	}
	public void setClrCenterNo(String clrCenterNo) {
		this.clrCenterNo = clrCenterNo;
	}
	public String getEventNo() {
		return eventNo;
	}
	public void setEventNo(String eventNo) {
		this.eventNo = eventNo;
	}
	public String getEventName() {
		return eventName;
	}
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}
	public String getEventDesp() {
		return eventDesp;
	}
	public void setEventDesp(String eventDesp) {
		this.eventDesp = eventDesp;
	}
	public Integer getIsValid() {
		return isValid;
	}
	public void setIsValid(Integer isValid) {
		this.isValid = isValid;
	}

}
