package com.zjft.microservice.treasurybrain.storage.po;

import lombok.Data;

/**
 * @author 崔耀中
 * @since 2020-03-02
 */
@Data
public class StorageEntityPropertyPO {

	private String id;

	private String key;

	private Integer propertyType;

	private String name;

	private String value;

}
