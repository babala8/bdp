package com.zjft.microservice.treasurybrain.securitycenter.domain;

import lombok.Data;

@Data
public class SampleMessageInfo {

	private String warnMessageId;

	private String warnMessageDate;

	private String warnMessageTime;

	private String warnMessageDetailInfo;

	private String warnMessageHandleStatus;

	private String warnMessageHandleUserNo;

	private String warnMessageHandleUserName;

	private String warnMessageHandleDate;

	private String warnMessageHandleResult;

	private String warnMessageType;

	private String warnMessageInfo;

	private String warnMessageToUserNo;

	private String warnMessageToRoleNo;

	private String clrCenterNo;

	private String centerName;

	public String getWarnMessageId() {
		return warnMessageId;
	}

	public void setWarnMessageId(String warnMessageId) {
		this.warnMessageId = warnMessageId;
	}

	public String getWarnMessageDate() {
		return warnMessageDate;
	}

	public void setWarnMessageDate(String warnMessageDate) {
		this.warnMessageDate = warnMessageDate;
	}

	public String getWarnMessageTime() {
		return warnMessageTime;
	}

	public void setWarnMessageTime(String warnMessageTime) {
		this.warnMessageTime = warnMessageTime;
	}

	public String getWarnMessageDetailInfo() {
		return warnMessageDetailInfo;
	}

	public void setWarnMessageDetailInfo(String warnMessageDetailInfo) {
		this.warnMessageDetailInfo = warnMessageDetailInfo;
	}

	public String getWarnMessageHandleStatus() {
		return warnMessageHandleStatus;
	}

	public void setWarnMessageHandleStatus(String warnMessageHandleStatus) {
		this.warnMessageHandleStatus = warnMessageHandleStatus;
	}

	public String getWarnMessageHandleUserNo() {
		return warnMessageHandleUserNo;
	}

	public void setWarnMessageHandleUserNo(String warnMessageHandleUserNo) {
		this.warnMessageHandleUserNo = warnMessageHandleUserNo;
	}

	public String getWarnMessageHandleUserName() {
		return warnMessageHandleUserName;
	}

	public void setWarnMessageHandleUserName(String warnMessageHandleUserName) {
		this.warnMessageHandleUserName = warnMessageHandleUserName;
	}

	public String getWarnMessageHandleDate() {
		return warnMessageHandleDate;
	}

	public void setWarnMessageHandleDate(String warnMessageHandleDate) {
		this.warnMessageHandleDate = warnMessageHandleDate;
	}

	public String getWarnMessageHandleResult() {
		return warnMessageHandleResult;
	}

	public void setWarnMessageHandleResult(String warnMessageHandleResult) {
		this.warnMessageHandleResult = warnMessageHandleResult;
	}

	public String getWarnMessageType() {
		return warnMessageType;
	}

	public void setWarnMessageType(String warnMessageType) {
		this.warnMessageType = warnMessageType;
	}

	public String getWarnMessageInfo() {
		return warnMessageInfo;
	}

	public void setWarnMessageInfo(String warnMessageInfo) {
		this.warnMessageInfo = warnMessageInfo;
	}

	public String getWarnMessageToUserNo() {
		return warnMessageToUserNo;
	}

	public void setWarnMessageToUserNo(String warnMessageToUserNo) {
		this.warnMessageToUserNo = warnMessageToUserNo;
	}

	public String getWarnMessageToRoleNo() {
		return warnMessageToRoleNo;
	}

	public void setWarnMessageToRoleNo(String warnMessageToRoleNo) {
		this.warnMessageToRoleNo = warnMessageToRoleNo;
	}
}
