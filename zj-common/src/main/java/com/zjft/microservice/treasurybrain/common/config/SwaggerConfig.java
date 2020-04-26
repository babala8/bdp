package com.zjft.microservice.treasurybrain.common.config;

import com.github.xiaoymin.swaggerbootstrapui.annotations.EnableSwaggerBootstrapUI;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 杨光
 * @since 2019-05-04
 */
@Configuration
@ConditionalOnMissingClass(value = {"com.zjft.microservice.authadmin.config.SwaggerConfig"})
@EnableSwagger2
@EnableSwaggerBootstrapUI
public class SwaggerConfig {

	@Value("${spring.application.name:}")
	private String name;

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.useDefaultResponseMessages(false)
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.zjft.microservice"))
				.paths(PathSelectors.regex("^(?!auth).*$"))
				.build()
				.securitySchemes(securitySchemes())
				.securityContexts(securityContexts())
				.apiInfo(apiInfo())
				;
	}

	private List<ApiKey> securitySchemes() {
		List<ApiKey> list = new ArrayList<>();
		list.add(new ApiKey("Authorization", "Authorization", "header"));
		return list;
	}

	private List<SecurityContext> securityContexts() {
		List<SecurityContext> list = new ArrayList<>();
		SecurityContext build = SecurityContext.builder()
				.securityReferences(defaultAuth())
				.forPaths(PathSelectors.regex("^(?!auth).*$"))
				.build();
		list.add(build);
		return list;
	}

	private List<SecurityReference> defaultAuth() {
		AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
		authorizationScopes[0] = new AuthorizationScope("global", "accessEverything");
		SecurityReference authorization = new SecurityReference("Authorization", authorizationScopes);

		List<SecurityReference> list = new ArrayList<>();
		list.add(authorization);
		return list;
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title(name).version("v2.0").build();
	}
}
