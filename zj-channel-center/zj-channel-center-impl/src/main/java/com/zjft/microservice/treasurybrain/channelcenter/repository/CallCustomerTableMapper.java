package com.zjft.microservice.treasurybrain.channelcenter.repository;

import com.zjft.microservice.treasurybrain.channelcenter.domain.CallCustomerInfo;
import com.zjft.microservice.treasurybrain.channelcenter.domain.CallCustomerTypeDO;
import com.zjft.microservice.treasurybrain.channelcenter.dto.CallCustomerLineRunDetailDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface CallCustomerTableMapper {

	List<CallCustomerInfo> selectCallCustomerListByParams(Map<String, Object> params);

	int qryTotalRow(Map<String, Object> paramsMap);

	int deleteByPrimaryKey(String no);

	int insert(CallCustomerInfo record);

	int insertSelective(CallCustomerInfo record);

	CallCustomerInfo selectByPrimaryKey(String no);

	int updateByPrimaryKeySelective(CallCustomerInfo record);

	int updateByPrimaryKey(CallCustomerInfo record);

	String selectOutOrgLineNo(String customerNo);

	Map<String,Object> selectlineNo (String id);

	@Select("<script>" +
			"select tt.* from (select t.*, rownum as rn\n" +
			"\tfrom\n" +
			"\t  (\n" +
			"\t  select d.NO,d.NAME from CALL_CUSTOMER_TYPE d\n" +
			"\t\twhere 1=1 \n" +
			"\t\t  <if test=\"no!= null and no != ''\">\n" +
			"\t\t\t  and d.no like concat(concat('%',#{no,jdbcType=VARCHAR}),'%')\n" +
			"\t\t  </if>\n" +
			"\t\t  <if test=\"name != null and name != ''\">\n" +
			"\t\t\t  and d.name like concat(concat('%',#{name,jdbcType=VARCHAR}),'%')\n" +
			"\t\t  </if>\n" +
			"\torder by d.no)t)tt\n" +
			"\twhere tt.rn &gt; #{startRow,jdbcType=NUMERIC} and tt.rn &lt;= #{endRow,jdbcType=NUMERIC}" +
			"</script>")
	@Results({
			@Result(column = "NO", property = "no", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NAME", property = "name", jdbcType = JdbcType.VARCHAR)
	})
	List<CallCustomerTypeDO> queryCallCustomerTypeListByPage(Map<String, Object> params);

	@Select("<script>" +
			"\t  select d.NO,d.NAME from CALL_CUSTOMER_TYPE d\n" +
			"\t\twhere 1=1 \n" +
			"\t\t  <if test=\"no!= null and no != ''\">\n" +
			"\t\t\t  and d.no like concat(concat('%',#{no,jdbcType=VARCHAR}),'%')\n" +
			"\t\t  </if>\n" +
			"\t\t  <if test=\"name != null and name != ''\">\n" +
			"\t\t\t  and d.name like concat(concat('%',#{name,jdbcType=VARCHAR}),'%')\n" +
			"\t\t  </if>\n" +
			"\torder by d.no\n" +
			"</script>")
	@Results({
			@Result(column = "NO", property = "no", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NAME", property = "name", jdbcType = JdbcType.VARCHAR)
	})
	List<CallCustomerTypeDO> queryCallCustomerTypeList(Map<String, Object> params);

    @Select("<script>" +
    		"  select COUNT(1) from CALL_CUSTOMER_TYPE d\n" +
			"\t\twhere 1=1 \n" +
			"\t\t  <if test=\"no!= null and no != ''\">\n" +
			"\t\t\t  and d.no like concat(concat('%',#{no,jdbcType=VARCHAR}),'%')\n" +
			"\t\t  </if>\n" +
			"\t\t  <if test=\"name != null and name != ''\">\n" +
			"\t\t\t  and d.name like concat(concat('%',#{name,jdbcType=VARCHAR}),'%')\n" +
			"\t\t  </if>\n" +
			"</script>")
	int qryTypeTotalRow(Map<String, Object> paramsMap);
}
