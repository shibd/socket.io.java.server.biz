package com.dfocus.pmsg.config;

import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author: baozi
 * @date: 2019/8/16 15:54
 * @description: 解决跨域,打开后静态页面不正常,注释掉
 */
// @Configuration
// @EnableWebMvc
// @CrossOrigin
public class WebAppConfig implements WebMvcConfigurer {

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**").allowedOrigins("*").allowedMethods("PUT", "DELETE", "GET", "POST");
	}

}
