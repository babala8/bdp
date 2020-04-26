package com.zjft.microservice.treasurybrain.managecenter.web_inner;

import com.zjft.microservice.treasurybrain.managecenter.service.CassetteInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author 崔耀中
 * @since 2020-01-06
 */
@Slf4j
@RestController
public class CassetteBagInfoInnerResourceImpl implements CassetteBagInfoInnerResource{

	@Resource
	CassetteInfoService cassetteInfoService;

	@Override
	public int updateStatusByNo(Map<String,Object> map){
		return cassetteInfoService.updateStatusByNo(map);
	}


}
