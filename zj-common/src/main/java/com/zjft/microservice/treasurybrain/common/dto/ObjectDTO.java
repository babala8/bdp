package com.zjft.microservice.treasurybrain.common.dto;

import com.zjft.microservice.treasurybrain.common.util.RetCodeEnum;
import lombok.Data;

@Data
public class ObjectDTO extends DTO {
	
	private static final long serialVersionUID = 1L;
	
	private Object element;

	public ObjectDTO() {
		super();
	}

	public ObjectDTO(RetCodeEnum e) {
		super(e);
	}

	public ObjectDTO(String retCode, String retMsg) {
		super(retCode, retMsg);
	}

	public Object getElement() {
		return element;
	}

	public void setElement(Object element) {
		this.element = element;
	}
}
