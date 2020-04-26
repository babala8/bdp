package com.zjft.microservice.treasurybrain.pushserver.websocket;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zjft.microservice.treasurybrain.common.dto.RoleDTO;
import com.zjft.microservice.treasurybrain.common.dto.UserDTO;
import com.zjft.microservice.treasurybrain.pushserver.config.WebSocketHttpSessionConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author 杨光
 * @since 2019-06-18
 */
@Slf4j
@ServerEndpoint(value = "/push-server/v2/websocket", configurator = WebSocketHttpSessionConfig.class)
@Component
public class WebSocketServer {


	/**
	 * 静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
	 */
	private static int onlineCount = 0;

	/**
	 * concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
	 */
	private static CopyOnWriteArraySet<WebSocketServer> webSocketSet = new CopyOnWriteArraySet<WebSocketServer>();

	/**
	 * 与某个客户端的连接会话，需要通过它来给客户端发送数据
	 */
	private Session session;

	/**
	 * 接收username
	 */
	private String username = "";

	/**
	 * 接收角色
	 */
	private JSONArray authorities = null;

	/**
	 * 连接建立成功调用的方法
	 */
	@OnOpen
	public void onOpen(Session session, EndpointConfig config) {

		Map<String, List<String>> requestParameterMap = session.getRequestParameterMap();
		Principal userPrincipal = session.getUserPrincipal();
		if (userPrincipal instanceof UserDTO) {
			// 获取权限框架验证过的的身份信息，框架自动通过请求头token验证
			log.debug("[userPrincipal instanceof UserDTO]: true");
			UserDTO user = (UserDTO) userPrincipal;
			username = user.getUsername();
			authorities = new JSONArray();
			for (RoleDTO roleDTO : user.getRoleList()) {
				authorities.add(roleDTO.getName());
			}
			openSuccess(session);
			return;

		} else if (requestParameterMap.size() != 0) {
			// TODO: 用于与第三方工具测试，测试完毕后需要注释掉
			// fixme: 从请求参数token中，直接反序列化token，没有做身份验证
			List<String> tokenList = requestParameterMap.get("token");
			if (tokenList != null) {
				String token = tokenList.get(0);
				log.debug("[requestParameterMap token ]: {}", token);
				String authToken = JwtHelper.decode(token).getClaims();
				JSONObject jsonObject = JSONObject.parseObject(authToken);
				username = jsonObject.getString("user_name");
				authorities = jsonObject.getJSONArray("authorities");
				openSuccess(session);
				return;
			}
		}
		addOnlineCount();
		try {
			log.info("[session close because without token]");
			session.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void openSuccess(Session session) {
		webSocketSet.add(this);
		this.session = session;
		addOnlineCount();
		log.info("新会话加入，当前在线人数为" + getOnlineCount());
		try {
			sendMessage("连接成功");
		} catch (IOException e) {
			log.error("websocket IO异常", e);
		}
	}

	/**
	 * 连接关闭调用的方法
	 */
	@OnClose
	public void onClose() {
		// 从set中删除
		webSocketSet.remove(this);
		// 在线数减1
		subOnlineCount();
		log.info("有一连接关闭！当前在线人数为" + getOnlineCount());
	}

	/**
	 * 收到客户端消息后调用的方法
	 *
	 * @param message 客户端发送过来的消息
	 */
	@OnMessage
	public void onMessage(String message, Session session) {
		try {
			session.getBasicRemote().sendText(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
		//群发消息
//		for (WebSocketServer item : webSocketSet) {
//			try {
//				item.sendMessage(message);
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
	}

	/**
	 * 发生错误
	 */
	@OnError
	public void onError(Session session, Throwable error) {
		log.error("发生错误");
		error.printStackTrace();
	}

	/**
	 * 实现服务器主动推送
	 */
	public void sendMessage(String message) throws IOException {
		this.session.getBasicRemote().sendText(message);
	}


	/**
	 * 群发自定义消息
	 */
	public static void sendInfo2User(String message, String username) {
		log.info("推送消息到[{}]，推送内容: {}", username == null ? "all" : username, message);
		for (WebSocketServer item : webSocketSet) {
			log.info("WebSocketServer item : {}", item.username);
			try {
				if (username == null) {
					item.sendMessage(message);
				} else if (item.username.equals(username)) {
					item.sendMessage(message);
				}
			} catch (IOException e) {
				log.info("sendInfo exception ", e);
			}
		}
	}

	/**
	 * 群发自定义消息
	 */
	public static void sendInfo2Roles(String message, JSONArray authorities) {
		log.info("推送消息到[{}]，推送内容: {}", authorities == null ? "all" : authorities, message);
		for (WebSocketServer item : webSocketSet) {
			log.info("WebSocketServer item : {} {}", item.username, item.authorities);
			try {
				if (authorities == null) {
					item.sendMessage(message);
				} else if (item.authorities.size() != 0) {
					for (Object itemRole : item.authorities) {
						for (Object sendRole : authorities) {
							if (itemRole.equals(sendRole)) {
								item.sendMessage(message);
							}
						}
					}
				}
			} catch (IOException e) {
				log.info("sendInfo exception ", e);
			}
		}
	}

	public static synchronized int getOnlineCount() {
		return onlineCount;
	}

	public static synchronized void addOnlineCount() {
		WebSocketServer.onlineCount++;
	}

	public static synchronized void subOnlineCount() {
		WebSocketServer.onlineCount--;
	}
}
