package com.zjft.microservice.treasurybrain.storage.domain;

import com.zjft.microservice.treasurybrain.storage.po.StorageEntityPropertyPO;
import com.zjft.microservice.treasurybrain.storage.po.StorageEntityTablePO;
import lombok.Data;

import java.util.List;

/**
 * @author liuyuan
 * @since 2019/9/3 10:48
 */
@Data
public class StorageEntityTableDO extends StorageEntityTablePO {

	private String productName;

	private List<StorageEntityPropertyPO> entityDetail;

}
