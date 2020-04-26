package com.zjft.microservice.treasurybrain.channelcenter.dto;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 钞袋信息接口类
 *
 * @author liuyuan
 * @since 2019/6/12 18:51
 */

@Data
@ApiModel(value = "钞袋信息", description = "钞袋信息")
public class CassetteBagInfoDTO extends DTO {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "钞箱袋管理编号", example = "051003281998800001")
	private String bagNo;

	@ApiModelProperty(value = "钞箱袋银行编号", allowEmptyValue = true, example = "051003281998800001")
	private String bagNoBank;

	@ApiModelProperty(value = "钞箱袋序列号", allowEmptyValue = true, example = "051003281998800001")
	private String bagSerial;

	@ApiModelProperty(value = "标签编号", example = "B1001")
	private String tagTid;

	@ApiModelProperty(value = "钞箱袋品牌 -- 0：默认", example = "0")
	private int bagVendor;

	@ApiModelProperty(value = "钞箱袋规格:最多容纳钞箱的个数", example = "1")
	private int bagSize;

	@ApiModelProperty(value = "状态 -- -1：未启用 0：启用", example = "-1")
	private int status;

}
