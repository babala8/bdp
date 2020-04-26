package com.zjft.microservice.treasurybrain.datainsight.dto;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.util.RetCodeEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class DetailQueryDTO extends DTO {

	private static final long serialVersionUID = 1L;

	private String title;
	private boolean pdftag;
	private String pdfFile;
	private boolean htmltag;
	private String htmlFile;
	private boolean xlstag;
	private String xlsFile;

	public DetailQueryDTO() {
		super();
	}

	public DetailQueryDTO(RetCodeEnum e) {
		super(e);
	}

	public DetailQueryDTO(String retCode, String retMsg) {
		super(retCode, retMsg);
	}


}
