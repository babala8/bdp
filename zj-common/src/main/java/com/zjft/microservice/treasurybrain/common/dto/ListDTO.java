package com.zjft.microservice.treasurybrain.common.dto;

import com.zjft.microservice.treasurybrain.common.util.RetCodeEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 杨光
 */
@ToString
@Getter
@Setter
public class ListDTO<E> extends DTO {

	private static final long serialVersionUID = 1L;

	private List<E> retList = new ArrayList<E>();

	public ListDTO() {
		super();
	}

	public ListDTO(String retCode, String retMsg) {
		super(retCode, retMsg);
	}

	public ListDTO(RetCodeEnum re) {
		super(re);
	}

	public List<E> getRetList() {
		return retList;
	}

	public void setRetList(List<E> retList) {
		this.retList = retList;
	}

	public void addAll(List<E> list) {
		for(E e : list) {
			addItem(e);
		}
	}

	public void addItem(E e) {
		this.retList.add(e);
	}

}
