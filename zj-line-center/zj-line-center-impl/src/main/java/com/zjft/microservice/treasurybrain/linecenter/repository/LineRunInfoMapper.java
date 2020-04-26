package com.zjft.microservice.treasurybrain.linecenter.repository;

import com.zjft.microservice.treasurybrain.linecenter.domain.LineRunInfo;
import com.zjft.microservice.treasurybrain.linecenter.domain.LineWorkDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface LineRunInfoMapper {
    int deleteByPrimaryKey(String lineRunNo);

    int insert(LineRunInfo record);

    int insertSelective(LineRunInfo record);

	int insertSelectiveByMap(Map<String, Object> paramMap);

	int qryTotalRowForMonth(Map<String, Object> paramMap);

    LineRunInfo selectByPrimaryKey(String lineRunNo);

	List<LineRunInfo> qryLineRunMap(Map<String, Object> paramMap);

	List<LineRunInfo> qryLineRunMapForMonth(Map<String, Object> paramMap);

    int updateByPrimaryKeySelective(LineWorkDO record);

    int updateByPrimaryKey(LineRunInfo record);

	List<LineWorkDO> qryLineRunMapDetail(Map<String, Object> paramMap);

	int deleteLine(Map<String, Object> paramMap);

    List<String>  selectTheYearMonthByLineNo(Map<String, Object> paramMap);

}
