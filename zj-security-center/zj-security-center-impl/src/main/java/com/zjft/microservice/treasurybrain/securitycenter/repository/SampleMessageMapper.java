package com.zjft.microservice.treasurybrain.securitycenter.repository;

import com.zjft.microservice.treasurybrain.securitycenter.domain.SampleMessageInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface SampleMessageMapper {

    int insert(SampleMessageInfo record);

    int qryTotalRowForMonth(Map<String, Object> paramMap);

    List<SampleMessageInfo> qrySecurityWarnInfoForMonth(Map<String, Object> paramMap);

}
