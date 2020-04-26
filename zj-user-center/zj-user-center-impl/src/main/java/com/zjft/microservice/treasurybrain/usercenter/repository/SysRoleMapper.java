package com.zjft.microservice.treasurybrain.usercenter.repository;


import com.zjft.microservice.treasurybrain.common.domain.SysMenuDO;
import com.zjft.microservice.treasurybrain.common.domain.SysRoleDO;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author 杨光
 * @since 2019-03-06
 */
@Repository
@Mapper
public interface SysRoleMapper {

	/**
	 * 根据用户名查询角色列表
	 *
	 * @param username 用户名
	 * @return List<SysRoleDO>
	 */
	@Select("select t1.NO, t1.NAME, t1.CATALOG, t1.ORG_GRADE_NO, t1.NOTE" +
			" from sys_role t1 " +
			"left join sys_user_role t2 on t1.no=t2.role_no " +
			"where t2.username=#{username}")
	@Results({
			@Result(column = "NO", property = "no", jdbcType = JdbcType.VARCHAR, id = true),
			@Result(column = "NAME", property = "name", jdbcType = JdbcType.VARCHAR),
			@Result(column = "CATALOG", property = "catalog", jdbcType = JdbcType.DECIMAL),
			@Result(column = "ORG_GRADE_NO", property = "orgGradeNo", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NOTE", property = "note", jdbcType = JdbcType.VARCHAR)
	})
	List<SysRoleDO> qryUserRoleByUsername(String username);


	@Select("select a1.* from " +
			"	(select SYS_ROLE.*,rownum rn from SYS_ROLE " +
			"	where org_grade_no like #{orgGradeNo,jdbcType=VARCHAR} and " +
			"		  org_grade_no >= (select org_grade_no from sys_org where no=#{authOrgNo,jdbcType=VARCHAR})" +
			"	order by SYS_ROLE.NO) a1 " +
			"where rn > #{startRow,jdbcType=NUMERIC} and rn <= #{endRow,jdbcType=NUMERIC}")
	@Results({
			@Result(column = "NO", property = "no", jdbcType = JdbcType.VARCHAR, id = true),
			@Result(column = "NAME", property = "name", jdbcType = JdbcType.VARCHAR),
			@Result(column = "CATALOG", property = "catalog", jdbcType = JdbcType.DECIMAL),
			@Result(column = "ORG_GRADE_NO", property = "orgGradeNo", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NOTE", property = "note", jdbcType = JdbcType.VARCHAR)
	})
	List<SysRoleDO> queryByPage(Map<String, Object> param);



	@Select("select NO, NAME,CATALOG,ORG_GRADE_NO,NOTE from SYS_ROLE " +
			"	where org_grade_no >= #{orgGradeNo} and " +
			"	org_grade_no >= (select org_grade_no from sys_org where no=#{authOrgNo,jdbcType=VARCHAR})")
	@Results({
			@Result(column = "NO", property = "no", jdbcType = JdbcType.VARCHAR, id = true),
			@Result(column = "NAME", property = "name", jdbcType = JdbcType.VARCHAR),
			@Result(column = "CATALOG", property = "catalog", jdbcType = JdbcType.DECIMAL),
			@Result(column = "ORG_GRADE_NO", property = "orgGradeNo", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NOTE", property = "note", jdbcType = JdbcType.VARCHAR)
	})
	List<SysRoleDO> queryOrgGradeNo(@Param("orgGradeNo") String orgGradeNo, @Param("authOrgNo") String authOrgNo);



	@Select({
			"select",
			"NO, NAME, CATALOG, ORG_GRADE_NO, NOTE",
			"from SYS_ROLE",
			"where NO = #{no,jdbcType=VARCHAR}"
	})
	@Results({
			@Result(column = "NO", property = "no", jdbcType = JdbcType.NUMERIC, id = true),
			@Result(column = "NAME", property = "name", jdbcType = JdbcType.VARCHAR),
			@Result(column = "CATALOG", property = "catalog", jdbcType = JdbcType.NUMERIC),
			@Result(column = "ORG_GRADE_NO", property = "orgGradeNo", jdbcType = JdbcType.NUMERIC),
			@Result(column = "NOTE", property = "note", jdbcType = JdbcType.VARCHAR)
	})
	SysRoleDO queryRoleDetailByNo(String no);


	/**
	 * 根据角色编号查询菜单列表
	 * checked不是表中字段
	 *
	 * @param roleNo 角色编号
	 * @return List<SysMenuDO>
	 */
	@Select({
			"select SM.*, case when SRM.ROLE_NO is  not null then '1' else null end checked ",
			"from SYS_MENU SM ",
			"left join SYS_ROLE_MENU SRM on SM.NO = SRM.MENU_NO and ROLE_NO=#{roleNo}"
	})
	@Results({
			@Result(column = "NO", property = "no", jdbcType = JdbcType.VARCHAR, id = true),
			@Result(column = "NAME", property = "name", jdbcType = JdbcType.VARCHAR),
			@Result(column = "MENU_FATHER", property = "menuFather", jdbcType = JdbcType.VARCHAR),
			@Result(column = "URL", property = "url", jdbcType = JdbcType.VARCHAR),
			@Result(column = "MENU_LEVEL", property = "menuLevel", jdbcType = JdbcType.NUMERIC),
			@Result(column = "MENU_ORDER", property = "menuOrder", jdbcType = JdbcType.NUMERIC),
			@Result(column = "NOTE", property = "note", jdbcType = JdbcType.VARCHAR),
			@Result(column = "BUTTON_TAG", property = "buttonTag", jdbcType = JdbcType.VARCHAR),
			@Result(column = "CHECKED", property = "checked", jdbcType = JdbcType.VARCHAR),
	})
	List<SysMenuDO> queryMenuListByRoleNo(String roleNo);



	@Select("select count(*) from sys_role " +
			"where " +
			"	org_grade_no like #{orgGradeNo,jdbcType=VARCHAR} and " +
			"	org_grade_no >= (select org_grade_no from sys_org where no=#{authOrgNo,jdbcType=VARCHAR})")
	int queryTotalRow(@Param("orgGradeNo") String orgGradeNo, @Param("authOrgNo") String authOrgNo);


	@Delete({
			"delete from SYS_ROLE",
			"where NO = #{no,jdbcType=DECIMAL}"
	})
	int deleteByPrimaryKey(int no);


	@Delete({
			"delete from SYS_ROLE_MENU",
			"where ROLE_NO = #{roleNo,jdbcType=DECIMAL}"
	})
	int deleteRoleMenu(int roleNo);


	@Insert({
			"insert into SYS_ROLE (NO, NAME, ORG_GRADE_NO, NOTE)",
			"values (#{no,jdbcType=NUMERIC}, #{name,jdbcType=VARCHAR}, ",
			" #{orgGradeNo,jdbcType=NUMERIC}, #{note,jdbcType=VARCHAR})"
	})
	int insert(SysRoleDO record);


	@Insert("<script> " +
			"insert into SYS_ROLE_MENU(ROLE_NO,MENU_NO) " +
			"<foreach item='item' index='index' collection='menu' separator='union all' > " +
			"(SELECT #{roleNo},#{item.no} from dual) " +
			"</foreach>" +
			"</script> "
	)
	int insertRoleMenu(@Param("roleNo") Integer roleNo, @Param("menu") List<SysMenuDO> menu);


	@Select("select max(no)+1 from sys_role ")
	int selectMaxNo();

	@Update({
			"update SYS_ROLE",
			"set NAME = #{name,jdbcType=VARCHAR},",
			"ORG_GRADE_NO = #{orgGradeNo,jdbcType=NUMERIC},",
			"NOTE = #{note,jdbcType=VARCHAR}",
			"where NO = #{no,jdbcType=NUMERIC}"
	})
	int updateByPrimaryKey(SysRoleDO record);


	@Select("(select org_grade_no from sys_org where no=#{authOrgNo,jdbcType=VARCHAR}) "
	)
	int getOrgGradeNoByAuthOrgNo(@Param("authOrgNo") String authOrgNo);


	@Select("select count(1) from sys_role " +
			"where " +
			"    org_grade_no >= (select org_grade_no from sys_org where no = #{authOrgNo,jdbcType=VARCHAR}) " +
			"    and no = #{roleNo,jdbcType=NUMERIC}"
	)
	int checkPermissionByRoleNo(@Param("roleNo") int roleNo, @Param("authOrgNo") String authOrgNo);

}
