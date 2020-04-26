package com.zjft.microservice.treasurybrain.business.service;

import com.zjft.microservice.treasurybrain.business.domain.AddnotesPlan;
import com.zjft.microservice.treasurybrain.business.dto.DevForChooseDTO;

import java.util.List;


public interface DevChooseService {

	List<DevForChooseDTO> qryAddnotesPlanDevsForKey(AddnotesPlan addnotesPlan, String param);

	List<DevForChooseDTO> qryAddnotesPlanDevsForMaintain(AddnotesPlan addnotesPlan, String param);

	List<DevForChooseDTO> qryAddnotesPlanDevsForPredict(AddnotesPlan addnotesPlan, String param);

	List<DevForChooseDTO> qryAddnotesPlanDevsForRunPlan(AddnotesPlan addnotesPlan, String param);

}
