package com.zjft.microservice.treasurybrain.productcenter.domain;

import com.zjft.microservice.treasurybrain.productcenter.po.ServiceTablePO;
import lombok.Data;

@Data
public class ServiceDO extends ServiceTablePO {

	String customerName;
}
