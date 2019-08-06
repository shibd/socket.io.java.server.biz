package com.dfocus.pmsg.config;

import lombok.ToString;

import java.security.Principal;

/**
 * @auther: baozi
 * @date: 2019/8/5 10:44
 * @description:
 */
@ToString
class User implements Principal {

	private final String name;

	public User(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}

}
