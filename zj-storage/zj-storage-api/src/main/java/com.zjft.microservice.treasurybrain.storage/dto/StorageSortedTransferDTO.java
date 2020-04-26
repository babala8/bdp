package com.zjft.microservice.treasurybrain.storage.dto;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.util.RetCodeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author liuyuan
 * @since 2019/8/26 17:09
 */


@Data
@ApiModel(value = "仓库调出")
public class StorageSortedTransferDTO extends DTO {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "记录编号",example = "UUID")
	private String recordNo;

	@ApiModelProperty(value = "金库编号",example = "028001")
	private String clrCenterNo;

	@ApiModelProperty(value = "金库名称",example = "工商银行成都分行金库")
	private String clrCenterName;

	@ApiModelProperty(value = "交出人员1",example = "zhangsan")
	private String delivererNo1;

	@ApiModelProperty(value = "交出人员1姓名",example = "张三")
	private String delivererName1;

	@ApiModelProperty(value = "交出人员2",example = "lisi")
	private String delivererNo2;

	@ApiModelProperty(value = "交出人员2姓名",example = "李四")
	private String delivererName2;

	@ApiModelProperty(value = "接收人员1",example = "wangwu")
	private String receiverNo1;

	@ApiModelProperty(value = "接收人员1姓名",example = "王五")
	private String receiverName1;

	@ApiModelProperty(value = "接收人员2",example = "jialiu")
	private String receiverNo2;

	@ApiModelProperty(value = "接收人员2姓名",example = "贾六")
	private String receiverName2;

	@ApiModelProperty(value = "交接日期" , notes="yyyy-MM-dd",example = "2019-09-02")
	private String transferDate;

	@ApiModelProperty(value = "交接时间" , notes = "HH:mm:ss",example = "16:23:22")
	private String transferTime;

	@ApiModelProperty(value = "起点",notes = "预留字段",example = "库房1")
	private String startPoint;

	@ApiModelProperty(value = "终点",notes = "预留字段",example = "清分区1")
	private String endPoint;

	@ApiModelProperty(value = "调入调出物品列表")
	private List<StorageSortedTransferEntityDTO> storageEntities;

	public StorageSortedTransferDTO() {
	}

	public StorageSortedTransferDTO(RetCodeEnum re) {
		super(re);
	}
}
