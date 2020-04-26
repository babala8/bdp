package com.zjft.microservice.treasurybrain.channelcenter.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 葛瑞莲
 * @since 2019/9/21
 */
@Data
@ApiModel(value = "外包人员信息", description = "外包人员信息")
public class OutsourcingDTO {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "人员编号", example = "A1001")
	private String no;

	@ApiModelProperty(value = "人员姓名", example = "张非")
	private String name;

	@ApiModelProperty(value = "岗位（0-外包人员；1-安保人员；2-车辆驾驶员）", example = "1")
	private Integer post;

	@ApiModelProperty(value = "年龄", example = "29")
	private Integer age;

	@ApiModelProperty(value = "家庭住址", example = "上海市黄浦区重庆路518号")
	private String familyAddr;

	@ApiModelProperty(value = "户籍地址", example = "南京市建邺区江东中路318号")
	private String residenceAddr;

	@ApiModelProperty(value = "联系方式", example = "18200066688")
	private String mobile;

}
