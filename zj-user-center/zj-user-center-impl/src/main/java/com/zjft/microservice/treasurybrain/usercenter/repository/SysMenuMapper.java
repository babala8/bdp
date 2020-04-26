package com.zjft.microservice.treasurybrain.usercenter.repository;

import com.zjft.microservice.treasurybrain.common.domain.SysMenuDO;
import com.zjft.microservice.treasurybrain.common.domain.SysPermissionPO;
import com.zjft.microservice.treasurybrain.usercenter.domain.SysMenu;
import com.zjft.microservice.treasurybrain.usercenter.dto.SysMenuDTO;
import com.zjft.microservice.treasurybrain.usercenter.po.SysMenuPO;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author 杨光
 * @author 张弛
 * @since 2019-03-06
 * @since 2020-01-15
 */
@Mapper
@Repository
public interface SysMenuMapper {

	@Select("select a1.NOTE,a1.NO,a1.BUTTON_TAG,a1.MENU_BG,a1.MENU_FATHER,a1.MENU_ICON,a1.MENU_LEVEL,a1.MENU_ORDER,a1.MENU_SIZE,a1.NAME,a1.URL,a1.BUTTON,a2.NAME as MENU_FATHER_NAME " +
			"from SYS_MENU a1 " +
			"left join SYS_MENU a2 on a1.MENU_FATHER = a2.no " +
			"order by a1.NO")
	@Results({
			@Result(column = "NO", property = "no", jdbcType = JdbcType.VARCHAR, id = true),
			@Result(column = "NAME", property = "name", jdbcType = JdbcType.VARCHAR),
			@Result(column = "MENU_FATHER", property = "menuFather", jdbcType = JdbcType.VARCHAR),
			@Result(column = "MENU_FATHER_NAME", property = "menuFatherName", jdbcType = JdbcType.VARCHAR),
			@Result(column = "URL", property = "url", jdbcType = JdbcType.VARCHAR),
			@Result(column = "MENU_LEVEL", property = "menuLevel", jdbcType = JdbcType.NUMERIC),
			@Result(column = "MENU_ORDER", property = "menuOrder", jdbcType = JdbcType.NUMERIC),
			@Result(column = "NOTE", property = "note", jdbcType = JdbcType.VARCHAR),
			@Result(column = "MENU_ICON", property = "menuIcon", jdbcType = JdbcType.VARCHAR),
			@Result(column = "MENU_SIZE", property = "menuSize", jdbcType = JdbcType.NUMERIC),
			@Result(column = "MENU_BG", property = "menuBg", jdbcType = JdbcType.VARCHAR),
			@Result(column = "BUTTON_TAG", property = "buttonTag", jdbcType = JdbcType.NUMERIC),
			@Result(column = "BUTTON", property = "button", jdbcType = JdbcType.VARCHAR)
	})
	List<SysMenu> queryAllMenu();


	/**
	 * 根据用户名查询菜单列表
	 * start with 遍历起始条件
	 * connect by 连接条件
	 * prior 递归方向，向上或向下
	 *
	 * @param username 用户名
	 * @return List<SysMenuDO>
	 */
	@Select("select distinct m1.NO,m1.NAME,m1.MENU_FATHER,m1.URL, m1.MENU_LEVEL, m1.MENU_ORDER, m1.NOTE,m1.MENU_ICON,m1.MENU_SIZE,m1.MENU_BG,m1.BUTTON_TAG,m1.BUTTON " +
			"from SYS_MENU  m1 " +
			"left join SYS_ROLE_MENU on m1.NO = SYS_ROLE_MENU.MENU_NO " +
			"left join SYS_USER_ROLE on SYS_USER_ROLE.ROLE_NO = SYS_ROLE_MENU.ROLE_NO " +
			"start with SYS_USER_ROLE.USERNAME= #{username,jdbcType=VARCHAR} connect by prior m1.MENU_FATHER=m1.NO " +
			"order by m1.NO "
	)
	@Results({
			@Result(column = "NO", property = "no", jdbcType = JdbcType.VARCHAR, id = true),
			@Result(column = "NAME", property = "name", jdbcType = JdbcType.VARCHAR),
			@Result(column = "MENU_FATHER", property = "menuFather", jdbcType = JdbcType.VARCHAR),
			@Result(column = "URL", property = "url", jdbcType = JdbcType.VARCHAR),
			@Result(column = "MENU_LEVEL", property = "menuLevel", jdbcType = JdbcType.NUMERIC),
			@Result(column = "MENU_ORDER", property = "menuOrder", jdbcType = JdbcType.NUMERIC),
			@Result(column = "NOTE", property = "note", jdbcType = JdbcType.VARCHAR),
			@Result(column = "MENU_ICON", property = "menuIcon", jdbcType = JdbcType.VARCHAR),
			@Result(column = "MENU_SIZE", property = "menuSize", jdbcType = JdbcType.NUMERIC),
			@Result(column = "MENU_BG", property = "menuBg", jdbcType = JdbcType.VARCHAR),
			@Result(column = "BUTTON_TAG", property = "buttonTag", jdbcType = JdbcType.NUMERIC),
			@Result(column = "BUTTON", property = "button", jdbcType = JdbcType.VARCHAR)
	})
	List<SysMenuDO> queryMenuByUsername(String username);


