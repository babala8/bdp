package com.zjft.microservice.treasurybrain.linecenter.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class LineInfoDTO {
	private String lineNo;

	private List<String> addDateList = new ArrayList<>();
}
