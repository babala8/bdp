package com.zjft.microservice.treasurybrain.pushserver.web_inner;

import com.zjft.microservice.treasurybrain.common.dto.DTO;

/**
 * websocket 在线测试工具：http://easyswoole.com/wstool.html
 * 服务地址示例：ws://localhost:8080/push-server/v2/websocket?token=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1NjEwMTk3OTIsInVzZXJfbmFtZSI6InFpYW5nc3VuIiwiYXV0aG9yaXRpZXMiOlsiUk9MRV8xMDAwMSJdLCJqdGkiOiI2YWZkNWY0MC02ZmVhLTRiNDktODVjYy05YjU5Y2I4ZmJmNmIiLCJjbGllbnRfaWQiOiJteS1jbGllbnQtMSIsInNjb3BlIjpbImFsbCJdfQ.BLYC1HBrrRvK5RzYY5jWhUyY7FGML3-_XfAQfYM3BAw
 *
 * @author 韩通
 */
public interface SendInfoInnerResource {

	/**
	 * 推送消息给某用户
	 * @param username 用户编号
	 * @param message 推送信息
	 * @return
	 */
	DTO sendInfo2User(String username, String message);

	/**
	 * 推送消息给某角色
	 * @param roles 角色编号，示例：  role1，role2，role3
	 * @param message 推送信息
	 * @return
	 */
	DTO sendInfo2Roles(String roles,String message);

	/**
	 * 推送消息给所有人
	 * @param message 推送信息
	 * @return
	 */
	DTO sendInfo2All(String message);

}
