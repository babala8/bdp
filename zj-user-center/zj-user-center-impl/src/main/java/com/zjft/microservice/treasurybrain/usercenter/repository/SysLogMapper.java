package com.zjft.microservice.treasurybrain.usercenter.repository;

import com.zjft.microservice.treasurybrain.usercenter.dto.SysWebLogDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface SysLogMapper {

	Integer qryTotalRow(Map<String, Object> paramMap);

	List<SysWebLogDTO> qrySysWebLogByPage(Map<String, Object> paramMap);
}
