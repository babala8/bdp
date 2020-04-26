package com.zjft.microservice.treasurybrain.datainsight.repository;

import com.zjft.microservice.treasurybrain.datainsight.domain.SelfDefDetailQueryDO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface SelfDefDetailQueryMapper {

    int deleteByPrimaryKey(String id);

    int insert(SelfDefDetailQueryDO record);

    int insertSelective(SelfDefDetailQueryDO record);

    SelfDefDetailQueryDO selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SelfDefDetailQueryDO record);

    int updateByPrimaryKeyWithBLOBs(SelfDefDetailQueryDO record);

    int updateByPrimaryKey(SelfDefDetailQueryDO record);
    
    List<SelfDefDetailQueryDO> getDetailsByOrgNo(String orgNo);
    
    int insertOrUpdate(SelfDefDetailQueryDO record);
    
    int qryTotalRowQuery(Map<String, Object> paramMap);
    
    List<SelfDefDetailQueryDO> qryDetailQuery(Map<String, Object> params);
}