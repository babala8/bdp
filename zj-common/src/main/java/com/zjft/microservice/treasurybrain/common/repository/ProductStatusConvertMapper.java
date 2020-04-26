package com.zjft.microservice.treasurybrain.common.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ProductStatusConvertMapper {

	@Select("select count(*) from TASK_TABLE where TASK_NO=#{taskNo,jdbcType=VARCHAR}")
	Integer isTaskNoExist(String taskNo);

	@Select("select STATUS from TASK_TABLE where TASK_NO=#{taskNo,jdbcType=VARCHAR}")
	Integer qryCurStatus(String taskNo);

	@Select("select count(*) from PRODUCT_STATUS_CONVERT " +
			"where SERVICE_NO = #{taskType,jdbcType = NUMERIC} and " +
			"CUR_STATUS = #{curStatus,jdbcType = NUMERIC} and OPERATE_TYPE = #{operateType,jdbcType = VARCHAR}")
	Integer isStatusConvertMatch(@Param("taskType") Integer taskType,@Param("curStatus") Integer curStatus, @Param("operateType") String operateType);

	/**
	 *当你确定上一个转换状态只有一个的时候用这个
	 */
	@Select("select distinct CUR_STATUS from PRODUCT_STATUS_CONVERT " +
			"where NEXT_STATUS = #{nextStatus,jdbcType = NUMERIC} and SERVICE_NO = #{serviceNo,jdbcType = NUMERIC}  and ROWNUM = 1")
	String mayStatusConvert(@Param("nextStatus") Integer nextStatus, @Param("serviceNo") Integer serviceNo);

	/**
	 *否则就用这个
	 */
	/*@Select("select CUR_STATUS from PRODUCT_STATUS_CONVERT " +
			"where NEXT_STATUS = #{nextStatus,jdbcType = NUMERIC} and SERVICE_NO = #{serviceNo,jdbcType = NUMERIC}")
	List<String> mayStatusConverts(@Param("nextStatus") Integer nextStatus, @Param("serviceNo") Integer serviceNo);*/

	@Select("select CUR_STATUS from PRODUCT_STATUS_CONVERT " +
			"where OPERATE_TYPE = #{operateType,jdbcType = VARCHAR} and SERVICE_NO = #{serviceNo,jdbcType = NUMERIC}")
	List<String> mayStatusConverts(@Param("operateType") String operateType, @Param("serviceNo") Integer serviceNo);

}
