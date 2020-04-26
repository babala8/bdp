package com.zjft.microservice.treasurybrain.linecenter.repository;

import com.zjft.microservice.treasurybrain.linecenter.domain.NetworkLineRunOrgDetail;
import com.zjft.microservice.treasurybrain.linecenter.domain.NetworkLineRunOrgDetailKey;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface LineNetworkLineRunDevDetailMapper {
    int deleteByPrimaryKey(NetworkLineRunOrgDetailKey key);

	int deleteByNetworkLineRunNo(String networkLineRunNo);

    int insert(NetworkLineRunOrgDetail record);

    int insertSelective(NetworkLineRunOrgDetail record);

    NetworkLineRunOrgDetail selectByPrimaryKey(NetworkLineRunOrgDetailKey key);

    int updateByPrimaryKeySelective(NetworkLineRunOrgDetail record);

    int updateByPrimaryKey(NetworkLineRunOrgDetail record);

	int deleteNetworkLineDetail(Map<String, Object> paramMap);
}
