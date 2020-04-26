package com.zjft.microservice.treasurybrain.linecenter.repository;

import com.zjft.microservice.treasurybrain.linecenter.domain.NetworkLineRunInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface LineNetworkLineRunInfoMapper {
    int deleteByPrimaryKey(String networkLineRunNo);

    int insert(NetworkLineRunInfo record);

    int insertSelective(NetworkLineRunInfo record);

	int qryTotalRowForMonth(Map<String, Object> paramMap);

    NetworkLineRunInfo selectByPrimaryKey(String networkLineRunNo);

	List<NetworkLineRunInfo> qryNetworkLineRunMap(Map<String, Object> paramMap);

	List<NetworkLineRunInfo> qryNetworkLineRunMapForMonth(Map<String, Object> paramMap);

	List<String> selectNetAcountList(String lineNo);

    int updateByPrimaryKeySelective(NetworkLineRunInfo record);

    int updateByPrimaryKey(NetworkLineRunInfo record);

	List<NetworkLineRunInfo> qryNetworkLineRunMapDetail(Map<String, Object> paramMap);

	int deleteNetworkLine(Map<String, Object> paramMap);

    List<String>  selectTheYearMonthByNetworkLineNo(Map<String, Object> paramMap);

	/**
	 *
	 *
	 * @param paramsMap clrCenterNo 金库编号（非必输,为空查全部） lineType 线路类型
	 * @return
	 */
	List<String> getLineNosWithTypeAndClrNo(Map<String,Object> paramsMap);

}
