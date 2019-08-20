package com.dfocus.pmsg.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author: baozi
 * @date: 2019/8/20 16:45
 * @description:
 */
@Slf4j
@Component
public class JwtOncePerRequestFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
			FilterChain filterChain) throws ServletException, IOException {

		boolean b = httpServletRequest.getRequestURI().startsWith("/msg-center/websocket");
		System.out.println(b);
		log.info("jinlaile");
		// UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new
		// UsernamePasswordAuthenticationToken(
		// "", "");
		// usernamePasswordAuthenticationToken.setAuthenticated(true);
		// SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

		// throw new RuntimeException("sdsds");
		filterChain.doFilter(httpServletRequest, httpServletResponse);
	}

}
