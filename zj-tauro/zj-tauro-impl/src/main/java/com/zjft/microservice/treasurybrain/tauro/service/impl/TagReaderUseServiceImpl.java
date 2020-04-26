package com.zjft.microservice.treasurybrain.tauro.service.impl;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.PageDTO;
import com.zjft.microservice.treasurybrain.common.util.*;
import com.zjft.microservice.treasurybrain.managecenter.web_inner.TagReaderInfoInnerResource;
import com.zjft.microservice.treasurybrain.tauro.domain.TagReaderUseInfoDO;
import com.zjft.microservice.treasurybrain.tauro.dto.TagReaderUseDTO;
import com.zjft.microservice.treasurybrain.tauro.mapstruct.TagReaderUseConverter;
import com.zjft.microservice.treasurybrain.tauro.po.TagReaderInfoPO;
import com.zjft.microservice.treasurybrain.tauro.po.TagReaderUseInfoPO;
import com.zjft.microservice.treasurybrain.tauro.repository.TagReaderUseMapper;
import com.zjft.microservice.treasurybrain.tauro.service.TagReaderUseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, timeout = 3600, rollbackFor = Exception.class)
public class TagReaderUseServiceImpl implements TagReaderUseService {

	@Resource
	private TagReaderUseMapper tagReaderUseMapper;

	@Resource
	private TagReaderInfoInnerResource tagReaderInfoInnerResource;

	/**
	 * 分页查询手持机领用记录
	 *
	 * @param paramMap 手持机领用记录查询参数集合
	 * @return 手持机领用记录
	 */
	@Override
	public PageDTO<TagReaderUseDTO> queryTagReaderUseListByPage(Map<String, Object> paramMap) {
		log.info("------------[queryTagReaderUseListByPage]TagReaderUseService-------------");
		PageDTO<TagReaderUseDTO> pageDTO = new PageDTO<>(RetCodeEnum.FAIL);
		Integer tagReaderUseStatus = StringUtil.objectToInt(paramMap.get("tagReaderUseStatus"));
		paramMap.put("tagReaderUseStatus", tagReaderUseStatus);
		int pageSize = PageUtil.transParam2Page(paramMap, pageDTO);
		String name = StringUtil.parseString(paramMap.get("requestOpNo"));
		List<String> userNameList = tagReaderUseMapper.qryUserName(name);
		paramMap.put("userNameList", userNameList);

		// 获取数据总条数
		int totalRow = tagReaderUseMapper.qryTotalRowTagReaderUse(paramMap);
		int totalPage = PageUtil.computeTotalPage(pageSize, totalRow);

		//获取分页数据
		List<TagReaderUseInfoDO> list = tagReaderUseMapper.queryTagReaderUseListByPage(paramMap);
		List<TagReaderUseDTO> dtoList = TagReaderUseConverter.INSTANCE.domain2dto(list);
		pageDTO.setTotalRow(totalRow);
		pageDTO.setTotalPage(totalPage);
		pageDTO.setPageSize(dtoList.size());
		pageDTO.setRetList(dtoList);
		pageDTO.setResult(RetCodeEnum.SUCCEED);
		return pageDTO;
	}

	/**
	 * 目前需求中，手持机领用后更新状态为审核通过，不需要审核过程了
	 *
	 * @param tagReaderUseDTO 手持机领用信息
	 * @return 是否成功
	 */
	@Override
	public DTO addTagReaderUseInfo(TagReaderUseDTO tagReaderUseDTO) {
		log.info("------------[addTagReaderUseInfo]TagReaderUseService-------------");
		TagReaderUseInfoPO tagReaderUseInfoPO = TagReaderUseConverter.INSTANCE.dto2domain(tagReaderUseDTO);

		//获取当前日期
		Date now = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		String date = dateFormat.format(now);
		//获取已有领用记录的最大顺序号
		String MaxNo = tagReaderUseMapper.getMaxNo();
		//根据“8位日期+6位顺序号”编码规则拼接领用记录编号tagReaderUseNo
		String tagReaderUseNo = null;
		if (MaxNo == null || "".equals(MaxNo)) {
			tagReaderUseNo = date + "000001";
		} else {
			int Max = Integer.parseInt(MaxNo) + 1;
			String num = String.format("%06d", Max);
			tagReaderUseNo = date + num;
		}
		tagReaderUseInfoPO.setTagReaderUseNo(tagReaderUseNo);
		//获取当前登录的用户名，作为申请人
//			String requestOpNo = ServletRequestUtil.getUsername();
//			tagReaderUseInfoPO.setRequestOpNo(requestOpNo);
		//获取当前登录的用户名，作为发放人
		String grantOpNo = ServletRequestUtil.getUsername();
		tagReaderUseInfoPO.setGrantOpNo(grantOpNo);
		//当前时间作为申请日期，申请时间
		String requestDate = new SimpleDateFormat("yyyy-MM-dd").format(now);
		String requestTime = new SimpleDateFormat("HH:mm:ss").format(now);
		tagReaderUseInfoPO.setRequestDate(requestDate);
		tagReaderUseInfoPO.setRequestTime(requestTime);
		tagReaderUseInfoPO.setReturnDate("");
		tagReaderUseInfoPO.setReturnTime("");

		//更新读写器领用状态为审核通过--2
		Integer tagReaderUseStatus = StatusEnum.TagReaderUseStatus.STATUS_AUDITED.getStatus();
		tagReaderUseInfoPO.setTagReaderUseStatus(tagReaderUseStatus);
		int insert = tagReaderUseMapper.insert(tagReaderUseInfoPO);

		//申请领用时更新读写器状态为已申请--2
		Integer tagReaderStatus = StatusEnum.TagReaderStatus.STATUS_APPLIED.getStatus();
		TagReaderInfoPO tagReaderInfoPO = new TagReaderInfoPO();
		tagReaderInfoPO.setTagReaderNo(tagReaderUseDTO.getTagReaderNo());
		tagReaderInfoPO.setStatus(tagReaderStatus);
		int update = tagReaderUseMapper.updateTagReaderStatus(tagReaderInfoPO);

		if (insert == 1 && update == 1) {
			return new DTO(RetCodeEnum.SUCCEED);
		}

		return new DTO(RetCodeEnum.FAIL);
	}

