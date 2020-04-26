package com.zjft.microservice.treasurybrain.business.domain;

import java.io.Serializable;

public class TSData implements Serializable {
	private static final long serialVersionUID = 1L;
	
	double dataValue;
	String dateTime;
	
	public double getDataValue() {
		return dataValue;
	}
	public void setDataValue(double dataValue) {
		this.dataValue = dataValue;
	}
	public String getDateTime() {
		return dateTime;
	}
	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}

}
