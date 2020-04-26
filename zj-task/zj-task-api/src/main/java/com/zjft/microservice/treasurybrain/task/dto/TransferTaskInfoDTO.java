package com.zjft.microservice.treasurybrain.task.dto;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.util.RetCodeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author geruilian
 * @since 2019/8/23
 */
@Data
@ApiModel(value = "任务信息",description = "任务信息")
public class TransferTaskInfoDTO extends DTO {

	@ApiModelProperty(value = "任务单编号")
	private String taskNo;

	@ApiModelProperty(value = "任务单类型" ,
			example = "1-加钞任务单 2-网点调缴任务单 3-上门收款任务单 4-人行调缴任务单 5-同业现金任务单")
	private int taskType;

	@ApiModelProperty(value = "计划完成时间")
	private String planFinishDate;

	@ApiModelProperty(value = "服务对象编号")
	private String customerNo;

	@ApiModelProperty(value = "服务对象类型",
	       example = "1-人民银行 2-金交所客户 3-网点 4-临售客户 5-对公客户 6-自助机具 7-现金中心")
	private int customerType;

	@ApiModelProperty(value = "操作")
	private String operateType;

	@ApiModelProperty(value = "所属金库编号")
	private String clrCenterNo;

	@ApiModelProperty(value = "押运人员1编号")
	private String opNo1;

	@ApiModelProperty(value = "押运人员1姓名")
	private String opName1;

	@ApiModelProperty(value = "押运人员2编号")
	private String opNo2;

	@ApiModelProperty(value = "押运人员2姓名")
	private String opName2;

	@ApiModelProperty(value = "创建人员编号")
	private String createOpNo;

	@ApiModelProperty(value = "创建人员姓名")
	private String createOpName;

	@ApiModelProperty(value = "创建时间")
	private String createTime;

	@ApiModelProperty(value = "备注")
	private String note;

	@ApiModelProperty(value = "加钞计划编号")
	private String addnotesPlanNo;

	@ApiModelProperty(value = "紧急标志")
	private int urgentFlag;

	@ApiModelProperty(value = "笼车编号")
	private String shelfNo;

	@ApiModelProperty(value = "推送消息类型")
	private String type;

	@ApiModelProperty(value = "订单产品信息")
	private List<TaskProductDTO> taskProductDTOList;

	@ApiModelProperty(value = "箱子信息")
	private List<TaskEntityDTO> taskEntityDTOList;

	public TransferTaskInfoDTO() {
	}

	public TransferTaskInfoDTO(RetCodeEnum re) {
		super(re);
	}
}
