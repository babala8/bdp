package com.zjft.microservice.treasurybrain.channelcenter.service;

public interface SysOrgService2 {

	int checkPermissionByOrgNo(String userOrgNo,String authOrgNo);

	int checkPermissionByUsername(String username,String authOrgNo);

}
