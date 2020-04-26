package com.zjft.microservice.treasurybrain.linecenter.dto;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 节假日线路管理接口类
 *
 * @author 吴朋
 * @since 2019/9/25 21:40
 */
@Data
@ApiModel(value = "节假日线路管理信息", description = "节假日线路管理信息")
public class HolidayLineRunDTO extends DTO {
	@ApiModelProperty(value = "批量修改起始时间")
	private String startDate;

	@ApiModelProperty(value = "批量修改截至时间")
	private String endDate;

	@ApiModelProperty(value = "线路运行图编号")
	private String lineWorkId;

	@ApiModelProperty(value = "网点号")
	private String customerNo;

	@ApiModelProperty(value = "网点号")
	private Integer customerType;

	@ApiModelProperty(value = "顺序")
	private Integer sortNo;

	private List<LineInfoDTO> lineInfoDTOList;
}
