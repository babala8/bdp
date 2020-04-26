package com.zjft.microservice.treasurybrain.managecenter.web;

import com.zjft.microservice.treasurybrain.managecenter.dto.CassetteBagInfoDTO;
import com.zjft.microservice.treasurybrain.managecenter.service.CassetteBagInfoService;
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
 * @author liuyuan
 * @since 2019/6/13 11:17
 */

@Slf4j
@RestController
public class CassetteBagInfoResourceImpl implements CassetteBagInfoResource {

	@Resource
	CassetteBagInfoService cassetteBagInfoService;


	/**
	 * 分页查询钞袋信息
	 *
	 * @param paramMap 查询参数
	 * @return 响应信息
	 */
	@Override
	public PageDTO<CassetteBagInfoDTO> qryCassetteBagInfoByPage(Map<String, Object> paramMap) {

		log.debug("------------[qryCassetteInfo]CassetteBagInfoResource-------------");
			return cassetteBagInfoService.qryByPage(paramMap);
	}

	/**
	 * 新增钞袋
	 *
	 * @param cassetteBagInfoDTO 新增的钞袋信息
	 * @return 响应信息
	 */
	@Override
	public DTO insert(CassetteBagInfoDTO cassetteBagInfoDTO) {
		log.debug("------------[insertCassetteInfo]CassetteBagInfoResource-------------");
		try {
			return cassetteBagInfoService.insert(cassetteBagInfoDTO);
		} catch (Exception e) {
			log.error("新增钞袋信息异常：", e);
			return new ObjectDTO(RetCodeEnum.FAIL);
		}
	}

	/**
	 * 根据钞袋编号修改钞袋信息
	 *
	 * @param cassetteBagInfoDTO 钞袋信息
	 * @return 响应信息
	 */
	@Override
	public DTO modByNo(CassetteBagInfoDTO cassetteBagInfoDTO) {
		log.debug("------------[modeCassetteInfo]CassetteBagInfoResource-------------");
		try {
			//校验参数
			if (StringUtil.isNullorEmpty(cassetteBagInfoDTO.getBagNo())) {
				return new ObjectDTO("E1", "钞袋编号为空");
			}
			return cassetteBagInfoService.updateByNo(cassetteBagInfoDTO);
		} catch (Exception e) {
			log.error("修改钞袋信息异常：", e);
			return new ObjectDTO(RetCodeEnum.FAIL);
		}
	}

	/**
	 * 根据钞袋编号删除钞袋信息
	 *
	 * @param bagNo 钞袋编号
	 * @return 响应信息
	 */
	@Override
	public DTO delByNo(String bagNo) {
		log.debug("------------[deleteCassetteInfo]CassetteBagInfoResource-------------");
		try {
			//校验参数
			if (StringUtil.isNullorEmpty(bagNo)) {
				return new ObjectDTO("E1", "钞袋编号为空");
			}
			return cassetteBagInfoService.delByNo(bagNo);
		} catch (Exception e) {
			log.error("删除钞袋信息异常：", e);
			return new ObjectDTO(RetCodeEnum.FAIL);
		}
	}

}
