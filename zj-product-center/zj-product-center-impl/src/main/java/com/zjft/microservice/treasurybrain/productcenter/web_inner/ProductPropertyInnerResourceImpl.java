package com.zjft.microservice.treasurybrain.productcenter.web_inner;

import com.zjft.microservice.treasurybrain.productcenter.service.ProductPropertyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@Slf4j
public class ProductPropertyInnerResourceImpl implements ProductPropertyInnerResource {
	@Resource
	private ProductPropertyService productPropertyService;

	@Override
	public int insert(Map<String, Object> productPropertyMap) {
		return productPropertyService.insert2(productPropertyMap);
	}

	@Override
	public int selectByNoAndValue(String propertyNo, String propertyValue) {
		return productPropertyService.selectByNoAndValue(propertyNo,propertyValue);
	}
}
