package com.zjft.microservice.treasurybrain.productcenter.web_inner;

import com.zjft.microservice.treasurybrain.productcenter.service.ServiceCenterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@Slf4j
public class ServiceInnerResourceImpl implements ServiceInnerResource {
	@Resource
	private ServiceCenterService productService;

	@Override
	public Integer qryNextByOperateType(String operateType) {
		return productService.qryNextByOperateType(operateType);
	}

	@Override
	public String qryNameByOperateType(String operateType) {
		return productService.qryNameByOperateType(operateType);
	}

	@Override
	public int qryExist(Map<String, Object> map) {
		return productService.qryExist(map);
	}

	@Override
	public Integer isStatusConvertMatch(Integer taskType, Integer curStatus, String operateType) {
		return productService.isStatusConvertMatch(taskType,curStatus,operateType);
	}

	@Override
	public String mayStatusConvert(Integer nextStatus, Integer serviceNo) {
		return productService.mayStatusConvert(nextStatus,serviceNo);
	}

	@Override
	public List<String> mayStatusConverts(String operateType, Integer serviceNo) {
		return productService.mayStatusConverts(operateType,serviceNo);
	}

	@Override
	public String getJSONTemplate(int serviceNo, String operateType) {
		return productService.getJSONTemplate(serviceNo,operateType);
	}

	@Override
	public List<String> selectCurStatus(Integer taskType, String operateType) {
		return productService.selectCurStatus(taskType,operateType);
	}

	@Override
	public boolean isConvert(String taskNo, String operateType, Set set) {
		return productService.isConvert(taskNo,operateType, set);
	}

//	@Override
//	public int isStatusConvert(String taskNo, String operateType) {
//		return productService.isStatusConvert(taskNo,operateType);
//	}
}

