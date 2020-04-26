package com.zjft.microservice.treasurybrain.common.config;

import com.zjft.microservice.commons.config.MybatisConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author 杨光
 * @since 2019-06-14
 */
@Configuration
@Import({MybatisConfig.class, SwaggerConfig.class})
public class MultiCommonConfig {
}
