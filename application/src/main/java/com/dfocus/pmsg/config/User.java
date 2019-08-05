package com.dfocus.pmsg.config;

import java.security.Principal;

/**
 * @auther: baozi
 * @date: 2019/8/5 10:44
 * @description:
 */
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
