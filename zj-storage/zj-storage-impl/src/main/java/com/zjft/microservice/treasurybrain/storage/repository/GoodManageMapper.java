package com.zjft.microservice.treasurybrain.storage.repository;


import com.zjft.microservice.treasurybrain.storage.domain.StorageCheckDO;
import com.zjft.microservice.treasurybrain.storage.po.StorageCheckPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author 葛瑞莲
 * @since 2019/8/14
 */
@Mapper
public interface GoodManageMapper {

	int qryTotalRow(Map<String, Object> paramMap);

	List<StorageCheckDO> qryCheckInfoByPage(Map<String, Object> paramMap);

	@Select("select sum(AMOUNT) from STORAGE_ENTITY_TABLE where CLR_CENTER_NO = #{clrCenterNo,jdbcType=VARCHAR}")
	BigDecimal selectTotalMoneyByClrCenterNo(String cleCenterNo);

	int insertCheckInfo(StorageCheckPO storageCheckPO);

}
