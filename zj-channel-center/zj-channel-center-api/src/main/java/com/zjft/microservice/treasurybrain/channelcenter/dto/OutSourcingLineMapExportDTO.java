package com.zjft.microservice.treasurybrain.channelcenter.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author liuyuan
 * @since 2019/9/26 14:18
 */
@Data
@ApiModel(value = "押运人员排班导出" ,description = "押运人员排班导出")
public class OutSourcingLineMapExportDTO {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(name = "导出排班记录编号列表",value = "导出排班记录编号列表")
	private List<String> lineRunNos;

//	@ApiModelProperty(value = "导出文件类型",name = "导出文件类型")
//	private String fileType;
}
