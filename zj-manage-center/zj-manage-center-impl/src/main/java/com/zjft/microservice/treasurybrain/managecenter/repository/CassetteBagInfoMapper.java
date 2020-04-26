package com.zjft.microservice.treasurybrain.managecenter.repository;

import com.zjft.microservice.treasurybrain.managecenter.po.CassetteBagInfoPO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @author liuyuan
 * @since 2019/6/13 09:44
 */

@Mapper
public interface CassetteBagInfoMapper {
	/**
	 * 分页查询
	 *
	 * @param paramsMap 分页查询条件
	 * @return 查询结果
	 */
	List<CassetteBagInfoPO> qryByPage(Map<String, Object> paramsMap);

	/**
	 * 查询行数
	 *
	 * @param paramsMap 分页查询条件
	 * @return 行数
	 */
	int qryRowNumForPage(Map<String, Object> paramsMap);

	/**
	 * 新增
	 *
	 * @param cassetteBagInfoPO 新增钞袋信息
	 * @return 新增记录数
	 */
	int insert(CassetteBagInfoPO cassetteBagInfoPO);

	/**
	 * 删除
	 *
	 * @param bagNo 钞袋编号
	 * @return 删除数量
	 */
	int delByNo(String bagNo);

	/**
	 * 修改
	 *
	 * @param cassetteBagInfoPO 修改后的钞袋信息
	 * @return 修改记录数
	 */
	int updateByNo(CassetteBagInfoPO cassetteBagInfoPO);

	/**
	 * 根据钞袋编号查询钞袋信息
	 *
	 * @param bagNo 钞袋编号
	 * @return 钞袋信息
	 */
	CassetteBagInfoPO qryByBagNo(String bagNo);
}
