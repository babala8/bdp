package com.zjft.microservice.treasurybrain.storage.domain;

import com.zjft.microservice.treasurybrain.storage.po.StorageCheckPO;
import lombok.Data;

/**
 * @author 常 健
 * @since 2019/10/12
 */
@Data
public class StorageCheckDO extends StorageCheckPO {

	private String centerName;

}
