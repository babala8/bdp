package com.zjft.microservice.treasurybrain.channelcenter.dto;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class DevStatusDTO extends DTO {

	private String devNo;

	private String statusLastTime;

	private String devRunStatus;

	private String devModStatus;

	private String devCashboxStatus;

	private String idcDeviceStatus;

	private String chkDeviceStatus;

	private String pbkDeviceStatus;

	private String pinDeviceStatus;

	private String siuDeviceStatus;

	private String depDeviceStatus;

	private String camDeviceStatus;

	private String cimDeviceStatus;

	private String cdmDeviceStatus;

	private String sprDeviceStatus;

	private String rprDeviceStatus;

	private String jprDeviceStatus;

	private String ttuDeviceStatus;

	private String rprPaperStatus;

	private String jprPaperStatus;

	private String cdmShutterStatus;

	private String cdmStackerStatus;

	private String cdmInputOutputStatus;

	private String cimShutterStatus;

	private String cimEscrowStatus;

	private String cimInputOutputStatus;

	private String idcCaptureBinCount;

	private Integer availableAmt;

	private String lastAddnoteDate;

	private String lastAddnoteTime;

}