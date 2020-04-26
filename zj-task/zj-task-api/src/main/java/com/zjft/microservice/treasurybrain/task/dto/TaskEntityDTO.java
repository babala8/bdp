package com.zjft.microservice.treasurybrain.task.dto;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.util.RetCodeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author liuyuan
 * @since 2019/8/7 10:22
 */
@Data
@ApiModel("任务内物品信息")
public class TaskEntityDTO extends DTO {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "主键编号")
	private String Id;

	@ApiModelProperty(value = "任务编号")
	private String taskNo;

	@ApiModelProperty(value = "容器编号")
	private String entityNo;

	@ApiModelProperty(value = "容器类型")
	private String productNo;

	@ApiModelProperty(value = "金额")
	private BigDecimal amount;

	@ApiModelProperty(value = "父容器")
	private String parentEntity;

	@ApiModelProperty(value = "调运方向")
	private Integer direction;

	@ApiModelProperty(value = "是否为最下级容器:0-不是 1-是")
	private Integer leafFlag;

	@ApiModelProperty(value = "备注")
	private String note;

	@ApiModelProperty(value = "1：长寄库 2：短寄库")
	private Integer depositType;

	@ApiModelProperty(value = "服务对象编号")
	private String customerNo;

	List<TaskEntityDetailDTO> taskEntityDetailDTOList;


	public TaskEntityDTO(){super();}

	public TaskEntityDTO(RetCodeEnum retCodeEnum){super(retCodeEnum);}

	public TaskEntityDTO(String retCode,String retMsg){super(retCode,retMsg);}

}
