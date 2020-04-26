package com.zjft.microservice.treasurybrain.tauro.repository;

import com.zjft.microservice.treasurybrain.tauro.domain.TagReaderCoordsInfoDO;
import com.zjft.microservice.treasurybrain.tauro.po.TagReaderCoordsInfoPO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TagReaderCoordsMapper {

	int addTagReaderCoordsInfo(TagReaderCoordsInfoPO tagReaderCoordsInfoPO);

	List<TagReaderCoordsInfoDO> queryCoordsByTaskNo(String taskNo);
}
