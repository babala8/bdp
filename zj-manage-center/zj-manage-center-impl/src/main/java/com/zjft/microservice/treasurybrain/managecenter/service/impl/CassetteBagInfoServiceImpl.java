package com.zjft.microservice.treasurybrain.managecenter.service.impl;

import com.zjft.microservice.treasurybrain.managecenter.dto.CassetteBagInfoDTO;
import com.zjft.microservice.treasurybrain.managecenter.mapstruct.CassetteBagInfoConverter;
import com.zjft.microservice.treasurybrain.managecenter.po.CassetteBagInfoPO;
import com.zjft.microservice.treasurybrain.managecenter.po.TagInfoPO;
import com.zjft.microservice.treasurybrain.managecenter.repository.CassetteBagInfoMapper;
import com.zjft.microservice.treasurybrain.managecenter.repository.TagMapper;
import com.zjft.microservice.treasurybrain.managecenter.service.CassetteBagInfoService;
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
 * @since 2019/6/13 10:04
 */

@Slf4j
@Service
public class CassetteBagInfoServiceImpl implements CassetteBagInfoService {


	@Resource
	private CassetteBagInfoMapper cassetteBagInfoMapper;

	@Resource
	private TagMapper tagMapper;

	/**
	 * 分页查询钞袋信息
	 *
	 * @param paramsMap 分页查询条件
	 * @return 查询结果
	 */
	@Override
	public PageDTO<CassetteBagInfoDTO> qryByPage(Map<String, Object> paramsMap) {

		PageDTO<CassetteBagInfoDTO> pageDTO = new PageDTO<>(RetCodeEnum.SUCCEED);

		int pageSize = PageUtil.transParam2Page(paramsMap, pageDTO);
		int totalRow = cassetteBagInfoMapper.qryRowNumForPage(paramsMap);
		int totalPage = PageUtil.computeTotalPage(pageSize, totalRow);

		List<CassetteBagInfoPO> cassetteBagInfoPOS = cassetteBagInfoMapper.qryByPage(paramsMap);
		List<CassetteBagInfoDTO> retList = CassetteBagInfoConverter.INSTANCE.dto2domain(cassetteBagInfoPOS);

		pageDTO.setRetList(retList);
		pageDTO.setTotalPage(totalPage);
		pageDTO.setTotalRow(totalRow);


		return pageDTO;
	}

	/**
	 * 新增钞袋
	 *
	 * @param cassetteBagInfoDTO 新增的钞袋信息
	 * @return 新增结果
	 */
	@Override
	public DTO insert(CassetteBagInfoDTO cassetteBagInfoDTO) {

		//校验参数
		if (StringUtil.isNullorEmpty(cassetteBagInfoDTO.getBagNo())) {
			return new ObjectDTO("E1", "钞袋编号为空");
		}
		log.debug(cassetteBagInfoDTO.toString());
		//根据标签判断是否立即启用
		String tagTid = cassetteBagInfoDTO.getTagTid();
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

			//启用钞袋
			cassetteBagInfoDTO.setStatus(StatusEnum.CassetteBagInfoStatus.STATUS_USED.getStatus());
		} else {
			//新增钞袋初始化状态“未启用”
			cassetteBagInfoDTO.setStatus(StatusEnum.CassetteBagInfoStatus.STATUS_UNUSED.getStatus());
		}

		if (checkExistByBagNo(cassetteBagInfoDTO.getBagNo())) {
			log.error("编号为[" + cassetteBagInfoDTO.getBagNo() + "]的钞袋已存在");
			return new DTO(RetCodeEnum.AUDIT_OBJECT_EXIST);
		}

