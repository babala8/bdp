package com.zjft.microservice.treasurybrain.usercenter.repository;

import com.zjft.microservice.treasurybrain.usercenter.domain.PostUserInfoDO;
import com.zjft.microservice.treasurybrain.usercenter.po.SysPostLimitPO;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

/**
 * @author 常 健
 * @since 2019/11/14
 */
@Mapper
public interface SysPostLimitMapper {

	@Insert("<script> " +
			"insert into SYS_POST_LIMIT(POST_NO,POST_LIMIT_NO,POST_LIMIT_NAME,NOTE) " +
			"<foreach item='item' index='index' collection='postLimitList' separator='union all' > " +
			"(SELECT #{postNo},#{item.postLimitNo},#{item.postLimitName},#{item.note} from dual) " +
			"</foreach>" +
			"</script> "
	)
	void insertPostLimit(@Param("postNo") String postNo, @Param("postLimitList") List<SysPostLimitPO> postLimitList);

	@Delete("delete from SYS_POST_LIMIT where post_no=#{postNo,jdbcType=VARCHAR}")
	int delete(@Param("postNo") String postNo);

	@Select("select POST_NO,POST_LIMIT_NO,POST_LIMIT_NAME,NOTE from SYS_POST_LIMIT where post_no=#{postNo,jdbcType=VARCHAR}")
	@Results({
			@Result(column = "POST_NO", property = "postNo", jdbcType = JdbcType.VARCHAR, id = true),
			@Result(column = "POST_LIMIT_NO", property = "postLimitNo", jdbcType = JdbcType.VARCHAR, id = true),
			@Result(column = "POST_LIMIT_NAME", property = "postLimitName", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NOTE", property = "note", jdbcType = JdbcType.VARCHAR)
	})
	List<SysPostLimitPO> qryByPostNo(String postNO);

	@Select("select b.USERNAME,b.NAME,c.NAME as orgName,c.NO as orgNo from SYS_USER_POST a left join SYS_USER b on a.USERNAME = b.USERNAME left join SYS_ORG c on b.ORG_NO=c.NO  " +
			"where POST_NO=#{postNo,jdbcType=VARCHAR} and exists(select s.NO\n" +
			"\t\t\t\tFROM SYS_ORG s\n" +
			"\t\t\t\tWHERE s.NO=#{orgNo,jdbcType=VARCHAR} and c.LEFT >=s.LEFT and c.RIGHT <=s.RIGHT)")
	List<PostUserInfoDO> qryUserByPostNo(@Param("postNo") String postNo, @Param("orgNo") String orgNo);

}
