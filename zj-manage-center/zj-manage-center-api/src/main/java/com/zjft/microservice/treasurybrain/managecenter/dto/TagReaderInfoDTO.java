package com.zjft.microservice.treasurybrain.managecenter.dto;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 标签读写器（手持机）DTO
 */
@Data
@ApiModel(value = "手持机信息", description = "手持机信息")
public class TagReaderInfoDTO extends DTO {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "手持机编号 -- 服务站编号+2位顺序号", example = "01021101")
	private String tagReaderNo;

	@ApiModelProperty(value = "手持机类型 -- 1：固定式 2：手持式 3：发卡器", example = "1")
	private Integer readerType;

	@ApiModelProperty(value = "安放地址", example = "四川省成都市武侯区益州大道中段")
	private String location;

	@ApiModelProperty(value = "是否有GPS模块 -- 0：无 1：有", example = "0")
	private Integer whetherGpsModule;

	@ApiModelProperty(value = "是否有GPRS模块 -- 0：无 1：有", example = "0")
	private Integer whetherGprsModule;

	@ApiModelProperty(value = "是否有WIFI模块 -- 0：无 1：有", example = "0")
	private Integer whetherWifiModule;

	@ApiModelProperty(value = "是否有条码模块 -- 0：无 1：有", example = "0")
	private Integer whetherBarcodeModule;

	@ApiModelProperty(value = "GPRS信息量阈值 -- 取值范围[0,31]", example = "0")
	private Integer gprsVolThreshold;

	@ApiModelProperty(value = "GPRS信息量阈值最大值", example = "31")
	private Integer gprsVolMaxThreshold;

	@ApiModelProperty(value = "GPRS信息量阈值最小值", example = "0")
	private Integer gprsVolMinThreshold;

	@ApiModelProperty(value = "GPRS信息量下浮偏移量", example = "1")
	private Integer gprsVolOffset;

	@ApiModelProperty(value = "定时任务时间间隔(分钟)", example = "10")
	private Integer timingTaskInterval;

	@ApiModelProperty(value = "GPRS包月流量(MB)", example = "100")
	private Integer gprsMonthlyFreeFlow;

	@ApiModelProperty(value = "设备主密钥", example = "123456")
	private String tmk;

	@ApiModelProperty(value = "备注", example = "备注信息")
	private String note;

	@ApiModelProperty(value = "状态 -- 1：未领用 2：已申请 3：已领用 4：已遗失 5：已损坏", example = "1")
	private Integer status;

	@ApiModelProperty(value = "清机中心编号", example = "028001")
	private String clrCenterNo;

	@ApiModelProperty(value = "SIM卡手机号码", example = "13288888888")
	private String simNumberNo;
}
