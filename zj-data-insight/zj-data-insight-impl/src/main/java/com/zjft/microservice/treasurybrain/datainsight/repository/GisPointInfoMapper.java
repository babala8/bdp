package com.zjft.microservice.treasurybrain.datainsight.repository;

import com.zjft.microservice.treasurybrain.datainsight.domain.GisPointInfoDO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface GisPointInfoMapper {
    int deleteByPrimaryKey(String pointId);

    int insert(GisPointInfoDO record);

    int insertSelective(GisPointInfoDO record);

	GisPointInfoDO selectByPrimaryKey(String pointId);

    int updateByPrimaryKeySelective(GisPointInfoDO record);

    int updateByPrimaryKeyWithBLOBs(GisPointInfoDO record);

    int updateByPrimaryKey(GisPointInfoDO record);

	GisPointInfoDO selectByOrgNo(String pointOrgno);
    
    int updateByOrgNo(GisPointInfoDO record);
    
    int createOrUpdateByOrgNo(GisPointInfoDO record);
}