package com.zjft.microservice.treasurybrain.linecenter.repository;

import com.zjft.microservice.treasurybrain.linecenter.domain.NetPointMatrix;
import com.zjft.microservice.treasurybrain.linecenter.domain.NetPointMatrixKey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mapper
public interface LineNetPointMatrixMapper {
    int deleteByPrimaryKey(NetPointMatrixKey key);

    int insert(NetPointMatrix record);

    int insertSelective(NetPointMatrix record);

    NetPointMatrix selectByPrimaryKey(NetPointMatrixKey key);

    int updateByPrimaryKeySelective(NetPointMatrix record);

    int updateByPrimaryKey(NetPointMatrix record);

	List<Map<String, Object>> getDistanceByDevs(HashMap<String, Object> params);
	
	List<NetPointMatrix> getNetMatrix(Map<String, Object> paramMap);

	int getNetMatrixRow(Map<String, Object> paramMap);
	
	int createOrUpdate(NetPointMatrix record);
	
	int createOrUpdateBatch(List<NetPointMatrix> list);
	
	List<NetPointMatrix> selectNetpointMatrix(Map<String, Object> paramMap);

	List<Map<String, Object>> getLinkedList(@Param("tactic") int tactic, @Param("iPointNo") String iPointNo);

	List<Map<String, Object>> getPathLinked(@Param("clrCenterNo") String clrCenterNo, @Param("dataType") Integer dataType);

	int getLinkedListByClrNo(@Param("tactic") int tactic, @Param("clrCenterNo") String clrCenterNo);

	void deleteLinkedListByClrNo(@Param("tactic") int tactic, @Param("clrCenterNo") String clrCenterNo);

	NetPointMatrix selectByPrimaryKeyMap(Map<String, Object> map);

}
