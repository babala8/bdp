package com.zjft.microservice.treasurybrain.usercenter.repository;


import com.zjft.microservice.treasurybrain.usercenter.domain.SysPermissionDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 张弛
 * @since 2020-01-22
 */
@Mapper
@Repository
public interface SysPermissionMapper {
	/**
	 *查询所有权限
	 *
	 * @return DTO
	 */
	@Select("select NO, NAME, URL, METHOD, CATALOG, NOTE"+
	        "from SYS_PERMISSION"+
	        "order by NO")
	@Results({
			@Result(column = "NO", property = "no", jdbcType = JdbcType.VARCHAR, id = true),
			@Result(column = "NAME", property = "name", jdbcType = JdbcType.VARCHAR),
			@Result(column = "URL", property = "url", jdbcType = JdbcType.VARCHAR),
			@Result(column = "METHOD", property = "method", jdbcType = JdbcType.VARCHAR),
			@Result(column = "METHOD", property = "method", jdbcType = JdbcType.VARCHAR),
			@Result(column = "CATALOG", property = "catalog", jdbcType = JdbcType.NUMERIC),
			@Result(column = "NOTE", property = "note", jdbcType = JdbcType.VARCHAR),
	})
	List<SysPermissionDO> queryAllPermission();
}
