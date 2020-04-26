package com.zjft.microservice.treasurybrain.productcenter.service.impl;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.ListDTO;
import com.zjft.microservice.treasurybrain.common.util.RetCodeEnum;
import com.zjft.microservice.treasurybrain.common.util.StringUtil;
import com.zjft.microservice.treasurybrain.productcenter.dto.ProductDetailDTO;
import com.zjft.microservice.treasurybrain.productcenter.dto.ProductPropertyDetailDTO;
import com.zjft.microservice.treasurybrain.productcenter.dto.ProductPropertyKeyValueDTO;
import com.zjft.microservice.treasurybrain.productcenter.dto.ProductPropertyValueDetailDTO;
import com.zjft.microservice.treasurybrain.productcenter.mapstruct.ProductPropertyValueConverter;
import com.zjft.microservice.treasurybrain.productcenter.po.ProductBaseTablePO;
import com.zjft.microservice.treasurybrain.productcenter.po.ProductPropertyKeyPO;
import com.zjft.microservice.treasurybrain.productcenter.po.ProductPropertyValuePO;
import com.zjft.microservice.treasurybrain.productcenter.repository.ProductTableMapper;
import com.zjft.microservice.treasurybrain.productcenter.repository.ProductPropertyKeyMapper;
import com.zjft.microservice.treasurybrain.productcenter.repository.ProductPropertyValueMapper;
import com.zjft.microservice.treasurybrain.productcenter.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 常 健
 * @since 2019/09/03
 */
@Slf4j
@Service
public class ProductServiceImpl implements ProductService {

	@Resource
	private ProductTableMapper productTableMapper;

	@Resource
	private ProductPropertyKeyMapper productPropertyKeyMapper;

	@Resource
	private ProductPropertyValueMapper productPropertyValueMapper;

	@Transactional(rollbackFor = Exception.class)
	@Override
	public DTO addGoodsBase(ProductPropertyDetailDTO productPropertyDetailDTO) {
		DTO dto = new DTO(RetCodeEnum.FAIL);
		String productNo = productPropertyDetailDTO.getProductNo();
		int isExist = productTableMapper.qryExist(productNo);
		if (isExist != 0) {
			dto.setRetMsg("编号已经存在，请重新输入！");
			return dto;
		}

		String productName = productPropertyDetailDTO.getProductName();
		int nameIsExist = productTableMapper.qryNameExist(productName);
		if (nameIsExist != 0) {
			dto.setRetMsg("产品名称已经存在，请重新输入！");
			return dto;
		}

		String productType = productPropertyDetailDTO.getProductType();
		String createTime = productPropertyDetailDTO.getCreateTime();
		ProductBaseTablePO productBaseTablePO = new ProductBaseTablePO();
		productBaseTablePO.setProductNo(productNo);
		productBaseTablePO.setProductName(productName);
		productBaseTablePO.setProductType(productType);
		productBaseTablePO.setCreateTime(createTime);
		int x = productTableMapper.insertSelective(productBaseTablePO);
		if (x != 1) {
			log.error("新增商品基本信息失败！");
			return new DTO(RetCodeEnum.FAIL);
		}
		List<ProductPropertyKeyValueDTO> list = productPropertyDetailDTO.getProductPropertyKeyValueDTOList();
		if (list.size() > 0) {
			for (ProductPropertyKeyValueDTO propertyInfo : list) {
				String maxNo = productPropertyKeyMapper.selectMaxNo();
				String newPropertyNo;
				if (StringUtil.isNullorEmpty(maxNo)) {
					newPropertyNo = "000001";
				} else {
					Integer tmpInt = Integer.parseInt(maxNo) + 1;
					newPropertyNo = String.format("%06d", tmpInt);
				}
				ProductPropertyKeyPO productPropertyKeyPO = new ProductPropertyKeyPO();
				productPropertyKeyPO.setPropertyNo(newPropertyNo);
				productPropertyKeyPO.setPropertyName(propertyInfo.getPropertyName());
				productPropertyKeyPO.setProductNo(productNo);
				productPropertyKeyPO.setCreateTime(createTime);
				productPropertyKeyPO.setPropertyType(propertyInfo.getPropertyType());
				int y = productPropertyKeyMapper.insertSelective(productPropertyKeyPO);
				if (y != 1) {
					log.error("新增属性信息失败！");
					//return new DTO(RetCodeEnum.FAIL);
					throw new RuntimeException("新增商品属性信息失败！");
				}
				if (1 == (propertyInfo.getPropertyType())) {
					List<String> propertyValueList = propertyInfo.getPropertyValue();
					if (propertyValueList.size() > 0) {
						for (String propertyValue : propertyValueList) {
							ProductPropertyValuePO productPropertyValuePO = new ProductPropertyValuePO();
							productPropertyValuePO.setId(java.util.UUID.randomUUID().toString().replace("-", ""));
							productPropertyValuePO.setPropertyNo(newPropertyNo);
							productPropertyValuePO.setPropertyValue(propertyValue);
							productPropertyValuePO.setCreateTime(createTime);
							productPropertyValuePO.setProductNo(productNo);
							int z = productPropertyValueMapper.insertSelective(productPropertyValuePO);
							if (z != 1) {
								log.error("新增属性值信息失败！");
								//return new DTO(RetCodeEnum.FAIL);
								throw new RuntimeException("新增商品属性值信息失败！");
							}
						}
					}
				}
			}
		}
		return new DTO(RetCodeEnum.SUCCEED);
	}

