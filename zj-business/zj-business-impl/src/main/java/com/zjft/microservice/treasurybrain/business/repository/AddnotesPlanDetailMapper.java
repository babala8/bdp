package com.zjft.microservice.treasurybrain.business.repository;

import com.zjft.microservice.treasurybrain.business.domain.AddnotesPlanDetail;
import com.zjft.microservice.treasurybrain.business.domain.AddnotesPlanDetailKey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface AddnotesPlanDetailMapper {
    int deleteByPrimaryKey(AddnotesPlanDetailKey key);

    int insert(AddnotesPlanDetail record);

    int insertSelective(AddnotesPlanDetail record);

    AddnotesPlanDetail selectByPrimaryKey(AddnotesPlanDetailKey key);

    int updateByPrimaryKeySelective(AddnotesPlanDetail record);

    int updateByPrimaryKey(AddnotesPlanDetail record);

	List<AddnotesPlanDetail> qryAddNotesPlanDetailByNo(String addnotesPlanNo);

	void deleteByAddnotesPlanNo(String addnotesPlanNo);

	List<Map<String, Object>> getDayAvgAmtList(@Param("addnotesPlanNo") String addnotesPlanNo,
											   @Param("startDate") String startDate, @Param("endDate") String endDate);
	
	int getNetpointNum(String addnotesPlanNo);
	
	List<Map<String, Object>> getNetPointsNotGroup(String addnotesPlanNo);
	
	int deleteGroup(String addnotesPlanNo);
	
	List<String> getNetpointList(Map<String, Object> paramMap);

	List<String> getDevPointList(Map<String, Object> paramMap);

	List<AddnotesPlanDetail> qryDetailByNetPoint(Map<String, Object> paramMap);

	List<AddnotesPlanDetail> qryDetailByDevPoint(Map<String, Object> paramMap);

	int updateSortNoNull(Map<String, Object> paramMap);
	
	int updateSortNoByNetPoint(Map<String, Object> paramMap);

	int updateSortNoByDevNo(Map<String, Object> paramMap);

	List<Map<String, Object>> getNetPointsWithGroup(Map<String, Object> paramMap);
	
	int updateByGroupNo(Map<String, Object> paramMap);
	
	int updateSortByGroupNo(Map<String, Object> paramMap);
	
	List<AddnotesPlanDetail> qryDetailByNetPointGroup(Map<String, Object> paramMap);

	List<AddnotesPlanDetail> qryDetailByDevPointGroup(Map<String, Object> paramMap);

	List<Map<String, Object>> getNetPointsOrderBySortNo(Map<String, Object> paramMap);
	
	List<String> getGroupNos(String addnotesPlanNo);
	
	List<AddnotesPlanDetail> selectByLineNo(Map<String, Object> paramMap);

	List<Map<String, Object>> getPlanGroupNetpoints(Map<String, Object> paramMap);

	List<Map<String, Object>> getLineMsgList(Map<String, Object> paramMap);

	/**
	 * 查询加钞的分组和对应设备数
	 *
	 * @param addnotesPlanNo 加钞计划编号
	 * @return 线路编号和设备数
	 */
	List<Map<String,Object>> getDevCountEachGroup(String addnotesPlanNo);

	/**
	 * 查询当前分组下的网点数
	 *
	 * @param addnotesPlanDetail 加钞计划编号 线路编号
	 * @return 网点数
	 */
	int getNetCountInGroup(AddnotesPlanDetail addnotesPlanDetail);

	/**
	 * 根据addnotesPlanNo查询预测总金额
	 * @param addnotesPlanNo
	 * @return
	 */
	double selectSumAddnoteAmount(String addnotesPlanNo);

	int updateByMapSelective(Map<String, Object> addnotesPlanMap);

	/**
	 * 查询当前分组下的网点数
	 *
	 * @param paramMap 加钞计划编号 线路编号
	 * @return 网点数
	 */
	int getNetCountInGroupByMap(Map<String, Object> paramMap);

}
