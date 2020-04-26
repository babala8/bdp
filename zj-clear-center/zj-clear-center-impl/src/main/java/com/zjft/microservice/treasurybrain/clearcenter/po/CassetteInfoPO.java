package com.zjft.microservice.treasurybrain.clearcenter.po;

import lombok.Data;

/**
 * 钞箱信息
 *
 * @author liuyuan
 * @since 2019/6/17 15:04
 */

@Data
public class CassetteInfoPO {
	private String cassetteNo;

	private String cassetteNoBank;

	private String cassetteSerial;

	private String tagTid;

	private int cassetteType;

	private int cassetteNoteValue;

	private String cassetteCurrency;

	private int cassetteMaxNotesnum;

	private int cassetteVendor;

	private int status;

}
