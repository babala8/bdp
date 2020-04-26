package com.zjft.microservice.treasurybrain.datainsight.service;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.ListDTO;
import com.zjft.microservice.treasurybrain.common.dto.PageDTO;

import java.util.Map;


public interface ReportService  {

	ListDTO queryReports();

	PageDTO queryReportsByPage(Map<String, Object> paramsMap);

	DTO delReport(String no);

	DTO addReport(Map<String, Object> paramsMap);

	ListDTO queryGroups();

}
