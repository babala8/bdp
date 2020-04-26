package com.zjft.microservice.treasurybrain.datainsight.repository;

import com.zjft.microservice.treasurybrain.datainsight.domain.SelfDefReports;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


@Mapper
@Repository
public interface SelfDefReportsMapper{
    int deleteByPrimaryKey(String reportId);

    int insert(SelfDefReports record);

    int insertSelective(SelfDefReports record);

    SelfDefReports selectByPrimaryKey(String reportId);

    int updateByPrimaryKeySelective(SelfDefReports record);

    int updateByPrimaryKeyWithBLOBs(SelfDefReports record);

    int updateByPrimaryKey(SelfDefReports record);
    
    List<SelfDefReports> queryReportsByOrgNo(String orgNo);

	int qryTotalRowReports(Map<String, Object> paramMap);

	List<SelfDefReports> queryReportsByPage(Map<String, Object> paramMap);

	int insertOrUpdate(SelfDefReports selfDefReports);
}