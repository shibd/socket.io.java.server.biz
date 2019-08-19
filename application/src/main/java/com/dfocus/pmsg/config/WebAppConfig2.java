// package com.dfocus.pmsg.config;
//
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.web.bind.annotation.CrossOrigin;
// import org.springframework.web.cors.CorsConfiguration;
// import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
// import org.springframework.web.filter.CorsFilter;
// import org.springframework.web.servlet.config.annotation.EnableWebMvc;
//
/// **
// * @author: baozi
// * @date: 2019/8/19 10:08
// * @description:
// */
// @Configuration
// @EnableWebMvc
// @CrossOrigin
// public class WebAppConfig2 {
//
// @Bean
// public CorsFilter corsFilter() {
// UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//
// // Allow anyone and anything access. Probably ok for Swagger spec
// CorsConfiguration config = new CorsConfiguration();
// config.setAllowCredentials(true);
// config.addAllowedOrigin("*");
// config.addAllowedHeader("*");
// config.addAllowedMethod("*");
//
// source.registerCorsConfiguration("/msg-center/websocket/**", config);
// return new CorsFilter(source);
// }
//
// }
