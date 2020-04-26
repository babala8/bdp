package com.zjft.microservice.treasurybrain.datainsight.service;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.ListDTO;
import com.zjft.microservice.treasurybrain.datainsight.dto.InsightUserConfigDTO;
import com.zjft.microservice.treasurybrain.datainsight.dto.SubjectDTO;

import java.util.List;

/**
 *
 * @author 杨光
 * @author 常健
 */
public interface UserViewService {


	DTO updateUsrDefaultViewByUsername(InsightUserConfigDTO insightUserConfigDTO);

	InsightUserConfigDTO selectUserConfigBySubjectId(int subjectId);

	ListDTO<SubjectDTO> getUserSubjectsByUsername();

}
