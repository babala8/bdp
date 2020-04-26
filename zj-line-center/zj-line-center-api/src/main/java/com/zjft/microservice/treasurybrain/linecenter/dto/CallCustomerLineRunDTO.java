package com.zjft.microservice.treasurybrain.linecenter.dto;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author liuyuan
 * @since 2019/9/23 13:39
 */
@Data
@ApiModel(value = "上门收款线路安排信息",description = "上门收款线路安排信息")
public class CallCustomerLineRunDTO extends DTO {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(name = "编号",value = "编号")
	private String lineRunNo;

	@ApiModelProperty(name = "线路编号",value = "线路编号")
	private String lineNo;

	@ApiModelProperty(name = "线路名称",value = "线路名称")
	private String lineName;

	@ApiModelProperty(value = "年月",name = "年月")
	private String theYearMonth;

	@ApiModelProperty(value = "日",name = "日")
	private String theDay;

	@ApiModelProperty(value = "用户数",name = "用户数")
	private Integer callCustomerCount;

	@ApiModelProperty(value = "详情列表",name = "详情列表")
	private List<CallCustomerLineRunDetailDTO> detailList;

	public List<CallCustomerLineRunDetailDTO> getDetailList(){
		return this.detailList;
	}

	public void setDetailDTOS(List<CallCustomerLineRunDetailDTO> detailList){
		this.detailList = detailList;
	}
}