	/**
	 * 删除某个叶子菜单下的接口
	 *
	 * @return DTO
	 */
	@Delete({
			"delete from SYS_MENU_PERMISSION",
			"where MENU_NO = #{menuNo,jdbcType=VARCHAR}"
	})
	int deleteMenuPermission(String menuNo);

	/**
	 * 更新某个叶子菜单下的接口
	 *
	 * @return DTO
	 */
	@Insert("<script> " +
			"insert into SYS_MENU_PERMISSION(MENU_NO,PERMISSION_NO) " +
			"<foreach item='item' index='index' collection='permissionList' separator='union all' > " +
			"(SELECT #{menuNo},#{item.no} from dual) " +
			"</foreach>" +
			"</script> "
	)
	int insertMenuPermission(@Param("menuNo") String menuNo, @Param("permissionList") List<SysPermissionPO> permissionList);

	/**
	 * 获取某个叶子菜单下的接口
	 *
	 * @return ListDTO
	 */
	@Select("select NO, NAME, URL, METHOD, CATALOG, NOTE\n" +
			"from SYS_PERMISSION\n" +
			"left join SYS_MENU_PERMISSION on SYS_PERMISSION.NO = SYS_MENU_PERMISSION.PERMISSION_NO\n" +
			"where MENU_NO=#{menuNo,jdbcType=VARCHAR}"
	)
	@Results({
			@Result(column = "NO", property = "no", jdbcType = JdbcType.VARCHAR, id = true),
			@Result(column = "NAME", property = "name", jdbcType = JdbcType.VARCHAR),
			@Result(column = "URL", property = "url", jdbcType = JdbcType.VARCHAR),
			@Result(column = "METHOD", property = "method", jdbcType = JdbcType.VARCHAR),
			@Result(column = "CATALOG", property = "catalog", jdbcType = JdbcType.NUMERIC),
			@Result(column = "NOTE", property = "note", jdbcType = JdbcType.VARCHAR),
	})
	List<SysPermissionPO> getPermissionListByMenuNo(String menuNo);


	/**
	 * 模糊查询所有的接口列表
	 *
	 * @return ListDTO
	 */
	@Select("<script> select r.* from " +
			"(select a1.NO, a1.NAME, a1.URL, a1.METHOD, a1.CATALOG, a1.NOTE,rownum rn " +
			"from SYS_PERMISSION a1 " +
			"where 1=1 " +
			"  <if test=\"no != null and no != '' \"> " +
			"    AND a1.NO like CONCAT('%',CONCAT(#{no,jdbcType=VARCHAR},'%')) </if>" +
			"  <if test=\"url != null and url != '' \"> " +
			"    AND a1.URL like CONCAT('%',CONCAT(#{url,jdbcType=VARCHAR},'%')) </if>" +
			"  <if test=\"name != null and name != '' \"> " +
			"    AND a1.NAME like CONCAT('%',CONCAT(#{name,jdbcType=VARCHAR},'%')) </if>" +
			" order by a1.NAME) r " +
			" where r.rn &gt; #{startRow,jdbcType=NUMERIC} and r.rn &lt;= #{endRow,jdbcType=NUMERIC}" +
			"</script>"
	)
	@Results({
			@Result(column = "NO", property = "no", jdbcType = JdbcType.VARCHAR, id = true),
			@Result(column = "NAME", property = "name", jdbcType = JdbcType.VARCHAR),
			@Result(column = "URL", property = "url", jdbcType = JdbcType.VARCHAR),
			@Result(column = "METHOD", property = "method", jdbcType = JdbcType.VARCHAR),
			@Result(column = "CATALOG", property = "catalog", jdbcType = JdbcType.NUMERIC),
			@Result(column = "NOTE", property = "note", jdbcType = JdbcType.VARCHAR),
	})
	List<SysPermissionPO> getPermissionList(Map<String, Object> param);


