package com.zjft.microservice.treasurybrain.business.repository;

import com.zjft.microservice.treasurybrain.business.domain.AddnoteLineDetailInfo;
import com.zjft.microservice.treasurybrain.business.domain.AddnoteLineDetailInfoKey;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface AddnoteLineDetailInfoMapper {
    int deleteByPrimaryKey(AddnoteLineDetailInfoKey key);

    int insert(AddnoteLineDetailInfo record);

    int insertSelective(AddnoteLineDetailInfo record);

    AddnoteLineDetailInfo selectByPrimaryKey(AddnoteLineDetailInfoKey key);

    int updateByPrimaryKeySelective(AddnoteLineDetailInfo record);

    int updateByPrimaryKey(AddnoteLineDetailInfo record);

	List selectTaskCount(Map<String, Object> paramMap);
}
