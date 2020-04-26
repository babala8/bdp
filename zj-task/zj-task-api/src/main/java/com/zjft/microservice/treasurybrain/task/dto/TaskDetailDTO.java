package com.zjft.microservice.treasurybrain.task.dto;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.util.RetCodeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author liuyuan
 * @since 2019/8/7 10:01
 */
@Data
@ApiModel(value = "任务详情",description = "任务详情")
public class TaskDetailDTO extends DTO {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "任务单编号")
	private String taskNo;

	@ApiModelProperty(value = "服务对象编号")
	private String customerNo;

	@ApiModelProperty(value = "服务对象类型")
	private Integer customerType;

	@ApiModelProperty(value = "服务对象类型名称")
	private String customerTypeDesp;

	@ApiModelProperty(value = "总金额")
	private double amount;

	@ApiModelProperty(value = "调运顺序")
	private Integer sort;

	@ApiModelProperty(value = "地址")
	private String address;

	@ApiModelProperty(value = "状态",example = "1-未执行 2-已备钞 3-已送达 4-已回收 5-已确认回收物品")
	private Integer status;

	@ApiModelProperty(value = "配钞物品列表",required = true)
	private List<TaskEntityDTO> containersForCustomer;

	@ApiModelProperty(value = "清点物品列表",required = true)
	private List<TaskEntityDTO> containersFromCustomer;

	private String devTypeName;

	private String devCatalogName;

	private String devVendorName;

	private String devAddress;

	private Integer addnotesAmt;

	public TaskDetailDTO(){
		super();
	}


	public TaskDetailDTO(RetCodeEnum retCodeEnum){
		super(retCodeEnum);
	}


	public TaskDetailDTO(String retcode,String retMsg){
		super(retcode,retMsg);
	}
}

