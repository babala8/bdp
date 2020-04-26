package com.zjft.microservice.treasurybrain.param.dto;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.util.RetCodeEnum;

public class SysParamDTO extends DTO {

	private String logicId;

	private Integer catalog;

	private String paramName;

	private String paramValue;

	private String statement;

	private String description;

	public SysParamDTO() {
	}

	public SysParamDTO(RetCodeEnum re) {
		super(re);
	}

	public String getLogicId() {
		return logicId;
	}

	public void setLogicId(String logicId) {
		this.logicId = logicId == null ? null : logicId.trim();
	}

	public Integer getCatalog() {
		return catalog;
	}

	public void setCatalog(Integer catalog) {
		this.catalog = catalog;
	}

	public String getParamName() {
		return paramName;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName == null ? null : paramName.trim();
	}

	public String getParamValue() {
		return paramValue;
	}

	public void setParamValue(String paramValue) {
		this.paramValue = paramValue == null ? null : paramValue.trim();
	}

	public String getStatement() {
		return statement;
	}

	public void setStatement(String statement) {
		this.statement = statement == null ? null : statement.trim();
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description == null ? null : description.trim();
	}

}
