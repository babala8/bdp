package com.zjft.microservice.treasurybrain.channelcenter.web_inner;

import com.zjft.microservice.treasurybrain.channelcenter.service.SysOrgService;
import com.zjft.microservice.treasurybrain.channelcenter.service.SysOrgService2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
/**
 * @author 张弛
 * @since 2020-01-06
 */

@Slf4j
@RestController
public class SysOrglnnerResource2Impl implements SysOrglnnerResource2 {

	@Resource
	private SysOrgService2 sysOrgService2;
	
	@Override
	public int checkPermissionByOrgNo (String userOrgNo,String authOrgNo){
		return sysOrgService2.checkPermissionByOrgNo(userOrgNo,authOrgNo);
	}

	@Override
	public int checkPermissionByUsername(String username,String authOrgNo){
		return sysOrgService2.checkPermissionByUsername(username,authOrgNo);
	}

}
