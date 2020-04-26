package com.zjft.microservice.treasurybrain.linecenter.repository;

import com.zjft.microservice.treasurybrain.linecenter.domain.NetworkLineDetailInfo;
import com.zjft.microservice.treasurybrain.linecenter.domain.NetworkLineDetailInfoKey;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface LineNetworkLineDetailInfoMapper {
    int deleteByPrimaryKey(NetworkLineDetailInfoKey key);

    int insert(NetworkLineDetailInfo record);

    int insertSelective(NetworkLineDetailInfo record);

    NetworkLineDetailInfo selectByPrimaryKey(NetworkLineDetailInfoKey key);

    int updateByPrimaryKeySelective(NetworkLineDetailInfo record);

    int updateByPrimaryKey(NetworkLineDetailInfo record);

	List selectTaskCount(Map<String, Object> paramMap);
}
