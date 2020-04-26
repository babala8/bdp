package com.zjft.microservice.treasurybrain.channelcenter.web_inner;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;

/**
 * @author 张弛
 */
public interface SysOrglnnerResource2 {

	@ApiOperation(value = "（内部服务）", notes = "（内部服务）")
	int checkPermissionByOrgNo (@Param("userOrgNo") String userOrgNo, @Param("authOrgNo") String authOrgNo);

	@ApiOperation(value = "（内部服务）", notes = "（内部服务）")
	int checkPermissionByUsername(@Param("username") String username, @Param("authOrgNo") String authOrgNo);
}
