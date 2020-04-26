package com.zjft.microservice.treasurybrain;

import com.github.xiaoymin.swaggerbootstrapui.annotations.EnableSwaggerBootstrapUI;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@ComponentScan("com.zjft.microservice")
@EnableAutoConfiguration
@Configuration
@EnableSwagger2
@EnableSwaggerBootstrapUI
@EnableTransactionManagement
public class TreasuryBrainApplication {

	public static void main(String[] args) {
		SpringApplication.run(TreasuryBrainApplication.class, args);
	}

}