		CassetteBagInfoPO cassetteBagInfoPO = CassetteBagInfoConverter.INSTANCE.domain2dto(cassetteBagInfoDTO);
		int result = cassetteBagInfoMapper.insert(cassetteBagInfoPO);
		if (0 < result) {
			return new DTO(RetCodeEnum.SUCCEED);
		}
		return new DTO(RetCodeEnum.FAIL);
	}

	/**
	 * 根据钞袋编号删除钞袋
	 * 1.停用相关标签
	 * 2.删除钞箱数据
	 *
	 * @param bagNo 钞袋编号
	 * @return 删除结果响应信息
	 */
	@Override
	public DTO delByNo(String bagNo) {

		if (!checkExistByBagNo(bagNo)) {
			log.error("编号为[" + bagNo + "]的钞袋不存在");
			return new DTO(RetCodeEnum.AUDIT_OBJECT_DELETED);
		}

		CassetteBagInfoPO bagInfo = cassetteBagInfoMapper.qryByBagNo(bagNo);
		String tagTid = bagInfo.getTagTid();

		//更新标签状态
		TagInfoPO tagInfoPO = new TagInfoPO();
		tagInfoPO.setTagTid(tagTid);
		tagInfoPO.setStatus(StatusEnum.TagStatus.STATUS_STOPPED.getStatus());
		tagMapper.updateStatusByTagTid(tagInfoPO);

		int result = cassetteBagInfoMapper.delByNo(bagNo);
		if (1 == result) {
			return new DTO(RetCodeEnum.SUCCEED);
		}

		return new DTO(RetCodeEnum.FAIL);
	}

	/**
	 * 根据钞袋编号修改钞袋信息
	 * 1.查询原钞箱信息
	 * 2.如果标签信息变化，停用原有标签，启用新标签
	 * 3.更新钞袋信息
	 *
	 * @param cassetteBagInfoDTO 修改的钞袋信息
	 * @return 修改结果响应信息
	 */
	@Override
	public DTO updateByNo(CassetteBagInfoDTO cassetteBagInfoDTO) {

		if (!checkExistByBagNo(cassetteBagInfoDTO.getBagNo())) {
			log.error("编号为[" + cassetteBagInfoDTO.getBagNo() + "]的钞袋不存在");
			return new DTO(RetCodeEnum.AUDIT_OBJECT_DELETED);
		}
		//查询原钞箱信息
		CassetteBagInfoPO oldBagInfo = cassetteBagInfoMapper.qryByBagNo(cassetteBagInfoDTO.getBagNo());
		String oldTagTid = oldBagInfo.getTagTid();

		//标签是否更换
		String tagTid = cassetteBagInfoDTO.getTagTid();
		if (!oldTagTid.equals(cassetteBagInfoDTO.getTagTid())) {

			//启用新标签
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

		//修改钞袋信息
		CassetteBagInfoPO cassetteBagInfoPO = CassetteBagInfoConverter.INSTANCE.domain2dto(cassetteBagInfoDTO);
		int result = cassetteBagInfoMapper.updateByNo(cassetteBagInfoPO);
		if (1 == result) {
			return new DTO(RetCodeEnum.SUCCEED);
		}

		return new DTO(RetCodeEnum.FAIL);
	}

	/**
	 * 根据钞袋编号查询钞袋信息
	 *
	 * @param bagNo 钞袋编号
	 * @return 钞袋信息
	 */
	@Override
	public CassetteBagInfoDTO qryByBagNo(String bagNo) {
		CassetteBagInfoPO cassetteBagInfoPO = cassetteBagInfoMapper.qryByBagNo(bagNo);
		return CassetteBagInfoConverter.INSTANCE.dto2domain(cassetteBagInfoPO);
	}

	/**
	 * 钞袋是否已存在
	 *
	 * @param bagNo 钞袋编号
	 * @return 存在-true 不存在-false
	 */
	private boolean checkExistByBagNo(String bagNo) {
		Map<String, Object> paramsMap = new HashMap<>();
		paramsMap.put("bagNo", bagNo);
		return 0 < cassetteBagInfoMapper.qryRowNumForPage(paramsMap);
	}


}
