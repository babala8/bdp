package com.zjft.microservice.treasurybrain.usercenter.repository;


import com.zjft.microservice.treasurybrain.usercenter.domain.OutsourcingLineMapDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author liuyuan
 * @since 2019/9/25 15:03
 */
@Mapper
public interface OutsourcingLineMapDetailMapper {

	int deleteByLineRunNo(String lineRunNo);

	/**
	 * 删除一段时间内的详情
	 *
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * @param lineNos 线路列表
	 * @return
	 */
	int deleteInPeriod(@Param("startDate") String startDate, @Param("endDate") String endDate, @Param("lineNos") List<String> lineNos);

	int insertList(OutsourcingLineMapDO outsourcingLineMapDO);

}
