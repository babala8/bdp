package com.zjft.microservice.treasurybrain.usercenter.repository;

import com.zjft.microservice.treasurybrain.common.domain.SysRoleDO;
import com.zjft.microservice.treasurybrain.common.domain.SysUserDO;
import com.zjft.microservice.treasurybrain.common.domain.SysUserPostDO;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author 杨光
 * @since 2019-03-08
 */
@Mapper
@Repository
public interface SysUserMapper {

	/**
	 * 根据用户名查询用户详情
	 *
	 * @param username 用户名
	 * @return SysUserDO
	 */
	@Select("select USERNAME, PASSWORD, NAME, STATUS, ONLINE_FLAG, ORG_NO, PHONE, MOBILE, EMAIL, PHOTO," +
			"LOGIN_IP, LOGIN_TIME,LOGIN_TERM, PASSWORD_EXPIRATION,PASSWORD_ERROR,GROUP_TYPE,SERVICE_COMPANY " +
			" from sys_user where username=#{username}")
	@Results({
			@Result(column = "USERNAME", property = "username", jdbcType = JdbcType.VARCHAR, id = true),
			@Result(column = "PASSWORD", property = "password", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NAME", property = "name", jdbcType = JdbcType.VARCHAR),
			@Result(column = "STATUS", property = "status", jdbcType = JdbcType.DECIMAL),
			@Result(column = "ONLINE_FLAG", property = "onlineFlag", jdbcType = JdbcType.DECIMAL),
			@Result(column = "ORG_NO", property = "orgNo", jdbcType = JdbcType.VARCHAR),
			@Result(column = "PHONE", property = "phone", jdbcType = JdbcType.VARCHAR),
			@Result(column = "MOBILE", property = "mobile", jdbcType = JdbcType.VARCHAR),
			@Result(column = "EMAIL", property = "email", jdbcType = JdbcType.VARCHAR),
			@Result(column = "PHOTO", property = "photo", jdbcType = JdbcType.VARCHAR),
			@Result(column = "LOGIN_IP", property = "loginIp", jdbcType = JdbcType.VARCHAR),
			@Result(column = "LOGIN_TIME", property = "loginTime", jdbcType = JdbcType.VARCHAR),
			@Result(column = "LOGIN_TERM", property = "loginTerm", jdbcType = JdbcType.VARCHAR),
			@Result(column = "PASSWORD_EXPIRATION", property = "passwordExpiration", jdbcType = JdbcType.VARCHAR),
			@Result(column = "PASSWORD_ERROR", property = "passwordError", jdbcType = JdbcType.DECIMAL),
			@Result(column = "GROUP_TYPE", property = "groupType", jdbcType = JdbcType.DECIMAL),
			@Result(column = "SERVICE_COMPANY", property = "serviceCompany", jdbcType = JdbcType.VARCHAR),
			@Result(column = "USERNAME", property = "roleList", many = @Many(select = "com.zjft.microservice.treasurybrain.usercenter.repository.SysRoleMapper.qryUserRoleByUsername")),
			@Result(column = "ORG_NO", property = "sysOrg", one = @One(select = "com.zjft.microservice.treasurybrain.channelcenter.repository.SysOrgMapper.selectByPrimaryKey")),
			@Result(column = "USERNAME", property = "menuList", many = @Many(select = "com.zjft.microservice.treasurybrain.usercenter.repository.SysMenuMapper.queryMenuByUsername"))
	})
	SysUserDO selectByPrimaryKey(String username);

	/**
	 * 根据用户名查询用户信息
	 *
	 * @param username 用户名
	 * @return SysUserDO
	 */
	@Select("select USERNAME, PASSWORD, NAME, STATUS, ONLINE_FLAG, ORG_NO, PHONE, MOBILE, EMAIL, PHOTO " +
			" from sys_user where username=#{username}")
	@Results({
			@Result(column = "USERNAME", property = "username", jdbcType = JdbcType.VARCHAR, id = true),
			@Result(column = "PASSWORD", property = "password", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NAME", property = "name", jdbcType = JdbcType.VARCHAR),
			@Result(column = "STATUS", property = "status", jdbcType = JdbcType.DECIMAL),
			@Result(column = "ONLINE_FLAG", property = "onlineFlag", jdbcType = JdbcType.DECIMAL),
			@Result(column = "ORG_NO", property = "orgNo", jdbcType = JdbcType.VARCHAR),
			@Result(column = "PHONE", property = "phone", jdbcType = JdbcType.VARCHAR),
			@Result(column = "MOBILE", property = "mobile", jdbcType = JdbcType.VARCHAR),
			@Result(column = "EMAIL", property = "email", jdbcType = JdbcType.VARCHAR),
			@Result(column = "PHOTO", property = "photo", jdbcType = JdbcType.VARCHAR),
			@Result(column = "USERNAME", property = "roleList", many = @Many(select = "com.zjft.microservice.treasurybrain.usercenter.repository.SysRoleMapper.qryUserRoleByUsername")),
			@Result(column = "ORG_NO", property = "sysOrg", one = @One(select = "com.zjft.microservice.treasurybrain.channelcenter.repository.SysOrgMapper.selectByPrimaryKey")),
	})
	SysUserDO selectInfoByPrimaryKey(String username);


	@Delete({
			"delete from SYS_USER_ROLE",
			"where USERNAME = #{username,jdbcType=VARCHAR}"
	})
	int deleteUserRole(String username);

	@Delete({
			"delete from SYS_USER_POST",
			"where USERNAME = #{username,jdbcType=VARCHAR}"
	})
	int deleteUserPost(String username);

	@Insert("<script> " +
			"insert into SYS_USER_ROLE(USERNAME,ROLE_NO) " +
			"<foreach item='item' index='index' collection='role' separator='union all' > " +
			"(SELECT #{username},#{item.no} from dual) " +
			"</foreach>" +
			"</script> "
	)
	void insertUserRole(@Param("username") String username, @Param("role") List<SysRoleDO> role);

	@Insert("<script> " +
			"insert into SYS_USER_POST(USERNAME,POST_NO) " +
			"<foreach item='item' index='index' collection='post' separator='union all' > " +
			"(SELECT #{username},#{item.postNo} from dual) " +
			"</foreach>" +
			"</script> "
	)
	void insertUserPost(@Param("username") String username, @Param("post") List<SysUserPostDO> postList);

	@Insert({
			"insert into SYS_USER (USERNAME, PASSWORD, NAME, ORG_NO, PHONE, MOBILE, EMAIL, PHOTO , GROUP_TYPE)",
			"values (#{username,jdbcType=VARCHAR}, #{password,jdbcType=VARCHAR}, ",
			"#{name,jdbcType=VARCHAR}, #{orgNo,jdbcType=VARCHAR}, #{phone,jdbcType=VARCHAR}, ",
			"#{mobile,jdbcType=VARCHAR}, #{email,jdbcType=VARCHAR}, #{photo,jdbcType=VARCHAR},#{groupType,jdbcType=NUMERIC})"
	})
	int insert(SysUserDO record);

	@Update({
			"update SYS_USER SET ",
			"NAME = #{name,jdbcType=VARCHAR},",
			"ORG_NO = #{orgNo,jdbcType=VARCHAR},",
			"PHONE = #{phone,jdbcType=VARCHAR},",
			"MOBILE = #{mobile,jdbcType=VARCHAR},",
			"EMAIL = #{email,jdbcType=VARCHAR},",
			"PHOTO = #{photo,jdbcType=VARCHAR},",
			"GROUP_TYPE = #{groupType,jdbcType=NUMERIC},",
			"SERVICE_COMPANY = #{serviceCompany,jdbcType=VARCHAR} ",
			"where USERNAME = #{username,jdbcType=VARCHAR}"
	})
	int updateByPrimaryKey(SysUserDO record);


	@Select("<script> select r.* from " +
			"(select a1.USERNAME, a1.PASSWORD, a1.NAME, a1.STATUS, a1.ONLINE_FLAG, a1.ORG_NO, a1.PHONE, a1.MOBILE, " +
			"a1.EMAIL, a1.PHOTO, a1.LOGIN_IP, a1.LOGIN_TIME,a1.LOGIN_TERM, a1.PASSWORD_EXPIRATION,a1.PASSWORD_ERROR,a1.GROUP_TYPE,rownum rn " +
			"from (select SYS_USER.* from SYS_USER) a1 " +
			"where 1=1 " +
			"  and a1.ORG_NO in (select o.no from SYS_ORG o left join SYS_ORG tOrg  on tOrg.no=#{userOrgNo,jdbcType=VARCHAR} where o.left &gt;= tOrg.LEFT and o.right &lt;= tOrg.RIGHT) " +
			"  <if test=\"orgNo != null and orgNo != '' \"> " +
			"    AND a1.ORG_NO in (select o.no from SYS_ORG o left join SYS_ORG tOrg  on tOrg.no=#{orgNo,jdbcType=VARCHAR} where o.left &gt;= tOrg.LEFT and o.right &lt;= tOrg.RIGHT) </if>" +
			"  <if test=\"roleNo != null and roleNo != '' \"> " +
			"    AND a1.USERNAME in ( select USERNAME from sys_user_role where ROLE_NO=#{roleNo,jdbcType=NUMERIC})</if>" +
			"  <if test=\"username != null and username != '' \"> " +
			"    AND a1.USERNAME like CONCAT('%',CONCAT(#{username,jdbcType=VARCHAR},'%')) </if>" +
			"  <if test=\"name != null and name != '' \"> " +
			"    AND a1.NAME like CONCAT('%',CONCAT(#{name,jdbcType=VARCHAR},'%')) </if>" +
			"   " +
			" order by a1.NAME) r " +
			" where r.rn &gt; #{startRow,jdbcType=NUMERIC} and r.rn &lt;= #{endRow,jdbcType=NUMERIC}" +
			"</script>"
	)
	@Results({
			@Result(column = "USERNAME", property = "username", jdbcType = JdbcType.VARCHAR, id = true),
			@Result(column = "PASSWORD", property = "password", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NAME", property = "name", jdbcType = JdbcType.VARCHAR),
			@Result(column = "STATUS", property = "status", jdbcType = JdbcType.NUMERIC),
			@Result(column = "ONLINE_FLAG", property = "onlineFlag", jdbcType = JdbcType.NUMERIC),
			@Result(column = "ORG_NO", property = "orgNo", jdbcType = JdbcType.VARCHAR),
			@Result(column = "PHONE", property = "phone", jdbcType = JdbcType.VARCHAR),
			@Result(column = "MOBILE", property = "mobile", jdbcType = JdbcType.VARCHAR),
			@Result(column = "EMAIL", property = "email", jdbcType = JdbcType.VARCHAR),
			@Result(column = "PHOTO", property = "photo", jdbcType = JdbcType.VARCHAR),
			@Result(column = "LOGIN_IP", property = "loginIp", jdbcType = JdbcType.VARCHAR),
			@Result(column = "LOGIN_TIME", property = "loginTime", jdbcType = JdbcType.VARCHAR),
			@Result(column = "LOGIN_TERM", property = "loginTerm", jdbcType = JdbcType.VARCHAR),
			@Result(column = "PASSWORD_EXPIRATION", property = "passwordExpiration", jdbcType = JdbcType.VARCHAR),
			@Result(column = "PASSWORD_ERROR", property = "passwordError", jdbcType = JdbcType.NUMERIC),
			@Result(column = "GROUP_TYPE", property = "groupType", jdbcType = JdbcType.NUMERIC),
			@Result(column = "SERVICE_COMPANY", property = "serviceCompany", jdbcType = JdbcType.VARCHAR),
			@Result(column = "USERNAME", property = "roleList", many = @Many(select = "com.zjft.microservice.treasurybrain.usercenter.repository.SysRoleMapper.qryUserRoleByUsername")),
			@Result(column = "USERNAME", property = "postDetailList", many = @Many(select = "com.zjft.microservice.treasurybrain.usercenter.repository.SysUserPostMapper.qryUserPostByUsername")),
			@Result(column = "ORG_NO", property = "sysOrg", one = @One(select = "com.zjft.microservice.treasurybrain.channelcenter.repository.SysOrgMapper.selectByPrimaryKey"))
	})
	List<SysUserDO> queryByPage(Map<String, Object> param);


	@Select("<script>" +
			"select count(*) from sys_user a1 " +
			"where 1=1 " +
			"  and a1.ORG_NO in (select o.no from SYS_ORG o left join SYS_ORG tOrg  on tOrg.no=#{userOrgNo,jdbcType=VARCHAR} where o.left &gt;= tOrg.LEFT and o.right &lt;= tOrg.RIGHT) " +
			"  <if test=\"orgNo != null and orgNo != '' \"> " +
			"    AND a1.ORG_NO in (select o.no from SYS_ORG o left join SYS_ORG tOrg  on tOrg.no=#{orgNo,jdbcType=VARCHAR} where o.left &gt;= tOrg.LEFT and o.right &lt;= tOrg.RIGHT) </if>" +
			"  <if test=\"roleNo != null and roleNo != '' \"> " +
			"    AND a1.USERNAME in ( select USERNAME from sys_user_role where ROLE_NO=#{roleNo,jdbcType=NUMERIC})</if>" +
			"  <if test=\"username != null and username != '' \"> " +
			"    AND a1.USERNAME like CONCAT('%',CONCAT(#{username,jdbcType=VARCHAR},'%')) </if>" +
			"  <if test=\"name != null and name != '' \"> " +
			"    AND a1.NAME like CONCAT('%',CONCAT(#{name,jdbcType=VARCHAR},'%')) </if>" +
			"</script>")
	int queryTotalRow(Map<String, Object> param);


	@Delete({
			"delete from SYS_USER ",
			"where username = #{username,jdbcType=VARCHAR}"
	})
	int deleteByPrimaryKey(String username);


	@Update({
			"update SYS_USER SET PASSWORD = #{password,jdbcType=VARCHAR} ",
			"where USERNAME = #{username,jdbcType=VARCHAR}"
	})
	int modPassword(@Param("username") String username, @Param("password") String password);


	@Select("select count(1) from sys_org " +
			"where " +
			"    NO in (select o.no from SYS_ORG o left join SYS_ORG tOrg  on tOrg.no=#{authOrgNo,jdbcType=VARCHAR} where o.left >= tOrg.LEFT and o.right <= tOrg.RIGHT) " +
			"    and NO = #{userOrgNo,jdbcType=VARCHAR}"
	)
	int checkPermissionByOrgNo(@Param("userOrgNo") String userOrgNo, @Param("authOrgNo") String authOrgNo);

	@Select("select count(1) from sys_org " +
			"where " +
			"    no in (select o.no from SYS_ORG o left join SYS_ORG tOrg  on tOrg.no=#{authOrgNo,jdbcType=VARCHAR} where o.left >= tOrg.LEFT and o.right <= tOrg.RIGHT) " +
			"    and no = (select ORG_NO from sys_user where username = #{username,jdbcType=VARCHAR})"
	)
	int checkPermissionByUsername(@Param("username") String username, @Param("authOrgNo") String authOrgNo);

	@Select("select USERNAME, PASSWORD, NAME, STATUS, ONLINE_FLAG, ORG_NO, PHONE, MOBILE, EMAIL, PHOTO," +
			"LOGIN_IP, LOGIN_TIME,LOGIN_TERM, PASSWORD_EXPIRATION,PASSWORD_ERROR,GROUP_TYPE,SERVICE_COMPANY " +
			" from sys_user where ORG_NO in (select o.no from SYS_ORG o left join SYS_ORG tOrg on tOrg.no=#{userOrgNo,jdbcType=VARCHAR} where o.left >= tOrg.LEFT and o.right <= tOrg.RIGHT) " +
			" and group_Type !=3 ")
	@Results({
			@Result(column = "USERNAME", property = "username", jdbcType = JdbcType.VARCHAR, id = true),
			@Result(column = "PASSWORD", property = "password", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NAME", property = "name", jdbcType = JdbcType.VARCHAR),
			@Result(column = "STATUS", property = "status", jdbcType = JdbcType.DECIMAL),
			@Result(column = "ONLINE_FLAG", property = "onlineFlag", jdbcType = JdbcType.DECIMAL),
			@Result(column = "ORG_NO", property = "orgNo", jdbcType = JdbcType.VARCHAR),
			@Result(column = "PHONE", property = "phone", jdbcType = JdbcType.VARCHAR),
			@Result(column = "MOBILE", property = "mobile", jdbcType = JdbcType.VARCHAR),
			@Result(column = "EMAIL", property = "email", jdbcType = JdbcType.VARCHAR),
			@Result(column = "PHOTO", property = "photo", jdbcType = JdbcType.VARCHAR),
			@Result(column = "LOGIN_IP", property = "loginIp", jdbcType = JdbcType.VARCHAR),
			@Result(column = "LOGIN_TIME", property = "loginTime", jdbcType = JdbcType.VARCHAR),
			@Result(column = "LOGIN_TERM", property = "loginTerm", jdbcType = JdbcType.VARCHAR),
			@Result(column = "PASSWORD_EXPIRATION", property = "passwordExpiration", jdbcType = JdbcType.VARCHAR),
			@Result(column = "PASSWORD_ERROR", property = "passwordError", jdbcType = JdbcType.DECIMAL),
			@Result(column = "GROUP_TYPE", property = "groupType", jdbcType = JdbcType.DECIMAL),
			@Result(column = "SERVICE_COMPANY", property = "serviceCompany", jdbcType = JdbcType.VARCHAR)
	})
	List<SysUserDO> getUserListForAddnotes(String userOrgNo);

	int qryUserNumByGroupType(int groupType);

	String getUserEmail(String userName);

	List<SysUserDO> qryRolesEmail(List<String> roleList);

	@Select("select MOBILE from SYS_USER where USERNAME =#{userName,jdbcType=VARCHAR}")
	String getUserPhoneNumber(String userName);

	List<SysUserDO> getUserAndPhoneNumber(List<String> roleList);

	List<SysUserDO> getAllUserInfo();

	@Select("select name from sys_user where userName = #{userName,jdbcType=VARCHAR}")
	String getNameByUserName(String userName);
}
