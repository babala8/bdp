package com.zjft.microservice.treasurybrain.managecenter.service.impl;

import com.zjft.microservice.treasurybrain.managecenter.domain.CassetteInfoDO;
import com.zjft.microservice.treasurybrain.managecenter.dto.CassetteInfoDTO;
import com.zjft.microservice.treasurybrain.managecenter.mapstruct.CassetteInfoConverter;
import com.zjft.microservice.treasurybrain.managecenter.po.TagInfoPO;
import com.zjft.microservice.treasurybrain.managecenter.repository.CassetteInfoMapper;
import com.zjft.microservice.treasurybrain.managecenter.repository.TagMapper;
import com.zjft.microservice.treasurybrain.managecenter.service.CassetteInfoService;
import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.ObjectDTO;
import com.zjft.microservice.treasurybrain.common.dto.PageDTO;
import com.zjft.microservice.treasurybrain.common.util.PageUtil;
import com.zjft.microservice.treasurybrain.common.util.RetCodeEnum;
import com.zjft.microservice.treasurybrain.common.util.StatusEnum;
import com.zjft.microservice.treasurybrain.common.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author liuyuan
 * @since 2019/6/12 08:47
 */

@Slf4j
@Service
public class CassetteInfoServiceImpl implements CassetteInfoService {

	@Resource
	private CassetteInfoMapper cassetteInfoMapper;

	@Resource
	TagMapper tagMapper;

	/**
	 * 分页查询钞箱信息
	 *
	 * @param paramsMap 分页参数
	 * @return 响应数据
	 */
	@Override
	public PageDTO<CassetteInfoDTO> qryCassetteInfoByPage(Map<String, Object> paramsMap) {

		PageDTO<CassetteInfoDTO> pageDTO = new PageDTO<CassetteInfoDTO>(RetCodeEnum.SUCCEED);
		int pageSize = PageUtil.transParam2Page(paramsMap, pageDTO);
		int totalRow = cassetteInfoMapper.qryTotalRowOfCassetteInfo(paramsMap);
		int totalPage = PageUtil.computeTotalPage(pageSize, totalRow);

		List<CassetteInfoDO> cassetteInfoDOS = cassetteInfoMapper.qryCassetteInfoByPage(paramsMap);
		List<CassetteInfoDTO> retList = CassetteInfoConverter.INSTANCE.dto2domain(cassetteInfoDOS);

		pageDTO.setRetList(retList);
		pageDTO.setTotalPage(totalPage);
		pageDTO.setTotalRow(totalRow);


		return pageDTO;
	}

	/**
	 * 新增钞箱
	 * 1.查询该编号钞箱是否已经存在
	 * 2.根据标签判断是否立即启用，并修改标签状态
	 * 3.新增钞箱信息
	 * @param cassetteInfoDTO 钞箱信息
	 * @return 新增结果
	 */
	@Override
	public DTO insert(CassetteInfoDTO cassetteInfoDTO) {

		CassetteInfoDO cassetteInfoDO = CassetteInfoConverter.INSTANCE.domain2dto(cassetteInfoDTO);

		//查询该编号钞箱是否已经存在
		if (0 < exist(cassetteInfoDTO.getCassetteNo())) {
			return new DTO(RetCodeEnum.AUDIT_OBJECT_EXIST);
		}

		//根据标签判断是否立即启用
		String tagTid = cassetteInfoDTO.getTagTid();
		if (!StringUtil.isNullorEmpty(tagTid)) {
			//标签是否存在
			int tagNum = tagMapper.queryByTagTid(tagTid);
			if (0 == tagNum) {
				log.error("标签[" + tagTid + "]不存在");
				return new ObjectDTO("FF", "标签[" + tagTid + "]不存在");
			}
			//校验标签状态
			int tagStatus = tagMapper.queryTagStatusByTagTid(tagTid);
			if (StatusEnum.TagStatus.STATUS_USED.getStatus() == tagStatus) {
				log.error("标签[" + tagTid + "]已被占用");
				return new ObjectDTO("FF", "标签[" + tagTid + "]已被占用");
			}

			//更新标签状态
			TagInfoPO tagInfoPO = new TagInfoPO();
			tagInfoPO.setTagTid(tagTid);
			tagInfoPO.setStatus(StatusEnum.TagStatus.STATUS_USED.getStatus());
			tagMapper.updateStatusByTagTid(tagInfoPO);
			//启用钞箱
			cassetteInfoDTO.setStatus(StatusEnum.CassetteInfoStatus.CASSETTE_STATUS_STARTUP.getStatus());
		} else {
			//新增钞箱初始化状态“未启用”
			cassetteInfoDTO.setStatus(StatusEnum.CassetteInfoStatus.CASSETTE_STATUS_UNUSED.getStatus());
		}

		//新增钞箱信息
		int result = cassetteInfoMapper.insert(cassetteInfoDO);
		if (1 == result) {
			return new DTO(RetCodeEnum.SUCCEED);
		}

		return new DTO(RetCodeEnum.FAIL);
	}

