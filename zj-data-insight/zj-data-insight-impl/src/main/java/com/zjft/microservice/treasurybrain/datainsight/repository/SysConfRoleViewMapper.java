package com.zjft.microservice.treasurybrain.datainsight.repository;


import com.zjft.microservice.treasurybrain.datainsight.domain.SysConfRoleView;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 杨光
 */
@Mapper
public interface SysConfRoleViewMapper {
    int deleteByPrimaryKey(Integer roleNo);

    int insert(SysConfRoleView record);

    int insertSelective(SysConfRoleView record);

    SysConfRoleView selectByPrimaryKey(Integer roleNo);

    int updateByPrimaryKeySelective(SysConfRoleView record);

    int updateByPrimaryKeyWithBLOBs(SysConfRoleView record);
}