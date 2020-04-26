package com.zjft.microservice.treasurybrain.pushserver.repository;


import com.zjft.microservice.treasurybrain.pushserver.po.PushServerInfoPO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface PushServerInfoMapper {

    int insert(PushServerInfoPO record);

	int insertBatch(List<PushServerInfoPO> list);

    int qryTotalRow(Map<String, Object> paramMap);

	List<PushServerInfoPO> queryPushServerInfoByPage(Map<String, Object> paramMap);

	int updateNoticeFlag(String no);

	int updateNoticeFlagBatch(List<String> list);

	List<PushServerInfoPO> qryPushInfo();

	@Delete("delete from PUSH_SERVER_INFO where NO=#{no,jdbcType=VARCHAR}")
	int deleteInfoById(String id);

	PushServerInfoPO qryInfoByNo(String no);
}