	@Select("<script>" +
			"select count(*) from sys_PERMISSION a1 " +
			"where 1=1 " +
			"  <if test=\"no != null and no != '' \"> " +
			"    AND a1.NO like CONCAT('%',CONCAT(#{no,jdbcType=VARCHAR},'%')) </if>" +
			"  <if test=\"url != null and url != '' \"> " +
			"    AND a1.URL like CONCAT('%',CONCAT(#{url,jdbcType=VARCHAR},'%')) </if>" +
			"  <if test=\"name != null and name != '' \"> " +
			"    AND a1.NAME like CONCAT('%',CONCAT(#{name,jdbcType=VA RCHAR},'%')) </if>" +
			"</script>")
	int queryTotalRow(Map<String, Object> param);


	/**
	 * 分页查询所有的菜单列表
	 *
	 * @return ListDTO
	 */
	@Select("<script> select r.* from " +
			"(select a1.NOTE,a1.NO,a1.BUTTON_TAG,a1.MENU_BG,a1.MENU_FATHER,a1.MENU_ICON,a1.MENU_LEVEL,a1.MENU_ORDER,a1.MENU_SIZE,a1.NAME,a1.URL,a1.BUTTON,a2.NAME as MENU_FATHER_NAME,rownum rn " +
			"from SYS_MENU a1 " +
			"left join SYS_MENU a2 on a1.MENU_FATHER = a2.no " +
			"where 1=1 " +
			"  <if test=\"no != null and no != '' \"> " +
			"    AND a1.NO like CONCAT('%',CONCAT(#{no,jdbcType=VARCHAR},'%')) </if>" +
			"  <if test=\"url != null and url != '' \"> " +
			"    AND a1.URL like CONCAT('%',CONCAT(#{url,jdbcType=VARCHAR},'%')) </if>" +
			"  <if test=\"name != null and name != '' \"> " +
			"    AND a1.NAME like CONCAT('%',CONCAT(#{name,jdbcType=VARCHAR},'%')) </if>" +
			"  <if test=\"menuFather != null and menuFather != '' \"> " +
			"    AND a1.MENU_FATHER like CONCAT('%',CONCAT(#{menuFather,jdbcType=VARCHAR},'%')) </if>" +
			"  <if test=\"menuFatherName != null and menuFatherName != '' \"> " +
			"    AND a2.NAME like CONCAT('%',CONCAT(#{menuFatherName,jdbcType=VARCHAR},'%')) </if>" +
			" order by a1.NAME) r " +
			" where r.rn &gt; #{startRow,jdbcType=NUMERIC} and r.rn &lt;= #{endRow,jdbcType=NUMERIC}" +
			"</script>"
	)
	@Results({
			@Result(column = "NO", property = "no", jdbcType = JdbcType.VARCHAR, id = true),
			@Result(column = "NAME", property = "name", jdbcType = JdbcType.VARCHAR),
			@Result(column = "URL", property = "url", jdbcType = JdbcType.VARCHAR),
			@Result(column = "MENU_FATHER", property = "menuFather", jdbcType = JdbcType.VARCHAR),
			@Result(column = "MENU_FATHER_NAME", property = "menuFatherName", jdbcType = JdbcType.VARCHAR),
			@Result(column = "BUTTON_TAG", property = "buttonTag", jdbcType = JdbcType.DECIMAL),
			@Result(column = "NOTE", property = "note", jdbcType = JdbcType.VARCHAR),
			@Result(column = "BUTTON", property = "button", jdbcType = JdbcType.VARCHAR),
			@Result(column = "MENU_ICON", property = "menuIcon", jdbcType = JdbcType.VARCHAR),
			@Result(column = "MENU_SIZE", property = "menuSize", jdbcType = JdbcType.DECIMAL),
			@Result(column = "MENU_LEVEL", property = "menuLevel", jdbcType = JdbcType.DECIMAL),
			@Result(column = "MENU_ORDER", property = "menuOrder", jdbcType = JdbcType.DECIMAL),
			@Result(column = "MENU_BG", property = "menuBg", jdbcType = JdbcType.VARCHAR),


	})
	List<SysMenu> qryMenuList(Map<String, Object> param);


