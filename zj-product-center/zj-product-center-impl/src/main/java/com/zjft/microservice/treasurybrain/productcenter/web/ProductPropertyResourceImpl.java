package com.zjft.microservice.treasurybrain.productcenter.web;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.ListDTO;
import com.zjft.microservice.treasurybrain.common.util.RetCodeEnum;
import com.zjft.microservice.treasurybrain.common.util.StringUtil;
import com.zjft.microservice.treasurybrain.productcenter.dto.ProductDetailDTO;
import com.zjft.microservice.treasurybrain.productcenter.dto.ProductPropertyDTO;
import com.zjft.microservice.treasurybrain.productcenter.dto.ProductPropertyDetailDTO;
import com.zjft.microservice.treasurybrain.productcenter.dto.ProductPropertyValueInfoDTO;
import com.zjft.microservice.treasurybrain.productcenter.service.ProductService;
import com.zjft.microservice.treasurybrain.productcenter.service.ProductPropertyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author 常 健
 * @since 2019/09/03
 */
@RestController
@Slf4j
public class ProductPropertyResourceImpl implements ProductPropertyResource {

	@Resource
	private ProductPropertyService productPropertyService;

	@Resource
	private ProductService productService;

	@Override
	public DTO addGoodsProperty(ProductPropertyDTO productPropertyDTO) {
		log.info("------------[addGoodsProperty]ProductPropertyResource-------------");
		try {
			return productPropertyService.addGoodsProperty(productPropertyDTO);
		} catch (Exception e) {
			log.error("增加商品属性信息失败", e);
			return new DTO(RetCodeEnum.EXCEPTION);
		}
	}

	@Override
	public ProductPropertyValueInfoDTO qryGoodsPropertyValue(String propertyNo) {
		log.info("------------[addGoodsProperty]ProductPropertyResource-------------");
		try {
			return productPropertyService.qryGoodsPropertyValue(propertyNo);
		} catch (Exception e) {
			log.error("查询商品属性值异常", e);
			return new ProductPropertyValueInfoDTO(RetCodeEnum.EXCEPTION);
		}
	}

	@Override
	public ListDTO qryPropertyType() {
		log.info("------------[qryPropertyType]GoodsBaseResource-------------");
		try {
			return productPropertyService.qryPropertyType();
		} catch (Exception e) {
			log.error("查询商品类型失败", e);
			return new ListDTO<>(RetCodeEnum.EXCEPTION);
		}
	}

	@Override
	public ListDTO qryGoodsBaseByPage(Map<String, Object> paramMap) {
		log.info("------------[qryGoodsBaseByPage]GoodsBaseResource-------------");
		try {
			return productService.qryGoodsBaseByPage(paramMap);
		} catch (Exception e) {
			log.error("查询商品信息失败", e);
			return new ListDTO<>(RetCodeEnum.EXCEPTION);
		}
	}

	@Override
	public DTO addGoodsBase(ProductPropertyDetailDTO productPropertyDetailDTO) {
		log.info("------------[addGoodsBase]GoodsBaseResource-------------");
		try {
			String goodsNo = productPropertyDetailDTO.getProductNo();
			if (StringUtil.isNullorEmpty(goodsNo)) {
				return new DTO(RetCodeEnum.FAIL);
			}
			return productService.addGoodsBase(productPropertyDetailDTO);
		} catch (Exception e) {
			log.error("新增商品信息失败", e);
			return new DTO(RetCodeEnum.EXCEPTION);
		}
	}

	@Override
	public DTO modGoodsBase(ProductPropertyDetailDTO productPropertyDetailDTO) {
		log.info("------------[modGoodsBase]GoodsBaseResource-------------");
		try {
			return productService.modGoodsBase(productPropertyDetailDTO);
		} catch (Exception e) {
			log.error("修改商品信息失败", e);
			return new DTO(RetCodeEnum.EXCEPTION);
		}
	}

	@Override
	public DTO delGoodsBase(String productNo) {
		log.info("------------[delGoodsBase]GoodsBaseResource-------------");
		try {
			if (StringUtil.isNullorEmpty(productNo)) {
				return new DTO(RetCodeEnum.FAIL);
			}
			return productService.delGoodsBase(productNo);
		} catch (Exception e) {
			log.error("删除商品信息失败", e);
			return new DTO(RetCodeEnum.EXCEPTION);
		}
	}

	@Override
	public ProductDetailDTO qryGoodsBaseDetail(String productNo) {
		log.info("------------[qryGoodsBaseDetail]GoodsBaseResource-------------");
		try {
			return productService.qryGoodsBaseDetail(productNo);
		} catch (Exception e) {
			log.error("查询商品详细信息失败", e);
			return new ProductDetailDTO(RetCodeEnum.EXCEPTION);
		}
	}

	@Override
	public ListDTO qryGoodsUpperInfo() {
		log.info("------------[qryGoodsUpperInfo]GoodsBaseResource-------------");
		try {
			return productService.qryUpperInfo();
		} catch (Exception e) {
			log.error("查询商品详细信息失败", e);
			return new ListDTO<>(RetCodeEnum.EXCEPTION);
		}
	}

	/*@Override
	public DTO delGoodsProperty(String propertyNo) {
		log.info("------------[delGoodsProperty]ProductPropertyResource-------------");
		try {
			if (StringUtil.isNullorEmpty(propertyNo)){
				return new DTO(RetCodeEnum.FAIL);
			}
			return productPropertyService.delGoodsProperty(propertyNo);
		} catch (Exception e) {
			log.error("删除商品属性信息失败", e);
			return new DTO(RetCodeEnum.EXCEPTION);
		}
	}*/
}
