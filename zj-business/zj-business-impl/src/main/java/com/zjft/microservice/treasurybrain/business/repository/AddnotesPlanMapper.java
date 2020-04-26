package com.zjft.microservice.treasurybrain.business.repository;

import com.zjft.microservice.treasurybrain.business.domain.AddnotesPlan;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface AddnotesPlanMapper {
    int deleteByPrimaryKey(String addnotesPlanNo);

    int insert(AddnotesPlan record);

    int insertSelective(AddnotesPlan record);

    AddnotesPlan selectByPrimaryKey(String addnotesPlanNo);

    int updateByPrimaryKeySelective(AddnotesPlan record);

    int updateByPrimaryKey(AddnotesPlan record);

	List<AddnotesPlan> qryAddnotesPlan(Map<String, Object> paramMap);

	int qryTotalRowPlan(Map<String, Object> paramMap);

	String selectMaxNo(@Param("clrCenterNo") String clrCenterNo, @Param("planAddnotesDate") String planAddnotesDate);

	AddnotesPlan selectDetailByPrimaryKey(String addnotesPlanNo);

	/**
	 * 查询待审核的加钞计划数
	 * @return
	 */
	int selectWaitVerifyCount(int status);

	int qryOrgGradeNoByOrgNo(String orgNo);

	int qryClrCenterFlag(String orgNo);

	int updateByPrimaryKeyByMap(Map<String, Object> addnotesPlanMap);
}
