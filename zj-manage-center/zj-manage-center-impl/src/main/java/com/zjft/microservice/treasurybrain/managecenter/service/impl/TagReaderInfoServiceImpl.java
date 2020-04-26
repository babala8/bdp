package com.zjft.microservice.treasurybrain.managecenter.service.impl;

import com.zjft.microservice.treasurybrain.managecenter.dto.TagReaderInfoDTO;
import com.zjft.microservice.treasurybrain.managecenter.mapstruct.TagReaderInfoConverter;
import com.zjft.microservice.treasurybrain.managecenter.po.TagReaderInfoPO;
import com.zjft.microservice.treasurybrain.managecenter.repository.TagReaderInfoMapper;
import com.zjft.microservice.treasurybrain.managecenter.service.TagReaderInfoService;
import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.PageDTO;
import com.zjft.microservice.treasurybrain.common.util.RetCodeEnum;
import com.zjft.microservice.treasurybrain.common.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class TagReaderInfoServiceImpl implements TagReaderInfoService {

	@Resource
	private TagReaderInfoMapper tagReaderInfoMapper;

	/**
	 * 增加标签读写器信息
	 */
	@Override
	public DTO addTagReaderInfo(TagReaderInfoDTO tagReaderInfoDTO) {
		log.info("------------[addTagReaderInfo]TagRedaerInfoService-------------");
		//如果不传ClrCenterNo，则设为默认值010211
		if (StringUtil.isNullorEmpty(tagReaderInfoDTO.getClrCenterNo())) {
			DTO dto = new DTO();
			dto.setRetCode(RetCodeEnum.PARAM_LACK.getCode());
			dto.setRetMsg("所在金库不能为空");
			return dto;
		}

		if(0<tagReaderInfoMapper.qryExistsByNo(tagReaderInfoDTO.getTagReaderNo())){
			DTO dto = new DTO();
			dto.setRetCode(RetCodeEnum.AUDIT_OBJECT_EXIST.getCode());
			dto.setRetMsg("该变得的读写器已存在");
			return dto;
		}

		//TAG_READER_NO编码规则：服务站编号+2位顺序号
//		//查找最大标签读写器编号
//		String tagReaderNoMax = tagReaderInfoMapper.qryTagReaderNoMax(tagReaderInfoDTO.getClrCenterNo());
//		String tagReaderNo;
//		if (StringUtil.isNullorEmpty(tagReaderNoMax)) {
//			tagReaderNo = tagReaderInfoDTO.getClrCenterNo() + "01";
//		} else {
//			String tmpString = tagReaderNoMax.substring(tagReaderNoMax.length() - 2, tagReaderNoMax.length());
//			Integer tmpInt = Integer.parseInt(tmpString) + 1;
//			//最新2位顺序号newNo
//			String newNo = String.format("%02d", tmpInt);
//			tagReaderNo = tagReaderInfoDTO.getClrCenterNo() + newNo;
//		}
//		tagReaderInfoDTO.setTagReaderNo(tagReaderNo);
		//当没有GPRS模块时，下列字段为空
		if (tagReaderInfoDTO.getWhetherGprsModule() == 0) {
			tagReaderInfoDTO.setGprsVolThreshold(null);
			tagReaderInfoDTO.setGprsVolMaxThreshold(null);
			tagReaderInfoDTO.setGprsVolMinThreshold(null);
			tagReaderInfoDTO.setGprsVolOffset(null);
			tagReaderInfoDTO.setTimingTaskInterval(null);
			tagReaderInfoDTO.setGprsMonthlyFreeFlow(null);
		}
		//读写器类型为固定型时location存在
		if (tagReaderInfoDTO.getReaderType() != 1) {
			tagReaderInfoDTO.setLocation("");
		}
		TagReaderInfoPO tagReaderInfoPO = TagReaderInfoConverter.INSTANCE.dto2domain(tagReaderInfoDTO);
		int i = tagReaderInfoMapper.insert(tagReaderInfoPO);
		if (i == 1) {
			return new DTO(RetCodeEnum.SUCCEED);
		}
		return new DTO(RetCodeEnum.FAIL);
	}

	/**
	 * 分页查询标签读写器信息
	 */
	@Override
	public PageDTO<TagReaderInfoDTO> queryTagReaderInfoByPage(Map<String, Object> paramsMap) {
		log.info("------------[queryTagReaderInfoByPage]TagRedaerInfoService-------------");
		PageDTO<TagReaderInfoDTO> pageDTO = new PageDTO<>(RetCodeEnum.SUCCEED);

		int curPage = StringUtil.objectToInt(paramsMap.get("curPage"));
		int pageSize = StringUtil.objectToInt(paramsMap.get("pageSize"));
		paramsMap.put("startRow", pageSize * (curPage - 1));
		paramsMap.put("endRow", pageSize * curPage);

		int totalRow = tagReaderInfoMapper.qryTotalRow(paramsMap);
		int totalPage = totalRow % pageSize == 0 ? totalRow / pageSize : totalRow / pageSize + 1;
		List<TagReaderInfoPO> doList = tagReaderInfoMapper.queryTagReaderInfoByPage(paramsMap);
		List<TagReaderInfoDTO> dtoList = TagReaderInfoConverter.INSTANCE.domain2dto(doList);
		pageDTO.setTotalRow(totalRow);
		pageDTO.setTotalPage(totalPage);
		pageDTO.setCurPage(curPage);
		pageDTO.setPageSize(pageSize);
		pageDTO.setRetList(dtoList);
		return pageDTO;
	}

	/**
	 * 修改标签读写器信息
	 */
	@Override
	public DTO modTagReaderInfo(TagReaderInfoDTO tagReaderInfoDTO) {
		log.info("------------[modTagReaderInfo]TagRedaerInfoService-------------");
		//当领用状态为已申请或已领用时不得修改
		Integer status = tagReaderInfoDTO.getStatus();
		if (status == 1 || status == 4 || status == 5) {
			//当没有GPRS模块时，下列字段为空
			if (tagReaderInfoDTO.getWhetherGprsModule() == 0) {
				tagReaderInfoDTO.setGprsVolThreshold(null);
				tagReaderInfoDTO.setGprsVolMaxThreshold(null);
				tagReaderInfoDTO.setGprsVolMinThreshold(null);
				tagReaderInfoDTO.setGprsVolOffset(null);
				tagReaderInfoDTO.setTimingTaskInterval(null);
				tagReaderInfoDTO.setGprsMonthlyFreeFlow(null);
			}
			//读写器类型为固定型时location存在
			if (tagReaderInfoDTO.getReaderType() != 1) {
				tagReaderInfoDTO.setLocation("");
			}
			TagReaderInfoPO tagReaderInfoPO = TagReaderInfoConverter.INSTANCE.dto2domain(tagReaderInfoDTO);
			int x = tagReaderInfoMapper.updateByPrimaryKeySelective(tagReaderInfoPO);
			if (x == 1) {
				return new DTO(RetCodeEnum.SUCCEED);
			}
		}
		return new DTO(RetCodeEnum.FAIL);
	}

	/**
	 * 删除标签读写器信息
	 */
	@Override
	public DTO delTagReaderInfoByNo(String no) {
		log.info("------------[delTagReaderInfoByNo]TagRedaerInfoService-------------");
		//当领用状态为已申请或已领用时不得删除
		Integer status = tagReaderInfoMapper.qryStatusByNo(no);
		if (StringUtil.isNullorEmpty(StringUtil.parseString(status))) {
			return new DTO(RetCodeEnum.FAIL);
		} else if (status == 1 || status == 4 || status == 5) {
			int y = tagReaderInfoMapper.deleteByPrimaryKey(no);
			if (y == 1) {
				return new DTO(RetCodeEnum.SUCCEED);
			}
		}
		return new DTO(RetCodeEnum.FAIL);
	}

	@Override
	public int queryTagReaderStatusByNo(String tagReaderNo){
		return tagReaderInfoMapper.qryStatusByNo(tagReaderNo);
	}
}
