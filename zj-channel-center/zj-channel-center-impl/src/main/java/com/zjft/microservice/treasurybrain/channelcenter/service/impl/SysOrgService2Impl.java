package com.zjft.microservice.treasurybrain.channelcenter.service.impl;

import com.zjft.microservice.treasurybrain.channelcenter.repository.SysOrgMapper;
import com.zjft.microservice.treasurybrain.channelcenter.service.SysOrgService2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author 崔耀中
 * @since 2020-01-06
 */
@Slf4j
@Service
public class SysOrgService2Impl implements SysOrgService2 {

	@Resource(name = "sysOrgMapperChannel")
	private SysOrgMapper sysOrgMapper;

	@Override
	public int checkPermissionByOrgNo(String userOrgNo, String authOrgNo) {
		return sysOrgMapper.checkPermissionByOrgNo(userOrgNo, authOrgNo);
	}

	@Override
	public int checkPermissionByUsername(String username, String authOrgNo) {
		return sysOrgMapper.checkPermissionByUsername(username, authOrgNo);
	}

}