	@Select("<script>" +
			"select count(1) from sys_Menu a1 " +
			"left join SYS_MENU a2 on a1.MENU_FATHER = a2.no " +
			"where 1=1 " +
			"  <if test=\"no != null and no != '' \"> " +
			"    AND a1.NO like CONCAT('%',CONCAT(#{no,jdbcType=VARCHAR},'%')) </if>" +
			"  <if test=\"url != null and url != '' \"> " +
			"    AND a1.URL like CONCAT('%',CONCAT(#{url,jdbcType=VARCHAR},'%')) </if>" +
			"  <if test=\"name != null and name != '' \"> " +
			"    AND a1.NAME like CONCAT('%',CONCAT(#{name,jdbcType=VARCHAR},'%')) </if>" +
			"  <if test=\"menuFather != null and menuFather != '' \"> " +
			"    AND a1.MENU_FATHER like CONCAT('%',CONCAT(#{menuFather,jdbcType=VARCHAR},'%')) </if>" +
			"  <if test=\"menuFatherName != null and menuFatherName != '' \"> " +
			"    AND a2.NAME like CONCAT('%',CONCAT(#{menuFatherName,jdbcType=VARCHAR},'%')) </if>" +
			"</script>")
	int queryAllRow(Map<String, Object> param);


	/**
	 * 查询菜单详情
	 *
	 * @return SysMenu
	 */
	@Select("select a1.NOTE,a1.NO,a1.BUTTON_TAG,a1.MENU_BG,a1.MENU_FATHER,a1.MENU_ICON,a1.MENU_LEVEL,a1.MENU_ORDER,a1.MENU_SIZE,a1.NAME,a1.URL,a1.BUTTON,a2.NAME as MENU_FATHER_NAME " +
			"from SYS_MENU a1 " +
			"left join SYS_MENU a2 on a1.MENU_FATHER = a2.no " +
			"where a1.NO=#{no,jdbcType=VARCHAR}")

	@Results({
			@Result(column = "NO", property = "no", jdbcType = JdbcType.VARCHAR, id = true),
			@Result(column = "NAME", property = "name", jdbcType = JdbcType.VARCHAR),
			@Result(column = "URL", property = "url", jdbcType = JdbcType.VARCHAR),
			@Result(column = "MENU_FATHER", property = "menuFather", jdbcType = JdbcType.VARCHAR),
			@Result(column = "MENU_FATHER_NAME", property = "menuFatherName", jdbcType = JdbcType.VARCHAR),
			@Result(column = "BUTTON_TAG", property = "buttonTag", jdbcType = JdbcType.DECIMAL),
			@Result(column = "NOTE", property = "note", jdbcType = JdbcType.VARCHAR),
			@Result(column = "BUTTON", property = "button", jdbcType = JdbcType.VARCHAR),
			@Result(column = "MENU_ICON", property = "menuIcon", jdbcType = JdbcType.VARCHAR),
			@Result(column = "MENU_SIZE", property = "menuSize", jdbcType = JdbcType.DECIMAL),
			@Result(column = "MENU_LEVEL", property = "menuLevel", jdbcType = JdbcType.DECIMAL),
			@Result(column = "MENU_ORDER", property = "menuOrder", jdbcType = JdbcType.DECIMAL),
			@Result(column = "MENU_BG", property = "menuBg", jdbcType = JdbcType.VARCHAR),


	})
	SysMenu qryMenuDetailByMenuNo(String no);


