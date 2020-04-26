package com.zjft.microservice.treasurybrain.managecenter.web;

import com.zjft.microservice.treasurybrain.managecenter.dto.CassetteInfoDTO;
import com.zjft.microservice.treasurybrain.managecenter.service.CassetteInfoService;
import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.ObjectDTO;
import com.zjft.microservice.treasurybrain.common.dto.PageDTO;
import com.zjft.microservice.treasurybrain.common.util.RetCodeEnum;
import com.zjft.microservice.treasurybrain.common.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 钞箱信息controller
 *
 * @author liuyuan
 * @since 2019/6/12 10:50
 */

@Slf4j
@RestController
public class CassetteInfoResourceImpl implements CassetteInfoResource {

	@Resource
	CassetteInfoService cassetteInfoService;

	/**
	 * 分页查询钞箱
	 *
	 * @param paramMap 查询参数
	 * @return 响应信息
	 */
	@Override
	public PageDTO<CassetteInfoDTO> qryCassetteInfoByPage(Map<String, Object> paramMap) {

		log.info("------------[qryCassetteInfo]qryCassetteInfoWebService-------------");
		return cassetteInfoService.qryCassetteInfoByPage(paramMap);

	}

	/**
	 * 新增钞箱
	 *
	 * @param cassetteInfoDTO 新增的钞箱信息
	 * @return 响应信息
	 */
	@Override
	public DTO insert(CassetteInfoDTO cassetteInfoDTO) {

		try {

			//校验参数
			if (StringUtil.isNullorEmpty(cassetteInfoDTO.getCassetteNo())) {
				return new ObjectDTO("E1", "钞箱编号为空");
			}

			return cassetteInfoService.insert(cassetteInfoDTO);
		} catch (Exception e) {
			log.error("新增钞箱信息异常：", e);
			return new ObjectDTO(RetCodeEnum.FAIL);
		}
	}

	/**
	 * 根据钞箱编号删除钞箱信息
	 *
	 * @param cassetteNo 钞箱编号
	 * @return 响应信息
	 */
	@Override
	public DTO delByNo(String cassetteNo) {
		try {
			//校验参数
			if (StringUtil.isNullorEmpty(cassetteNo)) {
				return new ObjectDTO("E1", "钞箱编号为空");
			}
			return cassetteInfoService.delByNo(cassetteNo);
		} catch (Exception e) {
			log.error("删除钞箱信息异常：", e);
			return new ObjectDTO(RetCodeEnum.FAIL);
		}
	}

	/**
	 * 根据钞箱编号修改钞箱信息
	 *
	 * @param cassetteInfoDTO 修改后的钞箱信息
	 * @return 响应信息
	 */
	@Override
	public DTO modByNo(CassetteInfoDTO cassetteInfoDTO) {
		try {
			//校验参数
			if (StringUtil.isNullorEmpty(cassetteInfoDTO.getCassetteNo())) {
				return new ObjectDTO("E1", "钞箱编号为空");
			}
			return cassetteInfoService.modByNo(cassetteInfoDTO);
		} catch (Exception e) {
			log.error("修改钞箱信息异常：", e);
			return new ObjectDTO(RetCodeEnum.FAIL);
		}
	}

}
