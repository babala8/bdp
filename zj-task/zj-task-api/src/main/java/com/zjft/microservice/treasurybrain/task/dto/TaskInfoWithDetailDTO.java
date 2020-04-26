package com.zjft.microservice.treasurybrain.task.dto;

import com.zjft.microservice.treasurybrain.common.util.RetCodeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author liuyuan
 * @since 2019/8/7 11:05
 */
@Data
@ApiModel(value = "任务及详情信息",description = "任务及详情信息")
public class TaskInfoWithDetailDTO extends TaskInfoDTO{

	@ApiModelProperty(value = "详情列表")
	private List<TaskDetailDTO> detailList;

	private Object element;

	public void setElement(Object element) {
		this.element = element;
	}

	public TaskInfoWithDetailDTO() {
	}

	public TaskInfoWithDetailDTO(RetCodeEnum re) {
		super(re);
	}
}
