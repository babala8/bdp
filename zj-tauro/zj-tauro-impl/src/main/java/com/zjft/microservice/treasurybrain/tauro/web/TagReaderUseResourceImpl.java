package com.zjft.microservice.treasurybrain.tauro.web;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.PageDTO;
import com.zjft.microservice.treasurybrain.common.util.RetCodeEnum;
import com.zjft.microservice.treasurybrain.common.util.StringUtil;
import com.zjft.microservice.treasurybrain.tauro.dto.TagReaderUseDTO;
import com.zjft.microservice.treasurybrain.tauro.service.TagReaderUseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

@Slf4j
@RestController
public class TagReaderUseResourceImpl implements TagReaderUseResource {

	@Resource
	private TagReaderUseService tagReaderUseService;

	@Override
	public PageDTO<TagReaderUseDTO> queryTagReaderUseListByPage(Map<String, Object> paramMap) {
		log.info("------------[queryTagReaderUseListByPage]TagReaderUseResource-------------");
		try {
			return tagReaderUseService.queryTagReaderUseListByPage(paramMap);
		} catch (Exception e) {
			log.error("查询手持机领用记录失败", e);
			return new PageDTO<>(RetCodeEnum.EXCEPTION);
		}
	}

	@Override
	public DTO addTagReaderUseInfo(TagReaderUseDTO TagReaderUseDTO) {
		log.info("------------[addTagReaderUseInfo]TagReaderUseResource-------------");
		try {
			String tagReaderNo = TagReaderUseDTO.getTagReaderNo();
			String crashFlag = TagReaderUseDTO.getCrashFlag();
			if (StringUtil.isNullorEmpty(tagReaderNo) || StringUtil.isNullorEmpty(crashFlag)) {
				return new DTO(RetCodeEnum.PARAM_LACK);
			} else if (tagReaderUseService.queryTagReaderStatusByNo(tagReaderNo) == 1) {//此处‘1’为未领用状态的读写器
				return tagReaderUseService.addTagReaderUseInfo(TagReaderUseDTO);
			}
			log.error("该读写器状态不是未领用，不能申请领用");
			return new DTO(RetCodeEnum.FAIL);
		} catch (Exception e) {
			log.error("新增手持机领用记录异常：" + e.getMessage());
			return new DTO(RetCodeEnum.EXCEPTION);
		}
	}

	@Override
	public DTO modTagReaderUseInfo(TagReaderUseDTO tagReaderUseDTO) {
		log.info("------------[modTagReaderUseInfo]TagReaderUseResource-------------");
		try {
			String tagReaderUseNo = tagReaderUseDTO.getTagReaderUseNo();
			if (StringUtil.isNullorEmpty(tagReaderUseNo)) {
				return new DTO(RetCodeEnum.PARAM_LACK);
			}
			return tagReaderUseService.modTagReaderUseInfo(tagReaderUseDTO);
		} catch (Exception e) {
			log.error("修改手持机领用记录失败", e);
			return new DTO(RetCodeEnum.EXCEPTION);
		}
	}

	@Override
	public TagReaderUseDTO queryTagReaderUseDetail(String tagReaderUseNo) {
		log.info("------------[queryTagReaderUseDetail]TagReaderUseResource-------------");
		try {
			if (StringUtil.isNullorEmpty(tagReaderUseNo)) {
				return new TagReaderUseDTO(RetCodeEnum.PARAM_LACK);
			}
			return tagReaderUseService.queryTagReaderUseDetail(tagReaderUseNo);
		} catch (Exception e) {
			log.error("查询手持机领用记录详情失败", e);
			return new TagReaderUseDTO(RetCodeEnum.EXCEPTION);
		}
	}

	@Override
	public DTO returnTagReaderUseInfo(TagReaderUseDTO tagReaderUseDTO) {
		log.info("------------[returnTagReaderUseInfo]TagReaderUseResource-------------");
		DTO dto = new DTO();
		try {
			if (StringUtil.isNullorEmpty(tagReaderUseDTO.getTagReaderUseNo()) ||
					StringUtil.isNullorEmpty(tagReaderUseDTO.getTagReaderNo()) ||
					StringUtil.isNullorEmpty(tagReaderUseDTO.getReturnOpNo()) ||
					StringUtil.isNullorEmpty(tagReaderUseDTO.getSignOpNo())) {
				return new DTO(RetCodeEnum.PARAM_LACK);
			} else if (tagReaderUseDTO.getReturnOpNo().equals(tagReaderUseDTO.getSignOpNo())) {
				//归还和接受不能为同一人
				dto.setRetMsg("归还人和接收人不能为同一人！");
				dto.setRetCode(RetCodeEnum.FAIL.getCode());
				return dto;
			}
			return tagReaderUseService.returnTagReaderUseInfo(tagReaderUseDTO);
		} catch (Exception e) {
			log.error("手持机归还登记失败", e);
			dto.setRetCode(RetCodeEnum.EXCEPTION.getCode());
			dto.setRetMsg("手持机归还异常！");
			return dto;
		}
	}

	@Override
	public DTO auditTagReaderUseInfo(TagReaderUseDTO tagReaderUseDTO) {
		log.info("------------[auditTagReaderUseInfo]TagReaderUseResource-------------");
		try {
			if (StringUtil.isNullorEmpty(tagReaderUseDTO.getTagReaderUseNo()) ||
					StringUtil.isNullorEmpty(tagReaderUseDTO.getTagReaderNo())) {
				return new DTO(RetCodeEnum.PARAM_LACK);
			}
			return tagReaderUseService.auditTagReaderUseInfo(tagReaderUseDTO);
		} catch (Exception e) {
			log.error("手持机领用记录审核失败", e);
			return new DTO(RetCodeEnum.EXCEPTION);
		}
	}

	@Override
	public DTO delTagReaderUseInfoByTagReaderUseNo(String tagReaderUseNo) {
		log.info("------------[delTagReaderUseInfoByTagReaderUseNo]TagReaderUseResource-------------");
		try {
			if (StringUtil.isNullorEmpty(tagReaderUseNo)) {
				return new DTO(RetCodeEnum.FAIL);
			}
			return tagReaderUseService.delTagReaderUseInfoByTagReaderUseNo(tagReaderUseNo);
		} catch (Exception e) {
			log.error("删除手持机领用记录失败", e);
			return new DTO(RetCodeEnum.EXCEPTION);
		}
	}

}
