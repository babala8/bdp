package com.zjft.microservice.treasurybrain.managecenter.repository;

import com.zjft.microservice.treasurybrain.managecenter.domain.CassetteInfoDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @author liuyuan
 * @since 2019/6/12 08:40
 */

@Mapper
public interface CassetteInfoMapper {

	/**
	 * 分页查询
	 *
	 * @param paramMaps 分页查询参数
	 * @return 查询结果列表
	 */
	List<CassetteInfoDO> qryCassetteInfoByPage(Map<String, Object> paramMaps);

	/**
	 * 分页查询查询总行数
	 *
	 * @param paramsMap 分页查询参数
	 * @return 总行数
	 */
	int qryTotalRowOfCassetteInfo(Map<String, Object> paramsMap);

	/**
	 * 新增
	 *
	 * @param cassetteInfoDO 新增的钞箱信息
	 * @return 新增数量
	 */
	int insert(CassetteInfoDO cassetteInfoDO);

	/**
	 * 按钞箱编号删除
	 *
	 * @param cassetteNo 钞箱编号
	 * @return 删除数量
	 */
	int delByNo(String cassetteNo);

	/**
	 * 根据钞箱编号修改钞箱信息
	 *
	 * @param cassetteInfoDO 修改后的钞箱信息
	 * @return 修改数量
	 */
	int modByNo(CassetteInfoDO cassetteInfoDO);

	/**
	 * 根据钞箱编号查询钞箱信息
	 *
	 * @param cassetteNo 钞箱编号
	 * @return 查询结果
	 */
	CassetteInfoDO qryByCassetteNo(String cassetteNo);

	int updateStatusByNo(Map<String,Object> map);
}
