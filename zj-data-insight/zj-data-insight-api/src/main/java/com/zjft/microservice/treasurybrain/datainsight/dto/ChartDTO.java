package com.zjft.microservice.treasurybrain.datainsight.dto;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ChartDTO extends DTO {
	
	private static final long serialVersionUID = 1L;
	
	private String chartId;
	private String chartName;
	private String chartTypeName;
	private Integer chartStatus;
    private Integer subjectId;
	private String subjectName;
	private String chartDef;
	private String chartOption;
	private String icon;
	private String chartDesc;
	private String creator;
	private String createTime;
	private String lastModTime;
	private String lastModOp;
	

	
}
