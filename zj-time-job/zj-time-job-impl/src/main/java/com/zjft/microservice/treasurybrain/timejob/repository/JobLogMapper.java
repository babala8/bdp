package com.zjft.microservice.treasurybrain.timejob.repository;

import com.zjft.microservice.treasurybrain.timejob.po.JobLogPO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @author 张思雨
 * @since 2019-08-05
 */
@Mapper
public interface JobLogMapper {
	int qryTotalRow(Map<String, Object> paramMap);

	List<JobLogPO> qryTimeJobLogByPage(Map<String, Object> paramMap);
}
