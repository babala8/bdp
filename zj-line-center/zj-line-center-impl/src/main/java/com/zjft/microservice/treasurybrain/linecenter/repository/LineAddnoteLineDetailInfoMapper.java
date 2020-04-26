package com.zjft.microservice.treasurybrain.linecenter.repository;

import com.zjft.microservice.treasurybrain.linecenter.domain.LineAddnoteLineDetailInfoDO;
import com.zjft.microservice.treasurybrain.linecenter.domain.AddnoteLineDetailInfoDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface LineAddnoteLineDetailInfoMapper {
	int deleteByPrimaryKey(AddnoteLineDetailInfoDO key);

	int insert(LineAddnoteLineDetailInfoDO record);

	int insertSelective(LineAddnoteLineDetailInfoDO record);

	LineAddnoteLineDetailInfoDO selectByPrimaryKey(AddnoteLineDetailInfoDO key);

	int updateByPrimaryKeySelective(LineAddnoteLineDetailInfoDO record);

	int updateByPrimaryKey(LineAddnoteLineDetailInfoDO record);

	List selectTaskCount(Map<String, Object> paramMap);
}