	/**
	 * 修改手持机领用记录
	 *
	 * @param tagReaderUseDTO 手持机领用信息
	 * @return 是否成功
	 */
	@Override
	public DTO modTagReaderUseInfo(TagReaderUseDTO tagReaderUseDTO) {
		log.info("------------[modTagReaderUseInfo]TagReaderUseService-------------");
		DTO dto = new DTO(RetCodeEnum.FAIL);
		TagReaderUseInfoPO tagInfoPO = TagReaderUseConverter.INSTANCE.dto2domain(tagReaderUseDTO);
		int x = tagReaderUseMapper.update(tagInfoPO);
		if (x == 1) {
			dto.setResult(RetCodeEnum.SUCCEED);
		}
		return dto;
	}

	/**
	 * 根据编号删除申请记录
	 * 1.根据查询记录信息，如果状态为提交审批通过之后，则不能删除
	 * 2.删除申请记录前修改读写器状态为未领用
	 * 3.删除记录
	 *
	 * @param tagReaderUseNo 编号
	 * @return 是否成功
	 */
	@Override
	public DTO delTagReaderUseInfoByTagReaderUseNo(String tagReaderUseNo) {
		log.info("------------[delTagReaderUseInfoByTagReaderUseNo]TagReaderUseService-------------");
		DTO dto = new DTO(RetCodeEnum.FAIL);
		/*根据查询记录信息，如果状态为提交审批通过之后，则不能删除*/
		TagReaderUseInfoDO tagReaderUseInfoDO = tagReaderUseMapper.queryTagReaderUseDetail(tagReaderUseNo);
		if (null == tagReaderUseInfoDO || StringUtil.isNullorEmpty(tagReaderUseInfoDO.getTagReaderNo())) {
			dto.setResult(RetCodeEnum.AUDIT_OBJECT_DELETED);
			dto.setRetMsg("申请记录不存在或已删除");
			return dto;
		}
		if (StatusEnum.TagReaderUseStatus.STATUS_AUDITED.getStatus() == tagReaderUseInfoDO.getTagReaderUseStatus()
				|| StatusEnum.TagReaderUseStatus.STATUS_RETURNED.getStatus() == tagReaderUseInfoDO.getTagReaderUseStatus()) {
			dto.setRetCode(RetCodeEnum.FAIL.getCode());
			dto.setRetMsg("申请记录已审批通过，不可删除");
			return dto;
		}
		/*删除申请记录前修改读写器状态为未领用*/
		TagReaderInfoPO tagReaderInfoPO = new TagReaderInfoPO();
		tagReaderInfoPO.setTagReaderNo(tagReaderUseInfoDO.getTagReaderNo());
		tagReaderInfoPO.setStatus(StatusEnum.TagReaderStatus.STATUS_UNUSED.getStatus());
		tagReaderUseMapper.updateTagReaderStatus(tagReaderInfoPO);

		/*删除记录*/
		int result = tagReaderUseMapper.delTagReaderUseInfoByTagReaderUseNo(tagReaderUseNo);
		if (result == 1) {
			dto.setResult(RetCodeEnum.SUCCEED);
		}

		return dto;
	}

	@Override
	public Integer queryTagReaderStatusByNo(String tagReaderNo) {
		log.info("------------[queryTagStatusByTagTid]TagService-------------");
		return tagReaderInfoInnerResource.queryTagReaderStatusByNo(tagReaderNo);
	}

