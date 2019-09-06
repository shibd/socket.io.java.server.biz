package com.baozi.pmsg.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author baozi
 * @Date: 2019/6/23 17:28
 * @Description:
 */
@EnableSwagger2
@MapperScan(AppConfiguration.DAO_PACKAGE)
@EnableFeignClients(AppConfiguration.INTERGRATION_PACKAGE)
@EnableDiscoveryClient
@Configuration
public class AppConfiguration {

	final static String DAO_PACKAGE = "com.baozi.pmsg.common.dao";

	final static String INTERGRATION_PACKAGE = "com.baozi.pmsg.integration";

	/**
	 * 调度提出配置,可以开关控制开启
	 */
	@ConditionalOnProperty(value = "app.scheduling.enable", havingValue = "true", matchIfMissing = true)
	@Configuration
	@EnableScheduling
	public static class SchedulingConfiguration {

	}

	/**
	 * swagger配置
	 * @return
	 */
	@Bean
	public Docket createRestApi() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.baozi.pmsg.controller")).paths(PathSelectors.any())
				.build();
	}

}
