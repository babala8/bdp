package com.zjft.microservice.treasurybrain.channelcenter.dto;

import com.zjft.microservice.treasurybrain.common.domain.DevCatalogTable;
import com.zjft.microservice.treasurybrain.common.domain.DevVendorTable;
import com.zjft.microservice.treasurybrain.common.dto.DTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class DevTypeDTO extends DTO {

	private static final long serialVersionUID = 1L;

	private String no;

	private String name;

	private String devVendor;

	private String devCatalog;

	private String spec;

	private String weight;

	private String watt;

	private String cashType;

	private DevCatalogTable devCatalogTable;

	private DevVendorTable devVendorTable;
}
