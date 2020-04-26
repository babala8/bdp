package com.zjft.microservice.treasurybrain.pushserver.config;

import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;

/**
 * @author 杨光
 * @since 2019-06-19
 */
public class WebSocketHttpSessionConfig extends ServerEndpointConfig.Configurator {

	@Override
	public void modifyHandshake(ServerEndpointConfig sec,
								HandshakeRequest request,
								HandshakeResponse response) {
		sec.getUserProperties().put("headers", request.getHeaders());
//		sec.getUserProperties().put("parameterMap", request.getParameterMap());
//		sec.getUserProperties().put("userPrincipal", request.getUserPrincipal());
	}


}
