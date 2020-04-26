package com.zjft.microservice.treasurybrain.datainsight.web;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.ListDTO;
import com.zjft.microservice.treasurybrain.common.util.RetCodeEnum;
import com.zjft.microservice.treasurybrain.common.util.StringUtil;
import com.zjft.microservice.treasurybrain.datainsight.dto.InsightUserConfigDTO;
import com.zjft.microservice.treasurybrain.datainsight.dto.SubjectDTO;
import com.zjft.microservice.treasurybrain.datainsight.service.UserViewService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 杨光
 * @author 常健
 */
@Slf4j
@RestController
public class UserViewResourceImpl implements UserViewResource {

	private final UserViewService userViewService;

	@Autowired
	public UserViewResourceImpl(UserViewService userViewService) {
		this.userViewService = userViewService;
	}

	@Override
	public InsightUserConfigDTO getUserConfig(int subjectId) {
		return userViewService.selectUserConfigBySubjectId(subjectId);

	}

	/**
	 * 根据用户名修改驾驶舱配置 UsrDefaultView
	 *
	 * @param config username、userDefaultView
	 * @return DTO
	 */
	@Override
	public DTO modUsrDefaultView(InsightUserConfigDTO config) {
		try {
			if (StringUtil.isNullorEmpty(config.getUserDefaultView())) {
				return new DTO(RetCodeEnum.FAIL);
			}
			return userViewService.updateUsrDefaultViewByUsername(config);
		} catch (Exception e) {
			log.info("[更新UserDefaultView异常]: ", e);
			return new DTO(RetCodeEnum.FAIL);
		}
	}


	@Override
	public ListDTO<SubjectDTO> getUserSubjects() {
		return userViewService.getUserSubjectsByUsername();
	}

}