	@Override
	public ListDTO qryGoodsBaseByPage(Map<String, Object> paramMap) {
		ListDTO<Map<String, Object>> listDTO = new ListDTO<>(RetCodeEnum.SUCCEED);
		List<ProductBaseTablePO> doList = productTableMapper.qryGoodsBase(paramMap);
		List<Map<String, Object>> retList = new ArrayList<>();
		for (ProductBaseTablePO productBaseTablePO : doList) {
			String upperGoodsName = productTableMapper.selectUpperName(productBaseTablePO.getProductNo());
			Map<String, Object> map = new HashMap<>(doList.size());
			map.put("productNo", productBaseTablePO.getProductNo());
			map.put("productType", productBaseTablePO.getProductType());
			map.put("productTypeName", upperGoodsName);
			map.put("productName", productBaseTablePO.getProductName());
			map.put("createTime", productBaseTablePO.getCreateTime());
			map.put("updateTime", productBaseTablePO.getUpdateTime());
			retList.add(map);
		}
		listDTO.setRetList(retList);
		return listDTO;
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public DTO modGoodsBase(ProductPropertyDetailDTO productPropertyDetailDTO) {
		//更新的时候将次商品的所有属性信息删除 再进行插入操作，基本信息执行更新操作
		String productNo = productPropertyDetailDTO.getProductNo();
		int d = productPropertyKeyMapper.deleteInfoByProductNo(productNo);
		int i = productPropertyValueMapper.deleteInfoByProductNo(productNo);
		if (d < -1 ) {
			log.error("删除商品属性信息失败！");
			throw new RuntimeException("删除商品属性信息失败！");
		}
		String createTime = productPropertyDetailDTO.getCreateTime();
		String updateTime = productPropertyDetailDTO.getUpdateTime();
		String productType = productPropertyDetailDTO.getProductType();
		String productName = productPropertyDetailDTO.getProductName();
		ProductBaseTablePO productBaseTablePO = new ProductBaseTablePO();
		productBaseTablePO.setUpdateTime(updateTime);
		productBaseTablePO.setCreateTime(createTime);
		productBaseTablePO.setProductNo(productNo);
		productBaseTablePO.setProductName(productName);
		productBaseTablePO.setProductType(productType);
		int x = productTableMapper.updateByPrimaryKey(productBaseTablePO);
		if (x != 1) {
			log.error("更新商品基本信息失败！");
			throw new RuntimeException("更新商品基本信息失败！");
		}

		List<ProductPropertyKeyValueDTO> list = productPropertyDetailDTO.getProductPropertyKeyValueDTOList();
		if (list.size() > 0) {
			for (ProductPropertyKeyValueDTO propertyInfo : list) {
				//propertyNo采用修改操作保留之前的No值，新增的采用10位随机数（前端生成）
				String propertyNo = propertyInfo.getPropertyNo();
				Integer propertyType = propertyInfo.getPropertyType();
				/*String maxNo = productPropertyKeyMapper.selectMaxNo();
				String newPropertyNo;
				if (StringUtil.isNullorEmpty(maxNo)) {
					newPropertyNo = "0001";
				} else {
					Integer tmpInt = Integer.parseInt(maxNo) + 1;
					newPropertyNo = String.format("%04d", tmpInt);
				}*/
				ProductPropertyKeyPO productPropertyKeyPO = new ProductPropertyKeyPO();
				productPropertyKeyPO.setPropertyNo(propertyNo);
				productPropertyKeyPO.setPropertyName(propertyInfo.getPropertyName());
				productPropertyKeyPO.setProductNo(productNo);
				productPropertyKeyPO.setCreateTime(updateTime);
				productPropertyKeyPO.setPropertyType(propertyType);
				int y = productPropertyKeyMapper.insertSelective(productPropertyKeyPO);
				if (y != 1) {
					log.error("新增属性信息失败！");
					throw new RuntimeException("新增属性信息失败！");
				}
				if (1 == propertyType) {
					List<String> propertyValueList = propertyInfo.getPropertyValue();
					if (propertyValueList.size() > 0) {
						for (String propertyValue : propertyValueList) {
							ProductPropertyValuePO productPropertyValuePO = new ProductPropertyValuePO();
							productPropertyValuePO.setId(java.util.UUID.randomUUID().toString().replace("-", ""));
							productPropertyValuePO.setPropertyNo(propertyNo);
							productPropertyValuePO.setPropertyValue(propertyValue);
							productPropertyValuePO.setCreateTime(updateTime);
							productPropertyValuePO.setProductNo(productNo);
							int z = productPropertyValueMapper.insertSelective(productPropertyValuePO);
							if (z != 1) {
								log.error("新增属性值信息失败！");
								throw new RuntimeException("新增属性值信息失败！");
							}
						}
					}
				}
			}
		}
		return new DTO(RetCodeEnum.SUCCEED);
	}

	@Override
	public DTO delGoodsBase(String productNo) {
		int flag = productTableMapper.qryExist(productNo);
		if (flag < 1) {
			log.error("商品编号不存在，请重新输入！");
			return new DTO(RetCodeEnum.AUDIT_OBJECT_DELETED);
		}
		productPropertyValueMapper.deleteInfoByProductNo(productNo);
		productPropertyKeyMapper.deleteInfoByProductNo(productNo);
		int x = productTableMapper.deleteByPrimaryKey(productNo);
		if (x != 1) {
			return new DTO(RetCodeEnum.FAIL);
		}
		return new DTO(RetCodeEnum.SUCCEED);
	}

	@Override
	public ProductDetailDTO qryGoodsBaseDetail(String productNo) {
		ProductDetailDTO productDetailDTO = new ProductDetailDTO(RetCodeEnum.SUCCEED);
		List<ProductPropertyKeyValueDTO> productPropertyKeyValueDTOList = new ArrayList<>();
		List<String> propertyNoList = productPropertyKeyMapper.selectPropertyNo(productNo);
		for (String propertyNo : propertyNoList) {
//			List<String> propertyValueList = productPropertyValueMapper.selectPropertyValue(propertyNo);
			List<ProductPropertyValuePO> productPropertyValuePOS = productPropertyValueMapper.selectPropertyValue(propertyNo);
			List<ProductPropertyValueDetailDTO> productPropertyValueDetailDTOS = ProductPropertyValueConverter.INSTANCE.do2dto(productPropertyValuePOS);
			String propertyName = productPropertyKeyMapper.selectPropertyNameByPropertyNo(propertyNo);
			Integer propertyType = productPropertyKeyMapper.selectFlagByPropertyNo(propertyNo);
			ProductPropertyKeyValueDTO productPropertyKeyValueDTO = new ProductPropertyKeyValueDTO();
			productPropertyKeyValueDTO.setPropertyValueDetail(productPropertyValueDetailDTOS);
//			productPropertyKeyValueDTO.setPropertyValue(propertyValueList);
			productPropertyKeyValueDTO.setPropertyName(propertyName);
			productPropertyKeyValueDTO.setPropertyNo(propertyNo);
			productPropertyKeyValueDTO.setPropertyType(propertyType);
			productPropertyKeyValueDTOList.add(productPropertyKeyValueDTO);
		}
		String productType = productTableMapper.selectUpperName(productNo);
		String productName = productTableMapper.selectGoodsNameByGoodsNo(productNo);
		productDetailDTO.setProductNo(productNo);
		productDetailDTO.setProductName(productName);
		productDetailDTO.setProductType(productType);
		productDetailDTO.setProductPropertyKeyValueDTOList(productPropertyKeyValueDTOList);
		return productDetailDTO;
	}

	@Override
	public ListDTO qryUpperInfo() {
		ListDTO<Map<String, Object>> listDTO = new ListDTO<>(RetCodeEnum.SUCCEED);
		List<ProductBaseTablePO> productBaseTablePOList = productTableMapper.qryGoodsInfo();
		List<Map<String, Object>> retList = new ArrayList<>();
		for (ProductBaseTablePO productBaseTablePO : productBaseTablePOList) {
			Map<String, Object> entity = new HashMap<>();
			entity.put("productNo", productBaseTablePO.getProductNo());
			entity.put("productName", productBaseTablePO.getProductName());
			retList.add(entity);
		}
		listDTO.setRetList(retList);
		return listDTO;
	}

	@Override
	public int qryExist(String goodsNo) {
		return productTableMapper.qryExist(goodsNo);
	}
}
