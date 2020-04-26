package com.zjft.microservice.treasurybrain.channelcenter.dto;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author 崔耀中
 * @since 2019-10-10
 */

@Data
@ApiModel(value = "清分机信息", description = "清分机信息")
public class DevCountDTO extends DTO {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "设备编号",example = "10001")
	private String devNo;

	@ApiModelProperty(value = "设备类别 1：清分机 2：点钞机",example = "1")
	private Integer devType;

	@ApiModelProperty(value = "清机中心编号",example = "028001")
	private String clrCenterNo;

	@ApiModelProperty(value = "设备状态 1：启用 2：停用",example = "1")
	private Integer devStatus;

	@ApiModelProperty(value = "设备型号",example = "A1")
	private String devModel;

	@ApiModelProperty(value = "设备规格",example = "3寸")
	private String devStandards;

	@ApiModelProperty(value = "投入使用日期 YYYY-MM-DD",example = "2019-10-10")
	private String userDate;

	@ApiModelProperty(value = "摆放位置",example = "金库")
	private String location;

	@ApiModelProperty(value = "备注",example = "测试")
	private String note;

	@ApiModelProperty(value = "设备ip",example = "127.0.0.1")
	private String ip;

	@ApiModelProperty(value = "批次",example = "1")
	private Integer batch;

	@ApiModelProperty(value = "清机中心名称",example = "现金中心1")
	private String centerName;

	@ApiModelProperty(value = "批次数量",example = "1")
	private Integer countNum;

	@ApiModelProperty(value = "工作状态")
	private int workStatus;

	@ApiModelProperty(value = "批次数量")
	private int batchCount;



	private List<CountTaskInfoDTO> doingList;

	private List<CountTaskInfoDTO> queueList;

	private List<CountTaskInfoDTO> doneList;
}
