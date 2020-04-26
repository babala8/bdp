package com.zjft.microservice.treasurybrain.pushserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @author 杨光
 * @since 2019-06-18
 */
@Configuration
public class WebSocketConfig  {

	/**
	 * 如果您想在使用嵌入式容器的Spring引导应用程序中使用@ServerEndpoint
	 * 则必须声明单例Bean ServerEndpointExporter
	 * <p>
	 * 此对象向底层WebSocket容器注册任何@ServerEndpoint注释bean
	 * 当部署到独立的servlet容器时，此角色由servlet容器初始化器执行，并不需要ServerEndpointExporter bean。
	 */
	@Bean
	public ServerEndpointExporter serverEndpointExporter() {
		return new ServerEndpointExporter();
	}
}
