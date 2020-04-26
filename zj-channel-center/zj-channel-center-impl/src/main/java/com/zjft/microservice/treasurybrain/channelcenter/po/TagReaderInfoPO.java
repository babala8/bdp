package com.zjft.microservice.treasurybrain.channelcenter.po;

import lombok.Data;

@Data
public class TagReaderInfoPO {

	private String tagReaderNo;

	private Integer readerType;

	private String location;

	private Integer whetherGpsModule;

	private Integer whetherGprsModule;

	private Integer whetherWifiModule;

	private Integer whetherBarcodeModule;

	private Integer gprsVolThreshold;

	private Integer gprsVolMaxThreshold;

	private Integer gprsVolMinThreshold;

	private Integer gprsVolOffset;

	private Integer timingTaskInterval;

	private Integer gprsMonthlyFreeFlow;

	private String tmk;

	private String note;

	private Integer status;

	private String clrCenterNo;

	private String simNumberNo;
}
