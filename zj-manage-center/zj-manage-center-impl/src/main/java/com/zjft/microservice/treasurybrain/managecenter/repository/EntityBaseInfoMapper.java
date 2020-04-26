package com.zjft.microservice.treasurybrain.managecenter.repository;


import com.zjft.microservice.treasurybrain.managecenter.domain.BaseEntityInfoDO;
import com.zjft.microservice.treasurybrain.managecenter.domain.GoodsPropertyKeyDO;
import com.zjft.microservice.treasurybrain.managecenter.po.BaseEntityInfoPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author 葛瑞莲
 * @since 2019/9/11
 */
@Mapper
public interface EntityBaseInfoMapper {

	List<String> qryContainerNoList(String customerNo);

	/**
	 * 查询满足查询条件的款箱信息数
	 *
	 * @param paramMap
	 * @return
	 */
	int qryTotalRowEntity(Map<String, Object> paramMap);

	/**
	 * 分页查询所有满足条件的款箱基础信息
	 *
	 * @param paramMap
	 * @return
	 */
	List<BaseEntityInfoDO> queryEntityByPage(Map<String, Object> paramMap);

	/**
	 * 查询该物品编号是否存在
	 *
	 * @param entityNo
	 * @return
	 */
	Integer queryByEntityNo(@Param("entityNo") String entityNo);

	int insert(BaseEntityInfoPO baseEntityInfoPO);

	int update(BaseEntityInfoPO baseEntityInfoPO);

	int delete(String entityNo);
}
