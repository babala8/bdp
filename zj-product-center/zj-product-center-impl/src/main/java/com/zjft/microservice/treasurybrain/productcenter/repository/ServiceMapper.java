package com.zjft.microservice.treasurybrain.productcenter.repository;

import com.zjft.microservice.treasurybrain.productcenter.domain.ServiceDO;
import com.zjft.microservice.treasurybrain.productcenter.dto.*;
import com.zjft.microservice.treasurybrain.productcenter.po.ServiceTablePO;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;
import java.util.Map;

@Mapper
public interface ServiceMapper {
	List<ServiceDO> qryProductForUseByPage(Map<String, Object> paramMap);

	int qryProductForUseTotalRow(Map<String, Object> paramMap);

	int insert(ServiceTablePO serviceTablePO);

	int updateProductStatus(ServiceTablePO serviceTablePO);

	int qryTotalRow(Map<String, Object> paramMap);

	List<ServiceDO> qryProductInfoByPage(Map<String, Object> paramMap);

	ServiceDO qryProductInfo(String serviceNo);

	@Select("select STATUS, NAME, NOTE ,WEAK_NODE from SERVICE_STATUS where SERVICE_NO = #{serviceNo,jdbcType=NUMERIC}")
	@Results({
			@Result(column = "STATUS", property = "status", jdbcType = JdbcType.NUMERIC),
			@Result(column = "NAME", property = "name", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NOTE", property = "note", jdbcType = JdbcType.VARCHAR),
			@Result(column = "WEAK_NODE", property = "weakNode", jdbcType = JdbcType.VARCHAR),
	})
	List<ServiceStatusDTO> qryProductServiceStatus(String serviceNo);

	@Select("select p.ID, p.CUR_STATUS, PSS1.NAME as CUR_STATUS_NAME,PSE.NEXT_STATUS,PSS2.NAME as NEXT_STATUS_NAME,p.OPERATE_TYPE, PSE.DESCRIPTION as OPERATE_NAME,p.DESCRIPTION, PMT.MODULE_NAME as MODULE_NAME, PMT.MODULE_NO as MODULE_NO, PST.SERVICE_NAME as SERVICE_NAME, p.TEMPLATE from SERVICE_STATUS_CONVERT p\n" +
			"left join SERVICE_STATUS PSS1 on p.SERVICE_NO = PSS1.SERVICE_NO and p.CUR_STATUS = PSS1.STATUS\n" +
			"left join SERVICE_STATUS_EXPAND PSE on p.OPERATE_TYPE = PSE.OPERATE_TYPE\n" +
			"left join SERVICE_STATUS PSS2 on p.SERVICE_NO = PSS2.SERVICE_NO and PSE.NEXT_STATUS = PSS2.STATUS\n" +
			"left join SERVICE_MODULE_TABLE PMT on p.MODULE_NO = PMT.MODULE_NO\n" +
			"left join SERVICE_TABLE PST on PSS1.SERVICE_NO = PST.SERVICE_NO\n" +
			"where p.SERVICE_NO = #{serviceNo,jdbcType=NUMERIC}\n" +
			"order by cast(p.ID as int)")
	@Results({
			@Result(column = "CUR_STATUS", property = "curStatus", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NEXT_STATUS", property = "nextStatus", jdbcType = JdbcType.VARCHAR),
			@Result(column = "OPERATE_TYPE", property = "operateType", jdbcType = JdbcType.VARCHAR),
			@Result(column = "CUR_STATUS_NAME", property = "curStatusName", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NEXT_STATUS_NAME", property = "nextStatusName", jdbcType = JdbcType.VARCHAR),
			@Result(column = "OPERATE_NAME", property = "operateName", jdbcType = JdbcType.VARCHAR),
			@Result(column = "DESCRIPTION", property = "description", jdbcType = JdbcType.VARCHAR),
			@Result(column = "MODULE_NAME", property = "moduleName", jdbcType = JdbcType.VARCHAR),
			@Result(column = "MODULE_NO", property = "moduleNo", jdbcType = JdbcType.VARCHAR),
			@Result(column = "SERVICE_NAME", property = "serviceName", jdbcType = JdbcType.VARCHAR),
			@Result(column = "TEMPLATE", property = "template", jdbcType = JdbcType.VARCHAR),
	})
	List<ServiceStatusConvertDTO> qryProductStatusConvert(String serviceNo);

	@Select("select max(SERVICE_NO) as SERVICE_NO  from SERVICE_TABLE")
	@Results({
			@Result(column = "SERVICE_NO", property = "serviceNo", jdbcType = JdbcType.NUMERIC),
	})
	int qryMaxServiceNo();

	@Select("select CUSTOMER_TYPE, NAME from SERVICE_CUSTOMER_TYPE")
	@Results({
			@Result(column = "CUSTOMER_TYPE", property = "customerType", jdbcType = JdbcType.NUMERIC),
			@Result(column = "NAME", property = "name", jdbcType = JdbcType.VARCHAR),
	})
	List<CustomerTypeDTO> qryCustomerType();

	@Select("select MODULE_NO, MODULE_NAME from SERVICE_MODULE_TABLE")
	@Results({
			@Result(column = "MODULE_NO", property = "moduleNo", jdbcType = JdbcType.NUMERIC),
			@Result(column = "MODULE_NAME", property = "moduleName", jdbcType = JdbcType.VARCHAR),
	})
	List<ServiceModuleDTO> qryModules();

	@Select("select distinct a.OPERATE_TYPE,a.DESCRIPTION,a.NEXT_STATUS from SERVICE_STATUS_EXPAND a ")
	@Results({
			@Result(column = "OPERATE_TYPE", property = "operateType", jdbcType = JdbcType.VARCHAR),
			@Result(column = "DESCRIPTION", property = "description", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NEXT_STATUS", property = "nextStatus", jdbcType = JdbcType.VARCHAR),
			//@Result(column = "NAME", property = "statusName", jdbcType = JdbcType.VARCHAR),
	})
	List<ServiceStatusExpandDTO> qryProductStatusExpand(String operateType);

	@Select("select NEXT_STATUS from SERVICE_STATUS_EXPAND where OPERATE_TYPE = #{operateType,jdbcType=VARCHAR}")
	@Results({
			@Result(column = "NEXT_STATUS", property = "nextStatus", jdbcType = JdbcType.VARCHAR),
	})
	Integer qryNextByOperateType(String operateType);

	@Select("select DESCRIPTION from SERVICE_STATUS_EXPAND where OPERATE_TYPE = #{operateType,jdbcType=VARCHAR}")
	String qryNameByOperateType(@Param("operateType")String operateType);

	@Select("select t.CUSTOMER_NO ,t.CUSTOMER_SHORT_NAME  from CALL_CUSTOMER_TABLE t where t.CALL_CUSTOMER_TYPE = #{customerType,jdbcType=NUMERIC}  ")
	@Results({
			@Result(column = "CUSTOMER_NO", property = "customerNo", jdbcType = JdbcType.VARCHAR),
			@Result(column = "CUSTOMER_SHORT_NAME", property = "customerShortName", jdbcType = JdbcType.VARCHAR),
	})
	List<CallCustomerDTO> qryProductCustomer(Integer customerType);

	@Select(" select count(1) from SERVICE_STATUS_CONVERT\n" +
			"    where CUR_STATUS = #{curStatus,jdbcType=INTEGER} and\n" +
			"    OPERATE_TYPE = #{operateType,jdbcType=VARCHAR} and SERVICE_NO = #{serviceNo,jdbcType=INTEGER}")
	int qryExist(Map<String, Object> Map);

	@Select("select t.PARAMETER ,t.PARAMETER_NAME ,t.PARAMETER_TYPE  from GOODS_ARGUMENT_TABLE t where t.GOODS_NO = #{goodsNo,jdbcType=VARCHAR}  ")
	@Results({
			@Result(column = "PARAMETER", property = "parameter", jdbcType = JdbcType.VARCHAR),
			@Result(column = "PARAMETER_NAME", property = "parameterName", jdbcType = JdbcType.VARCHAR),
			@Result(column = "PARAMETER_TYPE", property = "parameterType", jdbcType = JdbcType.VARCHAR),
	})
	List<SelfServiceProductArgumentDTO> qryPGoodsArgument(String goodsNo);

}
