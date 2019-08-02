package com.dfocus.pmsg.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @Auther: baozi
 * @Date: 2019/6/23 17:28
 * @Description:
 */
@EnableSwagger2
@MapperScan(AppConfiguration.DAO_PACKAGE)
@EnableFeignClients(AppConfiguration.INTERGRATION_PACKAGE)
@EnableDiscoveryClient
@Configuration
public class AppConfiguration {

	final static String DAO_PACKAGE = "com.dfocus.pmsg.common.dao";

	final static String INTERGRATION_PACKAGE = "com.dfocus.pmsg.integration";

	/**
	 * 调度提出配置,可以开关控制开启
	 */
	@ConditionalOnProperty(value = "app.scheduling.enable", havingValue = "true", matchIfMissing = true)
	@Configuration
	@EnableScheduling
	public static class SchedulingConfiguration {

	}

}