package com.baozi.pmsg.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author: baozi
 * @Date: 2019/6/11 10:05
 * @Description:
 */
@Data
@Component
@ConfigurationProperties(prefix = "socket-io")
public class SocketIoProperties {

	/**
	 * socket端口
	 */
	private Integer socketPort;

	/**
	 * Ping消息间隔（毫秒）
	 */
	private Integer pingInterval;

	/**
	 * Ping消息超时时间（毫秒）
	 */
	private Integer pingTimeout;

	/**
	 * APK文件访问URL前缀
	 */
	private String apkUrlPrefix;

}
