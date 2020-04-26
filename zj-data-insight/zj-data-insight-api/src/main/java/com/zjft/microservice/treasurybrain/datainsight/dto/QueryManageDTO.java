package com.zjft.microservice.treasurybrain.datainsight.dto;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class QueryManageDTO extends DTO {

	private String id;
	private String name;
	private String def;
	private String status;
	private String groupName;
	private String creator;
	private String createTime;
	private String lastestModOp;
	private String lastestModTime;

}
