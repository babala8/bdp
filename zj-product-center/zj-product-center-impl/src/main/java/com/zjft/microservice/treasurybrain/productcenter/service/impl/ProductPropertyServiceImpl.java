package com.zjft.microservice.treasurybrain.productcenter.service.impl;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.ListDTO;
import com.zjft.microservice.treasurybrain.common.util.RetCodeEnum;
import com.zjft.microservice.treasurybrain.productcenter.dto.ProductPropertyDTO;
import com.zjft.microservice.treasurybrain.productcenter.dto.ProductPropertyValueInfoDTO;
import com.zjft.microservice.treasurybrain.productcenter.po.ProductPropertyKeyPO;
import com.zjft.microservice.treasurybrain.productcenter.po.ProductPropertyValuePO;
import com.zjft.microservice.treasurybrain.productcenter.repository.ProductPropertyKeyMapper;
import com.zjft.microservice.treasurybrain.productcenter.repository.ProductPropertyValueMapper;
import com.zjft.microservice.treasurybrain.productcenter.service.ProductPropertyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author 常 健
 * @since 2019/09/04
 */
@Slf4j
@Service
public class ProductPropertyServiceImpl implements ProductPropertyService {

	@Resource
	private ProductPropertyKeyMapper productPropertyKeyMapper;

	@Resource
	private ProductPropertyValueMapper productPropertyValueMapper;

	@Override
	public DTO addGoodsProperty(ProductPropertyDTO productPropertyDTO) {
		String propertyNo = productPropertyDTO.getPropertyNo();
		int x = productPropertyKeyMapper.qryExist(propertyNo);
		if (x == 1) {
			log.error("编号已经存在，请重新输入！");
			return new DTO(RetCodeEnum.FAIL);
		}
		String productNo = productPropertyDTO.getProductNo();
		String propertyName = productPropertyDTO.getPropertyName();
		String createTime = productPropertyDTO.getCreateTime();
		Integer propertyType = productPropertyDTO.getPropertyType();
		List<String> propertyValueList = productPropertyDTO.getPropertyValue();
		ProductPropertyKeyPO productPropertyKeyPO = new ProductPropertyKeyPO();
		productPropertyKeyPO.setPropertyNo(propertyNo);
		productPropertyKeyPO.setProductNo(productNo);
		productPropertyKeyPO.setPropertyName(propertyName);
		productPropertyKeyPO.setCreateTime(createTime);
		productPropertyKeyPO.setPropertyType(propertyType);
		int y = productPropertyKeyMapper.insertSelective(productPropertyKeyPO);
		if (y != 1) {
			return new DTO(RetCodeEnum.FAIL);
		}

		if (1 == propertyType) {
			for (String propertyValue : propertyValueList) {
				ProductPropertyValuePO productPropertyValuePO = new ProductPropertyValuePO();
				productPropertyValuePO.setId(java.util.UUID.randomUUID().toString().replace("-", ""));
				productPropertyValuePO.setPropertyNo(propertyNo);
				productPropertyValuePO.setPropertyValue(propertyValue);
				productPropertyValuePO.setCreateTime(createTime);
				int z = productPropertyValueMapper.insertSelective(productPropertyValuePO);
				if (z != 1) {
					return new DTO(RetCodeEnum.FAIL);
				}
			}
		}
		return new DTO(RetCodeEnum.SUCCEED);
	}

	@Override
	public ProductPropertyValueInfoDTO qryGoodsPropertyValue(String propertyNo) {
		ProductPropertyValueInfoDTO dto = new ProductPropertyValueInfoDTO(RetCodeEnum.FAIL);
		List<String> list = productPropertyValueMapper.qryPropertyValueByNo(propertyNo);
		dto.setResult(RetCodeEnum.SUCCEED);
		dto.setPropertyValue(list);
		return dto;
	}

	@Override
	public ListDTO qryPropertyType() {
		ListDTO<Integer> listDTO = new ListDTO<>(RetCodeEnum.SUCCEED);
		List<Integer> list= productPropertyValueMapper.qryPropertyType();
		listDTO.setRetList(list);
		return listDTO;
	}

	@Override
	public int insert(Map<String, Object> GoodsPropertyKeyMap) {
		return productPropertyKeyMapper.insert(GoodsPropertyKeyMap);
	}

	@Override
	public int selectCountByGoodsNo(String goodsNo, String propertyName) {
		return productPropertyKeyMapper.selectCountByGoodsNo(goodsNo,propertyName);
	}

	@Override
	public String selectPropertyNoByGoodsNo(String goodsNo, String propertyName) {
		return productPropertyKeyMapper.selectPropertyNoByGoodsNo(goodsNo,propertyName);
	}

	@Override
	public String selectMaxNo() {
		return productPropertyKeyMapper.selectMaxNo();
	}

	@Override
	public int insert2(Map<String, Object> GoodsPropertyMap) {
		return productPropertyValueMapper.insert(GoodsPropertyMap);
	}

	@Override
	public int selectByNoAndValue(String propertyNo, String propertyValue) {
		return productPropertyValueMapper.selectByNoAndValue(propertyNo,propertyValue);
	}

	/*@Override
	public DTO delGoodsProperty(String propertyNo) {
		int x = productPropertyKeyMapper.deleteByPrimaryKey(propertyNo);
		if (x != 1) {
			return new DTO(RetCodeEnum.FAIL);
		}
		return new DTO(RetCodeEnum.SUCCEED);
	}*/
}