	/**
	 * 删除钞箱
	 * 1.确认钞箱是否存在
	 * 2.询原标签信息，停用原标签
	 * 3.删除钞箱
	 *
	 * @param cassetteNo 钞箱编号
	 * @return 删除结果
	 */
	@Override
	public DTO delByNo(String cassetteNo) {
		//确认钞箱是否存在
		if (0 == exist(cassetteNo)) {
			return new DTO(RetCodeEnum.AUDIT_OBJECT_DELETED);
		}

		//查询原标签信息，停用原标签
		CassetteInfoDO cassetteInfoDO = cassetteInfoMapper.qryByCassetteNo(cassetteNo);
		String tagTid = cassetteInfoDO.getTagTid();

		//停用标签状态
		TagInfoPO tagInfoPO = new TagInfoPO();
		tagInfoPO.setTagTid(tagTid);
		tagInfoPO.setStatus(StatusEnum.TagStatus.STATUS_STOPPED.getStatus());
		tagMapper.updateStatusByTagTid(tagInfoPO);

		//删除钞箱
		int deleteFlag = cassetteInfoMapper.delByNo(cassetteNo);
		if (0 < deleteFlag) {
			return new DTO(RetCodeEnum.SUCCEED);
		}

		return new DTO(RetCodeEnum.FAIL);
	}

	/**
	 * 修改钞箱信息
	 * 1.确认钞箱是否存在
	 * 2.如果标签信息变化，停用原有标签，启用新标签
	 * 3.更新钞箱信息
	 *
	 * @param cassetteInfoDTO 修改后的钞箱信息
	 * @return 修改结果
	 */
	@Override
	public DTO modByNo(CassetteInfoDTO cassetteInfoDTO) {
		//确认钞箱是否存在
		if (0 == exist(cassetteInfoDTO.getCassetteNo())) {
			return new DTO(RetCodeEnum.AUDIT_OBJECT_DELETED);
		}

		//查询原钞箱信息
		CassetteInfoDO oldCassetteInfoDO = cassetteInfoMapper.qryByCassetteNo(cassetteInfoDTO.getCassetteNo());
		String oldTagTid = oldCassetteInfoDO.getTagTid();

		//标签是否更换
		String tagTid = cassetteInfoDTO.getTagTid();
		if (!oldTagTid.equals(cassetteInfoDTO.getTagTid())) {
			//新标签是否存在
			int tagNum = tagMapper.queryByTagTid(tagTid);
			if (0 == tagNum) {
				log.error("标签[" + tagTid + "]不存在");
				return new ObjectDTO("FF", "标签[" + tagTid + "]不存在");
			}
			//新校验标签状态
			int tagStatus = tagMapper.queryTagStatusByTagTid(tagTid);
			if (StatusEnum.TagStatus.STATUS_USED.getStatus() == tagStatus) {
				log.error("标签[" + tagTid + "]已被占用");
				return new ObjectDTO("FF", "标签[" + tagTid + "]已被占用");
			}

			//停用旧标签状态
			TagInfoPO tagInfoPO = new TagInfoPO();
			tagInfoPO.setTagTid(oldTagTid);
			tagInfoPO.setStatus(StatusEnum.TagStatus.STATUS_STOPPED.getStatus());
			tagMapper.updateStatusByTagTid(tagInfoPO);

			//更新标签状态
			tagInfoPO = new TagInfoPO();
			tagInfoPO.setTagTid(tagTid);
			tagInfoPO.setStatus(StatusEnum.TagStatus.STATUS_USED.getStatus());
			tagMapper.updateStatusByTagTid(tagInfoPO);

		}

		//更新钞箱信息
		CassetteInfoDO cassetteInfoDO = CassetteInfoConverter.INSTANCE.domain2dto(cassetteInfoDTO);
		int modFlag = cassetteInfoMapper.modByNo(cassetteInfoDO);
		if (0 < modFlag) {
			return new DTO(RetCodeEnum.SUCCEED);
		}

		return new DTO(RetCodeEnum.FAIL);
	}

	/**
	 * 根据编号查询钞箱信息
	 *
	 * @param cassetteNo 钞箱编号
	 * @return 钞箱信息
	 */
	@Override
	public CassetteInfoDTO qryByCassetteNo(String cassetteNo) {
		CassetteInfoDO cassetteInfoDO = cassetteInfoMapper.qryByCassetteNo(cassetteNo);
		return CassetteInfoConverter.INSTANCE.dto2domain(cassetteInfoDO);
	}

	/**
	 * 根据钞箱编号查询钞箱是否存在
	 *
	 * @param cassetteNo 钞箱编号
	 * @return 钞箱数量
	 */
	private int exist(String cassetteNo) {
		Map<String, Object> checkMap = new HashMap<String, Object>();
		checkMap.put("cassetteNo", cassetteNo);
		return cassetteInfoMapper.qryTotalRowOfCassetteInfo(checkMap);
	}

	@Override
	public int updateStatusByNo(Map<String,Object> map){
		return cassetteInfoMapper.updateStatusByNo(map);
	}
}
