package com.zjft.microservice.treasurybrain.storage.dto;

import lombok.Data;

/**
 * @author 常 健
 * @since 2020/1/2
 */
@Data
public class ShelfDTO {

	private String shelfNo;

	private Integer type;

	private Integer status;

	private String clrCenterNo;
}
