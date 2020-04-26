package com.zjft.microservice.treasurybrain.param.repository;

import com.zjft.microservice.treasurybrain.param.domain.DevChsEstParam;
import com.zjft.microservice.treasurybrain.param.domain.DevChsEstParamKey;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DevChsEstParamMapper {

    int deleteByPrimaryKey(DevChsEstParamKey key);

    int insert(DevChsEstParam record);

    int insertSelective(DevChsEstParam record);

    DevChsEstParam selectByPrimaryKey(DevChsEstParamKey key);

    int updateByPrimaryKeySelective(DevChsEstParam record);

    int updateByPrimaryKey(DevChsEstParam record);

	List<DevChsEstParam> selectByClrNo(String clrCenterNo);

	int selectIsValidCountsByClrNo(String clrCenterNo);

	List<DevChsEstParam> getEstParamByClrNo(String clrCenterNo);

}
