package com.zjft.microservice.treasurybrain.param.repository;

import com.zjft.microservice.treasurybrain.param.domain.DevChsKeyEvent;
import com.zjft.microservice.treasurybrain.param.domain.DevChsKeyEventKey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
public interface DevChsKeyEventMapper {

    int deleteByPrimaryKey(DevChsKeyEventKey key);

    int insert(DevChsKeyEvent record);

    int insertSelective(DevChsKeyEvent record);

    DevChsKeyEvent selectByPrimaryKey(DevChsKeyEventKey key);

    int updateByPrimaryKeySelective(DevChsKeyEvent record);

    int updateByPrimaryKey(DevChsKeyEvent record);

	List<DevChsKeyEvent> selectPostponeKeyEventByClrNo(@Param("clrCenterNo") String clrCenterNo);

	List<DevChsKeyEvent> selectKeyEventsByClrNo(String clrCenterNo);

	List<DevChsKeyEvent> getKeyEventByClrNo(String clrCenterNo);

}
