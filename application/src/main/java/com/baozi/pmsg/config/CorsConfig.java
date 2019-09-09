package com.baozi.pmsg.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/***
 * @author: baozi
 * @date: 2019/8/16 15:54
 * @description: 解决跨域,打开后静态页面不正常,注释掉
 */
@ConditionalOnProperty(value = "cors.enable", havingValue = "true")
@Configuration
@EnableWebMvc
@CrossOrigin
public class CorsConfig implements WebMvcConfigurer {

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**").allowedOrigins("*").allowedMethods("PUT", "DELETE", "GET", "POST");
	}

}
