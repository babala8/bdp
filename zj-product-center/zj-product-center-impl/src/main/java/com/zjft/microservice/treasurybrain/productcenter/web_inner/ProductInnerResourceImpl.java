package com.zjft.microservice.treasurybrain.productcenter.web_inner;

import com.zjft.microservice.treasurybrain.productcenter.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Slf4j
public class ProductInnerResourceImpl implements ProductInnerResource {
	@Resource
	private ProductService productService;

	@Override
	public int qryExist(String productNo) {
		return productService.qryExist(productNo);
	}
}
