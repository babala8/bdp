package com.zjft.microservice.treasurybrain.managecenter.service;

import com.zjft.microservice.treasurybrain.managecenter.dto.CassetteInfoDTO;
import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.PageDTO;

import java.util.Map;

/**
 * @author liuyuan
 * @since 2019/6/12 08:45
 */

public interface CassetteInfoService {
	/**
	 * 分页查询钞箱信息
	 * @param paramsMap 分页参数
	 * @return 查询结果
	 */
	PageDTO<CassetteInfoDTO> qryCassetteInfoByPage(Map<String, Object> paramsMap);

	/**
	 * 新增钞箱信息
	 * @param cassetteInfoDTO 钞箱信息
	 * @return 新增结果
	 */
	DTO insert(CassetteInfoDTO cassetteInfoDTO);

	/**
	 * 按钞箱编号删除钞箱信息
	 * @param cassetteNo 钞箱编号
	 * @return 删除结果
	 */
	DTO delByNo(String cassetteNo);

	/**
	 * 按钞箱编号修改钞箱信息
	 * @param cassetteInfoDTO 钞箱信息
	 * @return 修改结果
	 */
	DTO modByNo(CassetteInfoDTO cassetteInfoDTO);

	/**
	 *
	 * @param cassetteNo 钞箱编号
	 * @return 钞箱信息
	 */
	CassetteInfoDTO qryByCassetteNo(String cassetteNo);


	int updateStatusByNo(Map<String,Object> map);
}