	@Select(
			"select no,name " +
					"from SYS_MENU " +
					"where MENU_FATHER=#{menuFather,jdbcType=VARCHAR}"
	)

	@Results({
			@Result(column = "NO", property = "no", jdbcType = JdbcType.VARCHAR, id = true),
			@Result(column = "NAME", property = "name", jdbcType = JdbcType.VARCHAR),
	})
	List<SysMenu> querySysMenuListByNo(String no);


	/**
	 * 添加菜单信息
	 *
	 * @return SysMenu
	 */

	@Insert("insert into SYS_MENU(NO, NAME, MENU_FATHER, URL, MENU_LEVEL, MENU_ORDER, NOTE, MENU_ICON, MENU_SIZE, MENU_BG, BUTTON_TAG, BUTTON)" +
			"values(#{no,jdbcType=VARCHAR},#{name,jdbcType=VARCHAR},#{menuFather,jdbcType=VARCHAR},#{url,jdbcType=VARCHAR}," +
			"       #{menuLevel,jdbcType=DECIMAL},#{menuOrder,jdbcType=DECIMAL},#{note,jdbcType=VARCHAR},#{menuIcon,jdbcType=VARCHAR}," +
			"       #{menuSize,jdbcType=DECIMAL},#{menuBg,jdbcType=VARCHAR},#{buttonTag,jdbcType=DECIMAL},#{button,jdbcType=VARCHAR})")
	int insert(SysMenuPO sysMenuPO);

	/**
	 * 查询最大菜单编号
	 *
	 * @return SysMenu
	 */

	@Select("select max(NO) from SYS_MENU where MENU_FATHER=#{menuFather,jdbcType=VARCHAR}")
	String qryMaxNoByMenuFather(String menuFather);


	/**
	 * 更新菜单信息
	 *
	 * @return SysMenu
	 */

	@Update("update SYS_MENU set NO=#{no,jdbcType=VARCHAR},NAME=#{name,jdbcType=VARCHAR},MENU_FATHER=#{menuFather,jdbcType=VARCHAR}," +
			"URL=#{url,jdbcType=VARCHAR},MENU_LEVEL=#{menuLevel,jdbcType=DECIMAL},MENU_ORDER=#{menuOrder,jdbcType=DECIMAL}," +
			"NOTE=#{note,jdbcType=VARCHAR},MENU_ICON=#{menuIcon,jdbcType=VARCHAR},MENU_SIZE=#{menuSize,jdbcType=DECIMAL}," +
			"MENU_BG=#{menuBg,jdbcType=VARCHAR},BUTTON_TAG=#{buttonTag,jdbcType=DECIMAL},BUTTON=#{button,jdbcType=VARCHAR}" +
			"where NO=#{no,jdbcType=VARCHAR}")
	int updateByNo(SysMenuPO sysMenuPO);


	/**
	 * 删除菜单信息
	 *
	 * @return SysMenu
	 */

	@Delete("delete from SYS_MENU where NO=#{no,jdbcType=VARCHAR}")
	int delMenuByNo(String no);

	/**
	 * 删除编号
	 *
	 * @return SysMenu
	 */
	@Delete("delete from SYS_MENU_PERMISSION where MENU_NO=#{menuNo,jdbcType=VARCHAR}")
	int deletePermissionNoByMenuNo(@Param("menuNo") String menuNo);


	@Select("select count(1) " +
			"from SYS_MENU a1 " +
			"left join SYS_MENU a2 on a1.MENU_FATHER = a2.no " +
			"where a1.MENU_FATHER=#{menuFather,jdbcType=VARCHAR}")
	int qryMenuDetailByMenuFateher(String no);


	/**
	 * 添加某个叶子菜单下的接口
	 *
	 * @return DTO
	 */
	@Insert(
			"insert into SYS_MENU_PERMISSION(MENU_NO,PERMISSION_NO) " +
					"values(#{menuNo,jdbcType=VARCHAR},#{permissionNo,jdbcType=VARCHAR}) "
	)
	int insertPermissionList(@Param("menuNo") String menuNo, @Param("permissionNo") String permissionNo);

}
