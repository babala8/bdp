package com.zjft.microservice.treasurybrain.usercenter.repository;

import com.zjft.microservice.treasurybrain.usercenter.domain.OutsourcingLineMapDO;
import com.zjft.microservice.treasurybrain.usercenter.po.OutsourcingLineMapPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * @author liuyuan
 * @since 2019/9/25 10:45
 */
@Mapper
public interface OutsourcingLineMapMapper {

	int qryTotalRow(Map<String, Object> paramsMap);

	List<OutsourcingLineMapDO> qryByPage(Map<String, Object> paramsMap);

	OutsourcingLineMapPO qryByPrimaryKey(String lineRunNo);

	int deleteByPrimaryKey(String lineRunNo);

	int modeByPrimaryKey(OutsourcingLineMapPO outsourcingLineMapPO);

	int insert(OutsourcingLineMapPO outsourcingLineMapPO);

	int deleteInPeriod(@Param("startDate") String startDate, @Param("endDate") String endDate, @Param("lineNos") List<String> lineNos);

	List<OutsourcingLineMapPO> qryInfoByDate(String dutyDate);

	@Select("select c.NAME from OUTSOURCING_LINE_MAP a left join OUTSOURCING_LINE_MAP_DETAIL b on a.LINE_RUN_NO=b.LINE_RUN_NO left join OUTSOURCING_INFO c on b.OUTSOURCING_OP_NO=c.NO\n" +
			"where a.LINE_RUN_NO=#{lineRunNo,jdbcType=VARCHAR}")
	List<String> qryNameByNo(String no);
}