	@Override
	public TagReaderUseDTO queryTagReaderUseDetail(String tagReaderUseNo) {
		log.info("------------[queryTagReaderUseDetail]TagService-------------");
		TagReaderUseDTO tagReaderUseDTO = new TagReaderUseDTO(RetCodeEnum.FAIL);
		try {
			TagReaderUseInfoDO tagReaderUseInfoDO = tagReaderUseMapper.queryTagReaderUseDetail(tagReaderUseNo);
			tagReaderUseDTO = TagReaderUseConverter.INSTANCE.domain2dto(tagReaderUseInfoDO);
			if (tagReaderUseDTO != null) {
				tagReaderUseDTO.setResult(RetCodeEnum.SUCCEED);
			}
		} catch (Exception e) {
			log.error("[queryTagReaderUseDetail]Fail", e);
			tagReaderUseDTO.setResult(RetCodeEnum.EXCEPTION);
		}
		return tagReaderUseDTO;
	}

	/**
	 * 手持机审核领用接口，现需求中没有使用
	 *
	 * @param tagReaderUseDTO 手持机领用审核信息
	 * @return 是否成功
	 */
	@Override
	public DTO auditTagReaderUseInfo(TagReaderUseDTO tagReaderUseDTO) {
		log.info("------------[auditTagReaderUseInfo]TagReaderUseService-------------");
		DTO dto = new DTO(RetCodeEnum.FAIL);
		TagReaderUseInfoPO tagReaderUseInfoPO = TagReaderUseConverter.INSTANCE.dto2domain(tagReaderUseDTO);

		//获取当前登录的用户名
		String grantOpNo = ServletRequestUtil.getUsername();
		//获取当前日期
		Date now = new Date();
		String grantDate = new SimpleDateFormat("yyyy-MM-dd").format(now);
		String grantTime = new SimpleDateFormat("HH:mm:ss").format(now);
		tagReaderUseInfoPO.setGrantOpNo(grantOpNo);
		tagReaderUseInfoPO.setGrantDate(grantDate);
		tagReaderUseInfoPO.setGrantTime(grantTime);

		int reviewResult = tagReaderUseInfoPO.getReviewResult();
		int update = 0;
		TagReaderInfoPO tagReaderInfoPO = new TagReaderInfoPO();
		tagReaderInfoPO.setTagReaderNo(tagReaderUseDTO.getTagReaderNo());
		if (reviewResult == 1) {//审核通过，更新读写器状态为3--已领用、读写器领用状态为2--已审核（通过）
			Integer status = 3;
			tagReaderUseInfoPO.setTagReaderUseStatus(2);
			tagReaderInfoPO.setStatus(status);
			update = tagReaderUseMapper.updateTagReaderStatus(tagReaderInfoPO);
		} else {//审核未通过，更新读写器状态为1--未领用、读写器领用状态为2--已审核（拒绝）
			Integer status = 1;
			tagReaderUseInfoPO.setTagReaderUseStatus(3);
			tagReaderInfoPO.setStatus(status);
			update = tagReaderUseMapper.updateTagReaderStatus(tagReaderInfoPO);
		}
		int audit = tagReaderUseMapper.audit(tagReaderUseInfoPO);
		if (audit == 1 && update == 1) {
			dto.setResult(RetCodeEnum.SUCCEED);
		}
		return dto;
	}

	/**
	 * 归还手持机
	 *
	 * @param tagReaderUseDTO 手持机归还信息
	 * @return 是否成功
	 */
	@Override
	public DTO returnTagReaderUseInfo(TagReaderUseDTO tagReaderUseDTO) {
		log.info("------------[returnTagReaderUseInfo]TagReaderUseService-------------");
		DTO dto = new DTO(RetCodeEnum.FAIL);
		TagReaderUseInfoPO tagReaderUseInfoPO = TagReaderUseConverter.INSTANCE.dto2domain(tagReaderUseDTO);

		//获取当前日期
		Date now = new Date();
		String returnDate = new SimpleDateFormat("yyyy-MM-dd").format(now);
		String returnTime = new SimpleDateFormat("HH:mm:ss").format(now);
		tagReaderUseInfoPO.setReturnDate(returnDate);
		tagReaderUseInfoPO.setReturnTime(returnTime);

		//手持机归还，更新读写器状态为1--未领用、读写器领用状态为4--已归还
		Integer tagReaderStatus = StatusEnum.TagReaderStatus.STATUS_UNUSED.getStatus();
		Integer tagReaderUseStatus = StatusEnum.TagReaderUseStatus.STATUS_RETURNED.getStatus();

		TagReaderInfoPO tagReaderInfoPO = new TagReaderInfoPO();
		tagReaderInfoPO.setTagReaderNo(tagReaderUseDTO.getTagReaderNo());
		tagReaderInfoPO.setStatus(tagReaderStatus);
		tagReaderUseInfoPO.setTagReaderUseStatus(tagReaderUseStatus);

		int update = tagReaderUseMapper.updateTagReaderStatus(tagReaderInfoPO);
		int returnTagReader = tagReaderUseMapper.returnBack(tagReaderUseInfoPO);
		if (returnTagReader == 1 && update == 1) {
			dto.setResult(RetCodeEnum.SUCCEED);
		}
		return dto;
	}
}
