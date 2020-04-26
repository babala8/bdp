package com.zjft.microservice.treasurybrain.datainsight.dto;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SubjectDTO extends DTO {
	
	private static final long serialVersionUID = 1L;
	
    private Integer subjectId;
	private String subjectName;
	private String subjectDesc;
}
