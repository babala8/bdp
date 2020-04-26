package com.zjft.microservice.treasurybrain.datainsight.repository;

import com.zjft.microservice.treasurybrain.datainsight.domain.SelfDefGroup;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface SelfDefGroupMapper {
    int deleteByPrimaryKey(Integer groupid);

    int insert(SelfDefGroup record);

    int insertSelective(SelfDefGroup record);

    SelfDefGroup selectByPrimaryKey(Integer groupid);
    
    int updateByPrimaryKeySelective(SelfDefGroup record);

    int updateByPrimaryKey(SelfDefGroup record);

    int getMaxGroupIdByGroupName(String groupName);

    int getMaxGroupId();

	List<SelfDefGroup> queryGroups();
    
}