package com.zjft.microservice.treasurybrain.business.repository;

import com.zjft.microservice.treasurybrain.business.domain.AddnoteLine;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface AddnoteLineMapper {
	//----------------------加   已加
    int deleteByPrimaryKey(String lineNo);

	//----------------------加  已加
    int insert(AddnoteLine record);

    int insertSelective(AddnoteLine record);

	//----------------------加 已加
    AddnoteLine selectByPrimaryKey(String lineNo);

    int updateByPrimaryKeySelective(AddnoteLine record);

	//----------------------加
    int updateByPrimaryKey(AddnoteLine record);

	//----------------------加
	List<AddnoteLine> getLineListByClrNo(Map<String, Object> paramMap);

	//----------------------加
	List<AddnoteLine> getLineListByDateAndClrNo(Map<String, Object> paramMap);

	//----------------------加
	List<AddnoteLine> getLineListByType(Map<String, Object> paramMap);

	//----------------------加
	List<AddnoteLine>  getAll();

	//----------------------加
	int  qryTotalRowPlan(Map<String, Object> paramMap);

	//----------------------加
	List<AddnoteLine> qryAddnotesLine(Map<String, Object> paramMap);

	//----------------------加
	String getLineNoMax();

	//----------------------加
	List<AddnoteLine>  rowSetList(Map<String, Object> paramMap);

	//----------------------加
	List<AddnoteLine>  rowSetList1(Map<String, Object> paramMap);

}
