package com.zjft.microservice.treasurybrain.datainsight.service.impl;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.ListDTO;
import com.zjft.microservice.treasurybrain.common.util.RetCodeEnum;
import com.zjft.microservice.treasurybrain.common.util.ServletRequestUtil;
import com.zjft.microservice.treasurybrain.datainsight.domain.ChartSubject;
import com.zjft.microservice.treasurybrain.datainsight.domain.InsightUserConfigDO;
import com.zjft.microservice.treasurybrain.datainsight.dto.InsightUserConfigDTO;
import com.zjft.microservice.treasurybrain.datainsight.dto.SubjectDTO;
import com.zjft.microservice.treasurybrain.datainsight.mapstruct.ChartSubjectConverter;
import com.zjft.microservice.treasurybrain.datainsight.mapstruct.InsightUserConfigConverter;
import com.zjft.microservice.treasurybrain.datainsight.repository.InsightUserConfigMapper;
import com.zjft.microservice.treasurybrain.datainsight.service.UserViewService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @author 杨光
 * @author 常健
 */
@Slf4j
@Service
public class UserViewServiceImpl implements UserViewService {

	private final InsightUserConfigMapper insightUserConfigMapper;

	@Autowired
	public UserViewServiceImpl(InsightUserConfigMapper insightUserConfigMapper) {
		this.insightUserConfigMapper = insightUserConfigMapper;
	}

	@Override
	public DTO updateUsrDefaultViewByUsername(InsightUserConfigDTO insightUserConfigDTO) {
		log.info("------------[updateUsrDefaultViewByUsername]userViewService-------------");
		// 修改之前查询是否有用户自定义的记录如果没有则新增
		// 如果返回的记录条数为0，则进行insert，否则update
		try {
			String username = initUserViewBySubjectID(insightUserConfigDTO.getSubjectId());
			// 查询结果：用户记录存在进行update，update失败返回FAIL，成功返回SUCCEED
			int i = insightUserConfigMapper.updateUsrDefaultViewByUsername(username,insightUserConfigDTO.getSubjectId(),insightUserConfigDTO.getUserDefaultView());
			if (i != 1) {
				return new DTO(RetCodeEnum.FAIL);
			}
			return new DTO(RetCodeEnum.SUCCEED);
		} catch (Exception e) {
			log.error("[更新用户驾驶舱配置异常]：", e);
			return new DTO(RetCodeEnum.FAIL);
		}

	}


	@Override
	public InsightUserConfigDTO selectUserConfigBySubjectId(int subjectId) {
		log.info("------------[InsightUserConfigDTO]userViewService-------------");
		try {
			String username = initUserViewBySubjectID(subjectId);
			// 通过用户名来获取用户配置
			InsightUserConfigDO insightUserConfigDO = insightUserConfigMapper.selectUserConfigBySubjectId(username,subjectId);
			// DO对象转DTO对象
			InsightUserConfigDTO insightUserConfigDTO = InsightUserConfigConverter.INSTANCE.configToDto(insightUserConfigDO);
			log.info("[查询用户配置]: {}", insightUserConfigDTO.getUserDefaultView());
			// 设置DTO的响应码
			insightUserConfigDTO.setResult(RetCodeEnum.SUCCEED);
			return insightUserConfigDTO;
		} catch (Exception e) {
			log.error("[获取用户自定义驾驶舱和大屏配置异常]："+e.getMessage(), e);
			return new InsightUserConfigDTO(RetCodeEnum.FAIL);
		}

	}

	/**
	 * 初始化用户记录
	 */
	private String initUserViewBySubjectID(int subjectId) {
		String username = ServletRequestUtil.getUsername();
		int x = insightUserConfigMapper.queryRowByUsername(username,subjectId);
		if (x == 0) {
			// 查询结果：用户记录不存在，进行insert（通过insert配合update实现用户记录的新增）
			int y = insightUserConfigMapper.insertUserConfigByUsername(username,subjectId);
			if (y != 1) {
				new DTO(RetCodeEnum.FAIL);
			}
		}
		return username;
	}

	@Override
	public ListDTO<SubjectDTO> getUserSubjectsByUsername() {
		log.info("------------[SubjectDTO]userViewService-------------");
		try {
			ListDTO listDTO = new ListDTO<>(RetCodeEnum.SUCCEED);
			String username =  ServletRequestUtil.getUsername();
			// 通过用户名来获取用户配置
			List<ChartSubject> chartSubjectDOList = insightUserConfigMapper.getUserSubjectsByUsername(username);
			// DO对象转DTO对象
			List<SubjectDTO> subjectDTOS = ChartSubjectConverter.INSTANCE.subjectToDto(chartSubjectDOList);
			listDTO.setRetList(subjectDTOS);
			return listDTO;
		} catch (Exception e) {
			log.error("[获取用户主题异常]："+e.getMessage(), e);
			return null;
		}
	}



}
