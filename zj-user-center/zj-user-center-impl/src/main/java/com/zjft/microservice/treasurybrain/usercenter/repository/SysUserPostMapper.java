package com.zjft.microservice.treasurybrain.usercenter.repository;

import com.zjft.microservice.treasurybrain.common.dto.SysPostDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

/**
 * @author 常 健
 * @since 2019/11/12
 */
@Mapper
public interface SysUserPostMapper {


	/**
	 * 根据用户名查询岗位列表
	 *
	 * @param username 用户名
	 * @return List<SysPostDTO>
	 */
	@Select("select t1.POST_NO, t1.POST_NAME, t1.POST_TYPE,  t1.NOTE" +
			" from SYS_POST t1 " +
			"left join SYS_USER_POST t2 on t1.POST_NO=t2.POST_NO " +
			"where t2.username=#{username}")
	@Results({
			@Result(column = "POST_NO", property = "postNo", jdbcType = JdbcType.VARCHAR, id = true),
			@Result(column = "POST_NAME", property = "postName", jdbcType = JdbcType.VARCHAR),
			@Result(column = "POST_TYPE", property = "postType", jdbcType = JdbcType.DECIMAL),
			@Result(column = "NOTE", property = "note", jdbcType = JdbcType.VARCHAR)
	})
	List<SysPostDTO> qryUserPostByUsername(String username);
}
