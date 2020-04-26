package com.zjft.microservice.treasurybrain.productcenter.web_inner;

import com.zjft.microservice.treasurybrain.productcenter.service.ProductPropertyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@Slf4j
public class ProductPropertyKeyInnerResourceIpml implements ProductPropertyKeyInnerResource {
	@Resource
	private ProductPropertyService productPropertyService;

	@Override
	public int insert(Map<String, Object> productPropertyKeyMap) {
		return productPropertyService.insert(productPropertyKeyMap);
	}

	@Override
	public int selectCountByGoodsNo(String productNo, String propertyName) {
		return productPropertyService.selectCountByGoodsNo(productNo,propertyName);
	}

	@Override
	public String selectPropertyNoByGoodsNo(String productNo, String propertyName) {
		return productPropertyService.selectPropertyNoByGoodsNo(productNo,propertyName);
	}

	@Override
	public String selectMaxNo() {
		return productPropertyService.selectMaxNo();
	}
}
