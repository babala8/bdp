package com.zjft.microservice.treasurybrain.tauro.dto;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.util.RetCodeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "手持机领用", description = "手持机领用")
public class TagReaderUseDTO extends DTO {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "编号 -- 8位日期+6位顺序号（审核、归还时必填）", example = "20190809000001")
	private String tagReaderUseNo;

	@ApiModelProperty(value = "读写器编号 -- 服务站编号+2位顺序号（审核、归还时必填）", example = "01021101")
	private String tagReaderNo;

	@ApiModelProperty(value = "申请人员", example = "zhangsan")
	private String requestOpNo;

	@ApiModelProperty(value = "申请人员名称", example = "张三")
	private String requestOpName;

	@ApiModelProperty(value = "申请日期", example = "2019-08-12")
	private String requestDate;

	@ApiModelProperty(value = "申请时间", example = "16:00:00")
	private String requestTime;

	@ApiModelProperty(value = "审核结果 -- 1：通过 2：拒绝（审核时必填）", example = "1")
	private Integer reviewResult;

	@ApiModelProperty(value = "拒绝原因（审核时可选填）", example = "拒绝时填写实际原因")
	private String rejectReason;

	@ApiModelProperty(value = "审核（发放）人员", example = "lisi")
	private String grantOpNo;

	@ApiModelProperty(value = "审核（发放）人员名称", example = "李四")
	private String grantOpName;

	@ApiModelProperty(value = "审核（发放）日期", example = "2019-08-12")
	private String grantDate;

	@ApiModelProperty(value = "审核（发放）时间", example = "17:00:00")
	private String grantTime;

	@ApiModelProperty(value = "签收人员（归还时必填）", example = "rlge")
	private String signOpNo;

	@ApiModelProperty(value = "签收人员名称", example = "geruilian")
	private String signOpName;

	@ApiModelProperty(value = "归还人员（归还时必填）", example = "zhangsan")
	private String returnOpNo;

	@ApiModelProperty(value = "归还人员名称", example = "张三")
	private String returnOpName;

	@ApiModelProperty(value = "归还日期", example = "2019-06-25")
	private String returnDate;

	@ApiModelProperty(value = "归还时间", example = "11:36:27")
	private String returnTime;

	@ApiModelProperty(value = "读写器领用状态 -- 1：待审核 2：已审核（通过） 3：已审核（拒绝） 4：已归还", example = "1")
	private Integer tagReaderUseStatus;

	@ApiModelProperty(value = "紧急领用标志 -- 0：正常 1：紧急", example = "1")
	private String crashFlag;

	public TagReaderUseDTO() {
	}

	public TagReaderUseDTO(RetCodeEnum re) {
		super(re);
	}
}
