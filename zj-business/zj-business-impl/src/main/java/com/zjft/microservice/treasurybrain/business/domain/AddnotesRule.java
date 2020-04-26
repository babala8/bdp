package com.zjft.microservice.treasurybrain.business.domain;

import lombok.Data;

@Data
public class AddnotesRule {

	private String ruleId;

	private String ruleDesp;

	private String ruleGenOp;

	private String ruleGenDate;

	private String ruleGenTime;

	private String ruleGenOpName;

	private String sysOrgName;

	private Double addnotesCoeff;

	private Double quotaRatio;

	private Double addnotesPeriod;

	public void setRuleId(String ruleId) {
		this.ruleId = ruleId == null ? null : ruleId.trim();
	}

	public void setRuleDesp(String ruleDesp) {
		this.ruleDesp = ruleDesp == null ? null : ruleDesp.trim();
	}

	public void setRuleGenOp(String ruleGenOp) {
		this.ruleGenOp = ruleGenOp == null ? null : ruleGenOp.trim();
	}

	public void setRuleGenDate(String ruleGenDate) {
		this.ruleGenDate = ruleGenDate == null ? null : ruleGenDate.trim();
	}

	public void setRuleGenTime(String ruleGenTime) {
		this.ruleGenTime = ruleGenTime == null ? null : ruleGenTime.trim();
	}

}
