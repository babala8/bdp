package com.zjft.microservice.treasurybrain.managecenter.service;

import com.zjft.microservice.treasurybrain.managecenter.dto.CassetteBagInfoDTO;
import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.PageDTO;

import java.util.Map;

/**
 * @author liuyuan
 * @since 2019/6/13 09:57
 */

public interface CassetteBagInfoService {
	/**
	 * 分页查询
	 *
	 * @param paramsMap 分页查询数据
	 * @return 查询结果
	 */
	PageDTO<CassetteBagInfoDTO> qryByPage(Map<String, Object> paramsMap);

	/**
	 * 新增钞袋
	 *
	 * @param cassetteBagInfoDTO 新增钞袋信息
	 * @return 新增记录数
	 */
	DTO insert(CassetteBagInfoDTO cassetteBagInfoDTO);

	/**
	 * 按编号删除钞袋信息
	 *
	 * @param bagNo 钞袋编号
	 * @return 删除记录数
	 */
	DTO delByNo(String bagNo);

	/**
	 * 根据编号修改钞袋信息
	 *
	 * @param cassetteBagInfoDTO 修改后的钞袋信息
	 * @return 修改结果
	 */
	DTO updateByNo(CassetteBagInfoDTO cassetteBagInfoDTO);

	/**
	 * 根据查询钞袋编号查询钞袋信息
	 * @param bagNo 钞袋编号
	 * @return 钞袋信息
	 */
	CassetteBagInfoDTO qryByBagNo(String bagNo);
}
