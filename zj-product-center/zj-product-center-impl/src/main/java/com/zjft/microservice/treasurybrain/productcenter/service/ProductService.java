package com.zjft.microservice.treasurybrain.productcenter.service;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.ListDTO;
import com.zjft.microservice.treasurybrain.productcenter.dto.ProductDetailDTO;
import com.zjft.microservice.treasurybrain.productcenter.dto.ProductPropertyDetailDTO;

import java.util.Map;

/**
 * @author 常 健
 * @since 2019/09/03
 */
public interface ProductService {

	DTO addGoodsBase(ProductPropertyDetailDTO productPropertyDetailDTO);

	ListDTO qryGoodsBaseByPage(Map<String, Object> paramMap);

	DTO modGoodsBase(ProductPropertyDetailDTO productPropertyDetailDTO);

	DTO delGoodsBase(String productNo);

	ProductDetailDTO qryGoodsBaseDetail(String productNo);

	ListDTO qryUpperInfo();

	int qryExist(String goodsNo);
}
