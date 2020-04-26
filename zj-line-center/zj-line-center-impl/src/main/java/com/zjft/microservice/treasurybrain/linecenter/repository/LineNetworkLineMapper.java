package com.zjft.microservice.treasurybrain.linecenter.repository;

import com.zjft.microservice.treasurybrain.linecenter.domain.NetworkLine;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface LineNetworkLineMapper {
    int deleteByPrimaryKey(String lineNo);

    int insert(NetworkLine record);

    int insertSelective(NetworkLine record);

    NetworkLine selectByPrimaryKey(String networkLineNo);

    int updateByPrimaryKeySelective(NetworkLine record);

    int updateByPrimaryKey(NetworkLine record);

	List<NetworkLine> getNetworkLineListByClrNo(String clrCenterNo);

	List<NetworkLine> getNetworkLineListByDateAndClrNo(Map<String, Object> paramMap);

	List<NetworkLine>  getAll();

	int  qryTotalRowPlan(Map<String, Object> paramMap);

	List<NetworkLine> qryNetworkLine(Map<String, Object> paramMap);

	String getNetworkLineNoMax();

	List<NetworkLine>  rowSetList(Map<String, Object> paramMap);
	List<NetworkLine>  rowSetList1(Map<String, Object> paramMap);

}
