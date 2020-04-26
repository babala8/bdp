package com.zjft.microservice.treasurybrain.managecenter.dto;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 钞箱信息DTO
 *
 * @author liuyuan
 * @since 2019/6/11 16:38
 */

@Data
@ApiModel(value = "钞箱信息", description = "钞箱信息")
public class CassetteInfoDTO extends DTO {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "钞箱管理号", example = "051003281088800001")
	private String cassetteNo;

	@ApiModelProperty(value = "银行钞箱编号", example = "051003281088800001")
	private String cassetteNoBank;

	@ApiModelProperty(value = "钞箱序列号", example = "051003281088800001")
	private String cassetteSerial;

	@ApiModelProperty(value = "标签编号", example = "C1001")
	private String tagTid;

	@ApiModelProperty(value = "钞箱类型 -- 0：循环箱 1：回收箱 2：废钞箱 3：存款箱 4：取款箱", example = "0")
	private int cassetteType;

	@ApiModelProperty(value = "钞箱面值 -- 0：100；1：50；2：20；3：10；4：5", example = "0")
	private int cassetteNoteValue;

	@ApiModelProperty(value = "钞箱币种 -- CNY：人民币 USD：美元", example = "CNY")
	private String cassetteCurrency;

	@ApiModelProperty(value = "钞箱最大张数", example = "8000")
	private int cassetteMaxNotesnum;

	@ApiModelProperty(value = "钞箱品牌编号", example = "10001")
	private int cassetteVendor;

	@ApiModelProperty(value = "状态 -- -1：未启用 0：启用 1：已装填 20：出库待审核 2：已出库/已交接 30：虚拟签收待审核 3：已虚拟签收 4：已落地 50：虚拟回收待审核 5：已虚拟回收 60：入库待审核；6：已入库/已交接 7：纸币已回收 8：损坏 9：维修 10：报废", example = "-1")
	private int status;

	@ApiModelProperty(value = "钞箱品牌名称", example = "Hitachi（日立）")
	private String cassetteVendorName;

}

