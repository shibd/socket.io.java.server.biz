package com.dfocus.pmsg.client;

/**
 * @author: baozi
 * @date: 2019/8/9 14:56
 * @description:
 */
public class Message {

	private String from;

	private String text;

	public String getText() {
		return text;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public void setText(String text) {
		this.text = text;
	}

}
