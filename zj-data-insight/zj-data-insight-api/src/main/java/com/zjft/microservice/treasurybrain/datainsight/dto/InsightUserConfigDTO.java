package com.zjft.microservice.treasurybrain.datainsight.dto;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.util.RetCodeEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author 杨光
 */
@ToString
@Getter
@Setter
public class InsightUserConfigDTO extends DTO {
	private static final long serialVersionUID = 1L;

	private String username;
	private String userDefaultView;
	private Integer subjectId;


	public InsightUserConfigDTO() {
	}

	public InsightUserConfigDTO(RetCodeEnum e) {
		super(e);
	}
}
